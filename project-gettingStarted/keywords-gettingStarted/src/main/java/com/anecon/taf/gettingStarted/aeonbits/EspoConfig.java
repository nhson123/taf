package com.anecon.taf.gettingStarted.aeonbits;

import org.aeonbits.owner.Config;

/*
 * According to the documentation of aeonbits, the EspoConfig.properties file should be found automatically if it is in
 * the same package as its mapping interface. This unfortunately does not work. The only thing that works is to use the
 * annotation @Sources in the following way: @Sources({"file:/Absolute_file_path_and_file_name_on_the_operating_system"})
 * or also the annotations: @DefaultValue importing them: import org.aeonbits.owner.Config.Sources as they are
 * annotations within the Config class.
 */

public interface EspoConfig extends Config {

    @Key("espo.home")
    @DefaultValue("http://localhost/espocrm")
    String home();

    @Key("espo.username")
    @DefaultValue("Admin")
    String username();

    @Key("espo.password")
    @DefaultValue("swqd2016")
    String password();

    @Key("api.url")
    @DefaultValue("${espo.home}/api/v1")
    String apiUrl();
}
