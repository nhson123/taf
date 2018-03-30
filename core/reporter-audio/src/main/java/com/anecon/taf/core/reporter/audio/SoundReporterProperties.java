package com.anecon.taf.core.reporter.audio;

import org.aeonbits.owner.Config;

public interface SoundReporterProperties extends Config {
    @Key("com.anecon.taf.reporting.sound.enabled")
    @DefaultValue("true")
    boolean soundReportingEnabled();
}
