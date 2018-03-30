package com.anecon.taf.client.ui.api;

import com.anecon.taf.client.ui.api.table.Table;

public interface UiClient<T> {
    void quit();

    Window window();

    Element<T> element(T locator);

    Elements<T> elements(T locator);

    Table<T> table(T tableLocator, T rowLocator);
}
