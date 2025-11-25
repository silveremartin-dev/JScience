/*
 * JScience Reimagined - Unified Scientific Computing Framework
 * Copyright (c) 2025 Silvere Martin-Michiellot
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.jscience.mathematics.number;

import java.math.BigDecimal;
import org.jscience.mathematics.context.MathContext;

/**
 * Abstract base class for real numbers (ℝ).
 * <p>
 * Real numbers form a Field under addition and multiplication.
 * This class provides a smart factory that chooses the backing implementation
 * based on the current {@link org.jscience.mathematics.context.MathContext}
 * preference.
 * </p>
 * <ul>
 * <li><b>FAST</b>: Uses {@code float} (32-bit)</li>
 * <li><b>NORMAL</b>: Uses {@code double} (64-bit) - Default</li>
 * <li><b>EXACT</b>: Uses {@link BigDecimal} (arbitrary precision)</li>
 * </ul>
 * 
 * <h2>Usage</h2>
 * 
 * <pre>{@code
 * // Default (NORMAL precision -> double)
 * Real pi = Real.of(3.14159);
 * 
 * // Explicit precision
 * MathContext.setCurrent(MathContext.exact());
 * Real exactPi = Real.of("3.14159265358979323846"); // Uses BigDecimal
 * }</pre>
 * 
 * @see org.jscience.mathematics.number.set.Reals
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public abstract class Real implements Comparable<Real> {

    private static final class Constants {
        private static final Real ZERO = RealDouble.of(0.0);
        private static final Real ONE = RealDouble.of(1.0);
        private static final Real NaN = RealDouble.of(Double.NaN);
    }

    /** The real number 0 */
    public static final Real ZERO = Constants.ZERO;

    /** The real number 1 */
    public static final Real ONE = Constants.ONE;

    /** The real number NaN */
    public static final Real NaN = Constants.NaN;

    /**
     * Creates a real number from a double value.
     * Uses current MathContext to decide implementation.
     * 
     * @param value the value
     * @return the Real instance
     */
    public static Real of(double value) {
        if (value == 0.0)
            return ZERO;
        if (value == 1.0)
            return ONE;
        if (Double.isNaN(value))
            return NaN;

        switch (MathContext.getCurrent().getRealPrecision()) {
            case FAST:
                return RealFloat.of((float) value);
            case EXACT:
                return RealBigDecimal.of(BigDecimal.valueOf(value));
            case NORMAL:
            default:
                return RealDouble.of(value);
        }
    }

    /**
     * Creates a real number from a String representation.
     * 
     * @param value the string value
     * @return the Real instance
     */
    public static Real of(String value) {
        switch (MathContext.getCurrent().getRealPrecision()) {
            case FAST:
                return RealFloat.of(Float.parseFloat(value));
            case EXACT:
                return RealBigDecimal.of(new BigDecimal(value));
            case NORMAL:
            default:
                return RealDouble.of(Double.parseDouble(value));
        }
    }

    // Package-private constructor
    Real() {
    }

    // --- Abstract operations ---

    public abstract Real add(Real other);

    public abstract Real subtract(Real other);

    public abstract Real multiply(Real other);

    public abstract Real divide(Real other);

    public abstract Real negate();

    public abstract Real abs();

    public abstract Real inverse();

    public abstract Real sqrt();

    public abstract Real pow(int exp);

    public abstract Real pow(Real exp);

    public abstract boolean isZero();

    public abstract boolean isOne();

    public abstract boolean isNaN();

    public abstract boolean isInfinite();

    public abstract double doubleValue();

    public abstract BigDecimal bigDecimalValue();

    // --- Standard methods ---

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

    @Override
    public abstract int compareTo(Real other);
}
