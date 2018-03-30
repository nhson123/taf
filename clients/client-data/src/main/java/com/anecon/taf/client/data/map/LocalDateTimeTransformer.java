package com.anecon.taf.client.data.map;

import com.anecon.taf.core.util.LocalDateTimeAdjuster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeTransformer implements Transformer<LocalDateTime> {
    private static final Logger log = LoggerFactory.getLogger(LocalDateTimeTransformer.class);

    @Override
    public LocalDateTime transform(String source) {
        if (source == null) {
            return null;
        }

        try {
            return ZonedDateTime.parse(source, DateTimeFormatter.ISO_OFFSET_DATE_TIME).toLocalDateTime();
        } catch (DateTimeParseException e) {
            try {
                return LocalDateTimeAdjuster.adjust(LocalDateTime.now(), source);
            } catch (Exception e1) {
                log.warn("Couldn't parse string {} to LocalDateTime, returning null instead", source);
                return null;
            }
        }
    }
}
