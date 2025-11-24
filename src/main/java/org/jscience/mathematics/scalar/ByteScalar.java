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
 * 8-bit integer scalar operations.
 * <p>
 * ByteScalar represents ℤ/256ℤ (integers modulo 256), range [-128, 127].
 * Primary use cases are image processing, embedded systems, and
 * memory-constrained
 * applications.
 * </p>
 * 
 * <h2>When to Use</h2>
 * <ul>
 * <li>✅ <strong>Image processing</strong> (pixel values 0-255)</li>
 * <li>✅ Embedded systems (memory-constrained)</li>
 * <li>✅ Network protocols (byte-level data)</li>
 * <li>✅ Color values (RGB components)</li>
 * <li>✅ Audio samples (8-bit audio)</li>
 * </ul>
 * 
 * <h2>Mathematical Properties</h2>
 * <p>
 * ByteScalar forms **ℤ/256ℤ** - integers modulo 256:
 * <ul>
 * <li>Forms a ring with modular arithmetic</li>
 * <li>Wraps around: 127 + 1 = -128</li>
 * <li>Range: -128 to 127</li>
 * </ul>
 * </p>
 * 
 * <h2>Image Processing Example</h2>
 * 
 * <pre>{@code
 * ScalarType<Byte> scalar = new ByteScalar();
 * 
 * // RGB pixel manipulation
 * Byte red = 255; // Stored as -1 (wrap around)
 * Byte green = 128; // Stored as -128
 * Byte blue = 0;
 * 
 * // Brightness adjustment
 * Byte brighterRed = scalar.add(red, scalar.fromInt(10));
 * 
 * // Image filtering (average of neighbors)
 * Byte avg = scalar.fromInt(
 *         (pixel1 + pixel2 + pixel3 + pixel4) / 4);
 * }</pre>
 * 
 * <h2>Memory Efficiency</h2>
 * 
 * <pre>
 * 1 megapixel image (1000x1000 RGB):
 * - ByteScalar:  3 MB
 * - IntScalar:  12 MB  (4x more memory!)
 * - DoubleScalar: 24 MB (8x more memory!)
 * </pre>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @version 1.0
 * @since 1.0
 * 
 * @see IntScalar
 * @see ScalarType
 */
public final class ByteScalar implements ScalarType<Byte> {

    /** Singleton instance */
    private static final ByteScalar INSTANCE = new ByteScalar();

    public static ByteScalar getInstance() {
        return INSTANCE;
    }

    public ByteScalar() {
    }

    @Override
    public Byte zero() {
        return 0;
    }

    @Override
    public Byte one() {
        return 1;
    }

    @Override
    public Byte add(Byte a, Byte b) {
        return (byte) (a + b); // Wraps on overflow
    }

    @Override
    public Byte subtract(Byte a, Byte b) {
        return (byte) (a - b);
    }

    @Override
    public Byte multiply(Byte a, Byte b) {
        return (byte) (a * b);
    }

    @Override
    public Byte divide(Byte a, Byte b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return (byte) (a / b);
    }

    @Override
    public Byte negate(Byte a) {
        return (byte) -a;
    }

    @Override
    public Byte inverse(Byte a) {
        if (a == 0) {
            throw new ArithmeticException("Division by zero");
        }
        if (a == 1)
            return 1;
        if (a == -1)
            return -1;
        throw new ArithmeticException("Byte has no byte inverse: " + a);
    }

    @Override
    public Byte abs(Byte a) {
        return (byte) Math.abs(a);
    }

    @Override
    public int compare(Byte a, Byte b) {
        return Byte.compare(a, b);
    }

    @Override
    public Byte fromInt(int value) {
        return (byte) value; // Truncates to 8 bits
    }

    @Override
    public Byte fromDouble(double value) {
        return (byte) value;
    }

    @Override
    public double toDouble(Byte value) {
        return value.doubleValue();
    }

    /**
     * Converts unsigned byte (0-255) to signed byte (-128 to 127).
     * <p>
     * Useful for image processing where pixels are 0-255.
     * </p>
     * 
     * @param unsigned value 0-255
     * @return signed byte
     */
    public Byte fromUnsigned(int unsigned) {
        return (byte) unsigned;
    }

    /**
     * Converts signed byte to unsigned int (0-255).
     * <p>
     * Useful for getting actual pixel values.
     * </p>
     * 
     * @param signed the signed byte
     * @return unsigned value 0-255
     */
    public int toUnsigned(Byte signed) {
        return signed & 0xFF;
    }

    @Override
    public String toString() {
        return "ByteScalar";
    }
}
