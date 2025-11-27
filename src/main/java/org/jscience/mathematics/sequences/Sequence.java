}

    /**
     * Returns the human-readable name of this sequence.
     * 
     * @return sequence name
     */
    default String getName() {
        return getClass().getSimpleName();
    }

    /**
     * Returns the mathematical formula for this sequence (if known).
     * 
     * @return formula string or null
     */
    default String getFormula() {
        return null;
    }
}
