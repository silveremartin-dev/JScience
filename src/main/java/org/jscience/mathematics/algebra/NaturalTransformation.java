package org.jscience.mathematics.algebra;

/**
 * Represents a Natural Transformation between two functors.
 * <p>
 * A natural transformation η: F => G provides a way to transform one functor
 * into another
 * while respecting the internal structure of the categories involved.
 * </p>
 * 
 * @param <O1> Object type of source category
 * @param <M1> Morphism type of source category
 * @param <O2> Object type of target category
 * @param <M2> Morphism type of target category
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public interface NaturalTransformation<O1, M1, O2, M2> {

    /**
     * Returns the component of the natural transformation at a given object.
     * <p>
     * For an object X in C, the component η_X is a morphism in D from F(X) to G(X).
     * </p>
     * 
     * @param object the object X in C
     * @return the morphism η_X: F(X) -> G(X) in D
     */
    M2 component(O1 object);

    /**
     * Returns the source functor F.
     */
    Functor<O1, M1, O2, M2> getSourceFunctor();

    /**
     * Returns the target functor G.
     */
    Functor<O1, M1, O2, M2> getTargetFunctor();
}
