package com.anecon.taf.core.reporting;

public enum Mode {
    /**
     * Use default value set in configuration
     */
    DEFAULT(null),
    /**
     * Overwrite default with false
     */
    FALSE(false),
    /**
     * Overwrite default with true
     */
    TRUE(true);

    private final Boolean value;

    Mode(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }
}
