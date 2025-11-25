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

/**
 * Generic Rational number (fraction).
 * <p>
 * Represents a number as a ratio of two integers (numerator / denominator).
 * Allows exact arithmetic for fractions.
 * </p>
 * 
 * @param <T> the underlying integer type (Integer, Long, BigInteger)
 */
public record Rational<T>(T numerator, T denominator, ScalarType<T> scalar) {

    public Rational {
        if (scalar.compare(denominator, scalar.zero()) == 0) {
            throw new ArithmeticException("Denominator cannot be zero");
        }
    }

    public Rational<T> add(Rational<T> other) {
        // a/b + c/d = (ad + bc) / bd
        T ad = scalar.multiply(numerator, other.denominator);
        T bc = scalar.multiply(denominator, other.numerator);
        T bd = scalar.multiply(denominator, other.denominator);

        return new Rational<>(scalar.add(ad, bc), bd, scalar);
    }

    public Rational<T> multiply(Rational<T> other) {
        // a/b * c/d = ac / bd
        return new Rational<>(
                scalar.multiply(numerator, other.numerator),
                scalar.multiply(denominator, other.denominator),
                scalar);
    }

    public Rational<T> inverse() {
        return new Rational<>(denominator, numerator, scalar);
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }
}
