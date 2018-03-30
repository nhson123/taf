package com.anecon.taf.core.util;

public enum SleepReasons implements SleepReason {
    // wait because ...
    UI("ui has to render"),
    SERVER("otherwise to many requests would be sent"),
    TIME("some event should happen after sleeping");

    private String reason;

    SleepReasons(String reason) {
        this.reason = reason;
    }

    @Override
    public String getReason() {
        return reason;
    }
}
