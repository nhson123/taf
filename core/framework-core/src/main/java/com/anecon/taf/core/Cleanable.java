package com.anecon.taf.core;

/**
 * This interface describes that objects can clean something.
 */
public interface Cleanable {
    /**
     * Called to trigger a normal cleaning.
     */
    void cleanUp();

    /**
     * Called when there was some error and it can't be guaranteed that a normal cleaning is possible.
     */
    default void emergencyCleanUp() {
        cleanUp();
    }
}
