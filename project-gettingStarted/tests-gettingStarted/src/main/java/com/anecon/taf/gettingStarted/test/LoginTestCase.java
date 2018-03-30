package com.anecon.taf.gettingStarted.test;

import com.anecon.taf.gettingStarted.EspoTestCaseBase;
import com.anecon.taf.gettingStarted.aeonbits.EspoConfigHolder;
import com.anecon.taf.gettingStarted.model.User;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTestCase extends EspoTestCaseBase {
    @Test
    public void testLoginProvider() {
        final User user = data().espo().user().createUser();
        apps().espo().ui().login(user);

        final String usernameUi = apps().espo().ui().profileMenu().clickMenu().getUsername();
        final String expected = user.getFirstName() + " " + user.getLastName();

        Assert.assertEquals(usernameUi, expected, "Is correct username displayed?");
    }

    @Test
    public void testLoginExplicitCredentials() {
        final String username = "Admin";
        apps().espo().ui().login(username, "swqd2016");

        final String usernameUi = apps().espo().ui().profileMenu().clickMenu().getUsername();

        Assert.assertEquals(usernameUi, username, "Is correct username displayed?");
    }

    @Test
    public void testLoginViaAeonbits() {
        apps().espo().ui().login(EspoConfigHolder.get().username(), EspoConfigHolder.get().password());

        final String usernameFromGUI = apps().espo().ui().profileMenu().clickMenu().getUsername();

        Assert.assertEquals(usernameFromGUI, EspoConfigHolder.get().username(), "Is correct username displayed?");
    }

    @Test
    public void testLoginInvalidCredentialsDoesNotWork() {
        apps().espo().ui().login("invUser", "invPass");

        Assert.assertTrue(apps().espo().ui().loginButtonVisible(), "Is login button still visible?");
    }
}
