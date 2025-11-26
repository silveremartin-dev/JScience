package org.jscience.mathematics.geometry;

import org.jscience.mathematics.number.Real;

/**
 * Represents a line in 3D Euclidean space.
 * <p>
 * Defined by a point P and a direction vector V: L(t) = P + tV.
 * </p>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public class Line3D {

    private final Vector3D point;
    private final Vector3D direction;

    /**
     * Creates a line from a point and a direction.
     * 
     * @param point     a point on the line
     * @param direction the direction vector of the line (will be normalized)
     */
    public Line3D(Vector3D point, Vector3D direction) {
        this.point = point;
        // Normalize direction to ensure consistent parameterization
        // We need to cast the result of normalize() back to Vector3D or create a new
        // one
        // Since normalize() returns Vector<Real>, we construct a new Vector3D
        // This assumes direction is not zero.
        if (direction.norm().equals(Real.ZERO)) {
            throw new IllegalArgumentException("Direction vector cannot be zero");
        }

        // Manual normalization to keep it as Vector3D without complex casting
        Real norm = direction.norm();
        this.direction = new Vector3D(
                direction.x().divide(norm),
                direction.y().divide(norm),
                direction.z().divide(norm));
    }

    public Vector3D getPoint() {
        return point;
    }

    public Vector3D getDirection() {
        return direction;
    }

    /**
     * Calculates the distance from a point to this line.
     * 
     * @param p the point
     * @return the perpendicular distance
     */
    public Real distance(Vector3D p) {
        // d = ||(p - point) x direction|| / ||direction||
        // Since direction is normalized, ||direction|| = 1
        // d = ||(p - point) x direction||

        Vector3D diff = new Vector3D(
                p.x().subtract(point.x()),
                p.y().subtract(point.y()),
                p.z().subtract(point.z()));

        return diff.cross(direction).norm();
    }

    /**
     * Checks if a point lies on this line (within tolerance).
     * 
     * @param p         the point
     * @param tolerance the error tolerance
     * @return true if p is on the line
     */
    public boolean contains(Vector3D p, double tolerance) {
        return distance(p).doubleValue() < tolerance;
    }
}
