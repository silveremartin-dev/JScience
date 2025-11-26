package org.jscience.mathematics.analysis;

/**
 * Represents a mathematical function from a domain D to a codomain R.
 * <p>
 * This interface generalizes the concept of a function, allowing for
 * symbolic manipulation, evaluation, and composition.
 * </p>
 * 
 * @param <D> the type of the domain (input)
 * @param <R> the type of the codomain (output)
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public interface Function<D, R> {

    /**
     * Evaluates this function at the given point.
     * 
     * @param x the input point
     * @return the value of the function at x
     */
    R evaluate(D x);

    /**
     * Returns the composition of this function with another function.
     * (f o g)(x) = f(g(x))
     * 
     * @param <V>   the domain of the inner function
     * @param inner the inner function g
     * @return the composite function f o g
     */
    default <V> Function<V, R> compose(Function<V, D> inner) {
        return x -> evaluate(inner.evaluate(x));
    }
}
