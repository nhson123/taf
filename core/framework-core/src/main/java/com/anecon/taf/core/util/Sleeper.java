package com.anecon.taf.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public final class Sleeper {
    private static final Logger log = LoggerFactory.getLogger(Sleeper.class);

    private Sleeper() {
        // no instantiation allowed
    }

    /**
     * Please use this only to avoid log spamming, ie. with Waiters
     *
     * @param ms Milliseconds to sleep
     */
    public static void silentSleep(long ms) {
        if (ms < 0) {
            log.warn("I won't sleep - ms is negative! ms: " + ms);
            return;
        }

        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            log.warn("Error while sleeping, reinterrupting", e);
            Thread.currentThread().interrupt();
        }
    }

    public static void sleep(long ms, SleepReason reason) {
        final StringBuilder sb = new StringBuilder("Sleeping for ").append(ms).append("ms");

        if (ms > 10 * 1000) {
            // log a "until" statement if we're sleeping longer than 10 seconds
            sb.append(", until ").append(LocalDateTime.now().plus(ms, ChronoUnit.MILLIS));
        }

        if (reason != null) {
            sb.append(", because ").append(reason.getReason());
        }

        log.trace(sb.toString());
        silentSleep(ms);
    }

    public static void sleep(int seconds, SleepReason reason) {
        sleep(seconds * 1000L, reason);
    }
}
