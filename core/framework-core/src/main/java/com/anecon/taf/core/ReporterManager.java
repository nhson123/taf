package com.anecon.taf.core;

import com.anecon.taf.core.reporter.Reporter;
import com.anecon.taf.core.reporter.TestExecutionDetails;

import java.util.ServiceLoader;

/**
 * Central class to hold all references to services implementing a {@link Reporter}.
 * <p>
 * These services will be discovered by {@link ServiceLoader}.
 */
public class ReporterManager {
    private static final Iterable<Reporter> REPORTERS = ServiceLoader.load(Reporter.class);

    private ReporterManager() {
        // no instantiation
    }

    /**
     * Reports {@code testExecutionDetails} to all found reporters.
     * @param testExecutionDetails a simple class to hold all information which could be useful for a reporter.
     */
    public static void report(TestExecutionDetails testExecutionDetails) {
        for (Reporter reporter : REPORTERS) {
            reporter.report(testExecutionDetails);
        }
    }
}
