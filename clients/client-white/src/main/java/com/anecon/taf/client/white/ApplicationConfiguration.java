package com.anecon.taf.client.white;

public class ApplicationConfiguration {
    private String applicationName;
    private String applicationBinaryPath;
    private String processToAttach;

    private String[] windowTitles;
    private String[] popupWindowTitles;

    public ApplicationConfiguration() {
    }

    public String getApplicationBinaryPath() {
        return applicationBinaryPath;
    }

    public void setApplicationBinaryPath(String applicationBinaryPath) {
        this.applicationBinaryPath = applicationBinaryPath;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String[] getWindowTitles() {
        return windowTitles;
    }

    public void setWindowTitles(String[] windowTitles) {
        this.windowTitles = windowTitles;
    }

    public String getProcessToAttach() {
        return processToAttach;
    }

    public void setProcessToAttach(String processToAttach) {
        this.processToAttach = processToAttach;
    }

    public String[] getPopupWindowTitles() {
        return popupWindowTitles;
    }

    public void setPopupWindowTitles(String[] popupWindowTitles) {
        this.popupWindowTitles = popupWindowTitles;
    }
}
