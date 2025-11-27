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
package org.jscience.mathematics.analysis;

import java.util.function.Function;

/**
 * A mathematical function from domain D to codomain C.
 * <p>
 * This extends Java's Function to add mathematical metadata and operations.
 * Sequences can be viewed as functions ℕ → T.
 * </p>
 * 
 * @param <D> domain type
 * @param <C> codomain type
 * 
 * @author Silvere Martin-Michiellot (silvere.martin@gmail.com)
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public interface MathematicalFunction<D, C> extends Function<D, C> {

    /**
     * Evaluates the function at x.
     * 
     * @param x input value
     * @return f(x)
     */
    @Override
    C apply(D x);

    /**
     * Returns the domain description.
     * 
     * @return domain name (e.g., "ℝ", "ℕ", "ℂ")
     */
    default String getDomain() {
        return "?";
    }

    /**
     * Returns the codomain description.
     * 
     * @return codomain name
     */
    default String getCodomain() {
        return "?";
    }

    /**
     * Composes this function with another: g ∘ f.
     * 
     * @param <R>   result type
     * @param after function to apply after this
     * @return g(f(x))
     */
    @Override
    default <R> MathematicalFunction<D, R> andThen(Function<? super C, ? extends R> after) {
        return new MathematicalFunction<D, R>() {
            @Override
            public R apply(D x) {
                return after.apply(MathematicalFunction.this.apply(x));
            }
        };
    }
}
