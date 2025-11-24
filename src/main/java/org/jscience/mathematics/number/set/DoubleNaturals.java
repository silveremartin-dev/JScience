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

import org.jscience.mathematics.algebra.Semiring;
import org.jscience.mathematics.algebra.InfiniteSet;
import org.jscience.mathematics.scalar.DoubleNatural;

/**
 * The structure of approximate natural numbers using doubles.
 * <p>
 * This implements {@link Semiring} for approximate natural number operations
 * using primitive {@code double} for performance.
 * </p>
 * 
 * @see Naturals
 * @see DoubleNatural
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public final class DoubleNaturals implements Semiring<DoubleNatural>, InfiniteSet<DoubleNatural> {

    /** Singleton instance */
    private static final DoubleNaturals INSTANCE = new DoubleNaturals();

    public static DoubleNaturals getInstance() {
        return INSTANCE;
    }

    private DoubleNaturals() {
    }

    // --- Semiring Implementation ---

    @Override
    public DoubleNatural operate(DoubleNatural a, DoubleNatural b) {
        return add(a, b);
    }

    @Override
    public DoubleNatural add(DoubleNatural a, DoubleNatural b) {
        return DoubleNatural.of(a.getValue() + b.getValue());
    }

    @Override
    public DoubleNatural zero() {
        return DoubleNatural.ZERO;
    }

    @Override
    public DoubleNatural multiply(DoubleNatural a, DoubleNatural b) {
        return DoubleNatural.of(a.getValue() * b.getValue());
    }

    @Override
    public DoubleNatural one() {
        return DoubleNatural.ONE;
    }

    @Override
    public boolean isMultiplicationCommutative() {
        return true;
    }

    // --- InfiniteSet Implementation ---

    @Override
    public boolean isCountable() {
        return false; // Doubles are uncountable (continuous approximation)
    }

    @Override
    public boolean contains(DoubleNatural element) {
        return element != null && element.getValue() >= 0.0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String description() {
        return "ℕ (Approximate, double precision)";
    }

    @Override
    public String toString() {
        return "DoubleNaturals(ℕ≈)";
    }
}
