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

package org.jscience.mathematics.scalar;

import org.jscience.mathematics.algebra.Field;

/**
 * Generic Complex number support.
 * <p>
 * Represents numbers of the form a + bi, where a and b are scalars of type T.
 * </p>
 * 
 * @param <T> the underlying scalar type (Double, BigDecimal, etc.)
 */
public record Complex<T>(T real, T imag, ScalarType<T> scalar) {

    public Complex<T> add(Complex<T> other) {
        return new Complex<>(
                scalar.add(real, other.real),
                scalar.add(imag, other.imag),
                scalar);
    }

    public Complex<T> multiply(Complex<T> other) {
        T ac = scalar.multiply(real, other.real);
        T bd = scalar.multiply(imag, other.imag);
        T ad = scalar.multiply(real, other.imag);
        T bc = scalar.multiply(imag, other.real);

        return new Complex<>(
                scalar.subtract(ac, bd),
                scalar.add(ad, bc),
                scalar);
    }

    public T modulus() {
        // |z| = sqrt(a² + b²)
        // Note: ScalarType needs sqrt() for this!
        // For now, returning squared modulus to avoid dependency
        return modulusSquared();
    }

    public T modulusSquared() {
        return scalar.add(
                scalar.multiply(real, real),
                scalar.multiply(imag, imag));
    }

    public Complex<T> conjugate() {
        return new Complex<>(real, scalar.negate(imag), scalar);
    }

    @Override
    public String toString() {
        return real + " + " + imag + "i";
    }
}
