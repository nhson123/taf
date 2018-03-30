package com.anecon.taf.gettingStarted;

import com.anecon.taf.core.TestCaseBase;
import com.anecon.taf.gettingStarted.aeonbits.EspoConfigHolder;
import com.anecon.taf.gettingStarted.model.Account;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class EspoTestCaseBase extends TestCaseBase {

    private Applications apps;
    private DataProvider data;

    protected static final String LOGIN_USERNAME = EspoConfigHolder.get().username();
    protected static final String LOGIN_PASSWORD = EspoConfigHolder.get().password();

    @BeforeMethod
    public void setUp() {
        apps = new Applications();
        data = new DataProvider(apps);
    }

    @AfterMethod
    public void tearDown(ITestResult testResult) {
        triggerAppropriateCleanUps(testResult, data, apps);
    }

    protected Applications apps() {
        return apps;
    }

    protected DataProvider data() {
        return data;
    }

    protected Account randomAccount(String type) {
        final Account account = new Account();
        account.setName(type + " Account " + (int) (Math.random() * 10000));
        account.setEmailAddress(type.toLowerCase() + "@test.com");
        account.setPhoneNumber("+436761234567");
        account.setWebsite("test.com/" + type.toLowerCase());

        return account;
    }
}
