package com.anecon.taf.core.config;

import org.aeonbits.owner.Config;

public interface CoreConfig extends Config {
    @Key("core.util.Waiter.waitSeconds")
    @DefaultValue("30")
    long waitSeconds();

    @Key("core.util.Waiter.pauseMilliseconds")
    @DefaultValue("1000")
    long pauseMilliseconds();
}
