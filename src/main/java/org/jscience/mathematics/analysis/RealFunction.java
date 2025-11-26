package org.jscience.mathematics.analysis;

import org.jscience.mathematics.number.Real;

/**
 * Represents a real-valued function of a real variable (R -> R).
 * <p>
 * Provides capabilities for numerical analysis, such as differentiation
 * and integration.
 * </p>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public interface RealFunction extends Function<Real, Real> {

    /**
     * Returns the derivative of this function.
     * 
     * @return the derivative function f'
     */
    default RealFunction derivative() {
        // Default implementation could use numerical differentiation
        // But for symbolic or analytical functions, this should be overridden
        return x -> {
            // Central difference approximation
            double h = 1e-8;
            double val = x.doubleValue();
            Real f_x_plus_h = evaluate(Real.of(val + h));
            Real f_x_minus_h = evaluate(Real.of(val - h));
            return f_x_plus_h.subtract(f_x_minus_h).divide(Real.of(2 * h));
        };
    }

    /**
     * Returns the integral of this function (antiderivative) with zero constant.
     * 
     * @return the antiderivative function F such that F' = f
     * @throws UnsupportedOperationException if symbolic integration is not
     *                                       supported
     */
    default RealFunction integrate() {
        throw new UnsupportedOperationException("Symbolic integration not supported by default");
    }

    /**
     * Integrates this function over a definite interval [a, b].
     * 
     * @param a the lower bound
     * @param b the upper bound
     * @return the definite integral value
     */
    default Real integrate(Real a, Real b) {
        // Default implementation: Simpson's Rule or similar numerical integration
        // Simplified Trapezoidal for now
        int n = 1000;
        double start = a.doubleValue();
        double end = b.doubleValue();
        double h = (end - start) / n;
        double sum = 0.5 * (evaluate(a).doubleValue() + evaluate(b).doubleValue());

        for (int i = 1; i < n; i++) {
            double x = start + i * h;
            sum += evaluate(Real.of(x)).doubleValue();
        }

        return Real.of(sum * h);
    }

    static RealFunction identity() {
        return x -> x;
    }
}
