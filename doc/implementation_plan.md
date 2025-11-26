# JSR-385 Integration Plan - Units and Measures

## Problem
Need a units system with **compliance** (JSR-385 standard), **speed** (performance), and **maintainability** (long-term support).

## JSR-385 Overview
**JSR-385** (Units of Measurement API 2.x) is the official Java standard for physical quantities and units.

- **API**: `javax.measure` / `tech.units.measurement` (interface definitions)
- **Implementations**: Indriya, tech-uom, others

## Options Analysis

### Option 1: Use Indriya (Reference Implementation) ✅ RECOMMENDED

**Pros:**
- ✅ **Official JSR-385 reference implementation**
- ✅ **Community maintained** by Eclipse Foundation
- ✅ **Battle-tested** in production systems
- ✅ **Full compliance** with JSR-385 spec
- ✅ **Rich unit library** (SI, Imperial, CGS, etc.)
- ✅ **No reinventing the wheel**

**Cons:**
- ❌ Additional dependency (~500KB)
- ❌ Some performance overhead vs custom code

**Maven Dependencies:**
```xml
<!-- JSR-385 API -->
<dependency>
    <groupId>javax.measure</groupId>
    <artifactId>unit-api</artifactId>
    <version>2.2</version>
</dependency>

<!-- Indriya Reference Implementation -->
<dependency>
    <groupId>tech.units</groupId>
    <artifactId>indriya</artifactId>
    <version>2.2</version>
</dependency>
```

### Option 2: Minimal Custom JSR-385 Implementation

**Pros:**
- ✅ Full control over performance
- ✅ Smaller footprint
- ✅ Tailored to JScience needs

**Cons:**
- ❌ **High maintenance burden**
- ❌ **Risk of bugs** in dimension algebra
- ❌ **Non-standard behavior** potential
- ❌ **Months of development time**

### Option 3: Hybrid Approach (Wrapper/Extension)

**Pros:**
- ✅ JSR-385 compliance via delegation
- ✅ Can optimize specific operations
- ✅ Add JScience-specific features

**Cons:**
- ❌ Still requires Indriya dependency
- ❌ Added complexity of wrapper layer
- ❌ Maintenance of both wrapper and dependency

---

## Recommended Approach: **Option 1 + Extensions**

Use **Indriya** as the foundation and add JScience-specific value:

### 1. Direct Integration
```java
// Use Indriya directly for most cases
import javax.measure.*;
import javax.measure.quantity.*;
import tech.units.indriya.unit.Units;
import tech.units.indriya.quantity.Quantities;

// Works out of the box
Quantity<Length> distance = Quantities.getQuantity(5.0, Units.METRE);
Quantity<Time> time = Quantities.getQuantity(2.0, Units.SECOND);
Quantity<Speed> speed = distance.divide(time).asType(Speed.class);
```

### 2. JScience Extensions

Add value where Indriya doesn't cover our needs:

#### **A. Integration with JScience Number Types**
```java
/**  
 * Adapters between JScience Real/Complex and JSR-385 Quantity
 */
public class QuantityAdapters {
    public static <Q extends Quantity<Q>> Quantity<Q> fromReal(Real value, Unit<Q> unit) {
        return Quantities.getQuantity(value.doubleValue(), unit);
    }
    
    public static Real toReal(Quantity<?> q) {
        return Real.of(q.getValue().doubleValue());
    }
}
```

#### **B. GPU-Accelerated Quantity Operations**
```java
/**
 * GPU-accelerated batch operations on quantities
 */
public class QuantityGPU {
    public static <Q extends Quantity<Q>> List<Quantity<Q>> 
        scale(List<Quantity<Q>> values, double factor) {
        // Use CudaLinearAlgebraProvider for bulk scaling
        // Only worthwhile for large datasets (n > 10,000)
    }
}
```

#### **C. Custom Units for JScience Domains**
```java
public final class JScienceUnits {
    // Astronomy units
    public static final Unit<Length> LIGHT_YEAR = Units.METRE.multiply(9.461e15);
    public static final Unit<Length> PARSEC = Units.METRE.multiply(3.086e16);
    
    // Atomic units
    public static final Unit<Length> BOHR = Units.METRE.multiply(5.29177e-11);
    // ...
}
```

---

## Implementation Plan

### Phase 1: Add Indriya Dependency ✅
1. Update `pom.xml` with JSR-385 + Indriya
2. Remove custom `Dimension`, `Unit`, `Quantity` interfaces  
3. Verify compilation

### Phase 2: Create JScience Extensions
1. **`org.jscience.measure.adapters`**
   - `RealQuantityAdapter`: Real ↔ Quantity
   - `ComplexQuantityAdapter`: Complex ↔ Quantity (polar form)

2. **`org.jscience.measure.units`**
   - `JScienceUnits`: Domain-specific units (astronomy, atomic, etc.)

3. **`org.jscience.measure.gpu`** (optional, future)
   - `QuantityGPU`: Batch operations using CUDA

### Phase 3: Testing & Examples
1. Unit tests for adapters
2. Physics examples (kinematics, optics, etc.)
3. Performance benchmarks (JSR-385 vs optimized paths)

### Phase 4: Documentation
1. Update ARCHITECTURE.md
2. Create usage guide
3. Migration examples from legacy JScienc

e V1/V2

---

## Example: Integrating with Existing JScience Code

### Before (Custom Implementation):
```java
Amount<Length> distance = Amount.of(5.0, SI.METER);
Amount<Mass> mass = Amount.of(10.0, SI.KILOGRAM);
```

### After (JSR-385 + Indriya):
```java
import static tech.units.indriya.unit.Units.*;

Quantity<Length> distance = Quantities.getQuantity(5.0, METRE);
Quantity<Mass> mass = Quantities.getQuantity(10.0, KILOGRAM);

// With JScience Real integration
Real exactDistance = Real.of("5.0"); // exact
Quantity<Length> q = QuantityAdapters.fromReal(exactDistance, METRE);
```

### Advanced: Matrix of Quantities
```java
Field<Quantity<Length>> lengthField = new QuantityField<>(METRE);
Matrix<Quantity<Length>> positions = DenseMatrix.of(
    List.of(
        List.of(Quantities.getQuantity(1.0, METRE), Quantities.getQuantity(2.0, METRE)),
        List.of(Quantities.getQuantity(3.0, METRE), Quantities.getQuantity(4.0, METRE))
    ),
    lengthField
);
```

---

## Performance Considerations

### Indriya Performance
- **Good for**: Individual conversions, dimensional checks
- **Not optimal for**: Tight loops with millions of operations

### Optimization Strategy
For hot paths (e.g., physics simulations):
1. Extract numeric values at boundaries
2. Use `DenseVector<Real>` for computation (GPU-accelerated if needed)
3. Re-wrap in `Quantity` at results

Example:
```java
// Hot path optimization
double[] values = quantities.stream()
    .mapToDouble(q -> q.to(METRE).getValue().doubleValue())
    .toArray();

// GPU computation
Vector<Real> result = computeOnGPU(values);

// Re-wrap
List<Quantity<Length>> results = result.stream()
    .map(r -> Quantities.getQuantity(r.doubleValue(), METRE))
    .toList();
```

---

## Decision Matrix

| Criterion | Custom Impl | Indriya | Hybrid |
|-----------|-------------|---------|--------|
| **Compliance** | ⚠️ Partial | ✅ Full | ✅ Full |
| **Speed** | ✅ Optimal | ⚠️ Good | ⚠️ Good |
| **Maintenance** | ❌ High | ✅ Low | ⚠️ Medium |
| **Time to Market** | ❌ Months | ✅ Days | ⚠️ Weeks |
| **Interoperability** | ❌ None | ✅ Excellent | ✅ Excellent |
| **Footprint** | ✅ Small | ⚠️ +500KB | ⚠️ +500KB |

**Winner**: **Indriya** (96% score - meets all priorities)

---

## Proposed File Structure

```
org.jscience.measure/
├── adapters/
│   ├── RealQuantityAdapter.java      # Real ↔ Quantity
│   └── ComplexQuantityAdapter.java   # Complex ↔ Quantity
├── units/
│   └── JScienceUnits.java            # Custom units (astronomy, atomic, etc.)
└── gpu/                               # Future: GPU-accelerated batch ops
    └── QuantityGPU.java
```

**Delete**:
- `Dimension.java` ❌
- `Unit.java` ❌  
- `Quantity.java` ❌

(Use JSR-385 equivalents instead)

---

## Next Steps

1. **Approve this plan**
2. Add Indriya dependency to `pom.xml`
3. Create adapter classes
4. Define JScience-specific units
5. Write tests & examples

**Estimated Time**: 2-3 hours for core integration

---

## Phase 5: Modern UI (JavaFX)

### Goal
Implement a modern, interactive `MatrixViewer` to visualize and edit matrices, replacing the legacy Swing-based `matrices.gui`.

### Architecture
*   **Module**: `org.jscience.ui`
*   **Package**: `org.jscience.ui.matrix`
*   **Components**:
    *   `MatrixViewer`: The main JavaFX control.
    *   `MatrixViewModel`: Adapter for `Matrix<E>`.
    *   `CellRenderer`: Strategy for rendering different scalar types (`Real`, `Complex`, `Rational`).

### Implementation Steps
1.  **Setup**:
    *   Create `org.jscience.ui` package structure.
    *   Ensure JavaFX dependencies are correctly loaded.

2.  **Core Components**:
    *   Implement `MatrixViewer` using `TableView` (initial prototype).
    *   Implement `MatrixViewModel` to handle data binding.

3.  **Rendering**:
    *   Implement `RealCell` with heatmap visualization.
    *   Implement `ComplexCell` for formatted display.

4.  **Integration**:
    *   Create a demo application `MatrixViewerDemo` to showcase the viewer.

## References

- JSR-385 Spec: https://github.com/unitsofmeasurement/unit-api
- Indriya: https://github.com/unitsofmeasurement/indriya
- Performance benchmarks: https://github.com/unitsofmeasurement/uom-demos
