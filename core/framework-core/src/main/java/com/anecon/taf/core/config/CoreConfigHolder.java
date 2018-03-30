package com.anecon.taf.core.config;

import org.aeonbits.owner.ConfigFactory;

public class CoreConfigHolder {
    private static final CoreConfig INSTANCE = ConfigFactory.create(CoreConfig.class, System.getProperties());

    public static CoreConfig get() {
        return INSTANCE;
    }
}
