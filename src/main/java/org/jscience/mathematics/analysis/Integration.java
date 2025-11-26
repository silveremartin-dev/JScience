package org.jscience.mathematics.analysis;

import org.jscience.mathematics.number.Real;

/**
 * Provides numerical integration capabilities.
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public final class Integration {

    private Integration() {
        // Utility class
    }

    /**
     * Computes the definite integral of a function over the interval [a, b]
     * using the Adaptive Simpson's method.
     * 
     * @param f the function to integrate
     * @param a the lower bound
     * @param b the upper bound
     * @return the approximate value of the integral
     */
    public static Real integrate(RealFunction f, Real a, Real b) {
        double start = a.doubleValue();
        double end = b.doubleValue();
        double epsilon = 1e-9; // Tolerance

        return Real.of(adaptiveSimpson(f, start, end, epsilon, 20));
    }

    private static double adaptiveSimpson(RealFunction f, double a, double b, double eps, int maxDepth) {
        double c = (a + b) / 2.0;
        double h = b - a;
        double fa = f.evaluate(Real.of(a)).doubleValue();
        double fb = f.evaluate(Real.of(b)).doubleValue();
        double fc = f.evaluate(Real.of(c)).doubleValue();

        double S = (h / 6.0) * (fa + 4 * fc + fb);

        return adaptiveSimpsonRec(f, a, b, eps, S, fa, fb, fc, maxDepth);
    }

    private static double adaptiveSimpsonRec(RealFunction f, double a, double b, double eps,
            double S, double fa, double fb, double fc, int depth) {
        double c = (a + b) / 2.0;
        double h = b - a;
        double d = (a + c) / 2.0;
        double e = (c + b) / 2.0;

        double fd = f.evaluate(Real.of(d)).doubleValue();
        double fe = f.evaluate(Real.of(e)).doubleValue();

        double Sleft = (h / 12.0) * (fa + 4 * fd + fc);
        double Sright = (h / 12.0) * (fc + 4 * fe + fb);
        double S2 = Sleft + Sright;

        if (depth <= 0 || Math.abs(S2 - S) <= 15 * eps) {
            return S2 + (S2 - S) / 15.0; // Richardson extrapolation
        }

        return adaptiveSimpsonRec(f, a, c, eps / 2.0, Sleft, fa, fc, fd, depth - 1) +
                adaptiveSimpsonRec(f, c, b, eps / 2.0, Sright, fc, fb, fe, depth - 1);
    }

    /**
     * Computes the definite integral using the Trapezoidal rule (simpler, faster,
     * less accurate).
     * 
     * @param f the function
     * @param a lower bound
     * @param b upper bound
     * @param n number of intervals
     * @return integral value
     */
    public static Real trapezoidal(RealFunction f, Real a, Real b, int n) {
        double start = a.doubleValue();
        double end = b.doubleValue();
        double h = (end - start) / n;
        double sum = 0.5 * (f.evaluate(a).doubleValue() + f.evaluate(b).doubleValue());

        for (int i = 1; i < n; i++) {
            double x = start + i * h;
            sum += f.evaluate(Real.of(x)).doubleValue();
        }

        return Real.of(sum * h);
    }
}
