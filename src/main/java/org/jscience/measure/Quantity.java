package org.jscience.measure;

/**
 * Marker interface for physical quantities.
 * <p>
 * This interface is used for type safety to ensure dimensional correctness.
 * Examples: Length, Mass, Time, Velocity, Force, etc.
 * </p>
 * 
 * @param <Q> the type of quantity
 * 
 * @author Silvere Martin-Michiellot
 * @since 1.0
 */
public interface Quantity<Q extends Quantity<Q>> {
    // Marker interface - no methods required
}
