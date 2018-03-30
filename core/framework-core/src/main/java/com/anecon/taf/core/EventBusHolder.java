package com.anecon.taf.core;

import com.google.common.eventbus.EventBus;

public class EventBusHolder {
    private static final EventBus INSTANCE = new EventBus();

    private EventBusHolder() {

    }

    public static void register(Object object) {
        INSTANCE.register(object);
    }

    public static void post(Object event) {
        INSTANCE.post(event);
    }

    public static void unregister(Object object) {
        INSTANCE.unregister(object);
    }
}
