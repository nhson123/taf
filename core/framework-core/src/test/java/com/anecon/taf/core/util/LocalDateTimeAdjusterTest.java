package com.anecon.taf.core.util;

import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;

import static org.testng.Assert.assertEquals;

public class LocalDateTimeAdjusterTest {
    private static final LocalDateTime START = LocalDateTime.of(2000, Month.JANUARY, 1, 1, 0, 0);
    
    @Test
    public void parsingNullAdjustsNothing() {
        final LocalDateTime result = LocalDateTimeAdjuster.adjust(START, null);

        assertEquals(result, START);
    }

    @Test
    public void parsingEmptyStringAdjustsNothing() {
        final LocalDateTime result = LocalDateTimeAdjuster.adjust(START, "");

        assertEquals(result, START);
    }

    @Test
    public void parsingZeroAdjustsNothing() {
        final LocalDateTime result = LocalDateTimeAdjuster.adjust(START, "0");

        assertEquals(result, START);
    }

    @Test
    public void parsingYear() {
        final LocalDateTime result = LocalDateTimeAdjuster.adjust(START, "1y");

        assertSingleUnit(result, ChronoUnit.YEARS);
    }

    @Test
    public void parsingMonth() {
        final LocalDateTime result = LocalDateTimeAdjuster.adjust(START, "1M");

        assertSingleUnit(result, ChronoUnit.MONTHS);
    }

    @Test
    public void subtractingOneMonthAndDay() {
        final LocalDateTime result = LocalDateTimeAdjuster.adjust(START, "-1M1d");

        assertEquals(result, START.minusMonths(1).minusDays(1));
    }

    @Test
    public void parsingWeeks() {
        final LocalDateTime result = LocalDateTimeAdjuster.adjust(START, "1w");

        assertSingleUnit(result, ChronoUnit.WEEKS);
    }

    @Test
    public void parsingDays() {
        final LocalDateTime result = LocalDateTimeAdjuster.adjust(START, "1d");

        assertSingleUnit(result, ChronoUnit.DAYS);
    }

    @Test
    public void parsingHours() {
        final LocalDateTime result = LocalDateTimeAdjuster.adjust(START, "1h");

        assertSingleUnit(result, ChronoUnit.HOURS);
    }

    @Test
    public void parsingMinutes() {
        final LocalDateTime result = LocalDateTimeAdjuster.adjust(START, "1m");

        assertSingleUnit(result, ChronoUnit.MINUTES);
    }

    @Test
    public void parsingSeconds() {
        final LocalDateTime result = LocalDateTimeAdjuster.adjust(START, "1s");

        assertSingleUnit(result, ChronoUnit.SECONDS);
    }

    @Test
    public void parsingEverything() {
        final LocalDateTime result = LocalDateTimeAdjuster.adjust(START, "1y1M1w1d1h1m1s");
        final LocalDateTime expected = START
                .plus(1, ChronoUnit.YEARS)
                .plus(1, ChronoUnit.MONTHS)
                .plus(1, ChronoUnit.WEEKS)
                .plus(1, ChronoUnit.DAYS)
                .plus(1, ChronoUnit.HOURS)
                .plus(1, ChronoUnit.MINUTES)
                .plus(1, ChronoUnit.SECONDS);

        assertEquals(result, expected);
    }

    @Test
    public void parsingWithSpace() {
        final LocalDateTime result = LocalDateTimeAdjuster.adjust(START, "1w 1h");
        final LocalDateTime expected = START.plus(1, ChronoUnit.WEEKS).plus(1, ChronoUnit.HOURS);

        assertEquals(result, expected);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void parsingUnknownSymbolThrowsException() {
        LocalDateTimeAdjuster.adjust(START, "1X");
    }

    private static void assertSingleUnit(LocalDateTime dateTime, ChronoUnit unit) {
        assertEquals(dateTime, START.plus(1, unit));
    }
}
