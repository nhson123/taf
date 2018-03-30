package com.anecon.taf.core.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

public final class Wait {
    private Wait() {
        throw new AssertionError("This class shouldn't be instantiated!");
    }

    /**
     * Wait for the beginning of the next minute - plus a few extra seconds to compensate timing issues
     */
    public static void forNextMinute() {
        final LocalDateTime nextMinute = LocalDateTime.now().plusMinutes(1).withSecond(0).with(ChronoField.MILLI_OF_SECOND, 0);
        till(nextMinute);
    }

    /**
     * Wait till it's {@code waitFor} o'clock
     *
     * @param waitFor until when the method should wait
     */
    public static void till(LocalDateTime waitFor) {
        till(waitFor.toInstant(ZoneOffset.UTC).toEpochMilli());
    }

    public static void till(long unixEpochMs) {
        final long diffMs = System.currentTimeMillis() - unixEpochMs;

        Sleeper.sleep(diffMs, SleepReasons.TIME);
    }
}
