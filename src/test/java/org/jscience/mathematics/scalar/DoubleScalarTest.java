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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link DoubleScalar}.
 */
@DisplayName("DoubleScalar Tests")
class DoubleScalarTest {

    private final ScalarType<Double> scalar = new DoubleScalar();

    @Test
    @DisplayName("Zero and One constants")
    void testConstants() {
        assertEquals(0.0, scalar.zero());
        assertEquals(1.0, scalar.one());
    }

    @Test
    @DisplayName("Addition")
    void testAddition() {
        assertEquals(8.0, scalar.add(5.0, 3.0));
        assertEquals(0.0, scalar.add(-5.0, 5.0));
        assertEquals(5.0, scalar.add(5.0, 0.0));
    }

    @Test
    @DisplayName("Subtraction")
    void testSubtraction() {
        assertEquals(2.0, scalar.subtract(5.0, 3.0));
        assertEquals(-10.0, scalar.subtract(0.0, 10.0));
    }

    @Test
    @DisplayName("Multiplication")
    void testMultiplication() {
        assertEquals(15.0, scalar.multiply(5.0, 3.0));
        assertEquals(0.0, scalar.multiply(5.0, 0.0));
        assertEquals(-15.0, scalar.multiply(5.0, -3.0));
    }

    @Test
    @DisplayName("Division")
    void testDivision() {
        assertEquals(2.5, scalar.divide(5.0, 2.0));
        assertEquals(-2.0, scalar.divide(10.0, -5.0));
    }

    @Test
    @DisplayName("Division by zero throws exception")
    void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> {
            scalar.divide(5.0, 0.0);
        });
    }

    @Test
    @DisplayName("Negation")
    void testNegation() {
        assertEquals(-5.0, scalar.negate(5.0));
        assertEquals(5.0, scalar.negate(-5.0));
        assertEquals(0.0, scalar.negate(0.0), 0.0); // Handle -0.0 edge case
    }

    @Test
    @DisplayName("Inverse")
    void testInverse() {
        assertEquals(0.5, scalar.inverse(2.0));
        assertEquals(0.2, scalar.inverse(5.0), 1e-10);
    }

    @Test
    @DisplayName("Inverse of zero throws exception")
    void testInverseOfZero() {
        assertThrows(ArithmeticException.class, () -> {
            scalar.inverse(0.0);
        });
    }

    @Test
    @DisplayName("Absolute value")
    void testAbs() {
        assertEquals(5.0, scalar.abs(5.0));
        assertEquals(5.0, scalar.abs(-5.0));
        assertEquals(0.0, scalar.abs(0.0));
    }

    @Test
    @DisplayName("Comparison")
    void testComparison() {
        assertTrue(scalar.compare(5.0, 3.0) > 0);
        assertTrue(scalar.compare(3.0, 5.0) < 0);
        assertEquals(0, scalar.compare(5.0, 5.0));
    }

    @Test
    @DisplayName("Conversion from int")
    void testFromInt() {
        assertEquals(5.0, scalar.fromInt(5));
        assertEquals(-10.0, scalar.fromInt(-10));
    }

    @Test
    @DisplayName("Conversion from/to double")
    void testDoubleConversion() {
        assertEquals(3.14, scalar.fromDouble(3.14));
        assertEquals(2.71, scalar.toDouble(2.71));
    }
}
