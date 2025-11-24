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
 * 32-bit integer scalar operations.
 * <p>
 * IntScalar provides exact arithmetic for integers in range [-2³¹, 2³¹-1].
 * Useful for discrete mathematics, combinatorics, and integer algorithms.
 * </p>
 * 
 * <h2>When to Use</h2>
 * <ul>
 * <li>✅ Discrete mathematics</li>
 * <li>✅ Combinatorics</li>
 * <li>✅ Graph algorithms</li>
 * <li>✅ Integer linear programming</li>
 * <li>✅ Indexing and counting</li>
 * </ul>
 * 
 * <h2>Limitations</h2>
 * <ul>
 * <li>⚠️ No fractional results (division truncates)</li>
 * <li>⚠️ Range: -2,147,483,648 to 2,147,483,647</li>
 * <li>⚠️ Overflow wraps around (use LongScalar for larger values)</li>
 * </ul>
 * 
 * <h2>Usage</h2>
 * 
 * <pre>{@code
 * ScalarType<Integer> scalar = new IntScalar();
 * 
 * Integer a = 15;
 * Integer b = 4;
 * 
 * Integer sum = scalar.add(a, b); // 19
 * Integer product = scalar.multiply(a, b); // 60
 * Integer quotient = scalar.divide(a, b); // 3 (truncated, not 3.75)
 * 
 * // For combinatorics
 * Integer factorial5 = factorial(5, scalar); // 120
 * }</pre>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @version 1.0
 * @since 1.0
 * 
 * @see ScalarType
 * @see LongScalar
 */
public final class IntScalar implements ScalarType<Integer> {

    /** Singleton instance */
    private static final IntScalar INSTANCE = new IntScalar();

    public static IntScalar getInstance() {
        return INSTANCE;
    }

    public IntScalar() {
    }

    @Override
    public Integer zero() {
        return 0;
    }

    @Override
    public Integer one() {
        return 1;
    }

    @Override
    public Integer add(Integer a, Integer b) {
        return a + b;
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        return a - b;
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        return a * b;
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b; // Integer division (truncates)
    }

    @Override
    public Integer negate(Integer a) {
        return -a;
    }

    @Override
    public Integer inverse(Integer a) {
        if (a == 0) {
            throw new ArithmeticException("Division by zero");
        }
        if (a == 1)
            return 1;
        if (a == -1)
            return -1;
        throw new ArithmeticException("Integer has no integer inverse: " + a);
    }

    @Override
    public Integer abs(Integer a) {
        return Math.abs(a);
    }

    @Override
    public int compare(Integer a, Integer b) {
        return Integer.compare(a, b);
    }

    @Override
    public Integer fromInt(int value) {
        return value;
    }

    @Override
    public Integer fromDouble(double value) {
        return (int) value;
    }

    @Override
    public double toDouble(Integer value) {
        return value.doubleValue();
    }

    @Override
    public String toString() {
        return "IntScalar";
    }
}
