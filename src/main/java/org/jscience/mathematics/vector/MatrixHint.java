package org.jscience.mathematics.vector;

/**
 * Hints for matrix optimization and storage selection.
 * <p>
 * These hints guide the system to choose the most efficient internal
 * storage representation based on matrix properties.
 * </p>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public enum MatrixHint {
    /**
     * Standard dense storage (row-major).
     * Use for general matrices with no special structure.
     */
    DENSE,

    /**
     * Sparse storage (map-based).
     * Use for matrices with <10% non-zero elements.
     */
    SPARSE,

    /**
     * Symmetric matrix: A[i,j] = A[j,i].
     * Stores only upper triangle (50% memory savings).
     */
    SYMMETRIC,

    /**
     * Upper triangular: A[i,j] = 0 for i > j.
     * Stores only upper triangle.
     */
    TRIANGULAR_UPPER,

    /**
     * Lower triangular: A[i,j] = 0 for i < j.
     * Stores only lower triangle.
     */
    TRIANGULAR_LOWER,

    /**
     * Diagonal matrix: A[i,j] = 0 for i ≠ j.
     * Stores only diagonal (99%+ memory savings).
     */
    DIAGONAL,

    /**
     * Banded matrix: non-zero only near diagonal.
     * Stores only relevant diagonals.
     */
    BANDED,

    /**
     * Tridiagonal matrix: three diagonals only.
     * Special case of banded with bandwidth=1.
     */
    TRIDIAGONAL,

    /**
     * Let the system automatically detect the best storage.
     * Analyzes data to choose optimal representation.
     */
    AUTO
}
