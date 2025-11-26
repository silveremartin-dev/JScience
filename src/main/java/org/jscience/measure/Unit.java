package org.jscience.measure;

import org.jscience.measure.converter.UnitConverter;

/**
 * Represents a measurement unit.
 * <p>
 * Units are used to express physical quantities. Each unit has an associated
 * dimension and can be converted to other units of the same dimension.
 * </p>
 * 
 * @param <Q> the quantity type
 * 
 * @author Silvere Martin-Michiellot
 * @since 1.0
 */
public interface Unit<Q extends Quantity<Q>> {

    /**
     * Returns the dimension of this unit.
     * 
     * @return the dimension
     */
    Dimension getDimension();

    /**
     * Returns the symbol for this unit.
     * 
     * @return the unit symbol (e.g., "m", "kg", "s")
     */
    String getSymbol();

    /**
     * Returns a converter to convert values from this unit to another.
     * 
     * @param that the target unit
     * @return the converter
     * @throws IllegalArgumentException if dimensions don't match
     */
    UnitConverter getConverterTo(Unit<Q> that);

    /**
     * Returns a unit equal to this unit scaled by the specified factor.
     * 
     * @param factor the scaling factor
     * @return the scaled unit
     */
    Unit<Q> multiply(double factor);

    /**
     * Returns the product of this unit with another.
     * 
     * @param <R>  the quantity type of the other unit
     * @param that the unit to multiply with
     * @return the product unit
     */
    <R extends Quantity<R>> Unit<?> multiply(Unit<R> that);

    /**
     * Returns the quotient of this unit by another.
     * 
     * @param <R>  the quantity type of the divisor unit
     * @param that the unit to divide by
     * @return the quotient unit
     */
    <R extends Quantity<R>> Unit<?> divide(Unit<R> that);

    /**
     * Returns this unit raised to a power.
     * 
     * @param n the exponent
     * @return this unit to the nth power
     */
    Unit<?> pow(int n);
}
