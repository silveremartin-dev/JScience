package org.jscience.mathematics.algebra.group;

import org.jscience.mathematics.algebra.Group;
import org.jscience.mathematics.vector.Matrix;
import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.number.set.Reals;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Lorentz Group SO(3,1).
 * <p>
 * The group of all linear transformations of Minkowski space that preserve the
 * spacetime interval ds² = -c²dt² + dx² + dy² + dz².
 * Elements are represented as 4x4 matrices.
 * </p>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public class SO3_1Group implements Group<Matrix<Real>> {

    private static final SO3_1Group INSTANCE = new SO3_1Group();

    public static SO3_1Group getInstance() {
        return INSTANCE;
    }

    private SO3_1Group() {
    }

    @Override
    public Matrix<Real> operate(Matrix<Real> left, Matrix<Real> right) {
        return left.multiply(right);
    }

    @Override
    public Matrix<Real> identity() {
        // 4x4 Identity Matrix
        Real[][] data = new Real[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                data[i][j] = (i == j) ? Real.ONE : Real.ZERO;
            }
        }
        // Convert array to List<List<Real>>
        List<List<Real>> rows = new ArrayList<>();
        for (Real[] rowData : data) {
            rows.add(Arrays.asList(rowData));
        }
        return new DenseMatrix<>(rows, Reals.getInstance());
    }

    @Override
    public Matrix<Real> inverse(Matrix<Real> element) {
        // Placeholder
        throw new UnsupportedOperationException("Matrix inversion not yet fully exposed in generic interface");
    }

    @Override
    public boolean isCommutative() {
        return false;
    }

    @Override
    public String description() {
        return "SO(3,1) - Lorentz Group";
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Matrix<Real> element) {
        if (element.rows() != 4 || element.cols() != 4)
            return false;
        return true;
    }

    /**
     * Creates a boost matrix in the x-direction.
     * 
     * @param beta velocity v/c
     * @return the Lorentz boost matrix
     */
    public Matrix<Real> boostX(double beta) {
        if (Math.abs(beta) >= 1.0)
            throw new IllegalArgumentException("Beta must be < 1");
        double gamma = 1.0 / Math.sqrt(1.0 - beta * beta);

        Real[][] data = new Real[4][4];
        // Initialize to 0
        for (int i = 0; i < 4; i++)
            Arrays.fill(data[i], Real.ZERO);

        Real g = Real.of(gamma);
        Real gb = Real.of(-gamma * beta);

        // t' = γt - γβx
        // x' = -γβt + γx
        data[0][0] = g;
        data[0][1] = gb;
        data[1][0] = gb;
        data[1][1] = g;
        data[2][2] = Real.ONE;
        data[3][3] = Real.ONE;

        // Convert array to List<List<Real>>
        List<List<Real>> rows = new ArrayList<>();
        for (Real[] rowData : data) {
            rows.add(Arrays.asList(rowData));
        }

        return new DenseMatrix<>(rows, Reals.getInstance());
    }
}
