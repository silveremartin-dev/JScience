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
package org.jscience.mathematics.numbertheory;

import org.jscience.mathematics.algebra.Ring;
import org.jscience.mathematics.number.Integer;

/**
 * Modular integer representing an element of ℤ/nℤ.
 * <p>
 * This is a proper algebraic structure (ring when n is composite, field when n
 * is prime).
 * </p>
 * 
 * @author Silvere Martin-Michiellot (silvere.martin@gmail.com)
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public final class ModularInteger implements Ring<ModularInteger> {

    private final Integer value;
    private final Integer modulus;

    private ModularInteger(Integer value, Integer modulus) {
        this.value = value.mod(modulus);
        this.modulus = modulus;
    }

    /**
     * Creates a modular integer.
     * 
     * @param value   the value
     * @param modulus the modulus (must be > 1)
     * @return value mod modulus
     */
    public static ModularInteger of(Integer value, Integer modulus) {
        if (modulus.compareTo(Integer.ONE) <= 0) {
            throw new IllegalArgumentException("Modulus must be > 1");
        }
        return new ModularInteger(value, modulus);
    }

    @Override
    public ModularInteger add(ModularInteger other) {
        checkSameModulus(other);
        return new ModularInteger(value.add(other.value), modulus);
    }

    @Override
    public ModularInteger multiply(ModularInteger other) {
        checkSameModulus(other);
        return new ModularInteger(value.multiply(other.value), modulus);
    }

    @Override
    public ModularInteger negate() {
        return new ModularInteger(value.negate(), modulus);
    }

    @Override
    public ModularInteger zero() {
        return new ModularInteger(Integer.ZERO, modulus);
    }

    @Override
    public ModularInteger one() {
        return new ModularInteger(Integer.ONE, modulus);
    }

    /**
     * Computes modular multiplicative inverse (only exists if gcd(value, modulus) =
     * 1).
     * 
     * @return this^(-1) mod modulus
     * @throws ArithmeticException if no inverse exists
     */
    public ModularInteger inverse() {
        return new ModularInteger(value.modInverse(modulus), modulus);
    }

    /**
     * Modular exponentiation.
     * 
     * @param exponent the exponent
     * @return this^exponent mod modulus
     */
    public ModularInteger pow(Integer exponent) {
        return new ModularInteger(value.modPow(exponent, modulus), modulus);
    }

    public Integer getValue() {
        return value;
    }

    public Integer getModulus() {
        return modulus;
    }

    private void checkSameModulus(ModularInteger other) {
        if (!modulus.equals(other.modulus)) {
            throw new IllegalArgumentException("Moduli must match");
        }
    }

    @Override
    public boolean contains(ModularInteger element) {
        return modulus.equals(element.modulus);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String description() {
        return "ℤ/" + modulus + "ℤ";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ModularInteger))
            return false;
        ModularInteger other = (ModularInteger) obj;
        return value.equals(other.value) && modulus.equals(other.modulus);
    }

    @Override
    public int hashCode() {
        return value.hashCode() ^ modulus.hashCode();
    }

    @Override
    public String toString() {
        return value + " (mod " + modulus + ")";
    }
}
