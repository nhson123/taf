package com.anecon.taf.core.reporter;

public interface Reporter {
    void report(TestExecutionDetails testExecutionDetails);

    String getReporterName();
}
