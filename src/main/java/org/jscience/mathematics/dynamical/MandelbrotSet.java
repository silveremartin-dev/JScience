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
package org.jscience.mathematics.dynamical;

import org.jscience.mathematics.number.Complex;

/**
 * The Mandelbrot Set as a proper mathematical set.
 * <p>
 * The Mandelbrot set M ⊂ ℂ is defined as:
 * M = { c ∈ ℂ : the sequence z₀ = 0, zₙ₊₁ = zₙ² + c remains bounded }
 * </p>
 * <p>
 * This implementation provides set membership testing using the escape-time
 * algorithm.
 * </p>
 * 
 * @author Silvere Martin-Michiellot (silvere.martin@gmail.com)
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public class MandelbrotSet implements org.jscience.mathematics.algebra.Set<Complex> {

    private final int maxIterations;
    private static final double ESCAPE_RADIUS_SQ = 4.0;

    /**
     * Creates a Mandelbrot set with default iteration limit.
     */
    public MandelbrotSet() {
        this(1000);
    }

    /**
     * Creates a Mandelbrot set with specified iteration limit.
     * 
     * @param maxIterations maximum iterations for membership test
     */
    public MandelbrotSet(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    @Override
    public boolean contains(Complex c) {
        Complex z = Complex.ZERO;

        for (int i = 0; i < maxIterations; i++) {
            z = z.multiply(z).add(c);

            double r = z.realValue();
            double im = z.imaginaryValue();
            if (r * r + im * im > ESCAPE_RADIUS_SQ) {
                return false; // Diverges, not in set
            }
        }

        return true; // Appears to be bounded
    }

    @Override
    public boolean isEmpty() {
        return false; // Mandelbrot set is non-empty
    }

    @Override
    public String description() {
        return "Mandelbrot Set M = { c ∈ ℂ : z_{n+1} = z_n² + c, z_0 = 0 remains bounded }";
    }

    /**
     * Computes the escape time for visualization.
     * 
     * @param c the point to test
     * @return number of iterations before escape, or maxIterations if bounded
     */
    public int escapeTime(Complex c) {
        Complex z = Complex.ZERO;

        for (int i = 0; i < maxIterations; i++) {
            z = z.multiply(z).add(c);

            double r = z.realValue();
            double im = z.imaginaryValue();
            if (r * r + im * im > ESCAPE_RADIUS_SQ) {
                return i;
            }
        }

        return maxIterations;
    }

    /**
     * Returns the iteration limit for this set.
     */
    public int getMaxIterations() {
        return maxIterations;
    }
}
