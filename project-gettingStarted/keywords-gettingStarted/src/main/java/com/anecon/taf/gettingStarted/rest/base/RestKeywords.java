package com.anecon.taf.gettingStarted.rest.base;

import com.anecon.taf.client.rest.RestClientBuilder;
import com.anecon.taf.gettingStarted.aeonbits.EspoConfigHolder;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.WebTarget;

public abstract class RestKeywords {
    private static final Logger log = LoggerFactory.getLogger(RestKeywords.class);

    protected final WebTarget restApi;

    public RestKeywords() {
        String username = EspoConfigHolder.get().username();
        String password = EspoConfigHolder.get().password();
        String url = EspoConfigHolder.get().apiUrl();

        HttpAuthenticationFeature authFeature = HttpAuthenticationFeature.basic(username, password);

        RestClientBuilder restClientBuilder = new RestClientBuilder();
        restClientBuilder.register(authFeature);

        restApi = restClientBuilder.build().target(url);
    }
}
