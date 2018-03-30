package com.anecon.taf.client.ranorex;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

class RanorexConfig {
    private static final RanorexConfigSpec INSTANCE = ConfigFactory.create(RanorexConfigSpec.class, System.getProperties());

    static RanorexConfigSpec get() {
        return INSTANCE;
    }

    interface RanorexConfigSpec extends Config {
        @Key("client.ranorex.exe")
        String ranorexClientPath();

        @Key("client.ranorex.port")
        @DefaultValue("5555")
        String port();

        @Key("client.ranorex.timeout")
        @DefaultValue("60000")
        long timeoutMs();
    }
}
