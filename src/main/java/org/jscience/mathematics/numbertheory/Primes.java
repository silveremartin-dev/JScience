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
import java.util.*;

/**
 * Prime number utilities including generation, testing, and factorization.
 * 
 * @author Silvere Martin-Michiellot (silvere.martin@gmail.com)
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public class Primes {

    private static final int MILLER_RABIN_ITERATIONS = 20;

    /**
     * Tests if a number is prime using Miller-Rabin primality test.
     * 
     * @param n the number to test
     * @return true if probably prime
     */
    public static boolean isPrime(BigInteger n) {
        if (n.compareTo(BigInteger.TWO) < 0) {
            return false;
        }
        if (n.equals(BigInteger.TWO) || n.equals(BigInteger.valueOf(3))) {
            return true;
        }
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return false;
        }

        return n.isProbablePrime(MILLER_RABIN_ITERATIONS);
    }

    /**
     * Generates primes up to n using Sieve of Eratosthenes.
     * 
     * @param n upper limit
     * @return list of primes up to n
     */
    public static List<Integer> sieveOfEratosthenes(int n) {
        if (n < 2) {
            return Collections.emptyList();
        }

        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;

        for (int i = 2; i * i <= n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }
        return primes;
    }

    /**
     * Computes prime factorization of n.
     * 
     * @param n number to factorize
     * @return map of prime factors to their exponents
     */
    public static Map<BigInteger, Integer> factorize(BigInteger n) {
        Map<BigInteger, Integer> factors = new HashMap<>();

        if (n.compareTo(BigInteger.ONE) <= 0) {
            return factors;
        }

        // Trial division by small primes
        BigInteger two = BigInteger.TWO;
        while (n.mod(two).equals(BigInteger.ZERO)) {
            factors.merge(two, 1, Integer::sum);
            n = n.divide(two);
        }

        // Try odd numbers
        BigInteger divisor = BigInteger.valueOf(3);
        BigInteger limit = n.sqrt().add(BigInteger.ONE);

        while (divisor.compareTo(limit) <= 0) {
            while (n.mod(divisor).equals(BigInteger.ZERO)) {
                factors.merge(divisor, 1, Integer::sum);
                n = n.divide(divisor);
            }
            divisor = divisor.add(BigInteger.TWO);
        }

        if (n.compareTo(BigInteger.ONE) > 0) {
            factors.put(n, 1);
        }

        return factors;
    }

    /**
     * Returns the next prime after n.
     * 
     * @param n starting number
     * @return next prime number
     */
    public static BigInteger nextPrime(BigInteger n) {
        if (n.compareTo(BigInteger.TWO) < 0) {
            return BigInteger.TWO;
        }

        BigInteger candidate = n.add(BigInteger.ONE);
        if (candidate.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            candidate = candidate.add(BigInteger.ONE);
        }

        while (!isPrime(candidate)) {
            candidate = candidate.add(BigInteger.TWO);
        }

        return candidate;
    }

    /**
     * Computes the greatest common divisor using Euclid's algorithm.
     * 
     * @param a first number
     * @param b second number
     * @return gcd(a, b)
     */
    public static BigInteger gcd(BigInteger a, BigInteger b) {
        return a.gcd(b);
    }

    /**
     * Computes the least common multiple.
     * 
     * @param a first number
     * @param b second number
     * @return lcm(a, b)
     */
    public static BigInteger lcm(BigInteger a, BigInteger b) {
        if (a.equals(BigInteger.ZERO) || b.equals(BigInteger.ZERO)) {
            return BigInteger.ZERO;
        }
        return a.multiply(b).divide(gcd(a, b)).abs();
    }
}
