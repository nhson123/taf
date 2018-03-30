package com.anecon.taf.gettingStarted.test;

import com.anecon.taf.gettingStarted.EspoTestCaseBase;
import com.anecon.taf.gettingStarted.aeonbits.EspoConfigHolder;
import com.anecon.taf.gettingStarted.model.User;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateOpSimple extends EspoTestCaseBase {
   /* @Test
    public void testLoginExplicitCredentials() {
        final String username = "Admin";
        apps().espo().ui().login(username, "swqd2016");

        final String usernameUi = apps().espo().ui().profileMenu().clickMenu().getUsername();

        Assert.assertEquals(usernameUi, username, "Is correct username displayed?");
    }
    */


    @Test
    public void testClickOppotunities() {
        final String username = "Admin";
        apps().espo().ui().login(username, "swqd2016");
        apps().espo().ui().oppotunities();
        apps().espo().ui().opportunitiesCreate();
        apps().espo().ui().opportunitieFillOut();
    }



}
