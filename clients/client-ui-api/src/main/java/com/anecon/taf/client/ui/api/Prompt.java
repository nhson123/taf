package com.anecon.taf.client.ui.api;

public interface Prompt {
    void dismissAlert();

    void acceptAlert();

    String getAlertText();

    void sendAlertText(String keys);

    boolean isDisplayed();
}
