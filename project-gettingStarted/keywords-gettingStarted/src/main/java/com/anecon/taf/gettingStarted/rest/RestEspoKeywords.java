package com.anecon.taf.gettingStarted.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestEspoKeywords {
    private static final Logger log = LoggerFactory.getLogger(RestEspoKeywords.class);

    public RestEspoKeywords() {
        log.info("Creating new EspoCRM REST Keywords");
    }

    public AccountRestEspoKeywords accounts() {
        return new AccountRestEspoKeywords();
    }

    public UserRestEspoKeywords users() {
        return new UserRestEspoKeywords();
    }
}
