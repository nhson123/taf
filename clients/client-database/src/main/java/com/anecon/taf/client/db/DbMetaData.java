package com.anecon.taf.client.db;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DbMetaData {
    private final List<Column> columns = new ArrayList<>();
    private final String str;

    DbMetaData(ResultSetMetaData metaData) throws SQLException {
        final int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            final String name = metaData.getColumnName(i);
            final String dataType = metaData.getColumnTypeName(i);

            columns.add(new Column(name, dataType, i));
        }

        this.str = buildString(columns);
    }

    @SuppressWarnings("unused")
    public int getColumnCount() {
        return columns.size();
    }

    @SuppressWarnings("unused")
    public List<Column> getColumns() {
        return columns;
    }

    private static String buildString(List<Column> columns) {
        return columns.stream().map(Column::toString).collect(Collectors.joining("\n"));
    }

    @Override
    public String toString() {
        return str;
    }

    public class Column {
        private final String name;
        private final String dataType;
        private final int index;

        Column(String name, String dataType, int index) {
            this.name = name;
            this.dataType = dataType;
            this.index = index;
        }

        @Override
        public String toString() {
            return name + " (type: " + dataType + ", index: " + index + ")";
        }
    }
}
