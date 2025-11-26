package org.jscience.mathematics.dynamical;

import org.jscience.mathematics.number.Complex;
import org.jscience.mathematics.number.Real;

/**
 * Tools for exploring the Mandelbrot Set, a famous example of a dynamical
 * system.
 * <p>
 * The Mandelbrot set is the set of complex numbers c for which the function
 * f_c(z) = z^2 + c does not diverge when iterated from z = 0.
 * </p>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public final class Mandelbrot {

    private Mandelbrot() {
        // Utility class
    }

    /**
     * Checks if a point c is in the Mandelbrot set.
     * 
     * @param c             the complex number to check
     * @param maxIterations maximum number of iterations
     * @return the number of iterations before divergence, or maxIterations if it
     *         stays bounded
     */
    public static int iterate(Complex c, int maxIterations) {
        Complex z = Complex.ZERO;
        double escapeRadiusSq = 4.0;

        for (int i = 0; i < maxIterations; i++) {
            // z = z^2 + c
            z = z.multiply(z).add(c);

            // Check for divergence: |z| > 2 => |z|^2 > 4
            // We compute |z|^2 manually to avoid sqrt cost
            double r = z.getReal().doubleValue();
            double im = z.getImaginary().doubleValue();
            if (r * r + im * im > escapeRadiusSq) {
                return i;
            }
        }

        return maxIterations;
    }

    /**
     * Generates a simple ASCII representation of the Mandelbrot set.
     * 
     * @param width  width of the grid
     * @param height height of the grid
     * @return a string representation
     */
    public static String asciiArt(int width, int height) {
        StringBuilder sb = new StringBuilder();
        double minX = -2.0;
        double maxX = 1.0;
        double minY = -1.0;
        double maxY = 1.0;

        double dx = (maxX - minX) / width;
        double dy = (maxY - minY) / height;

        for (int y = 0; y < height; y++) {
            double cy = minY + y * dy;
            for (int x = 0; x < width; x++) {
                double cx = minX + x * dx;
                Complex c = Complex.of(cx, cy);
                int iter = iterate(c, 100);

                if (iter == 100) {
                    sb.append("#");
                } else if (iter > 50) {
                    sb.append("%");
                } else if (iter > 20) {
                    sb.append("*");
                } else if (iter > 5) {
                    sb.append(".");
                } else {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
