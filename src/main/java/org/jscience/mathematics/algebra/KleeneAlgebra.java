package org.jscience.mathematics.algebra;

/**
 * A Kleene Algebra is an idempotent semiring with a closure operator (Kleene
 * star).
 * <p>
 * It is used in theoretical computer science to model regular expressions,
 * finite automata, and program semantics.
 * </p>
 * 
 * <h2>Mathematical Definition</h2>
 * <p>
 * A Kleene algebra (K, +, ·, *, 0, 1) is an idempotent semiring (a + a = a)
 * equipped with a unary operation * satisfying:
 * <ul>
 * <li>1 + a · a* ≤ a*</li>
 * <li>1 + a* · a ≤ a*</li>
 * <li>b + a · x ≤ x ⇒ a* · b ≤ x</li>
 * <li>b + x · a ≤ x ⇒ b · a* ≤ x</li>
 * </ul>
 * where a ≤ b is defined as a + b = b.
 * </p>
 * 
 * @param <E> the type of elements
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public interface KleeneAlgebra<E> extends Semiring<E> {

    /**
     * The Kleene star operator (closure).
     * Represents zero or more repetitions of the element.
     * 
     * @param a the element
     * @return a*
     */
    E star(E a);

    /**
     * The plus operator (a+ = a · a*).
     * Represents one or more repetitions.
     * 
     * @param a the element
     * @return a+
     */
    default E plus(E a) {
        return multiply(a, star(a));
    }
}
