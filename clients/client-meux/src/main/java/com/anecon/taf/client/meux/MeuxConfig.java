package com.anecon.taf.client.meux;

import org.aeonbits.owner.ConfigFactory;

class MeuxConfig {
    private static final Config INSTANCE = ConfigFactory.create(Config.class, System.getProperties());

    static Config get() {
        return INSTANCE;
    }

    public interface Config extends org.aeonbits.owner.Config {
        @Key("client.meux.actiondelay")
        @DefaultValue("0")
        int actionDelayMs();

        @Key("client.meux.findtimeout")
        @DefaultValue("20000") // 20 seconds
        int findTimeoutMs();
    }
}
