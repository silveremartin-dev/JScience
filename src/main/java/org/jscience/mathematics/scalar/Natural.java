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

import java.math.BigInteger;
import org.jscience.mathematics.algebra.Semiring;

/**
 * Represents a Natural number (ℕ).
 * <p>
 * Natural numbers are non-negative integers {0, 1, 2, ...}.
 * They form a {@link Semiring} (commutative semiring with identity), but not a
 * Ring
 * because they lack additive inverses (subtraction is not closed).
 * </p>
 * <p>
 * This implementation uses {@link BigInteger} to support arbitrary precision,
 * as the set of natural numbers is infinite.
 * </p>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public final class Natural implements ScalarType<Natural>, Semiring<Natural> {

    /** The scalar value 0. */
    public static final Natural ZERO = new Natural(BigInteger.ZERO);

    /** The scalar value 1. */
    public static final Natural ONE = new Natural(BigInteger.ONE);

    private final BigInteger value;

    /**
     * Creates a new Natural number.
     * 
     * @param value the value (must be non-negative)
     * @throws IllegalArgumentException if value is negative
     */
    private Natural(BigInteger value) {
        if (value.signum() < 0) {
            throw new IllegalArgumentException("Natural numbers cannot be negative: " + value);
        }
        this.value = value;
    }

    /**
     * Returns a Natural number with the specified value.
     * 
     * @param value the value
     * @return the Natural instance
     * @throws IllegalArgumentException if value is negative
     */
    public static Natural of(long value) {
        if (value < 0) {
            throw new IllegalArgumentException("Natural numbers cannot be negative: " + value);
        }
        if (value == 0)
            return ZERO;
        if (value == 1)
            return ONE;
        return new Natural(BigInteger.valueOf(value));
    }

    /**
     * Returns a Natural number with the specified value.
     * 
     * @param value the value
     * @return the Natural instance
     * @throws IllegalArgumentException if value is negative
     */
    public static Natural of(BigInteger value) {
        if (value.equals(BigInteger.ZERO))
            return ZERO;
        if (value.equals(BigInteger.ONE))
            return ONE;
        return new Natural(value);
    }

    /**
     * Returns the value as a BigInteger.
     * 
     * @return the value
     */
    public BigInteger getValue() {
        return value;
    }

    // --- Semiring Implementation ---

    @Override
    public Natural add(Natural a, Natural b) {
        return new Natural(a.value.add(b.value));
    }

    @Override
    public Natural zero() {
        return ZERO;
    }

    @Override
    public Natural multiply(Natural a, Natural b) {
        return new Natural(a.value.multiply(b.value));
    }

    @Override
    public Natural one() {
        return ONE;
    }

    @Override
    public boolean isMultiplicationCommutative() {
        return true;
    }

    // --- Convenience Instance Methods (delegates to Naturals structure) ---

    /**
     * Adds another natural number (convenience method).
     * Delegates to {@link org.jscience.mathematics.number.set.Naturals}.
     */
    public Natural add(Natural other) {
        return org.jscience.mathematics.number.set.Naturals.getInstance().add(this, other);
    }

    /**
     * Subtracts another natural number (convenience method).
     * Delegates to {@link org.jscience.mathematics.number.set.Naturals}.
     */
    public Natural subtract(Natural other) {
        BigInteger result = this.value.subtract(other.value);
        if (result.signum() < 0) {
            throw new ArithmeticException("Result of subtraction is not a Natural number: " + result);
        }
        return new Natural(result);
    }

    /**
     * Multiplies by another natural number (convenience method).
     * Delegates to {@link org.jscience.mathematics.number.set.Naturals}.
     */
    public Natural multiply(Natural other) {
        return org.jscience.mathematics.number.set.Naturals.getInstance().multiply(this, other);
    }

    public Natural divide(Natural other) {
        if (other.equals(ZERO)) {
            throw new ArithmeticException("Division by zero");
        }
        // Integer division
        return new Natural(this.value.divide(other.value));
    }

    public Natural negate() {
        if (this.equals(ZERO))
            return ZERO;
        throw new ArithmeticException("Natural numbers do not have additive inverses");
    }

    public boolean isZero() {
        return this.equals(ZERO);
    }

    public boolean isOne() {
        return this.equals(ONE);
    }

    public int compareTo(Natural other) {
        return this.value.compareTo(other.value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Natural))
            return false;
        return this.value.equals(((Natural) obj).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }

}