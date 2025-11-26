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
package org.jscience.mathematics.discrete;

import java.math.BigInteger;

/**
 * Combinatorial functions and utilities.
 * <p>
 * Provides methods for counting, permutations, combinations, and binomial
 * coefficients.
 * </p>
 * 
 * @author Silvere Martin-Michiellot (silvere.martin@gmail.com)
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public class Combinatorics {

    /**
     * Computes n! (factorial).
     * 
     * @param n non-negative integer
     * @return n!
     */
    public static BigInteger factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial undefined for negative numbers");
        }

        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    /**
     * Computes binomial coefficient C(n, k) = n! / (k! * (n-k)!).
     * Also known as "n choose k".
     * 
     * @param n total items
     * @param k items to choose
     * @return binomial coefficient
     */
    public static BigInteger binomial(int n, int k) {
        if (k < 0 || k > n) {
            return BigInteger.ZERO;
        }

        if (k > n - k) {
            k = n - k; // Optimization: C(n, k) = C(n, n-k)
        }

        BigInteger result = BigInteger.ONE;
        for (int i = 0; i < k; i++) {
            result = result.multiply(BigInteger.valueOf(n - i));
            result = result.divide(BigInteger.valueOf(i + 1));
        }
        return result;
    }

    /**
     * Computes the number of permutations P(n, k) = n! / (n-k)!.
     * 
     * @param n total items
     * @param k items to arrange
     * @return number of permutations
     */
    public static BigInteger permutations(int n, int k) {
        if (k < 0 || k > n) {
            return BigInteger.ZERO;
        }

        BigInteger result = BigInteger.ONE;
        for (int i = 0; i < k; i++) {
            result = result.multiply(BigInteger.valueOf(n - i));
        }
        return result;
    }

    /**
     * Computes Catalan number C_n = (2n)! / ((n+1)! * n!).
     * 
     * @param n index
     * @return nth Catalan number
     */
    public static BigInteger catalan(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Catalan number undefined for negative index");
        }

        return binomial(2 * n, n).divide(BigInteger.valueOf(n + 1));
    }

    /**
     * Computes Stirling number of the second kind S(n, k).
     * Represents the number of ways to partition n objects into k non-empty
     * subsets.
     * 
     * @param n number of objects
     * @param k number of subsets
     * @return Stirling number S(n, k)
     */
    public static BigInteger stirling2(int n, int k) {
        if (k == 0 || k > n) {
            return BigInteger.ZERO;
        }
        if (k == n || k == 1) {
            return BigInteger.ONE;
        }

        // S(n, k) = k * S(n-1, k) + S(n-1, k-1)
        return stirling2(n - 1, k).multiply(BigInteger.valueOf(k))
                .add(stirling2(n - 1, k - 1));
    }
}
