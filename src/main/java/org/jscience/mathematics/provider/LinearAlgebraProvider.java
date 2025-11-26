package org.jscience.mathematics.provider;

import org.jscience.mathematics.vector.Matrix;
import org.jscience.mathematics.vector.Vector;

/**
 * Service provider interface for linear algebra operations.
 * <p>
 * Implementations of this interface provide the actual computation logic
 * for vectors and matrices, allowing for different backends (Java CPU, Native
 * BLAS, GPU CUDA).
 * </p>
 * 
 * @param <E> the type of scalar elements
 * 
 * @author Silvere Martin-Michiellot
 * @since 1.0
 */
public interface LinearAlgebraProvider<E> {

    // --- Vector Operations ---

    Vector<E> add(Vector<E> a, Vector<E> b);

    Vector<E> subtract(Vector<E> a, Vector<E> b);

    Vector<E> multiply(Vector<E> vector, E scalar);

    E dot(Vector<E> a, Vector<E> b);

    // --- Matrix Operations ---

    Matrix<E> add(Matrix<E> a, Matrix<E> b);

    Matrix<E> multiply(Matrix<E> a, Matrix<E> b);

    Vector<E> multiply(Matrix<E> a, Vector<E> b);

    Matrix<E> inverse(Matrix<E> a);

    E determinant(Matrix<E> a);

    // Future extensions: solve, eigenvalue, etc.
}
