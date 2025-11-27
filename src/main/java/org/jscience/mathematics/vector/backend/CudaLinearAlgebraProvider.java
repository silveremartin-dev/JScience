package org.jscience.mathematics.vector.backend;

import java.util.ArrayList;
import java.util.List;
import org.jscience.mathematics.algebra.Field;
import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Matrix;
import org.jscience.mathematics.vector.Vector;

import jcuda.Pointer;
import jcuda.Sizeof;
import jcuda.jcublas.JCublas;

/**
 * Implementation of {@link LinearAlgebraProvider} using CUDA (via JCuda).
 * <p>
 * This provider offloads computations to the GPU.
 * Note: This initial implementation performs data transfer (Host <-> Device)
 * for every operation, which is inefficient. Future versions will use
 * persistent GPU memory handles.
 * </p>
 * 
 * @param <E> the type of scalar elements
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public class CudaLinearAlgebraProvider<E> implements LinearAlgebraProvider<E> {

    private final Field<E> field;
    private static boolean isAvailable = false;

    static {
        try {
            JCublas.cublasInit();
            isAvailable = true;
        } catch (Throwable t) {
            isAvailable = false;
            System.err.println("WARNING: CUDA not available: " + t.getMessage());
        }
    }

    public static boolean isAvailable() {
        return isAvailable;
    }

    public CudaLinearAlgebraProvider(Field<E> field) {
        this.field = field;
        if (!isAvailable) {
            throw new UnsupportedOperationException("CUDA is not available on this system");
        }
    }

    private void checkType(Vector<E> v) {
        if (v.dimension() > 0 && !(v.get(0) instanceof Real)) {
            throw new UnsupportedOperationException("CudaLinearAlgebraProvider currently only supports Real numbers");
        }
    }

    @Override
    public Vector<E> add(Vector<E> a, Vector<E> b) {
        checkType(a);
        int n = a.dimension();

        double[] h_a = new double[n];
        double[] h_b = new double[n];
        for (int i = 0; i < n; i++) {
            h_a[i] = ((Real) a.get(i)).doubleValue();
            h_b[i] = ((Real) b.get(i)).doubleValue();
        }

        Pointer d_a = new Pointer();
        Pointer d_b = new Pointer();
        JCublas.cublasAlloc(n, Sizeof.DOUBLE, d_a);
        JCublas.cublasAlloc(n, Sizeof.DOUBLE, d_b);

        JCublas.cublasSetVector(n, Sizeof.DOUBLE, Pointer.to(h_a), 1, d_a, 1);
        JCublas.cublasSetVector(n, Sizeof.DOUBLE, Pointer.to(h_b), 1, d_b, 1);

        JCublas.cublasDaxpy(n, 1.0, d_a, 1, d_b, 1);

        double[] h_result = new double[n];
        JCublas.cublasGetVector(n, Sizeof.DOUBLE, d_b, 1, Pointer.to(h_result), 1);

        JCublas.cublasFree(d_a);
        JCublas.cublasFree(d_b);

        List<E> result = new ArrayList<>(n);
        for (double val : h_result) {
            result.add((E) Real.of(val));
        }
        return new DenseVector<>(result, field);
    }

    @Override
    public Vector<E> subtract(Vector<E> a, Vector<E> b) {
        checkType(a);
        int n = a.dimension();

        double[] h_a = new double[n];
        double[] h_b = new double[n];
        for (int i = 0; i < n; i++) {
            h_a[i] = ((Real) a.get(i)).doubleValue();
            h_b[i] = ((Real) b.get(i)).doubleValue();
        }

        Pointer d_a = new Pointer();
        Pointer d_b = new Pointer();
        JCublas.cublasAlloc(n, Sizeof.DOUBLE, d_a);
        JCublas.cublasAlloc(n, Sizeof.DOUBLE, d_b);

        JCublas.cublasSetVector(n, Sizeof.DOUBLE, Pointer.to(h_a), 1, d_a, 1);
        JCublas.cublasSetVector(n, Sizeof.DOUBLE, Pointer.to(h_b), 1, d_b, 1);

        JCublas.cublasDaxpy(n, -1.0, d_b, 1, d_a, 1);

        double[] h_result = new double[n];
        JCublas.cublasGetVector(n, Sizeof.DOUBLE, d_a, 1, Pointer.to(h_result), 1);

        JCublas.cublasFree(d_a);
        JCublas.cublasFree(d_b);

        List<E> result = new ArrayList<>(n);
        for (double val : h_result) {
            result.add((E) Real.of(val));
        }
        return new DenseVector<>(result, field);
    }

    @Override
    public Vector<E> multiply(Vector<E> vector, E scalar) {
        checkType(vector);
        int n = vector.dimension();
        double alpha = ((Real) scalar).doubleValue();

        double[] h_v = new double[n];
        for (int i = 0; i < n; i++) {
            h_v[i] = ((Real) vector.get(i)).doubleValue();
        }

        Pointer d_v = new Pointer();
        JCublas.cublasAlloc(n, Sizeof.DOUBLE, d_v);
        JCublas.cublasSetVector(n, Sizeof.DOUBLE, Pointer.to(h_v), 1, d_v, 1);

        JCublas.cublasDscal(n, alpha, d_v, 1);

        double[] h_result = new double[n];
        JCublas.cublasGetVector(n, Sizeof.DOUBLE, d_v, 1, Pointer.to(h_result), 1);

        JCublas.cublasFree(d_v);

        List<E> result = new ArrayList<>(n);
        for (double val : h_result) {
            result.add((E) Real.of(val));
        }
        return new DenseVector<>(result, field);
    }

    @Override
    public E dot(Vector<E> a, Vector<E> b) {
        checkType(a);
        int n = a.dimension();

        double[] h_a = new double[n];
        double[] h_b = new double[n];
        for (int i = 0; i < n; i++) {
            h_a[i] = ((Real) a.get(i)).doubleValue();
            h_b[i] = ((Real) b.get(i)).doubleValue();
        }

        Pointer d_a = new Pointer();
        Pointer d_b = new Pointer();
        JCublas.cublasAlloc(n, Sizeof.DOUBLE, d_a);
        JCublas.cublasAlloc(n, Sizeof.DOUBLE, d_b);

        JCublas.cublasSetVector(n, Sizeof.DOUBLE, Pointer.to(h_a), 1, d_a, 1);
        JCublas.cublasSetVector(n, Sizeof.DOUBLE, Pointer.to(h_b), 1, d_b, 1);

        double result = JCublas.cublasDdot(n, d_a, 1, d_b, 1);

        JCublas.cublasFree(d_a);
        JCublas.cublasFree(d_b);

        return (E) Real.of(result);
    }

    @Override
    public Matrix<E> add(Matrix<E> a, Matrix<E> b) {
        if (a.rows() != b.rows() || a.cols() != b.cols()) {
            throw new IllegalArgumentException("Matrix dimensions must match");
        }

        int m = a.rows();
        int n = a.cols();
        int size = m * n;

        // Flatten matrices to column-major
        double[] h_a = new double[size];
        double[] h_b = new double[size];

        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                h_a[j * m + i] = ((Real) a.get(i, j)).doubleValue();
                h_b[j * m + i] = ((Real) b.get(i, j)).doubleValue();
            }
        }

        Pointer d_a = new Pointer();
        Pointer d_b = new Pointer();
        JCublas.cublasAlloc(size, Sizeof.DOUBLE, d_a);
        JCublas.cublasAlloc(size, Sizeof.DOUBLE, d_b);

        JCublas.cublasSetVector(size, Sizeof.DOUBLE, Pointer.to(h_a), 1, d_a, 1);
        JCublas.cublasSetVector(size, Sizeof.DOUBLE, Pointer.to(h_b), 1, d_b, 1);

        // Element-wise addition using DAXPY
        JCublas.cublasDaxpy(size, 1.0, d_a, 1, d_b, 1);

        double[] h_result = new double[size];
        JCublas.cublasGetVector(size, Sizeof.DOUBLE, d_b, 1, Pointer.to(h_result), 1);

        JCublas.cublasFree(d_a);
        JCublas.cublasFree(d_b);

        // Convert back to row-major
        List<List<E>> resultRows = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            List<E> row = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                row.add((E) Real.of(h_result[j * m + i]));
            }
            resultRows.add(row);
        }

        return new DenseMatrix<>(resultRows, field);
    }

    @Override
    public Matrix<E> multiply(Matrix<E> a, Matrix<E> b) {
        if (a.cols() != b.rows()) {
            throw new IllegalArgumentException("Matrix inner dimensions must match");
        }

        int m = a.rows();
        int k = a.cols();
        int n = b.cols();

        // Flatten matrices to column-major
        double[] h_A = new double[m * k];
        double[] h_B = new double[k * n];

        for (int j = 0; j < k; j++) {
            for (int i = 0; i < m; i++) {
                h_A[j * m + i] = ((Real) a.get(i, j)).doubleValue();
            }
        }

        for (int j = 0; j < n; j++) {
            for (int i = 0; i < k; i++) {
                h_B[j * k + i] = ((Real) b.get(i, j)).doubleValue();
            }
        }

        Pointer d_A = new Pointer();
        Pointer d_B = new Pointer();
        Pointer d_C = new Pointer();

        JCublas.cublasAlloc(m * k, Sizeof.DOUBLE, d_A);
        JCublas.cublasAlloc(k * n, Sizeof.DOUBLE, d_B);
        JCublas.cublasAlloc(m * n, Sizeof.DOUBLE, d_C);

        JCublas.cublasSetVector(m * k, Sizeof.DOUBLE, Pointer.to(h_A), 1, d_A, 1);
        JCublas.cublasSetVector(k * n, Sizeof.DOUBLE, Pointer.to(h_B), 1, d_B, 1);

        // C = alpha * A * B + beta * C
        // cublasDgemm(trans_a, trans_b, m, n, k, alpha, A, lda, B, ldb, beta, C, ldc)
        JCublas.cublasDgemm('N', 'N', m, n, k, 1.0, d_A, m, d_B, k, 0.0, d_C, m);

        double[] h_C = new double[m * n];
        JCublas.cublasGetVector(m * n, Sizeof.DOUBLE, d_C, 1, Pointer.to(h_C), 1);

        JCublas.cublasFree(d_A);
        JCublas.cublasFree(d_B);
        JCublas.cublasFree(d_C);

        // Convert back to row-major
        List<List<E>> resultRows = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            List<E> row = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                row.add((E) Real.of(h_C[j * m + i]));
            }
            resultRows.add(row);
        }

        return new DenseMatrix<>(resultRows, field);
    }

    @Override
    public Vector<E> multiply(Matrix<E> a, Vector<E> b) {
        checkType(b);

        int m = a.rows();
        int n = a.cols();

        if (n != b.dimension()) {
            throw new IllegalArgumentException("Dimension mismatch");
        }

        // Flatten matrix to column-major
        double[] h_A = new double[m * n];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                h_A[j * m + i] = ((Real) a.get(i, j)).doubleValue();
            }
        }

        double[] h_x = new double[n];
        for (int i = 0; i < n; i++) {
            h_x[i] = ((Real) b.get(i)).doubleValue();
        }

        Pointer d_A = new Pointer();
        Pointer d_x = new Pointer();
        Pointer d_y = new Pointer();

        JCublas.cublasAlloc(m * n, Sizeof.DOUBLE, d_A);
        JCublas.cublasAlloc(n, Sizeof.DOUBLE, d_x);
        JCublas.cublasAlloc(m, Sizeof.DOUBLE, d_y);

        JCublas.cublasSetVector(m * n, Sizeof.DOUBLE, Pointer.to(h_A), 1, d_A, 1);
        JCublas.cublasSetVector(n, Sizeof.DOUBLE, Pointer.to(h_x), 1, d_x, 1);

        JCublas.cublasDgemv('N', m, n, 1.0, d_A, m, d_x, 1, 0.0, d_y, 1);

        double[] h_y = new double[m];
        JCublas.cublasGetVector(m, Sizeof.DOUBLE, d_y, 1, Pointer.to(h_y), 1);

        JCublas.cublasFree(d_A);
        JCublas.cublasFree(d_x);
        JCublas.cublasFree(d_y);

        List<E> result = new ArrayList<>(m);
        for (double val : h_y) {
            result.add((E) Real.of(val));
        }
        return new DenseVector<>(result, field);
    }

    @Override
    public Matrix<E> inverse(Matrix<E> a) {
        throw new UnsupportedOperationException("GPU inverse not implemented yet");
    }

    @Override
    public E determinant(Matrix<E> a) {
        throw new UnsupportedOperationException("GPU determinant not implemented yet");
    }
}
