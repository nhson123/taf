package com.anecon.taf.client.ui.api;

import java.util.Optional;

public interface BrowserClient<T> extends UiClient<T> {
    void go(String url);

    String getCurrentUrl();

    void refresh();

    Optional<Object> executeScript(String script, Object... args);

    History history();

    Prompt prompt();
}
