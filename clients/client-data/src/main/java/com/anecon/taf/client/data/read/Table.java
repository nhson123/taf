package com.anecon.taf.client.data.read;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Table {
    private final List<String> header;
    private final List<List<String>> data;

    public Table(List<String> header, List<List<String>> data) {
        this.header = Collections.unmodifiableList(Objects.requireNonNull(header));
        this.data = Collections.unmodifiableList(data);
    }

    public List<String> getHeader() {
        return header;
    }

    public List<List<String>> getData() {
        return data;
    }
}
