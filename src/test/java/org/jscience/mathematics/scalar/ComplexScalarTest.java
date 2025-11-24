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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComplexScalarTest {

    private ComplexScalar<Double> scalar;
    private ScalarType<Double> doubleScalar;

    @BeforeEach
    void setUp() {
        doubleScalar = new DoubleScalar();
        scalar = new ComplexScalar<>(doubleScalar);
    }

    @Test
    void testZero() {
        Complex<Double> zero = scalar.zero();
        assertEquals(0.0, zero.getReal());
        assertEquals(0.0, zero.getImaginary());
    }

    @Test
    void testOne() {
        Complex<Double> one = scalar.one();
        assertEquals(1.0, one.getReal());
        assertEquals(0.0, one.getImaginary());
    }

    @Test
    void testAdd() {
        Complex<Double> a = new Complex<>(1.0, 2.0, doubleScalar);
        Complex<Double> b = new Complex<>(3.0, 4.0, doubleScalar);
        Complex<Double> result = scalar.add(a, b);
        assertEquals(4.0, result.getReal());
        assertEquals(6.0, result.getImaginary());
    }

    @Test
    void testSubtract() {
        Complex<Double> a = new Complex<>(1.0, 2.0, doubleScalar);
        Complex<Double> b = new Complex<>(3.0, 4.0, doubleScalar);
        Complex<Double> result = scalar.subtract(a, b);
        assertEquals(-2.0, result.getReal());
        assertEquals(-2.0, result.getImaginary());
    }

    @Test
    void testMultiply() {
        // (1+2i)(3+4i) = 3 + 4i + 6i - 8 = -5 + 10i
        Complex<Double> a = new Complex<>(1.0, 2.0, doubleScalar);
        Complex<Double> b = new Complex<>(3.0, 4.0, doubleScalar);
        Complex<Double> result = scalar.multiply(a, b);
        assertEquals(-5.0, result.getReal());
        assertEquals(10.0, result.getImaginary());
    }

    @Test
    void testDivide() {
        // (1+2i)/(3+4i) = (1+2i)(3-4i) / 25 = (3 - 4i + 6i + 8) / 25 = (11 + 2i) / 25 =
        // 0.44 + 0.08i
        Complex<Double> a = new Complex<>(1.0, 2.0, doubleScalar);
        Complex<Double> b = new Complex<>(3.0, 4.0, doubleScalar);
        Complex<Double> result = scalar.divide(a, b);
        assertEquals(0.44, result.getReal(), 1e-9);
        assertEquals(0.08, result.getImaginary(), 1e-9);
    }

    @Test
    void testNegate() {
        Complex<Double> a = new Complex<>(1.0, -2.0, doubleScalar);
        Complex<Double> result = scalar.negate(a);
        assertEquals(-1.0, result.getReal());
        assertEquals(2.0, result.getImaginary());
    }

    @Test
    void testInverse() {
        // 1/(3+4i) = (3-4i)/25 = 0.12 - 0.16i
        Complex<Double> a = new Complex<>(3.0, 4.0, doubleScalar);
        Complex<Double> result = scalar.inverse(a);
        assertEquals(0.12, result.getReal(), 1e-9);
        assertEquals(-0.16, result.getImaginary(), 1e-9);
    }

    @Test
    void testFromInt() {
        Complex<Double> val = scalar.fromInt(5);
        assertEquals(5.0, val.getReal());
        assertEquals(0.0, val.getImaginary());
    }

    @Test
    void testFromDouble() {
        Complex<Double> val = scalar.fromDouble(3.14);
        assertEquals(3.14, val.getReal());
        assertEquals(0.0, val.getImaginary());
    }
}
