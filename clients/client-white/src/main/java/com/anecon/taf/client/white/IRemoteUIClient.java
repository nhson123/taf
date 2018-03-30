package com.anecon.taf.client.white;

public interface IRemoteUIClient {
    void startApplication(ApplicationConfiguration config);

    void stopApplication();

    void stopApplication(ApplicationConfiguration config);

    ApplicationConfiguration getRunningApplication();

    void click(SearchProperties searchProperties);

    void click(SearchProperties searchProperties, int times);

    void setText(SearchProperties searchProperties, String text);

    void setText(SearchProperties searchProperties, String text, int times);

    void focus(SearchProperties searchProperties);

    String getText(SearchProperties searchProperties);

    int selectRows(SearchProperties searchProperties, String cellContent);

    boolean isVisible(SearchProperties searchProperties);

    boolean isEnabled(SearchProperties searchProperties);

    boolean isChecked(SearchProperties searchProperties);

    String takeScreenshot(SearchProperties searchProperties);

    String getClipboardContent();

    void setClipboardContent(String newContent);
}