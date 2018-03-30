package com.anecon.taf.core;

import com.anecon.taf.core.reporter.TestExecutionDetails;
import com.anecon.taf.core.reporting.ReportManager;
import com.anecon.taf.core.reporting.TestNGTestListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * Base class for automated test cases. Provides access to framework
 * functionality. To create new testcases, just extend.
 */
@Listeners({TestNGTestListener.class})
public abstract class TestCaseBase {
    private LocalDateTime executionStarted;

    private static final Logger log = LoggerFactory.getLogger(TestCaseBase.class);

    @BeforeMethod(alwaysRun = true)
    public void setUpBase(Method method) {
        executionStarted = LocalDateTime.now();

        ReportManager.startNewTest(method.getName()).assignCategory("automation");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownBase(ITestResult testResult) {
        ReporterManager.report(createTestExecutionDetails(testResult));
    }

    @AfterSuite(alwaysRun = true)
    public void flushAndCloseReport() {
        ReportManager.getReporter().flush();
        ReportManager.getReporter().close();
    }

    private TestExecutionDetails createTestExecutionDetails(ITestResult testResult) {
        final String className = testResult.getTestClass().getName();
        final String methodName = testResult.getMethod().getMethodName();
        final TestExecutionDetails.Status status = mapTestResultStatus(testResult);

        // TODO: we need information about the curring testrun, ie. from a configuration file!
        return new TestExecutionDetails.TestExecutionDetailsBuilder("sampleTestrun", className, methodName, status)
                .withStarted(executionStarted).withFinished(LocalDateTime.now()).build();
    }

    protected TestExecutionDetails.Status mapTestResultStatus(ITestResult testResult) {
        switch (testResult.getStatus()) {
            case ITestResult.FAILURE:
                return TestExecutionDetails.Status.FAILED;
            case ITestResult.SUCCESS:
                return TestExecutionDetails.Status.PASSED;
            case ITestResult.SKIP:
                return TestExecutionDetails.Status.SKIPPED;
            default:
                throw new IllegalArgumentException("testResult has an unkonwn status!");
        }
    }

    /**
     * This method will call the right cleanUp methods of {@link Cleanable} according to the passed {@link ITestResult}
     * If the testcase has passed, {@link Cleanable#cleanUp()} will be called. If there was a problem during normal cleanUp,
     * or if the testcase was skipped or didn't pass, {@link Cleanable#emergencyCleanUp()} will be called
     *
     * @param testResult the {@link ITestResult} holding the test result
     * @param cleanables one or more items to clean up
     */
    protected void triggerAppropriateCleanUps(ITestResult testResult, Cleanable... cleanables) {
        triggerAppropriateCleanUps(mapTestResultStatus(testResult), cleanables);
    }

    /**
     * This method will call the right cleanUp methods of {@link Cleanable} according to the passed {@link com.anecon.taf.core.reporter.TestExecutionDetails.Status}
     * If the testcase has passed, {@link Cleanable#cleanUp()} will be called. If there was a problem during normal cleanUp,
     * or if the testcase was skipped or didn't pass, {@link Cleanable#emergencyCleanUp()} will be called
     *
     * @param status     the {@link com.anecon.taf.core.reporter.TestExecutionDetails.Status} holding the test result
     * @param cleanables one or more items to clean up
     */
    protected void triggerAppropriateCleanUps(TestExecutionDetails.Status status, Cleanable... cleanables) {
        for (Cleanable cleanable : cleanables) {
            try {
                if (status == TestExecutionDetails.Status.PASSED) {
                    tryNormalCleanUp(cleanable);
                } else {
                    cleanable.emergencyCleanUp();
                }
            } catch (Exception e) {
                log.warn("Couldn't do an emergency cleanup on {}", cleanable, e);
            }
        }
    }

    private void tryNormalCleanUp(Cleanable cleanable) {
        try {
            cleanable.cleanUp();
        } catch (Exception e) {
            log.warn("Normal cleanup of {} failed, trying emergency cleanup", cleanable, e);
            cleanable.emergencyCleanUp();
        }
    }
}
