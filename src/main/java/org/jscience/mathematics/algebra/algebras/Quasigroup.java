package org.jscience.mathematics.algebra.algebras;

import org.jscience.mathematics.algebra.Set;

/**
 * A Quasigroup is a Magma where division is always possible.
 * <p>
 * For every a, b in Q, there exist unique x, y in Q such that:
 * a * x = b
 * y * a = b
 * </p>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public interface Quasigroup<E> extends Magma<E> {
    // No additional methods required for the interface definition,
    // but implies existence of left and right division.
}
