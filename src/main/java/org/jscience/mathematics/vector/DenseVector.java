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

import java.util.ArrayList;
import java.util.List;
import org.jscience.mathematics.algebra.Field;
import org.jscience.mathematics.context.ComputeContext;
import org.jscience.mathematics.context.ComputeMode;
import org.jscience.mathematics.provider.JavaLinearAlgebraProvider;
import org.jscience.mathematics.provider.LinearAlgebraProvider;

/**
 * A dense vector implementation backed by a list.
 * 
 * @param <E> the type of scalar elements
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public class DenseVector<E> implements Vector<E> {

    private final List<E> elements;
    private final Field<E> field;

    /**
     * Creates a new DenseVector.
     * 
     * @param elements the elements of the vector
     * @param field    the field structure for the elements
     */
    public DenseVector(List<E> elements, Field<E> field) {
        this.elements = new ArrayList<>(elements);
        this.field = field;
    }

    public static <E> DenseVector<E> of(List<E> elements, Field<E> field) {
        return new DenseVector<>(elements, field);
    }

    private LinearAlgebraProvider<E> getProvider() {
        ComputeMode mode = ComputeContext.getCurrent().getMode();

        if (mode == ComputeMode.CPU) {
            return new JavaLinearAlgebraProvider<>(field);
        }

        // Check if we can use GPU (must be Real numbers for now)
        boolean canUseGpu = (field.zero() instanceof org.jscience.mathematics.number.Real);

        if (mode == ComputeMode.GPU) {
            if (!canUseGpu) {
                throw new UnsupportedOperationException("GPU mode currently only supports Real numbers");
            }
            return new org.jscience.mathematics.provider.CudaLinearAlgebraProvider<>(field);
        }

        // AUTO mode
        if (canUseGpu) {
            try {
                return new org.jscience.mathematics.provider.CudaLinearAlgebraProvider<>(field);
            } catch (UnsupportedOperationException e) {
                // Fallback to CPU if CUDA not available
                return new JavaLinearAlgebraProvider<>(field);
            }
        }

        return new JavaLinearAlgebraProvider<>(field);
    }

    @Override
    public int dimension() {
        return elements.size();
    }

    @Override
    public E get(int index) {
        return elements.get(index);
    }

    @Override
    public Vector<E> add(Vector<E> other) {
        return getProvider().add(this, other);
    }

    @Override
    public Vector<E> subtract(Vector<E> other) {
        return getProvider().subtract(this, other);
    }

    @Override
    public Vector<E> multiply(E scalar) {
        return getProvider().multiply(this, scalar);
    }

    @Override
    public Vector<E> scale(E scalar, Vector<E> vector) {
        return vector.multiply(scalar);
    }

    @Override
    public Vector<E> operate(Vector<E> left, Vector<E> right) {
        return left.add(right);
    }

    @Override
    public E dot(Vector<E> other) {
        return getProvider().dot(this, other);
    }

    @Override
    public E norm() {
        // For general fields, norm might not be well-defined or might require square
        // root.
        // This is a simplified implementation assuming Euclidean-like norm via dot
        // product.
        // Note: This might fail if the field doesn't support sqrt (e.g., Integers).
        // For now, we return dot(this) which is norm squared, or we need a Sqrt
        // interface.
        // Let's return dot(this) for now as a placeholder or throw
        // UnsupportedOperationException
        // if we can't take sqrt.
        // Better approach: Vector space usually implies a norm.
        // Let's assume standard dot product norm.
        // But we can't take sqrt of generic E without knowing it supports it.
        // So we will leave it as dot(this) for now, or maybe we should change the
        // return type?
        // Actually, let's just return dot(this) representing squared norm for now,
        // or rely on the field to have a sqrt? Field doesn't have sqrt.
        // Let's throw for now until we define NormedVectorSpace.
        throw new UnsupportedOperationException("Norm requires a field with square root operation");
    }

    @Override
    public Vector<E> negate() {
        List<E> result = new ArrayList<>(dimension());
        for (E element : elements) {
            result.add(field.negate(element));
        }
        return new DenseVector<>(result, field);
    }

    @Override
    public String toString() {
        return elements.toString();
    }

    @Override
    public org.jscience.mathematics.algebra.Ring<E> getScalarRing() {
        return field;
    }

    @Override
    public Vector<E> inverse(Vector<E> element) {
        return element.negate();
    }

    @Override
    public String description() {
        return "DenseVector (dim " + dimension() + ")";
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Vector<E> element) {
        return element != null && element.dimension() == this.dimension();
    }
}
