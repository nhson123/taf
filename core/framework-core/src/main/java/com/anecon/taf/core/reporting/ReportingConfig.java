package com.anecon.taf.core.reporting;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.ConfigFactory;

// has to be public so aspectj can inject it
public class ReportingConfig {
    private static final ReportingConfigSpec INSTANCE = ConfigFactory.create(ReportingConfigSpec.class, System.getProperties(), System.getenv());

    public static ReportingConfigSpec get() {
        return INSTANCE;
    }

    @LoadPolicy(LoadType.MERGE)
    @Sources({"file:~/.taf/taf.properties", "classpath:taf.properties"})
    public interface ReportingConfigSpec extends Config {
        @Key("core.report.path")
        @DefaultValue("C:/taf")
        String reportPath();

        @Key("core.report.name")
        @DefaultValue("report.html")
        String reportName();

        @Key("core.report.screenshot.keyword.pass")
        @DefaultValue("false")
        boolean screenshotOnKeywordPass();

        @Key("core.report.screenshot.keyword.fail")
        @DefaultValue("true")
        boolean screenshotOnKeywordFail();

        @Key("core.report.screenshot.assert.pass")
        @DefaultValue("false")
        boolean screenshotOnAssertionPass();

        @Key("core.report.screenshot.assert.fail")
        @DefaultValue("true")
        boolean screenshotOnAssertionFail();

        @Key("core.report.screenshot.fail")
        @DefaultValue("true")
        boolean screenshotOnFail();

        @Key("core.report.screenshot.useDefault")
        @DefaultValue("true")
        boolean useDefaultScreenshotter();
    }
}
