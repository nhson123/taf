package com.anecon.taf.core;

/**
 * Indicates that a certain object can perform actions when requested through {@link InstanceManager}
 */
public interface Activatable {
    /**
     * Called when requested with {@link InstanceManager#getInstance(Class, Object...)}
     */
    void activate();
}
