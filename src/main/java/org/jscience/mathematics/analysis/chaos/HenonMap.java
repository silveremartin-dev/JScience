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

import org.jscience.mathematics.geometry.Point2D;

/**
 * The Hénon Map:
 * x_{n+1} = 1 - a * x_n^2 + y_n
 * y_{n+1} = b * x_n
 * <p>
 * A discrete-time dynamical system that exhibits chaotic behavior.
 * Classic values: a = 1.4, b = 0.3.
 * </p>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public class HenonMap implements DiscreteMap<Point2D> {

    private final double a;
    private final double b;

    /**
     * Creates a Hénon Map with default chaotic parameters (a=1.4, b=0.3).
     */
    public HenonMap() {
        this(1.4, 0.3);
    }

    /**
     * Creates a Hénon Map with specified parameters.
     * 
     * @param a parameter a
     * @param b parameter b
     */
    public HenonMap(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Point2D evaluate(Point2D p) {
        double x = p.getX().doubleValue();
        double y = p.getY().doubleValue();

        double nextX = 1.0 - a * x * x + y;
        double nextY = b * x;

        return Point2D.of(nextX, nextY);
    }

    @Override
    public String getDomain() {
        return "ℝ²";
    }

    @Override
    public String getCodomain() {
        return "ℝ²";
    }

    @Override
    public String toString() {
        return "HenonMap(a=" + a + ", b=" + b + ")";
    }
}
