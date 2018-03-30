package com.anecon.taf.gettingStarted.aeonbits;

import org.aeonbits.owner.ConfigFactory;

public class EspoConfigHolder {
    private static final EspoConfig INSTANCE = ConfigFactory.create(EspoConfig.class);

    public static EspoConfig get() {
        return INSTANCE;
    }
}
