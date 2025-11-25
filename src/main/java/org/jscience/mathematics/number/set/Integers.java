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

package org.jscience.mathematics.number.set;

import org.jscience.mathematics.algebra.Ring;
import org.jscience.mathematics.algebra.InfiniteSet;
import org.jscience.mathematics.scalar.LongScalar;

/**
 * The structure of integers (ℤ = {..., -2, -1, 0, 1, 2, ...}).
 * <p>
 * This class represents the <strong>structure</strong> of integers,
 * not individual elements. It implements {@link Ring} because ℤ forms
 * a commutative ring with unity under addition and multiplication.
 * </p>
 * <p>
 * Integers are an infinite, countable set.
 * </p>
 * 
 * <h2>Usage</h2>
 * 
 * <pre>{@code
 * Integers structure = Integers.getInstance();
 * LongScalar five = LongScalar.of(5);
 * LongScalar negThree = LongScalar.of(-3);
 * 
 * // Use structure for operations
 * LongScalar two = structure.add(five, negThree);
 * LongScalar negFifteen = structure.multiply(five, negThree);
 * LongScalar negFive = structure.negate(five);
 * }</pre>
 * 
 * @see LongScalar
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public final class Integers implements Ring<LongScalar>, InfiniteSet<LongScalar> {

    /** Singleton instance */
    private static final Integers INSTANCE = new Integers();

    /**
     * Returns the singleton instance.
     * 
     * @return the Integers structure
     */
    public static Integers getInstance() {
        return INSTANCE;
    }

    /** Private constructor for singleton */
    private Integers() {
    }

    // --- Ring Implementation ---

    @Override
    public LongScalar operate(LongScalar a, LongScalar b) {
        return add(a, b); // Ring operation is addition
    }

    @Override
    public LongScalar add(LongScalar a, LongScalar b) {
        return LongScalar.of(a.getValue() + b.getValue());
    }

    @Override
    public LongScalar zero() {
        return LongScalar.ZERO;
    }

    @Override
    public LongScalar negate(LongScalar element) {
        return LongScalar.of(-element.getValue());
    }

    @Override
    public LongScalar multiply(LongScalar a, LongScalar b) {
        return LongScalar.of(a.getValue() * b.getValue());
    }

    @Override
    public LongScalar one() {
        return LongScalar.ONE;
    }

    @Override
    public boolean isMultiplicationCommutative() {
        return true;
    }

    @Override
    public LongScalar inverse(LongScalar element) {
        // Integers don't have multiplicative inverses in general
        if (element.getValue() == 1 || element.getValue() == -1) {
            return element; // Only ±1 are self-inverse
        }
        throw new UnsupportedOperationException(
                "Multiplicative inverse not defined for integers (use Rationals instead)");
    }

    // --- InfiniteSet Implementation ---

    @Override
    public boolean isCountable() {
        return true; // ℤ is countably infinite
    }

    @Override
    public boolean contains(LongScalar element) {
        return element != null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String description() {
        return "ℤ (Integers)";
    }

    @Override
    public String toString() {
        return "Integers(ℤ)";
    }
}
