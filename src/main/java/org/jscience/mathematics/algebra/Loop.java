package org.jscience.mathematics.algebra;

/**
 * A Loop is a Quasigroup with an identity element.
 * <p>
 * A Quasigroup is a Magma where division is always possible (Latin Square
 * property).
 * A Loop adds the requirement of a neutral element (identity).
 * Unlike Groups, Loops are not required to be associative.
 * </p>
 * 
 * <h2>Mathematical Definition</h2>
 * <p>
 * A loop (L, ·) is a set L with a binary operation · such that:
 * <ul>
 * <li>For every a, b ∈ L, there exist unique x, y ∈ L such that a · x = b and y
 * · a = b.</li>
 * <li>There exists an identity element e ∈ L such that a · e = a and e · a = a
 * for all a ∈ L.</li>
 * </ul>
 * </p>
 * 
 * @param <E> the type of elements
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public interface Loop<E> extends Magma<E> {

    /**
     * Returns the identity element of the loop.
     * 
     * @return the identity element e
     */
    E identity();

    /**
     * Left division.
     * Returns the unique x such that a · x = b.
     * Often denoted as a \ b.
     * 
     * @param a the left operand
     * @param b the result
     * @return x such that a · x = b
     */
    E leftDivide(E a, E b);

    /**
     * Right division.
     * Returns the unique y such that y · a = b.
     * Often denoted as b / a.
     * 
     * @param a the right operand
     * @param b the result
     * @return y such that y · a = b
     */
    E rightDivide(E a, E b);
}
