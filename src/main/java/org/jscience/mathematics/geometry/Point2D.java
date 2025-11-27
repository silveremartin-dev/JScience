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
package org.jscience.mathematics.geometry;

import org.jscience.mathematics.number.Real;

/**
 * A point in 2D Euclidean space.
 * 
 * @author Silvere Martin-Michiellot (silvere.martin@gmail.com)
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public final class Point2D {

    private final Real x;
    private final Real y;

    private Point2D(Real x, Real y) {
        this.x = x;
        this.y = y;
    }

    public static Point2D of(double x, double y) {
        return new Point2D(Real.of(x), Real.of(y));
    }

    public static Point2D of(Real x, Real y) {
        return new Point2D(x, y);
    }

    public Real getX() {
        return x;
    }

    public Real getY() {
        return y;
    }

    public Real distanceTo(Point2D other) {
        Real dx = x.subtract(other.x);
        Real dy = y.subtract(other.y);
        return dx.multiply(dx).add(dy.multiply(dy)).sqrt();
    }

    public Point2D add(Vector2D vector) {
        return new Point2D(x.add(vector.getX()), y.add(vector.getY()));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point2D))
            return false;
        Point2D other = (Point2D) obj;
        return x.equals(other.x) && y.equals(other.y);
    }

    @Override
    public int hashCode() {
        return x.hashCode() * 31 + y.hashCode();
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
