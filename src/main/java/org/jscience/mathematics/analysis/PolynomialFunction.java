package org.jscience.mathematics.analysis;

import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.vector.Vector;
import java.util.Arrays;

/**
 * Represents a polynomial function of a real variable.
 * <p>
 * P(x) = a_n * x^n + ... + a_1 * x + a_0
 * </p>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public class PolynomialFunction implements RealFunction {

    private final double[] coefficients; // Ordered from lowest degree to highest (a_0, a_1, ...)

    /**
     * Creates a new polynomial with the given coefficients.
     * 
     * @param coefficients the coefficients a_0, a_1, ..., a_n
     */
    public PolynomialFunction(double... coefficients) {
        this.coefficients = Arrays.copyOf(coefficients, coefficients.length);
    }

    @Override
    public Real evaluate(Real x) {
        return Real.of(evaluate(x.doubleValue()));
    }

    /**
     * Evaluates the polynomial using Horner's method.
     */
    public double evaluate(double x) {
        double result = 0;
        for (int i = coefficients.length - 1; i >= 0; i--) {
            result = result * x + coefficients[i];
        }
        return result;
    }

    // @Override
    // public Vector<Real> evaluate(Vector<Real> x) {
    // return RealFunction.super.evaluate(x);
    // }

    @Override
    public RealFunction derivative() {
        if (coefficients.length <= 1) {
            return new PolynomialFunction(0);
        }
        double[] derivCoeffs = new double[coefficients.length - 1];
        for (int i = 1; i < coefficients.length; i++) {
            derivCoeffs[i - 1] = coefficients[i] * i;
        }
        return new PolynomialFunction(derivCoeffs);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = coefficients.length - 1; i >= 0; i--) {
            double c = coefficients[i];
            if (c == 0)
                continue;

            if (sb.length() > 0) {
                sb.append(c > 0 ? " + " : " - ");
            } else if (c < 0) {
                sb.append("-");
            }

            double absC = Math.abs(c);
            if (absC != 1.0 || i == 0)
                sb.append(absC);

            if (i > 0) {
                sb.append("x");
                if (i > 1)
                    sb.append("^").append(i);
            }
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }
}
