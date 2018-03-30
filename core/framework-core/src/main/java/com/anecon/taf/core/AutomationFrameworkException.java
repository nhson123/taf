package com.anecon.taf.core;

/**
 * This exception can be thrown for internal framework exceptions
 */
public class AutomationFrameworkException extends RuntimeException {
    public AutomationFrameworkException(String message) {
        super(message);
    }

    public AutomationFrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
