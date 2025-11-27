*</p>**@author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public class VectorSpace3D implements VectorSpace<Vector<Real>, Real> {

    private static final VectorSpace3D INSTANCE = new VectorSpace3D();

    public static VectorSpace3D getInstance() {
        return INSTANCE;
    }

    private VectorSpace3D() {
    }

    @Override
    public Vector<Real> operate(Vector<Real> left, Vector<Real> right) {
        return left.add(right);
    }

    @Override
    public Vector<Real> add(Vector<Real> a, Vector<Real> b) {
        return a.add(b);
    }

    @Override
    public Vector<Real> zero() {
        return new DenseVector<>(Arrays.asList(Real.ZERO, Real.ZERO, Real.ZERO), Reals.getInstance());
    }

    @Override
    public Vector<Real> negate(Vector<Real> element) {
        return element.negate();
    }

    @Override
    public Vector<Real> inverse(Vector<Real> element) {
        return negate(element);
    }

    @Override
    public Vector<Real> scale(Real scalar, Vector<Real> vector) {
        return vector.multiply(scalar);
    }

    @Override
    public boolean isCommutative() {
        return true;
    }

    @Override
    public String description() {
        return "R^3 (3D Euclidean Space)";
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Vector<Real> element) {
        return element != null && element.dimension() == 3;
    }

    @Override
    public org.jscience.mathematics.algebra.Field<Real> getScalarField() {
        return Reals.getInstance();
    }

    @Override
    public org.jscience.mathematics.algebra.Ring<Real> getScalarRing() {
        return Reals.getInstance();
    }

    @Override
    public int dimension() {
        return 3;
    }
}
