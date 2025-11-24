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
 * Single-precision (32-bit) floating-point scalar operations.
 * <p>
 * FloatScalar provides:
 * <ul>
 * <li>Half the memory of DoubleScalar (4 bytes vs 8 bytes)</li>
 * <li>GPU-friendly (many GPUs prefer float)</li>
 * <li>Good precision for most applications (6-7 decimal digits)</li>
 * <li>Faster on some architectures</li>
 * </ul>
 * </p>
 * 
 * <h2>When to Use</h2>
 * <ul>
 * <li>✅ GPU computing (CUDA/OpenCL often use float)</li>
 * <li>✅ Large datasets (half the memory)</li>
 * <li>✅ Graphics/rendering</li>
 * <li>✅ Neural networks training</li>
 * <li>✅ When 6-7 digits precision is sufficient</li>
 * </ul>
 * 
 * <h2>When NOT to Use</h2>
 * <ul>
 * <li>❌ Need >7 digits precision (use DoubleScalar)</li>
 * <li>❌ Accumulation errors matter (e.g., summing millions of values)</li>
 * <li>❌ Financial calculations</li>
 * </ul>
 * 
 * <h2>Precision Comparison</h2>
 * 
 * <pre>
 * Type    Bits  Decimal Digits  Range
 * Float   32    6-7            ±10^±38
 * Double  64    15-17          ±10^±308
 * </pre>
 * 
 * <h2>Performance</h2>
 * 
 * <pre>
 * Scenario                 FloatScalar    DoubleScalar
 * CPU operations           Similar        Similar
 * GPU operations           Faster         Slower
 * Memory bandwidth         2x better      Baseline
 * Cache efficiency         2x better      Baseline
 * Large matrix multiply    Faster         Slower
 * </pre>
 * 
 * <h2>Usage</h2>
 * 
 * <pre>{@code
 * ScalarType<Float> scalar = new FloatScalar();
 * 
 * Float a = 2.5f;
 * Float b = 3.7f;
 * 
 * Float sum = scalar.add(a, b); // 6.2
 * Float product = scalar.multiply(a, b); // 9.25
 * 
 * // GPU-accelerated processing
 * Matrix<Float> gpuMatrix = Matrix.create(data, scalar);
 * // Automatically uses GPU if available
 * }</pre>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @version 1.0
 * @since 1.0
 * 
 * @see ScalarType
 * @see DoubleScalar
 */
public final class FloatScalar implements ScalarType<Float> {

    /** Singleton instance */
    private static final FloatScalar INSTANCE = new FloatScalar();

    /**
     * Returns the singleton instance.
     * 
     * @return the FloatScalar instance
     */
    public static FloatScalar getInstance() {
        return INSTANCE;
    }

    /** Public constructor */
    public FloatScalar() {
    }

    @Override
    public Float zero() {
        return 0.0f;
    }

    @Override
    public Float one() {
        return 1.0f;
    }

    @Override
    public Float add(Float a, Float b) {
        return a + b;
    }

    @Override
    public Float subtract(Float a, Float b) {
        return a - b;
    }

    @Override
    public Float multiply(Float a, Float b) {
        return a * b;
    }

    @Override
    public Float divide(Float a, Float b) {
        if (b == 0.0f) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }

    @Override
    public Float negate(Float a) {
        return -a;
    }

    @Override
    public Float inverse(Float a) {
        if (a == 0.0f) {
            throw new ArithmeticException("Division by zero");
        }
        return 1.0f / a;
    }

    @Override
    public Float abs(Float a) {
        return Math.abs(a);
    }

    @Override
    public int compare(Float a, Float b) {
        return Float.compare(a, b);
    }

    @Override
    public Float fromInt(int value) {
        return (float) value;
    }

    @Override
    public Float fromDouble(double value) {
        return (float) value;
    }

    @Override
    public double toDouble(Float value) {
        return value.doubleValue();
    }

    @Override
    public String toString() {
        return "FloatScalar";
    }
}
