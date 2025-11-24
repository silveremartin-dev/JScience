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
import java.util.Iterator;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class SetHierarchyTest {

    static class MyFiniteSet implements FiniteSet<Integer> {
        @Override
        public boolean contains(Integer element) {
            return element == 1 || element == 2;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public String description() {
            return "{1, 2}";
        }

        @Override
        public long size() {
            return 2;
        }

        @Override
        public Iterator<Integer> iterator() {
            return java.util.Arrays.asList(1, 2).iterator();
        }
    }

    static class MyInfiniteSet implements InfiniteSet<Integer> {
        @Override
        public boolean contains(Integer element) {
            return true; // All integers
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public String description() {
            return "Integers";
        }

        @Override
        public boolean isCountable() {
            return true;
        }
    }

    @Test
    void testFiniteSet() {
        FiniteSet<Integer> set = new MyFiniteSet();
        assertEquals(2, set.size());
        assertFalse(set.isEmpty());
        assertTrue(set.contains(1));
        assertFalse(set.contains(3));

        // Test stream
        assertEquals(2, set.stream().count());
        assertTrue(set.stream().anyMatch(i -> i == 1));
    }

    @Test
    void testInfiniteSet() {
        InfiniteSet<Integer> set = new MyInfiniteSet();
        assertTrue(set.isCountable());
        assertFalse(set.isEmpty());
        assertTrue(set.contains(100));

        // No size() method to test, which is correct
    }
}
