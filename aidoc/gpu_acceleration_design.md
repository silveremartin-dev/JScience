# GPU Acceleration Design for JScience

## Objective
Enable high-performance linear algebra operations by leveraging GPU acceleration (via CUDA) while maintaining a seamless, robust fallback to CPU. The system should be transparent to the user by default but offer explicit control for advanced use cases.

## Core Concepts

### 1. Compute Context
We will introduce a `ComputeContext` (similar to `MathContext`) to manage execution preferences.

```java
public enum ComputeMode {
    AUTO,   // Use GPU if available and problem size warrants it; else CPU
    CPU,    // Force CPU execution
    GPU     // Force GPU execution (throws exception if unavailable)
}

public class ComputeContext {
    private static final ThreadLocal<ComputeContext> CURRENT = ...;
    
    private final ComputeMode mode;
    // ... other settings like device ID
}
```

### 2. Abstraction Layer
To support multiple backends without cluttering the core logic, we will separate data storage and computation.

#### Data Storage
`DenseVector` and `DenseMatrix` will no longer hold `List<E>` directly. Instead, they will hold a `DataStorage<E>` handle.

```java
interface DataStorage<E> {
    long size();
    E get(long index);
    void set(long index, E value);
    // Bulk access for transfer
    void copyTo(double[] array);
    void copyFrom(double[] array);
}
```

*   **`HeapStorage`**: Standard Java arrays/lists (CPU memory).
*   **`OffHeapStorage`**: Direct ByteBuffers or Unsafe (CPU/Native memory).
*   **`GpuStorage`**: JCuda `Pointer` (GPU memory).

#### Compute Engine
Operations will be delegated to a `LinearAlgebraProvider`.

```java
interface LinearAlgebraProvider<E> {
    Vector<E> add(Vector<E> a, Vector<E> b);
    Matrix<E> multiply(Matrix<E> a, Matrix<E> b);
    // ...
}
```

*   **`JavaProvider`**: Pure Java loops (current implementation).
*   **`NativeProvider`**: BLAS/LAPACK (via JNI/Panama) - *Future*.
*   **`CudaProvider`**: JCuda (CUBLAS/Custom Kernels).

### 3. Implementation Strategy

#### Phase 1: Architecture Refactoring (Current)
Refactor `DenseVector` and `DenseMatrix` to use the Provider pattern.
*   Move current loop-based logic into `JavaLinearAlgebraProvider`.
*   Ensure `DenseVector` delegates all math to the provider.

#### Phase 2: GPU Integration
Implement `CudaLinearAlgebraProvider` using JCuda.
*   **Dependency**: JCuda is already optional in `pom.xml`.
*   **Kernels**: Use CUBLAS for standard operations (SGEMM/DGEMM).
*   **Memory Management**: Implement `GpuStorage` to manage `cuMemAlloc`/`cuMemFree`.

#### Phase 3: Auto-Selection logic
Implement the `AUTO` mode logic.
*   If `size < Threshold` (e.g., 1000 elements), stay on CPU (overhead of transfer > gain).
*   If `size >= Threshold` and GPU available, move to GPU.

## User Experience

### Default (Transparent)
```java
// Uses CPU by default for small data
Vector<Real> v = Vector.of(1, 2, 3); 
Vector<Real> result = v.add(v); 
```

### Explicit Control
```java
// Force GPU context
ComputeContext.enter(ComputeMode.GPU);
try {
    Matrix<Real> m1 = Matrix.random(1000, 1000); // Allocates on GPU
    Matrix<Real> m2 = Matrix.random(1000, 1000);
    Matrix<Real> res = m1.multiply(m2);          // Runs on GPU (CUBLAS)
} finally {
    ComputeContext.exit();
}
```

### Fallback
If `ComputeMode.AUTO` is active and JCuda fails to load (no DLLs, no GPU), the system logs a warning once and falls back to `CPU` mode silently.

## Questions for User
1.  **Memory Management**: GPU memory is manual. Should we use `Cleaner`/`PhantomReference` for auto-cleanup, or explicit `dispose()`? (Auto is friendlier but riskier).
2.  **Data Types**: GPU is best at `float`/`double`. `BigDecimal` on GPU is very slow/complex. Should GPU support be limited to `Real` (approximate) and `Complex`?
