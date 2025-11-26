/*
 * JScience Reimagined - Unified Scientific Computing Framework
 * Copyright (c) 2025 Silvere Martin-Michiellot
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.jscience.mathematics.vector;

import org.jscience.mathematics.algebra.Ring;

/**
 * Represents a matrix of scalar elements.
 * <p>
 * A matrix is a rectangular array of numbers, symbols, or expressions, arranged
 * in rows and columns. Square matrices form a Ring.
 * </p>
 * 
 * @param <E> the type of scalar elements (e.g., Real, Complex)
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public interface Matrix<E> extends Ring<Matrix<E>> {

    /**
     * Returns the number of rows in this matrix.
     * 
     * @return the number of rows
     */
    int rows();

    /**
     * Returns the number of columns in this matrix.
     * 
     * @return the number of columns
     */
    int cols();

    /**
     * Returns the element at the specified row and column.
     * 
     * @param row the row index (0-based)
     * @param col the column index (0-based)
     * @return the element at the specified position
     * @throws IndexOutOfBoundsException if the indices are out of range
     */
    E get(int row, int col);

    /**
     * Returns the sum of this matrix and another.
     * 
     * @param other the matrix to add
     * @return this + other
     * @throws IllegalArgumentException if dimensions do not match
     */
    default Matrix<E> add(Matrix<E> other) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Returns the difference of this matrix and another.
     * 
     * @param other the matrix to subtract
     * @return this - other
     */
    default Matrix<E> subtract(Matrix<E> other) {
        return this.add(other.negate());
    }

    /**
     * Returns the product of this matrix and another.
     * 
     * @param other the matrix to multiply
     * @return this * other
     */
    default Matrix<E> multiply(Matrix<E> other) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Returns the transpose of this matrix.
     * 
     * @return A^T
     */
    default Matrix<E> transpose() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Returns the trace of this matrix (sum of diagonal elements).
     * 
     * @return tr(A)
     */
    default E trace() {
        if (rows() != cols()) {
            throw new ArithmeticException("Trace only defined for square matrices");
        }
        E sum = null; // Initialize with zero from first element's field/ring?
                      // Hard to get zero without an element or class token.
                      // Assuming we can get it from first element or passed in context.
        // Actually, we can't easily get 'zero' generically here without the Ring
        // structure instance.
        // But Matrix extends Ring<Matrix<E>>, not Ring<E>.
        // The elements E must form a Ring.
        // Let's leave it abstract or throw for now if we can't implement default
        // easily.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Returns a submatrix of this matrix.
     * 
     * @param rowStart the first row index (inclusive)
     * @param rowEnd   the last row index (exclusive)
     * @param colStart the first column index (inclusive)
     * @param colEnd   the last column index (exclusive)
     * @return the submatrix
     */
    Matrix<E> getSubMatrix(int rowStart, int rowEnd, int colStart, int colEnd);

    /**
     * Returns the row at the specified index as a vector.
     * 
     * @param row the row index
     * @return the row vector
     */
    Vector<E> getRow(int row);

    /**
     * Returns the column at the specified index as a vector.
     * 
     * @param col the column index
     * @return the column vector
     */
    Vector<E> getColumn(int col);

    /**
     * Returns the transpose of this matrix.
     * 
     * @return the transpose matrix
     */
    Matrix<E> transpose();

    /**
     * Returns the determinant of this matrix.
     * 
     * @return the determinant
     * @throws ArithmeticException if the matrix is not square
     */
    E determinant();

    /**
     * Returns the multiplicative inverse of this matrix.
     * 
     * @return the inverse matrix
     * @throws ArithmeticException if the matrix is singular or not square
     */
    Matrix<E> inverse();

    /**
     * Multiplies this matrix by a vector.
     * 
     * @param vector the vector to multiply
     * @return the resulting vector
     * @throws IllegalArgumentException if dimensions mismatch
     */
    Vector<E> multiply(Vector<E> vector);

    /**
     * Returns the negation of this matrix (-this).
     * 
     * @return -this
     */
    Matrix<E> negate();

    @Override
    default Matrix<E> inverse(Matrix<E> element) {
        return element.negate();
    }

    @Override
    default Matrix<E> operate(Matrix<E> left, Matrix<E> right) {
        return left.multiply(right);
    }
}
