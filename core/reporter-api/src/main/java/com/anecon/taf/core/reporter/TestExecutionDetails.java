package com.anecon.taf.core.reporter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TestExecutionDetails {
    public static class TestExecutionDetailsBuilder {
        private final String testRun;
        private final String testClass;
        private final String testMethod;
        private final Status status;
        private String note;
        private LocalDateTime started;
        private LocalDateTime finished;
        private Map<String, Object> metadata = new HashMap<>();

        public TestExecutionDetailsBuilder(String testRun, String testClass, String testMethod, Status status) {
            this.testRun = testRun;
            this.testClass = testClass;
            this.testMethod = testMethod;
            this.status = status;
        }

        public TestExecutionDetailsBuilder withNote(String note) {
            this.note = note;
            return this;
        }

        public TestExecutionDetailsBuilder withStarted(LocalDateTime started) {
            this.started = started;
            return this;
        }

        public TestExecutionDetailsBuilder withFinished(LocalDateTime finished) {
            this.finished = finished;
            return this;
        }

        public TestExecutionDetailsBuilder withMetadata(String key, Object value) {
            metadata.put(key, value);
            return this;
        }

        public TestExecutionDetails build() {
            TestExecutionDetails ted = new TestExecutionDetails();
            ted.testRun = testRun;
            ted.testClass = testClass;
            ted.testMethod = testMethod;
            ted.note = note;
            ted.status = status;
            ted.started = started;
            ted.finished = finished;
            ted.metadata = metadata;

            return ted;
        }
    }

    private String testRun;
    private String testClass;
    private String testMethod;
    private Status status;
    private String note;
    private LocalDateTime started;
    private LocalDateTime finished;
    private Map<String, Object> metadata = new HashMap<>();

    private TestExecutionDetails() {
        // construction only via builder
    }

    public enum Status {
        FAILED, SKIPPED, PASSED;
    }

    public String getNote() {
        return note;
    }

    public String getTestRun() {
        return testRun;
    }

    public String getTestClass() {
        return testClass;
    }

    public String getTestMethod() {
        return testMethod;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getStarted() {
        return started;
    }

    public LocalDateTime getFinished() {
        return finished;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }
}
