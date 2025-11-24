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
 * 64-bit integer scalar operations.
 * <p>
 * LongScalar provides exact arithmetic for integers in range [-2⁶³, 2⁶³-1],
 * extending the range of {@link IntScalar} by 32 bits.
 * </p>
 * 
 * <h2>When to Use</h2>
 * <ul>
 * <li>✅ Large integer computations (>2³¹)</li>
 * <li>✅ Combinatorics (factorials, binomial coefficients)</li>
 * <li>✅ Cryptography (key sizes, modular arithmetic)</li>
 * <li>✅ Big data (counts, IDs, timestamps)</li>
 * <li>✅ Number theory</li>
 * </ul>
 * 
 * <h2>Range Comparison</h2>
 * 
 * <pre>
 * Type      Range
 * Byte      -128 to 127
 * Int       -2,147,483,648 to 2,147,483,647
 * Long      -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807
 * </pre>
 * 
 * <h2>Examples</h2>
 * 
 * <pre>{@code
 * ScalarType<Long> scalar = new LongScalar();
 * 
 * // Factorial(20) = 2,432,902,008,176,640,000 (overflows int!)
 * Long fact20 = factorial(20, scalar);
 * 
 * // Large Fibonacci numbers
 * Long fib50 = fibonacci(50, scalar); // 12,586,269,025
 * 
 * // Cryptography: 2^63 - 1 (Mersenne prime)
 * Long largePrime = scalar.subtract(
 *         scalar.pow(scalar.fromInt(2), 63),
 *         scalar.one());
 * }</pre>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @version 1.0
 * @since 1.0
 * 
 * @see IntScalar
 * @see ScalarType
 */
public final class LongScalar implements ScalarType<Long> {

    /** Singleton instance */
    private static final LongScalar INSTANCE = new LongScalar();

    public static LongScalar getInstance() {
        return INSTANCE;
    }

    public LongScalar() {
    }

    @Override
    public Long zero() {
        return 0L;
    }

    @Override
    public Long one() {
        return 1L;
    }

    @Override
    public Long add(Long a, Long b) {
        return a + b;
    }

    @Override
    public Long subtract(Long a, Long b) {
        return a - b;
    }

    @Override
    public Long multiply(Long a, Long b) {
        return a * b;
    }

    @Override
    public Long divide(Long a, Long b) {
        if (b == 0L) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b; // Integer division
    }

    @Override
    public Long negate(Long a) {
        return -a;
    }

    @Override
    public Long inverse(Long a) {
        if (a == 0L) {
            throw new ArithmeticException("Division by zero");
        }
        if (a == 1L)
            return 1L;
        if (a == -1L)
            return -1L;
        throw new ArithmeticException("Integer has no integer inverse: " + a);
    }

    @Override
    public Long abs(Long a) {
        return Math.abs(a);
    }

    @Override
    public int compare(Long a, Long b) {
        return Long.compare(a, b);
    }

    @Override
    public Long fromInt(int value) {
        return (long) value;
    }

    @Override
    public Long fromDouble(double value) {
        return (long) value;
    }

    @Override
    public double toDouble(Long value) {
        return value.doubleValue();
    }

    @Override
    public String toString() {
        return "LongScalar";
    }
}
