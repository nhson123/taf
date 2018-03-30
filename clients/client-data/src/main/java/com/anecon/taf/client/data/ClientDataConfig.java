package com.anecon.taf.client.data;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

public class ClientDataConfig {
    private static final ClientDataConfigSpec INSTANCE = ConfigFactory.create(ClientDataConfigSpec.class, System.getProperties());

    public static ClientDataConfigSpec get() {
        return INSTANCE;
    }

    public interface ClientDataConfigSpec extends Config {
        @Key("client.data.csv.delimiter")
        @DefaultValue(";")
        String csvDelimiter();
    }
}
