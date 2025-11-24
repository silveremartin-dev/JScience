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
 * Generic scalar type providing arithmetic operations.
 * <p>
 * This interface allows algorithms to work with different numeric types
 * (Double, BigDecimal, GPU types) without code changes. Implementations
 * provide the actual arithmetic logic.
 * </p>
 * 
 * <h2>Design Pattern</h2>
 * <p>
 * ScalarType follows the Strategy pattern:
 * <ul>
 * <li>Algorithms are generic over T</li>
 * <li>ScalarType<T> provides operations</li>
 * <li>Concrete types (DoubleScalar, ExactScalar) implement specifics</li>
 * <li>JIT compiler optimizes away abstractions for primitives</li>
 * </ul>
 * </p>
 * 
 * <h2>Usage</h2>
 * 
 * <pre>{@code
 * // Generic algorithm works with any type
 * public <T> T compute(T a, T b, ScalarType<T> ops) {
 *     T sum = ops.add(a, b);
 *     T product = ops.multiply(sum, a);
 *     return product;
 * }
 * 
 * // Fast doubles
 * Double result1 = compute(2.0, 3.0, new DoubleScalar());
 * 
 * // Exact precision
 * BigDecimal result2 = compute(
 *         new BigDecimal("2"),
 *         new BigDecimal("3"),
 *         new ExactScalar());
 * 
 * // Same algorithm, different performance/precision!
 * }</pre>
 * 
 * @param <T> the scalar type (Double, BigDecimal, Complex, etc.)
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @version 1.0
 * @since 1.0
 * 
 * @see DoubleScalar
 * @see ExactScalar
 */
public interface ScalarType<T> {

    /**
     * Returns the additive identity (zero).
     * 
     * @return 0
     */
    T zero();

    /**
     * Returns the multiplicative identity (one).
     * 
     * @return 1
     */
    T one();

    /**
     * Returns the sum of two scalars.
     * 
     * @param a first addend
     * @param b second addend
     * @return a + b
     */
    T add(T a, T b);

    /**
     * Returns the difference of two scalars.
     * 
     * @param a minuend
     * @param b subtrahend
     * @return a - b
     */
    T subtract(T a, T b);

    /**
     * Returns the product of two scalars.
     * 
     * @param a first factor
     * @param b second factor
     * @return a × b
     */
    T multiply(T a, T b);

    /**
     * Returns the quotient of two scalars.
     * 
     * @param a dividend
     * @param b divisor (must be non-zero)
     * @return a ÷ b
     * @throws ArithmeticException if b is zero
     */
    T divide(T a, T b);

    /**
     * Returns the additive inverse.
     * 
     * @param a the value to negate
     * @return -a
     */
    T negate(T a);

    /**
     * Returns the multiplicative inverse.
     * 
     * @param a the value to invert (must be non-zero)
     * @return 1/a
     * @throws ArithmeticException if a is zero
     */
    T inverse(T a);

    /**
     * Returns the absolute value.
     * 
     * @param a the value
     * @return |a|
     */
    T abs(T a);

    /**
     * Compares two scalars.
     * 
     * @param a first value
     * @param b second value
     * @return negative if a < b, zero if a = b, positive if a > b
     */
    int compare(T a, T b);

    /**
     * Creates a scalar from an integer.
     * 
     * @param value the integer value
     * @return scalar representation
     */
    T fromInt(int value);

    /**
     * Creates a scalar from a double.
     * 
     * @param value the double value
     * @return scalar representation
     */
    T fromDouble(double value);

    /**
     * Converts scalar to double (may lose precision).
     * 
     * @param value the scalar value
     * @return double approximation
     */
    double toDouble(T value);
}
