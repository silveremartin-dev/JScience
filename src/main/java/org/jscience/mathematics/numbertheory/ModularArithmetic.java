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

import java.math.BigInteger;

/**
 * Modular arithmetic operations.
 * <p>
 * Provides operations in ℤ/nℤ (integers modulo n).
 * </p>
 * 
 * @author Silvere Martin-Michiellot (silvere.martin@gmail.com)
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public class ModularArithmetic {

    private final BigInteger modulus;

    /**
     * Creates modular arithmetic with the given modulus.
     * 
     * @param modulus the modulus (must be positive)
     */
    public ModularArithmetic(BigInteger modulus) {
        if (modulus.compareTo(BigInteger.ONE) <= 0) {
            throw new IllegalArgumentException("Modulus must be > 1");
        }
        this.modulus = modulus;
    }

    /**
     * Computes (a + b) mod n.
     * 
     * @param a first operand
     * @param b second operand
     * @return (a + b) mod n
     */
    public BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b).mod(modulus);
    }

    /**
     * Computes (a - b) mod n.
     * 
     * @param a first operand
     * @param b second operand
     * @return (a - b) mod n
     */
    public BigInteger subtract(BigInteger a, BigInteger b) {
        return a.subtract(b).mod(modulus);
    }

    /**
     * Computes (a * b) mod n.
     * 
     * @param a first operand
     * @param b second operand
     * @return (a * b) mod n
     */
    public BigInteger multiply(BigInteger a, BigInteger b) {
        return a.multiply(b).mod(modulus);
    }

    /**
     * Computes modular exponentiation a^b mod n.
     * 
     * @param base     the base
     * @param exponent the exponent
     * @return a^b mod n
     */
    public BigInteger power(BigInteger base, BigInteger exponent) {
        return base.modPow(exponent, modulus);
    }

    /**
     * Computes modular multiplicative inverse a^(-1) mod n.
     * 
     * @param a the number to invert
     * @return a^(-1) mod n
     * @throws ArithmeticException if no inverse exists
     */
    public BigInteger inverse(BigInteger a) {
        return a.modInverse(modulus);
    }

    /**
     * Computes (a / b) mod n = a * b^(-1) mod n.
     * 
     * @param a dividend
     * @param b divisor
     * @return (a / b) mod n
     * @throws ArithmeticException if b has no inverse
     */
    public BigInteger divide(BigInteger a, BigInteger b) {
        return multiply(a, inverse(b));
    }

    /**
     * Computes Euler's totient function φ(n).
     * φ(n) = count of integers ≤ n that are coprime to n.
     * 
     * @param n the number
     * @return φ(n)
     */
    public static BigInteger eulerTotient(BigInteger n) {
        if (n.equals(BigInteger.ONE)) {
            return BigInteger.ONE;
        }

        BigInteger result = n;

        // For each distinct prime factor p, multiply result by (1 - 1/p)
        BigInteger temp = n;
        BigInteger two = BigInteger.TWO;

        if (temp.mod(two).equals(BigInteger.ZERO)) {
            result = result.divide(two);
            while (temp.mod(two).equals(BigInteger.ZERO)) {
                temp = temp.divide(two);
            }
        }

        BigInteger i = BigInteger.valueOf(3);
        while (i.multiply(i).compareTo(temp) <= 0) {
            if (temp.mod(i).equals(BigInteger.ZERO)) {
                result = result.subtract(result.divide(i));
                while (temp.mod(i).equals(BigInteger.ZERO)) {
                    temp = temp.divide(i);
                }
            }
            i = i.add(BigInteger.TWO);
        }

        if (temp.compareTo(BigInteger.ONE) > 0) {
            result = result.subtract(result.divide(temp));
        }

        return result;
    }

    public BigInteger getModulus() {
        return modulus;
    }
}
