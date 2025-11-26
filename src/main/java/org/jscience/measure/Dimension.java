package org.jscience.measure;

/**
 * Represents a physical dimension (e.g., Length, Mass, Time).
 * <p>
 * Dimensions are used for dimensional analysis and ensuring physical
 * correctness of operations. Dimensions form an abelian group under
 * multiplication.
 * </p>
 * 
 * @author Silvere Martin-Michiellot
 * @since 1.0
 */
public interface Dimension {

    /**
     * Returns the product of this dimension with another.
     * 
     * @param that the dimension to multiply with
     * @return the product dimension
     */
    Dimension multiply(Dimension that);

    /**
     * Returns the quotient of this dimension by another.
     * 
     * @param that the dimension to divide by
     * @return the quotient dimension
     */
    Dimension divide(Dimension that);

    /**
     * Returns this dimension raised to a power.
     * 
     * @param n the exponent
     * @return this dimension raised to the nth power
     */
    Dimension pow(int n);

    /**
     * Returns the nth root of this dimension.
     * 
     * @param n the root degree
     * @return the nth root of this dimension
     */
    Dimension root(int n);

    /**
     * Indicates if this dimension is dimensionless.
     * 
     * @return true if this dimension is dimensionless
     */
    boolean isDimensionless();
}
