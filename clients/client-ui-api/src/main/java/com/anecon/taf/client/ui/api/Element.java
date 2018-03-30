package com.anecon.taf.client.ui.api;

import java.io.File;

public interface Element<T> extends Interactable {
    boolean isSelected();

    String getAttribute(String attributeName);

    String getText();

    String getElementType();

    boolean isEnabled();

    boolean isDisplayed();

    File takeScreenshot();

    Element getChild(T locator);
}
