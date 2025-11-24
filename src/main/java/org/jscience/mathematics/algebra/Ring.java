/*
 * JScience Reimagined - Unified Scientific Computing Framework
 * Copyright (c) 2025 Silvere Martin-Michiellot
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.jscience.mathematics.algebra;

/**
 * A ring is an abelian group with a second binary operation (multiplication).
 * <p>
 * Rings are fundamental algebraic structures that generalize arithmetic.
 * They appear throughout mathematics, from number theory to algebraic geometry.
 * </p>
 * 
 * <h2>Mathematical Definition</h2>
 * <p>
 * A ring (R, +, ×) consists of:
 * <ol>
 * <li>A set R</li>
 * <li>An addition operation + making (R, +) an abelian group</li>
 * <li>A multiplication operation × satisfying:
 * <ul>
 * <li><strong>Closure</strong>: ∀ a, b ∈ R: a × b ∈ R</li>
 * <li><strong>Associativity</strong>: (a × b) × c = a × (b × c)</li>
 * <li><strong>Distributivity</strong>: a × (b + c) = (a × b) + (a × c) and (a +
 * b) × c = (a × c) + (b × c)</li>
 * </ul>
 * </li>
 * </ol>
 * </p>
 * 
 * <h2>Examples</h2>
 * <ul>
 * <li>(ℤ, +, ×) - Integers (commutative ring with unity)</li>
 * <li>(ℝ, +, ×) - Real numbers (field, thus also a ring)</li>
 * <li>(M₂(ℝ), +, ×) - 2×2 real matrices (non-commutative ring)</li>
 * <li>(ℤ/nℤ, +, ×) - Integers modulo n</li>
 * <li>(ℝ[x], +, ×) - Polynomials with real coefficients</li>
 * </ul>
 * 
 * <h2>Special Types of Rings</h2>
 * <ul>
 * <li><strong>Commutative Ring</strong>: Multiplication commutes (a × b = b ×
 * a)</li>
 * <li><strong>Ring with Unity</strong>: Has multiplicative identity (1)</li>
 * <li><strong>Integral Domain</strong>: Commutative ring with unity, no zero
 * divisors</li>
 * <li><strong>Division Ring</strong>: Every non-zero element has multiplicative
 * inverse</li>
 * <li><strong>Field</strong>: Commutative division ring</li>
 * </ul>
 * 
 * <h2>Usage</h2>
 * 
 * <pre>{@code
 * // Integer ring
 * Ring<Integer> integers = IntegerRing.getInstance();
 * 
 * Integer a = 5;
 * Integer b = 3;
 * 
 * // Ring operations
 * Integer sum = integers.add(a, b); // 8 (abelian group)
 * Integer product = integers.multiply(a, b); // 15 (ring multiplication)
 * Integer diff = integers.subtract(a, b); // 2
 * 
 * // Ring properties
 * Integer zero = integers.zero(); // 0 (additive identity)
 * Integer one = integers.one(); // 1 (multiplicative identity)
 * 
 * // Distributivity: a × (b + c) = (a × b) + (a × c)
 * Integer c = 2;
 * Integer left = integers.multiply(a, integers.add(b, c));
 * Integer right = integers.add(
 *         integers.multiply(a, b),
 *         integers.multiply(a, c));
 * assert left.equals(right); // 5 × (3 + 2) = 5 × 3 + 5 × 2 = 25
 * }</pre>
 * 
 * @param <E> the type of elements in this ring
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @version 1.0
 * @since 1.0
 * 
 * @see AbelianGroup
 * @see Field
 */
public interface Ring<E> extends AbelianGroup<E> {

    /**
     * Returns the product of two elements.
     * <p>
     * Multiplication must be associative and distribute over addition:
     * <ul>
     * <li>(a × b) × c = a × (b × c)</li>
     * <li>a × (b + c) = (a × b) + (a × c)</li>
     * <li>(a + b) × c = (a × c) + (b × c)</li>
     * </ul>
     * </p>
     * 
     * @param a the first factor
     * @param b the second factor
     * @return a × b
     * @throws NullPointerException if either argument is null
     * 
     * @see #isCommutative()
     * @see #one()
     */
    E multiply(E a, E b);

    /**
     * Returns the multiplicative identity (one element), if it exists.
     * <p>
     * Satisfies: 1 × a = a × 1 = a for all elements a.
     * </p>
     * <p>
     * Not all rings have a multiplicative identity (e.g., even integers).
     * If this ring doesn't have one, this method throws
     * {@code UnsupportedOperationException}.
     * </p>
     * 
     * @return the multiplicative identity
     * @throws UnsupportedOperationException if this ring has no unity
     * 
     * @see #hasUnity()
     * @see #zero()
     */
    E one();

    /**
     * Tests whether this ring has a multiplicative identity.
     * 
     * @return {@code true} if this ring has unity (element 1)
     * 
     * @see #one()
     */
    boolean hasUnity();

    /**
     * Tests whether multiplication is commutative in this ring.
     * <p>
     * A commutative ring satisfies: a × b = b × a for all elements.
     * </p>
     * <p>
     * Examples:
     * <ul>
     * <li>Commutative: ℤ, ℝ, ℂ, ℚ[x] (polynomials)</li>
     * <li>Non-commutative: M₂(ℝ) (matrices), ℍ (quaternions)</li>
     * </ul>
     * </p>
     * 
     * @return {@code true} if multiplication commutes
     */
    boolean isMultiplicationCommutative();

    /**
     * Returns the nth power of an element.
     * <p>
     * Defined as:
     * <ul>
     * <li>a⁰ = 1 (if unity exists)</li>
     * <li>aⁿ = a × a × ... × a (n times) for n > 0</li>
     * </ul>
     * </p>
     * 
     * @param element the base
     * @param n       the exponent (must be non-negative)
     * @return element^n
     * @throws IllegalArgumentException      if n < 0
     * @throws UnsupportedOperationException if n = 0 and ring has no unity
     * 
     * @see #one()
     */
    default E pow(E element, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Negative exponents require Field");
        }
        if (n == 0) {
            return one();
        }

        E result = element;
        for (int i = 1; i < n; i++) {
            result = multiply(result, element);
        }
        return result;
    }
}
