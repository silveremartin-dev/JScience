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

import org.jscience.mathematics.algebra.Ring;

/**
 * Represents a 64-bit integer scalar.
 * <p>
 * This class wraps a primitive {@code long} value and implements the
 * {@link Ring}
 * interface. It is suitable for integer arithmetic where 64-bit precision is
 * sufficient.
 * </p>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public final class LongScalar implements ScalarType<LongScalar>, Ring<LongScalar> {

    /** The scalar value 0. */
    public static final LongScalar ZERO = new LongScalar(0L);

    /** The scalar value 1. */
    public static final LongScalar ONE = new LongScalar(1L);

    private final long value;

    /**
     * Creates a new LongScalar.
     * 
     * @param value the value
     */
    private LongScalar(long value) {
        this.value = value;
    }

    /**
     * Returns a LongScalar with the specified value.
     * 
     * @param value the value
     * @return the LongScalar instance
     */
    public static LongScalar of(long value) {
        if (value == 0)
            return ZERO;
        if (value == 1)
            return ONE;
        return new LongScalar(value);
    }

    /**
     * Returns the value as a long.
     * 
     * @return the value
     */
    public long getValue() {
        return value;
    }

    // --- Ring Implementation ---

    @Override
    public LongScalar add(LongScalar a, LongScalar b) {
        return new LongScalar(a.value + b.value);
    }

    @Override
    public LongScalar zero() {
        return ZERO;
    }

    @Override
    public LongScalar negate(LongScalar element) {
        return new LongScalar(-element.value);
    }

    @Override
    public LongScalar multiply(LongScalar a, LongScalar b) {
        return new LongScalar(a.value * b.value);
    }

    @Override
    public LongScalar one() {
        return ONE;
    }

    @Override
    public boolean isMultiplicationCommutative() {
        return true;
    }

    // --- Convenience Instance Methods (delegates to Integers structure) ---

    /**
     * Adds another integer (convenience method).
     * Delegates to {@link org.jscience.mathematics.number.set.Integers}.
     */
    public LongScalar add(LongScalar other) {
        return org.jscience.mathematics.number.set.Integers.getInstance().add(this, other);
    }

    /**
     * Subtracts another integer (convenience method).
     * Delegates to {@link org.jscience.mathematics.number.set.Integers}.
     */
    public LongScalar subtract(LongScalar other) {
        return new LongScalar(this.value - other.value);
    }

    /**
     * Multiplies by another integer (convenience method).
     * Delegates to {@link org.jscience.mathematics.number.set.Integers}.
     */
    public LongScalar multiply(LongScalar other) {
        return org.jscience.mathematics.number.set.Integers.getInstance().multiply(this, other);
    }

    public LongScalar divide(LongScalar other) {
        if (other.value == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return new LongScalar(this.value / other.value);
    }

    /**
     * Negates this integer (convenience method).
     * Delegates to {@link org.jscience.mathematics.number.set.Integers}.
     */
    public LongScalar negate() {
        return org.jscience.mathematics.number.set.Integers.getInstance().negate(this);
    }

    public boolean isZero() {
        return value == 0;
    }

    public boolean isOne() {
        return value == 1;
    }

    public int compareTo(LongScalar other) {
        return Long.compare(this.value, other.value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof LongScalar))
            return false;
        return this.value == ((LongScalar) obj).value;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(value);
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }
}
