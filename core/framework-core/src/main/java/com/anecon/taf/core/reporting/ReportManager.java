package com.anecon.taf.core.reporting;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.NetworkMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ReportManager {
    private static final Logger log = LoggerFactory.getLogger(ReportManager.class);

    private static final ExtentReports report;

    private static ExtentTest test = null;

    static {
        final Path reportPath = Paths.get(ReportingConfig.get().reportPath()).toAbsolutePath();
        final String fileName = ReportingConfig.get().reportName();
        final Path fullPath = reportPath.resolve(fileName).toAbsolutePath();

        log.debug("Creating new report, reporting to directory {}, using filename {} (full path: {})",
                reportPath, fileName, fullPath);
        report = new ExtentReports(fullPath.toString(), NetworkMode.OFFLINE);
    }

    protected ReportManager() {
        throw new AssertionError("Instantiation not allowed");
    }

    public static ExtentReports getReporter() {
        return report;
    }

    public static ExtentTest startNewTest(String testName) {
        test = getReporter().startTest(testName);
        return test;
    }

    public static ExtentTest getTest() {
        if (test == null) {
            test = startNewTest("New Default Test");
        }
        return test;
    }
}