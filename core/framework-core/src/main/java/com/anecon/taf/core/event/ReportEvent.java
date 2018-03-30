package com.anecon.taf.core.event;


import com.anecon.taf.core.EventBusHolder;

public class ReportEvent {
    private final String message;
    private final boolean okay;

    private ReportEvent(String message, boolean okay) {
        this.message = message;
        this.okay = okay;
    }

    public static void publish(String message, boolean okay) {
        EventBusHolder.post(new ReportEvent(message, okay));
    }

    public String getMessage() {
        return message;
    }

    public boolean isOkay() {
        return okay;
    }

    @Override
    public String toString() {
        return "ReportEvent{message='" + message + '\'' + ", okay=" + okay + '}';
    }
}
