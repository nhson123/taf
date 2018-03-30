package com.anecon.taf.client.data.map;

import com.anecon.taf.client.data.read.Table;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DataMapperTest {
    private static final List<String> VALID_HEADER = new ArrayList<>();
    private static final List<String> VALID_HEADER_MIXED_CASING = new ArrayList<>();
    private static final List<List<String>> VALID_DATA = new ArrayList<>();
    private static final List<String> ROW_1 = new ArrayList<>();
    private static final List<String> ROW_2 = new ArrayList<>();

    static {
        VALID_HEADER.addAll(Arrays.asList("a", "b", "c", "d", "e"));
        VALID_HEADER_MIXED_CASING.addAll(Arrays.asList("a", "B", "c", "D", "e"));

        ROW_1.addAll(Arrays.asList("string1", "true", "FALSE", "2", "-2"));
        ROW_2.addAll(Arrays.asList("string2", "TRUE", "false", "0", "1"));
        VALID_DATA.addAll(Arrays.asList(ROW_1, ROW_2));
    }

    // region Simple table mapping tests
    @Test
    public void mappingValidTableWorks() throws InstantiationException, IllegalAccessException {
        assertTableWithRow1AndRow2(new Table(VALID_HEADER, VALID_DATA));
    }

    @Test
    public void mappingMixedCasingTableWorks() throws InstantiationException, IllegalAccessException {
        assertTableWithRow1AndRow2(new Table(VALID_HEADER_MIXED_CASING, VALID_DATA));
    }

    private void assertTableWithRow1AndRow2(Table table) throws InstantiationException, IllegalAccessException {
        final List<SampleModel> mappedModels = new DataMapper<>(table, SampleModel.class).map();
        final SampleModel model1 = mappedModels.get(0);
        final SampleModel model2 = mappedModels.get(1);

        assertRow(ROW_1, model1);
        assertRow(ROW_2, model2);
    }

    private void assertRow(List<String> row, SampleModel model) {
        Assert.assertEquals(row.get(0), model.getA());
        Assert.assertEquals(Boolean.valueOf(row.get(1)), model.isB());
        Assert.assertEquals(Boolean.valueOf(row.get(2)), model.getC());
        Assert.assertEquals((int) Integer.valueOf(row.get(3)), model.getD());
        Assert.assertEquals(Integer.valueOf(row.get(4)), model.getE());
    }
    // endregion

    @Test
    public void mapNullValuesForReferenceTypes() throws InstantiationException, IllegalAccessException {
        final List<List<String>> data = Collections.singletonList(Arrays.asList("", "", "", "", ""));
        final Table table = new Table(VALID_HEADER, data);

        SampleModel model = new DataMapper<>(table, SampleModel.class).map().get(0);

        Assert.assertEquals("", model.getA());
        Assert.assertEquals(false, model.isB());
        Assert.assertEquals(null, model.getC());
        Assert.assertEquals(0, model.getD());
        Assert.assertEquals(null, model.getE());
    }
}
