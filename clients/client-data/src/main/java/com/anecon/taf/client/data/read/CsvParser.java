package com.anecon.taf.client.data.read;

import com.anecon.taf.client.data.ClientDataConfig;
import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvParser implements TableParser {
    private final CsvListReader csvListReader;

    public CsvParser(InputStream inputStream, char delimiter) {
        csvListReader = new CsvListReader(new InputStreamReader(inputStream), new CsvPreference.Builder('"', delimiter, "\n").build());
    }

    public CsvParser(InputStream inputStream) {
        this(inputStream, ClientDataConfig.get().csvDelimiter().charAt(0));
    }

    public CsvParser(File file) throws FileNotFoundException {
        this(new FileInputStream(file));
    }

    @Override
    public Table readFile() throws IOException {
        final List<List<String>> content = new ArrayList<>();

        final List<String> header = csvListReader.read();
        List<String> row;
        while ((row = csvListReader.read()) != null) {
            content.add(row);
        }

        return new Table(header, content);
    }
}
