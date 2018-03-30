package com.anecon.taf.core;

/**
 * This exception is intended to be thrown when there is a functional problem or misassumption within keywords or a testcase
 */
public class TestCaseException extends RuntimeException {
    public TestCaseException(String message) {
        super(message);
    }

    public TestCaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
