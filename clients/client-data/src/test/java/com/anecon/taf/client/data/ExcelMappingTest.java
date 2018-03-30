package com.anecon.taf.client.data;

import com.anecon.taf.client.data.map.DataMapper;
import com.anecon.taf.client.data.read.ExcelParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;

public class ExcelMappingTest {
    private DateAndEmptyModel model;

    @Before
    public void readModel() throws IOException {
        model = DataMapper.toModelList(ExcelParser.fromXls(getClass().getClassLoader().getResourceAsStream("sample.xls"), "dateAndEmpty"), DateAndEmptyModel.class).get(0);
    }

    @Test
    public void emptyCellsAreHandledCorrectly() {
        Assert.assertEquals(null, model.getEmpty());
        Assert.assertEquals("Test", model.getNotEmpty());
    }

    @Test
    public void dateCellsAreHandledCorrectly() {
        final LocalDateTime date = model.getDate();

        Assert.assertEquals(6, date.getDayOfMonth());
        Assert.assertEquals(10, date.getMonthValue());
        Assert.assertEquals(2016, date.getYear());
    }
}
