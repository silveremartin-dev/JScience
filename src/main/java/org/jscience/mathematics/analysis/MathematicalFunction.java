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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A mathematical function with extended metadata and operations.
 * <p>
 * This extends the JScience Function interface and implements Java's Function
 * for maximum interoperability. Sequences, series, and real functions all
 * extend this.
 * </p>
 * 
 * @param <D> domain type
 * @param <C> codomain type
 * 
 * @author Silvere Martin-Michiellot (silvere.martin@gmail.com)
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public interface MathematicalFunction<D, C> extends Function<D, C>, java.util.function.Function<D, C> {

    /**
     * Evaluates the function at x (from JScience Function interface).
     * 
     * @param x input value
     * @return f(x)
     */
    @Override
    C evaluate(D x);

    /**
     * Java Function interface implementation - delegates to evaluate().
     * 
     * @param x input value
     * @return f(x)
     */
    @Override
    default C apply(D x) {
        return evaluate(x);
    }

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
     * Composes this function with another (from java.util.function.Function).
     * 
     * @param <V>   result type
     * @param after function to apply after this
     * @return composed function
     */
    @Override
    default <V> MathematicalFunction<D, V> andThen(java.util.function.Function<? super C, ? extends V> after) {
        return new MathematicalFunction<D, V>() {
            @Override
            public V evaluate(D x) {
                return after.apply(MathematicalFunction.this.evaluate(x));
            }
        };
    }

    /**
     * Sets the compute backend for this function (e.g., enable GPU).
     * 
     * @param backend the compute backend
     */
    default void setBackend(org.jscience.mathematics.analysis.acceleration.ComputeBackend backend) {
        // Default implementation does nothing (stateless functions)
        // Stateful functions or those with specific GPU kernels should override
    }

    /**
     * Returns the currently set compute backend.
     * 
     * @return the compute backend, or null if none is explicitly set or supported.
     */
    default org.jscience.mathematics.analysis.acceleration.ComputeBackend getBackend() {
        return null; // Default implementation returns null
    }

    /**
     * Evaluates the function for a batch of input values.
     * 
     * @param inputs a collection of input values
     * @return a list of corresponding output values
     */
    default List<C> evaluate(Collection<D> inputs) {
        List<C> results = new ArrayList<>(inputs.size());
        for (D input : inputs) {
            results.add(evaluate(input));
        }
        return results;
    }
}
