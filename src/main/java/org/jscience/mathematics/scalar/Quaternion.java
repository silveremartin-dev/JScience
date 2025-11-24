/*
 * JScience Reimagined - Unified Scientific Computing Framework
 * Copyright (c) 2025 Silvere Martin-Michiellot
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.jscience.mathematics.scalar;

/**
 * Generic Quaternion implementation.
 * <p>
 * Quaternions are hypercomplex numbers of the form w + xi + yj + zk.
 * They are non-commutative under multiplication and are widely used
 * for representing 3D rotations and in physics.
 * </p>
 * 
 * @param <T> the underlying scalar type
 */
public final class Quaternion<T> {

    private final T w, x, y, z;
    private final ScalarType<T> scalar;

    public Quaternion(T w, T x, T y, T z, ScalarType<T> scalar) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
        this.scalar = scalar;
    }

    public T getW() {
        return w;
    }

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }

    public T getZ() {
        return z;
    }

    public Quaternion<T> add(Quaternion<T> other) {
        return new Quaternion<>(
                scalar.add(w, other.w),
                scalar.add(x, other.x),
                scalar.add(y, other.y),
                scalar.add(z, other.z),
                scalar);
    }

    // Hamilton product (non-commutative)
    public Quaternion<T> multiply(Quaternion<T> other) {
        // w1w2 - x1x2 - y1y2 - z1z2
        T newW = scalar.subtract(
                scalar.subtract(
                        scalar.subtract(scalar.multiply(w, other.w), scalar.multiply(x, other.x)),
                        scalar.multiply(y, other.y)),
                scalar.multiply(z, other.z));

        // w1x2 + x1w2 + y1z2 - z1y2
        T newX = scalar.subtract(
                scalar.add(
                        scalar.add(scalar.multiply(w, other.x), scalar.multiply(x, other.w)),
                        scalar.multiply(y, other.z)),
                scalar.multiply(z, other.y));

        // w1y2 - x1z2 + y1w2 + z1x2
        T newY = scalar.add(
                scalar.add(
                        scalar.subtract(scalar.multiply(w, other.y), scalar.multiply(x, other.z)),
                        scalar.multiply(y, other.w)),
                scalar.multiply(z, other.x));

        // w1z2 + x1y2 - y1x2 + z1w2
        T newZ = scalar.add(
                scalar.subtract(
                        scalar.add(scalar.multiply(w, other.z), scalar.multiply(x, other.y)),
                        scalar.multiply(y, other.x)),
                scalar.multiply(z, other.w));

        return new Quaternion<>(newW, newX, newY, newZ, scalar);
    }

    public Quaternion<T> conjugate() {
        return new Quaternion<>(
                w,
                scalar.negate(x),
                scalar.negate(y),
                scalar.negate(z),
                scalar);
    }

    public T normSquared() {
        return scalar.add(
                scalar.add(scalar.multiply(w, w), scalar.multiply(x, x)),
                scalar.add(scalar.multiply(y, y), scalar.multiply(z, z)));
    }

    @Override
    public String toString() {
        return w + " + " + x + "i + " + y + "j + " + z + "k";
    }
}
