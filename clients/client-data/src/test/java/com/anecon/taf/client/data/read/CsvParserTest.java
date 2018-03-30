package com.anecon.taf.client.data.read;

import org.junit.Test;

import java.io.IOException;

public class CsvParserTest extends ReaderTest {
    private static final String PATH = "sample.csv";

    @Test
    public void csvCanBeRead() throws IOException {
        assertReadTable(new CsvParser(getClass().getClassLoader().getResourceAsStream(PATH), ',').readFile());
    }
}
