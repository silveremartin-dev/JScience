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
import java.math.BigInteger;
import static org.junit.jupiter.api.Assertions.*;

class ScalarTypesTest {

    @Test
    void testNatural() {
        Natural n1 = Natural.of(10);
        Natural n2 = Natural.of(5);
        Natural n3 = Natural.of(BigInteger.valueOf(100));

        // Addition
        assertEquals(Natural.of(15), n1.add(n2));

        // Multiplication
        assertEquals(Natural.of(50), n1.multiply(n2));

        // Subtraction (valid)
        assertEquals(Natural.of(5), n1.subtract(n2));

        // Subtraction (invalid)
        assertThrows(ArithmeticException.class, () -> n2.subtract(n1));

        // Zero and One
        assertEquals(Natural.ZERO, n1.zero());
        assertEquals(Natural.ONE, n1.one());

        // Commutativity
        assertEquals(n1.multiply(n2), n2.multiply(n1));
    }

    @Test
    void testLongScalar() {
        LongScalar l1 = LongScalar.of(10L);
        LongScalar l2 = LongScalar.of(5L);

        // Ring operations
        assertEquals(LongScalar.of(15L), l1.add(l2));
        assertEquals(LongScalar.of(50L), l1.multiply(l2));
        assertEquals(LongScalar.of(5L), l1.subtract(l2));
        assertEquals(LongScalar.of(-10L), l1.negate());

        // Zero and One
        assertEquals(LongScalar.ZERO, l1.zero());
        assertEquals(LongScalar.ONE, l1.one());

        // Division
        assertEquals(LongScalar.of(2L), l1.divide(l2));
    }
}
