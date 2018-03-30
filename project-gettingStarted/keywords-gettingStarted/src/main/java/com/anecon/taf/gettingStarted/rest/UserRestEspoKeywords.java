package com.anecon.taf.gettingStarted.rest;

import com.anecon.taf.core.reporting.Keyword;
import com.anecon.taf.gettingStarted.model.User;
import com.anecon.taf.gettingStarted.rest.base.RestKeywords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

public class UserRestEspoKeywords extends RestKeywords {
    private static final Logger log = LoggerFactory.getLogger(UserRestEspoKeywords.class);

    @Keyword("Create user")
    public User createUser(String username, String password, String salutation, String firstName, String lastName, boolean isAdmin) {
        final User user = new User(username, password, salutation, firstName, lastName, isAdmin);
        log.info("Creating User via REST: {}", user);

        final User createdUser = restApi.path("User").request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE), User.class);
        createdUser.setPassword(user.getPassword());

        return createdUser;
    }

    @Keyword("Delete user")
    public void deleteUser(String id) {
        log.info("Deleting user via REST");

        restApi.path("User").path(String.valueOf(id)).request().delete();
    }

    @Keyword("Edit user")
    public User editUser(String id, User userDetails) {
        log.info("Updating User via REST: {}", userDetails);

       return restApi.path("User").path("id").request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(userDetails, MediaType.APPLICATION_JSON_TYPE), User.class);
    }

}
