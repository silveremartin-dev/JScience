package org.jscience.mathematics.algebra;

/**
 * Represents a Monad in Category Theory.
 * <p>
 * A Monad on a category C is an endofunctor T: C → C equipped with two natural
 * transformations:
 * <ul>
 * <li>Unit (η): Id → T</li>
 * <li>Multiplication (μ): T² → T</li>
 * </ul>
 * satisfying associativity and identity laws.
 * </p>
 * <p>
 * In computer science, Monads are used to model computations with side effects
 * (state, I/O, exceptions, non-determinism).
 * </p>
 * 
 * @param <O> the type of objects in the category
 * @param <M> the type of morphisms in the category
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public interface Monad<O, M> extends Functor<O, M, O, M> {

    /**
     * The unit natural transformation (η).
     * Lifts a value into the monadic context.
     * Also known as 'return' or 'pure'.
     * 
     * @param object the object to lift
     * @return the morphism from object to T(object)
     */
    M unit(O object);

    /**
     * The multiplication natural transformation (μ).
     * Flattens a nested monadic value.
     * Also known as 'join' or 'flatten'.
     * 
     * @param nestedObject the object representing T(T(x))
     * @return the morphism from T(T(x)) to T(x)
     */
    M join(O nestedObject);

    /**
     * Bind operation (>>=).
     * Maps a function over the monadic value and flattens the result.
     * 
     * @param object   the monadic object T(x)
     * @param morphism the morphism from x to T(y)
     * @return the result T(y)
     */
    // Note: Signature is tricky without HKTs. This is a conceptual definition.
    // In a concrete implementation (like ListMonad), this would be:
    // List<Y> bind(List<X> list, Function<X, List<Y>> f)
}
