package com.anecon.taf.gettingStarted.rest;

import com.anecon.taf.core.reporting.Keyword;
import com.anecon.taf.gettingStarted.model.Account;
import com.anecon.taf.gettingStarted.rest.base.RestKeywords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

public class AccountRestEspoKeywords extends RestKeywords {
    private static final Logger log = LoggerFactory.getLogger(AccountRestEspoKeywords.class);

    @Keyword("Create account")
    public Account createAccount(String name, String emailAddress, String phoneNumber, String website) {
        final Account account = new Account(name, emailAddress, phoneNumber, website);
        log.info("Creating account via REST: {}", account);

        return restApi.path("Account").request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(account, MediaType.APPLICATION_JSON_TYPE), Account.class);
    }

    @Keyword("Create account")
    public Account createAccount(Account account) {
        log.info("Creating account via REST: {}", account);

        return restApi.path("Account").request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(account, MediaType.APPLICATION_JSON_TYPE), Account.class);
    }

    public boolean deleteAccount(Account account) {
        log.info("Deleting account via REST with id: {}", account.getId());
        return restApi.path("Account").path(account.getId() == null ? "" : account.getId()).request().delete(Boolean.class);
    }
}
