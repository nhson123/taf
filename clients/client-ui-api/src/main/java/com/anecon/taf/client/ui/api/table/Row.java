package com.anecon.taf.client.ui.api.table;

import com.anecon.taf.client.ui.api.Element;

public interface Row<T> extends Element<T> {
    Element<T> getCell(Column<T> column);
}
