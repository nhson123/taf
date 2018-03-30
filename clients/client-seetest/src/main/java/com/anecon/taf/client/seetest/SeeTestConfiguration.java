package com.anecon.taf.client.seetest;

/**
 * Configuration class for SeeTest
 *
 * @author Michael.Hombauer
 *
 */
public class SeeTestConfiguration {

    /**
     * The host on which SeeTest is running
     */
    private String host = "localhost";

    /**
     * The port on which SeeTest is running. 8889 is the default
     */
    private int port = 8889;

    /**
     * Generate a PDF as report. The other option is xml
     *
     * @see <a href="https://docs.experitest.com/display/public/SA/SetReporter">SeeTest Documentation - SetReporter</a>
     */
    private String reporterName = "pdf";

    /**
     * Use this directory to store the reports in
     *
     * @see <a href="https://docs.experitest.com/display/public/SA/SetReporter">SeeTest Documentation - SetReporter</a>
     */
    private String reporterDirectory = "C:/reports";

    /**
     * The name the testcase will get in SeeTest's reporting
     *
     * @see <a href="https://docs.experitest.com/display/public/SA/SetReporter">SeeTest Documentation - SetReporter</a>
     */
    private String testName = "mobile-test";

    /**
     * Whether or not we want to start the application instrumented
     *
     * @see <a href="https://docs.experitest.com/display/public/SA/Launch">SeeTest Documentation - Launch</a>
     */
    private boolean startInstrumented = false;

    /**
     * Whether or not SeeTest should kill the application before launching it
     *
     * @see <a href="https://docs.experitest.com/display/public/SA/Launch">SeeTest Documentation - Launch</a>
     */
    private boolean killAppOnStart = false;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getReporterDirectory() {
        return reporterDirectory;
    }

    public void setReporterDirectory(String reporterDirectory) {
        this.reporterDirectory = reporterDirectory;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public boolean isStartInstrumented() {
        return startInstrumented;
    }

    public void setStartInstrumented(boolean startInstrumented) {
        this.startInstrumented = startInstrumented;
    }

    public boolean isKillAppOnStart() {
        return killAppOnStart;
    }

    public void setKillAppOnStart(boolean killAppOnStart) {
        this.killAppOnStart = killAppOnStart;
    }

}
