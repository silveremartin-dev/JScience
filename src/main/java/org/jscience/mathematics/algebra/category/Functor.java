package org.jscience.mathematics.algebra.category;

/**
 * Represents a Functor between two categories.
 * <p>
 * A functor F: C -> D maps objects of C to objects of D and morphisms of C to
 * morphisms of D,
 * preserving identity and composition.
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
public interface Functor<O1, M1, O2, M2> {

    /**
     * Maps an object from the source category to the target category.
     * 
     * @param object the object in C
     * @return F(object) in D
     */
    O2 applyObject(O1 object);

    /**
     * Maps a morphism from the source category to the target category.
     * 
     * @param morphism the morphism f: A -> B in C
     * @return F(f): F(A) -> F(B) in D
     */
    M2 applyMorphism(M1 morphism);

    /**
     * Returns the source category.
     * 
     * @return the domain category
     */
    Category<O1, M1> getSourceCategory();

    /**
     * Returns the target category.
     * 
     * @return the codomain category
     */
    Category<O2, M2> getTargetCategory();
}
