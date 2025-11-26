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

import org.jscience.mathematics.algebra.Module;

/**
 * Represents a vector in a vector space.
 * <p>
 * A vector is an element of a vector space, which is a module over a field.
 * This interface provides operations for vector addition, scalar
 * multiplication,
 * and dot product.
 * </p>
 * 
 * @param <E> the type of scalar elements (e.g., Real, Complex)
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public interface Vector<E> extends Module<Vector<E>, E> {

    /**
     * Returns the dimension of this vector.
     * 
     * @return the number of elements in this vector
     */
    int dimension();

    /**
     * Returns the element at the specified index.
     * 
     * @param index the index of the element to return (0-based)
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    E get(int index);

    /**
     * Returns the sum of this vector and another.
     * 
     * @param other the vector to add
     * @return this + other
     * @throws IllegalArgumentException if dimensions do not match
     */
    Vector<E> add(Vector<E> other);

    /**
     * Returns the difference of this vector and another.
     * 
     * @param other the vector to subtract
     * @return this - other
     * @throws IllegalArgumentException if dimensions do not match
     */
    Vector<E> subtract(Vector<E> other);

    /**
     * Returns the scalar product of this vector.
     * 
     * @param scalar the scalar multiplier
     * @return this * scalar
     */
    Vector<E> multiply(E scalar);

    /**
     * Returns the negation of this vector (-this).
     * 
     * @return -this
     */
    Vector<E> negate();

    /**
     * Returns the dot product of this vector with another vector.
     * 
     * @param other the other vector
     * @return the dot product
     * @throws IllegalArgumentException if the dimensions do not match
     */
    E dot(Vector<E> other);

    /**
     * Returns the norm (length) of this vector.
     * 
     * @return the norm of this vector
     */
    E norm();
}
