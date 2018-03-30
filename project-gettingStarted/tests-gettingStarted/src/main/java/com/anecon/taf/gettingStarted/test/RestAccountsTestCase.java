package com.anecon.taf.gettingStarted.test;

import com.anecon.taf.gettingStarted.EspoTestCaseBase;
import com.anecon.taf.gettingStarted.model.Account;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ws.rs.NotFoundException;
import java.util.List;

public class RestAccountsTestCase extends EspoTestCaseBase {

    @Test
    public void testAccountDataProvider() {
        List<Account> createdAccounts = data().espo().account().createAccounts();

        apps().espo().ui().login(LOGIN_USERNAME, LOGIN_PASSWORD);
        apps().espo().ui().accounts().openTab();

        for (Account account : createdAccounts) {
            Assert.assertTrue(apps().espo().ui().accounts().isAccountDisplayed(account.getName()), "Is account being displayed?");
        }
    }

    @Test
    public void testCreateAccountViaREST() {
        final Account account = randomAccount("REST");
        final Account createdAccount = apps().espo().rest().accounts().createAccount(account);

        Assert.assertNotNull(createdAccount.getId(), "Was an account ID generated after creation?");
    }

    @Test(dependsOnMethods = { "testCreateAccountViaREST" })
    public void testDeleteAccountViaREST() {
        final Account account = apps().espo().rest().accounts().createAccount(randomAccount("REST"));
        final boolean deleted = apps().espo().rest().accounts().deleteAccount(account);

        Assert.assertTrue(deleted, "Was the account successfully deleted?");
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testNonExistingAccountDeletion() {
        final Account account = new Account();

        // Should throw NotFoundException
        apps().espo().rest().accounts().deleteAccount(account);
    }
}
