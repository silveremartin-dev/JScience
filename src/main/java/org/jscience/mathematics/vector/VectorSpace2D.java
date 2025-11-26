package org.jscience.mathematics.vector;

import org.jscience.mathematics.algebra.VectorSpace;
import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.number.set.Reals;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a 2-dimensional Real Vector Space (R^2).
 * <p>
 * Optimized for 2D geometry and physics.
 * </p>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public class VectorSpace2D implements VectorSpace<Vector<Real>, Real> {

    private static final VectorSpace2D INSTANCE = new VectorSpace2D();

    public static VectorSpace2D getInstance() {
        return INSTANCE;
    }

    private VectorSpace2D() {
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
        return new DenseVector<>(Arrays.asList(Real.ZERO, Real.ZERO), Reals.getInstance());
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
        return "R^2 (2D Euclidean Space)";
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Vector<Real> element) {
        return element != null && element.dimension() == 2;
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
        return 2;
    }
}
