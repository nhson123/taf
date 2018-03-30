package com.anecon.taf.client.ui.api;

public interface Interactable {
    void click();

    void clear();

    void sendKeys(CharSequence... keys);

    default void setText(CharSequence text) {
        clear();

        if (text != null) {
            sendKeys(text);
        }
    }

    void selectDropdown(String visibleText);
}
