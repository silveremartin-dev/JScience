package org.jscience.mathematics.context;

/**
 * Context to manage computation preferences (e.g., CPU vs GPU).
 * <p>
 * This class allows users to control where linear algebra operations are
 * executed.
 * It uses a thread-local storage mechanism, similar to {@link MathContext}.
 * </p>
 * 
 * @author Silvere Martin-Michiellot
 * @since 1.0
 */
public final class ComputeContext {

    private static final ThreadLocal<ComputeContext> CURRENT = new ThreadLocal<>();
    private static final ComputeContext DEFAULT = new ComputeContext(ComputeMode.AUTO);

    private final ComputeMode mode;

    /**
     * Returns the current compute context.
     * 
     * @return the current context (defaults to AUTO if not set)
     */
    public static ComputeContext getCurrent() {
        ComputeContext ctx = CURRENT.get();
        return (ctx != null) ? ctx : DEFAULT;
    }

    /**
     * Enters a new compute context.
     * 
     * @param mode the compute mode to use
     */
    public static void enter(ComputeMode mode) {
        CURRENT.set(new ComputeContext(mode));
    }

    /**
     * Exits the current compute context, restoring the previous one.
     * <p>
     * Note: Since we use ThreadLocal directly without a stack in this simple
     * version,
     * "exit" effectively just clears the current one, reverting to default.
     * For nested contexts, a stack-based approach would be better, but for now
     * we assume simple usage.
     * </p>
     * TODO: Implement proper stack-based context management if nesting is required.
     */
    public static void exit() {
        CURRENT.remove();
    }

    private ComputeContext(ComputeMode mode) {
        this.mode = mode;
    }

    /**
     * Returns the compute mode of this context.
     * 
     * @return the compute mode
     */
    public ComputeMode getMode() {
        return mode;
    }
}
