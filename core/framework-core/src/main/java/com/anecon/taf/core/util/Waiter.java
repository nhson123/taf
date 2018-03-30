package com.anecon.taf.core.util;

import com.anecon.taf.core.config.CoreConfigHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BooleanSupplier;

public class Waiter {
    private static final Logger log = LoggerFactory.getLogger(Waiter.class);

    private final BooleanSupplier condition;

    private long waitSeconds = CoreConfigHolder.get().waitSeconds();
    private long pauseMilliseconds = CoreConfigHolder.get().pauseMilliseconds();

    public Waiter(BooleanSupplier condition) {
        this.condition = condition;
    }

    public static boolean waitFor(BooleanSupplier predicate) {
        return new Waiter(predicate).waitForConditionToBeMet();
    }

    public long getPauseMilliseconds() {
        return pauseMilliseconds;
    }

    /**
     * Set the seconds to wait for the condition to be met.
     *
     * @param waitSeconds a positive integer
     */
    protected void setWaitSeconds(long waitSeconds) {
        this.waitSeconds = waitSeconds;
    }

    /**
     * Set the milliseconds to pause inbetween every check
     *
     * @param pauseMilliseconds a positive integer
     */
    protected void setPauseMilliseconds(int pauseMilliseconds) {
        this.pauseMilliseconds = pauseMilliseconds;
    }

    /**
     * Wait for a condition to be met. If waitSeconds is set to 10s, a check takes 1s and pause is set to 2000ms,
     * the condition will be checked 4 times:<br>
     * <br>
     * <pre>
     * I---10s--I
     * *..*..*..*
     * (* = check, . = pause)</pre>
     *
     * @return true if the condition has been met, false if there was a timeout
     */
    public boolean waitForConditionToBeMet() {
        log.debug("Waiting " + waitSeconds + "s for a condition to be met");
        long start = System.currentTimeMillis();

        int tries = 0;
        while (System.currentTimeMillis() < start + waitSeconds * 1000) {
            tries++;

            boolean conditionMet;
            try {
                conditionMet = condition.getAsBoolean();
            } catch (Exception e) {
                conditionMet = false;
            }

            if (conditionMet) {
                log.debug("Condition met after " + tries + " retries / "
                        + ((System.currentTimeMillis() - start) / 1000) + " seconds.");
                return true;
            } else {
                Sleeper.silentSleep(pauseMilliseconds);
            }
        }

        log.warn("Condition not met after " + tries + " retries and " + ((System.currentTimeMillis() - start) / 1000)
                + "s waiting time with " + pauseMilliseconds / 1000 + " seconds wait in between - time out.");
        return false;
    }
}
