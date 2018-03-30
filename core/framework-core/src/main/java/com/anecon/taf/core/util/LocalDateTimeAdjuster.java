package com.anecon.taf.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to adjust a {@link LocalDateTime} by a string like &quot;1M2d&quot; or &quot;-1h 1s&quot;,
 * representing an offset.
 */
public class LocalDateTimeAdjuster {
    private static final Logger log = LoggerFactory.getLogger(LocalDateTimeAdjuster.class);

    private static final Pattern DURATION_PART_PATTERN = Pattern.compile("(\\d+)([A-Za-z]+)");
    private static final Map<String, ChronoUnit> UNIT_MAP = new HashMap<>();

    static {
        UNIT_MAP.put("y", ChronoUnit.YEARS);
        UNIT_MAP.put("M", ChronoUnit.MONTHS);
        UNIT_MAP.put("w", ChronoUnit.WEEKS);
        UNIT_MAP.put("d", ChronoUnit.DAYS);
        UNIT_MAP.put("h", ChronoUnit.HOURS);
        UNIT_MAP.put("m", ChronoUnit.MINUTES);
        UNIT_MAP.put("s", ChronoUnit.SECONDS);
    }

    private LocalDateTimeAdjuster() {
        throw new AssertionError("Instantiation not allowed");
    }

    /**
     * Adds or subtracts {@code duration} from {@code dateTime}.
     * <p>
     * If the first character is a {@code -}, everything in {@code duration} will be subtracted from {@code dateTime}.
     * Spaces and order of amount/unit pairs are irrelevant.
     * <p>
     * The following units are supported:
     * <pre> y ... year
     * M ... month
     * w ... week
     * d ... day
     * h ... hour
     * m ... minute
     * s ... second</pre>
     * <p>
     * <b>Examples for {@code duration}:</b><br/>
     * &quot;3d 5m&quot; - 3 days, 5 minutes
     * &quot;-1M&quot; - minus 1 month
     * &quot;- 1M&quot; - minus 1 month
     * &quot;3s1y&quot; - 1 year and 3 seconds
     * &quot;2d 9h&quot; - 2 days and 9 hours
     *
     * @param dateTime the {@link LocalDateTime} to adjust
     * @param duration the offset to use for adjustment
     * @return the adjusted {@link LocalDateTime}
     */
    public static LocalDateTime adjust(LocalDateTime dateTime, String duration) {
        if (duration == null || duration.isEmpty() || duration.equals("0")) {
            return dateTime;
        }

        log.trace("Adjusting {} by {}", dateTime, duration);

        LocalDateTime adjustedDateTime = dateTime;
        final boolean subtract = duration.startsWith("-");

        try {
            final Matcher matcher = DURATION_PART_PATTERN.matcher(duration);
            while (matcher.find()) {
                final int amount = Integer.parseInt(matcher.group(1));
                final TemporalUnit unit = Objects.requireNonNull(UNIT_MAP.get(matcher.group(2)));

                if (subtract) {
                    adjustedDateTime = adjustedDateTime.minus(amount, unit);
                } else {
                    adjustedDateTime = adjustedDateTime.plus(amount, unit);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Couldn't parse duration string " + duration, e);
        }

        log.trace("{} adjusted by {} is {}", dateTime, duration, adjustedDateTime);

        return adjustedDateTime;
    }
}