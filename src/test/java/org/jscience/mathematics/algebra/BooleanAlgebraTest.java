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

package org.jscience.mathematics.algebra;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BooleanAlgebraTest {

    @Test
    void testBooleanAlgebraStructure() {
        BooleanAlgebra bool = BooleanAlgebra.getInstance();

        // Test FiniteSet
        assertEquals(2, bool.size());
        assertFalse(bool.isEmpty());
        assertTrue(bool.contains(true));
        assertTrue(bool.contains(false));

        // Test Semiring (Additive)
        assertEquals(Boolean.FALSE, bool.zero());
        assertEquals(Boolean.TRUE, bool.add(true, false)); // OR
        assertEquals(Boolean.FALSE, bool.add(false, false));

        // Test Semiring (Multiplicative)
        assertEquals(Boolean.TRUE, bool.one());
        assertEquals(Boolean.FALSE, bool.multiply(true, false)); // AND
        assertEquals(Boolean.TRUE, bool.multiply(true, true));

        // Test Distributivity (a * (b + c) = a*b + a*c)
        // true * (false + true) = true * true = true
        // true*false + true*true = false + true = true
        Boolean a = true, b = false, c = true;
        assertEquals(
                bool.multiply(a, bool.add(b, c)),
                bool.add(bool.multiply(a, b), bool.multiply(a, c)));
    }
}
