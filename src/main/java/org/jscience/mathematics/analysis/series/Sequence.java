/*
 * JScience - Java(TM) Tools and Libraries for the Advancement of Sciences.
 * Copyright (C) 2025 - Silvere Martin-Michiellot (silvere.martin@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jscience.mathematics.analysis.series;

import org.jscience.mathematics.analysis.MathematicalFunction;

/**
 * A mathematical sequence a(n) for n ≥ 0.
 * <p>
 * A sequence is a function from natural numbers to type T: ℕ → T.
 * Compatible with OEIS (Online Encyclopedia of Integer Sequences) format.
 * </p>
 * 
 * @param <T> the type of elements in this sequence
 * 
 * @author Silvere Martin-Michiellot (silvere.martin@gmail.com)
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public interface Sequence<T> extends MathematicalFunction<Integer, T> {

    /**
     * Returns the n-th term of the sequence (0-indexed).
     * 
     * @param n the index (n ≥ 0)
     * @return a(n)
     * @throws IllegalArgumentException if n < 0
     */
    T get(int n);

    /**
     * Function interface implementation - delegates to get().
     * 
     * @param n the index
     * @return a(n)
     */
    @Override
    default T apply(Integer n) {
        return get(n);
    }

    /**
     * MathematicalFunction interface implementation - delegates to get().
     * 
     * @param n the index
     * @return a(n)
     */
    @Override
    default T evaluate(Integer n) {
        return get(n);
    }

    @Override
    default String getDomain() {
        return "ℕ";
    }

    @Override
    default String getCodomain() {
        return "?";
    }

    /**
     * Returns the OEIS identifier if applicable (e.g., "A000045" for Fibonacci).
     * 
     * @return OEIS ID or null
     */
    default String getOeisId() {
        return null;
    }

    /**
     * Returns the human-readable name of this sequence.
     * 
     * @return sequence name
     */
    default String getName() {
        return getClass().getSimpleName();
    }

    /**
     * Returns the mathematical formula for this sequence (if known).
     * 
     * @return formula string or null
     */
    default String getFormula() {
        return null;
    }
}
