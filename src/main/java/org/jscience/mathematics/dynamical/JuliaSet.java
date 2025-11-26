package org.jscience.mathematics.dynamical;

import org.jscience.mathematics.number.Complex;

/**
 * Tools for exploring Julia Sets.
 * <p>
 * A Julia set is defined by a constant complex parameter c.
 * It is the set of points z for which the iteration z = z^2 + c does not
 * diverge.
 * </p>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public final class JuliaSet {

    private JuliaSet() {
        // Utility class
    }

    /**
     * Checks if a point z is in the Julia set for a given parameter c.
     * 
     * @param z             the starting point
     * @param c             the constant parameter
     * @param maxIterations maximum number of iterations
     * @return the number of iterations before divergence, or maxIterations if
     *         bounded
     */
    public static int iterate(Complex z, Complex c, int maxIterations) {
        Complex currentZ = z;
        double escapeRadiusSq = 4.0;

        for (int i = 0; i < maxIterations; i++) {
            // z = z^2 + c
            currentZ = currentZ.multiply(currentZ).add(c);

            // Check for divergence: |z| > 2
            double r = currentZ.getReal().doubleValue();
            double im = currentZ.getImaginary().doubleValue();
            if (r * r + im * im > escapeRadiusSq) {
                return i;
            }
        }

        return maxIterations;
    }

    /**
     * Generates a simple ASCII representation of the Julia set for a given c.
     * 
     * @param c      the constant parameter
     * @param width  width of the grid
     * @param height height of the grid
     * @return a string representation
     */
    public static String asciiArt(Complex c, int width, int height) {
        StringBuilder sb = new StringBuilder();
        double minX = -1.5;
        double maxX = 1.5;
        double minY = -1.5;
        double maxY = 1.5;

        double dx = (maxX - minX) / width;
        double dy = (maxY - minY) / height;

        for (int y = 0; y < height; y++) {
            double cy = minY + y * dy;
            for (int x = 0; x < width; x++) {
                double cx = minX + x * dx;
                Complex z = Complex.of(cx, cy);
                int iter = iterate(z, c, 100);

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
