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
package org.jscience.mathematics.analysis.series;

import org.jscience.mathematics.numbertheory.Primes;
import java.math.BigInteger;
import java.util.List;

/**
 * Prime number sequence: 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, ...
 * <p>
 * OEIS A000040: The prime numbers.
 * </p>
 * 
 * @author Silvere Martin-Michiellot (silvere.martin@gmail.com)
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public class PrimeSequence implements IntegerSequence {

    private final List<Integer> cachedPrimes;

    public PrimeSequence() {
        // Pre-compute first 10000 primes (up to ~104730)
        this.cachedPrimes = Primes.sieveOfEratosthenes(105000);
    }

    @Override
    public BigInteger get(int n) {
        if (n < 0)
            throw new IllegalArgumentException("n must be ≥ 0");

        if (n < cachedPrimes.size()) {
            return BigInteger.valueOf(cachedPrimes.get(n));
        }

        // For larger n, use nextPrime beyond cached range
        BigInteger p = BigInteger.valueOf(cachedPrimes.get(cachedPrimes.size() - 1));
        for (int i = cachedPrimes.size(); i <= n; i++) {
            p = Primes.nextPrime(p);
        }
        return p;
    }

    @Override
    public String getOeisId() {
        return "A000040";
    }

    @Override
    public String getName() {
        return "Prime numbers";
    }

    @Override
    public String getFormula() {
        return "p(n) = n-th prime number";
    }
}
