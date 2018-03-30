package com.anecon.taf.client.data.read;

import org.junit.Test;

import java.io.IOException;

public class ExcelParserTest extends ReaderTest {


    @Test
    public void xlsFromSheetNameCanBeRead() throws IOException {
        assertReadTable(ExcelParser.fromXls(getClass().getClassLoader().getResourceAsStream("sample.xls"), "samplesheet").readFile());
    }

    @Test
    public void xlsxFromSheetNameCanBeRead() throws IOException {
        assertReadTable(ExcelParser.fromXlsx(getClass().getClassLoader().getResourceAsStream("sample.xlsx"), "samplesheet").readFile());
    }

    @Test
    public void xlsFromIndexCanBeRead() throws IOException {
        assertReadTable(ExcelParser.fromXls(getClass().getClassLoader().getResourceAsStream("sample.xls"), 0).readFile());
    }

    @Test
    public void xlsxFromIndexCanBeRead() throws IOException {
        assertReadTable(ExcelParser.fromXlsx(getClass().getClassLoader().getResourceAsStream("sample.xlsx"), 0).readFile());
    }
}
