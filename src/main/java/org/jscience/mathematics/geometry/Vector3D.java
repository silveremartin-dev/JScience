*providing convenient constructors and accessors.*</p>**@author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public class Vector3D extends DenseVector<Real> {

    public static final Vector3D ZERO = new Vector3D(0, 0, 0);
    public static final Vector3D X_AXIS = new Vector3D(1, 0, 0);
    public static final Vector3D Y_AXIS = new Vector3D(0, 1, 0);
    public static final Vector3D Z_AXIS = new Vector3D(0, 0, 1);

    /**
     * Creates a new Vector3D from double coordinates.
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Vector3D(double x, double y, double z) {
        super(Arrays.asList(Real.of(x), Real.of(y), Real.of(z)), Reals.getInstance());
    }

    /**
     * Creates a new Vector3D from Real coordinates.
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Vector3D(Real x, Real y, Real z) {
        super(Arrays.asList(x, y, z), Reals.getInstance());
    }

    public Real x() {
        return get(0);
    }

    public Real y() {
        return get(1);
    }

    public Real z() {
        return get(2);
    }

    /**
     * Returns the cross product of this vector and another.
     * Overridden to return Vector3D type.
     */
    public Vector3D cross(Vector3D other) {
        // We can optimize this to avoid list creation overhead of super.cross()
        // if performance becomes critical, but for now delegating is fine
        // provided we cast or wrap the result.
        // Super returns Vector<E>, which is likely a DenseVector.
        // We need to construct a new Vector3D from it.

        Real x1 = this.x();
        Real y1 = this.y();
        Real z1 = this.z();
        Real x2 = other.x();
        Real y2 = other.y();
        Real z2 = other.z();

        Real cx = y1.multiply(z2).subtract(z1.multiply(y2));
        Real cy = z1.multiply(x2).subtract(x1.multiply(z2));
        Real cz = x1.multiply(y2).subtract(y1.multiply(x2));

        return new Vector3D(cx, cy, cz);
    }

    public double getX() {
        return x().doubleValue();
    }

    public double getY() {
        return y().doubleValue();
    }

    public double getZ() {
        return z().doubleValue();
    }
}
