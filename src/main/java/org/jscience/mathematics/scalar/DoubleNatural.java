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

import org.jscience.mathematics.algebra.Semiring;

/**
 * Approximate natural numbers using primitive {@code double}.
 * <p>
 * This provides FAST but APPROXIMATE natural number operations.
 * Unlike {@link Natural} which uses {@link java.math.BigInteger} for exact
 * arbitrary precision, this uses primitive {@code double} for speed.
 * </p>
 * <p>
 * <strong>Performance</strong>: 100-1000x faster than {@link Natural}<br>
 * <strong>Precision</strong>: Limited to ~15 decimal digits<br>
 * <strong>Range</strong>: Up to ~10^308
 * </p>
 * 
 * <h2>When to Use</h2>
 * <ul>
 * <li><strong>Simulations</strong>: Particle counts, iterations</li>
 * <li><strong>Approximations</strong>: Statistical sampling</li>
 * <li><strong>Large-scale computations</strong>: When speed matters more than
 * exactness</li>
 * </ul>
 * 
 * <h2>When NOT to Use</h2>
 * <ul>
 * <li><strong>Number theory</strong>: Use {@link Natural}</li>
 * <li><strong>Cryptography</strong>: Use {@link Natural}</li>
 * <li><strong>Exact counting</strong>: Use {@link Natural}</li>
 * </ul>
 * 
 * @see Natural
 * @see org.jscience.mathematics.number.set.DoubleNaturals
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public final class DoubleNatural implements Comparable<DoubleNatural> {

    /** The scalar value 0.0 */
    public static final DoubleNatural ZERO = new DoubleNatural(0.0);

    /** The scalar value 1.0 */
    public static final DoubleNatural ONE = new DoubleNatural(1.0);

    private final double value;

    /**
     * Creates a new approximate natural number.
     * 
     * @param value the value (must be non-negative)
     * @throws IllegalArgumentException if value is negative or not finite
     */
    private DoubleNatural(double value) {
        if (value < 0.0 || !Double.isFinite(value)) {
            throw new IllegalArgumentException("DoubleNatural must be non-negative and finite: " + value);
        }
        this.value = value;
    }

    /**
     * Returns an approximate natural number.
     * 
     * @param value the value
     * @return the DoubleNatural instance
     * @throws IllegalArgumentException if value is negative or not finite
     */
    public static DoubleNatural of(double value) {
        if (value == 0.0)
            return ZERO;
        if (value == 1.0)
            return ONE;
        return new DoubleNatural(value);
    }

    /**
     * Returns the value as a double.
     * 
     * @return the approximate value
     */
    public double getValue() {
        return value;
    }

    // --- Convenience Instance Methods (delegates to DoubleNaturals structure) ---

    /**
     * Adds another approximate natural number.
     */
    public DoubleNatural add(DoubleNatural other) {
        return org.jscience.mathematics.number.set.DoubleNaturals.getInstance().add(this, other);
    }

    /**
     * Subtracts another approximate natural number.
     * 
     * @throws ArithmeticException if result would be negative
     */
    public DoubleNatural subtract(DoubleNatural other) {
        double result = this.value - other.value;
        if (result < 0.0) {
            throw new ArithmeticException("Result of subtraction is negative: " + result);
        }
        return new DoubleNatural(result);
    }

    /**
     * Multiplies by another approximate natural number.
     */
    public DoubleNatural multiply(DoubleNatural other) {
        return org.jscience.mathematics.number.set.DoubleNaturals.getInstance().multiply(this, other);
    }

    /**
     * Divides by another approximate natural number (integer division).
     */
    public DoubleNatural divide(DoubleNatural other) {
        if (other.value == 0.0) {
            throw new ArithmeticException("Division by zero");
        }
        return new DoubleNatural(Math.floor(this.value / other.value));
    }

    public boolean isZero() {
        return value == 0.0;
    }

    public boolean isOne() {
        return value == 1.0;
    }

    @Override
    public int compareTo(DoubleNatural other) {
        return Double.compare(this.value, other.value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof DoubleNatural))
            return false;
        return this.value == ((DoubleNatural) obj).value;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
