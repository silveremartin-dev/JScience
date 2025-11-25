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

package org.jscience.mathematics.number;

import org.jscience.mathematics.algebra.Semiring;

/**
 * Boolean algebraic type (𝔹 = {0, 1}).
 * <p>
 * This class represents a Boolean value as a mathematical object.
 * It implements multiple algebraic interfaces:
 * </p>
 * <ul>
 * <li>{@link Semiring}: Addition is OR, Multiplication is AND</li>
 * <li>{@link Lattice}: Join is OR, Meet is AND</li>
 * <li>{@link FiniteSet}: The set {FALSE, TRUE} has size 2</li>
 * </ul>
 * 
 * <h2>Usage</h2>
 * 
 * <pre>{@code
 * Boolean t = Boolean.TRUE;
 * Boolean f = Boolean.FALSE;
 * 
 * Boolean or = t.add(f); // TRUE (OR)
 * Boolean and = t.multiply(f); // FALSE (AND)
 * }</pre>
 * 
 * @see org.jscience.mathematics.number.set.Booleans
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public final class Boolean implements Comparable<Boolean> {

    /** The boolean value true (1) */
    public static final Boolean TRUE = new Boolean(true);

    /** The boolean value false (0) */
    public static final Boolean FALSE = new Boolean(false);

    private final boolean value;

    private Boolean(boolean value) {
        this.value = value;
    }

    /**
     * Returns the Boolean instance representing the specified boolean value.
     * 
     * @param value the boolean value
     * @return {@link #TRUE} if value is true, {@link #FALSE} otherwise
     */
    public static Boolean of(boolean value) {
        return value ? TRUE : FALSE;
    }

    /**
     * Returns the primitive boolean value.
     * 
     * @return the boolean value
     */
    public boolean booleanValue() {
        return value;
    }

    // --- Semiring Operations (Add=OR, Mult=AND) ---

    public Boolean add(Boolean other) {
        return of(value || other.value);
    }

    public Boolean zero() {
        return FALSE;
    }

    public Boolean multiply(Boolean other) {
        return of(value && other.value);
    }

    public Boolean one() {
        return TRUE;
    }

    // --- Lattice Operations (Join=OR, Meet=AND) ---

    public Boolean join(Boolean other) {
        return of(value || other.value);
    }

    public Boolean meet(Boolean other) {
        return of(value && other.value);
    }

    // --- Standard Methods ---

    // --- Standard Methods ---

    @Override
    public int compareTo(Boolean other) {
        return java.lang.Boolean.compare(value, other.value);
    }

    @Override
    public String toString() {
        return java.lang.Boolean.toString(value);
    }
}
