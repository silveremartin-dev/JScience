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
package org.jscience.mathematics.analysis.chaos;

/**
 * The Logistic Map: x_{n+1} = r * x_n * (1 - x_n).
 * <p>
 * A classic example of how complex, chaotic behaviour can arise from very
 * simple
 * non-linear dynamical equations.
 * </p>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public class LogisticMap implements DiscreteMap<Double> {

    private final double r;

    /**
     * Creates a Logistic Map with parameter r.
     * 
     * @param r the growth rate parameter (typically [0, 4])
     */
    public LogisticMap(double r) {
        this.r = r;
    }

    @Override
    public Double evaluate(Double x) {
        return r * x * (1.0 - x);
    }

    @Override
    public String getDomain() {
        return "[0, 1]";
    }

    @Override
    public String getCodomain() {
        return "[0, 1]";
    }

    @Override
    public String toString() {
        return "LogisticMap(r=" + r + ")";
    }
}
