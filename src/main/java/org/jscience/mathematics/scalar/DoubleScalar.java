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
 * Fast double-precision scalar operations.
 * <p>
 * This is the <strong>default and recommended</strong> scalar type for most
 * scientific computing.
 * Uses primitive double operations which are:
 * <ul>
 * <li>Fast (hardware-accelerated)</li>
 * <li>Cache-friendly (8 bytes per value)</li>
 * <li>SIMD-optimizable by JIT</li>
 * <li>Accurate (15-17 decimal digits)</li>
 * </ul>
 * </p>
 * 
 * <h2>When to Use</h2>
 * <ul>
 * <li>✅ Scientific computing (physics, chemistry, biology)</li>
 * <li>✅ Linear algebra</li>
 * <li>✅ Signal processing</li>
 * <li>✅ Numerical analysis</li>
 * <li>✅ When performance matters</li>
 * </ul>
 * 
 * <h2>When NOT to Use</h2>
 * <ul>
 * <li>❌ Financial calculations (use BigDecimal for exact decimal)</li>
 * <li>❌ Need >15 digits precision (use ExactScalar)</li>
 * <li>❌ Symbolic mathematics</li>
 * </ul>
 * 
 * <h2>Performance</h2>
 * 
 * <pre>
 * Operation          Time (DoubleScalar)  Time (BigDecimal)
 * Addition           ~1 ns                ~100 ns
 * Multiplication     ~1 ns                ~200 ns
 * Division           ~2 ns                ~500 ns
 * Matrix multiply    Fast (SIMD)          1000x slower
 * </pre>
 * 
 * <h2>Usage</h2>
 * 
 * <pre>{@code
 * ScalarType<Double> scalar = new DoubleScalar();
 * 
 * Double a = 2.5;
 * Double b = 3.7;
 * 
 * Double sum = scalar.add(a, b); // 6.2
 * Double product = scalar.multiply(a, b); // 9.25
 * Double quotient = scalar.divide(a, b); // 0.6756...
 * 
 * // JIT optimizes this to pure primitive operations!
 * }</pre>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @version 1.0
 * @since 1.0
 * 
 * @see ScalarType
 * @see ExactScalar
 */
public final class DoubleScalar implements ScalarType<Double> {

    /** Singleton instance */
    private static final DoubleScalar INSTANCE = new DoubleScalar();

    /**
     * Returns the singleton instance.
     * 
     * @return the DoubleScalar instance
     */
    public static DoubleScalar getInstance() {
        return INSTANCE;
    }

    /** Public constructor for explicit instantiation */
    public DoubleScalar() {
    }

    @Override
    public Double zero() {
        return 0.0;
    }

    @Override
    public Double one() {
        return 1.0;
    }

    @Override
    public Double add(Double a, Double b) {
        return a + b; // Primitive operation - JIT optimizes
    }

    @Override
    public Double subtract(Double a, Double b) {
        return a - b;
    }

    @Override
    public Double multiply(Double a, Double b) {
        return a * b;
    }

    @Override
    public Double divide(Double a, Double b) {
        if (b == 0.0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }

    @Override
    public Double negate(Double a) {
        return -a;
    }

    @Override
    public Double inverse(Double a) {
        if (a == 0.0) {
            throw new ArithmeticException("Division by zero");
        }
        return 1.0 / a;
    }

    @Override
    public Double abs(Double a) {
        return Math.abs(a);
    }

    @Override
    public int compare(Double a, Double b) {
        return Double.compare(a, b);
    }

    @Override
    public Double fromInt(int value) {
        return (double) value;
    }

    @Override
    public Double fromDouble(double value) {
        return value;
    }

    @Override
    public double toDouble(Double value) {
        return value;
    }

    @Override
    public String toString() {
        return "DoubleScalar";
    }
}
