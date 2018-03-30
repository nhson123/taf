package com.anecon.taf.client.data.read;

import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

abstract class ReaderTest {
    private static final List<String> EXPECTED_HEADER = Arrays.asList("a", "b", "c", "d", "e");
    private static final List<String> EXPECTED_DATA = Arrays.asList("Test", "false", " true", "1", "2");

    void assertReadTable(Table table) {
        Assert.assertEquals(EXPECTED_HEADER, table.getHeader());
        Assert.assertEquals(1, table.getData().size());
        Assert.assertEquals(EXPECTED_DATA, table.getData().get(0));
    }
}
