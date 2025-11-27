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
 * LICENSE

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
 * A line in 2D Euclidean space.
 * <p>
 * Represented in parametric form: P(t) = point + t * direction
 * </p>
 * 
 * @author Silvere Martin-Michiellot (silvere.martin@gmail.com)
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public final class Line2D {

    private final Point2D point;
    private final Vector2D direction;

    public Line2D(Point2D point, Vector2D direction) {
        this.point = point;
        this.direction = direction.normalize();
    }

    public Point2D getPoint() {
        return point;
    }

    public Vector2D getDirection() {
        return direction;
    }

    public Point2D pointAt(Real t) {
        return point.add(direction.scale(t));
    }

    public Real distanceTo(Point2D p) {
        // Distance from point to line
        Vector2D v = Vector2D.of(p.getX().subtract(point.getX()), p.getY().subtract(point.getY()));
        Real projection = v.dot(direction);
        Point2D closest = pointAt(projection);
        return p.distanceTo(closest);
    }

    @Override
    public String toString() {
        return "Line[" + point + " + t" + direction + "]";
    }
}
