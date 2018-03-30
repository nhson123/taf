package com.anecon.taf.core.reporting;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNGTestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Report.pass("Test succeeded", "");
        close();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Report.error("Test failed", result.getThrowable() != null ? result.getThrowable().getMessage() : null);
        if (ReportingConfig.get().screenshotOnFail()) {
            Report.takeScreenshot("Failure screenshot");
        }
        close();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Report.skip("Test skipped", "");
        close();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        Report.pass("Failed but within success percentage", "");
        close();
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
        ReportManager.getReporter().flush();
    }

    private void close() {
        ReportManager.getReporter().endTest(ReportManager.getTest());
    }
}
