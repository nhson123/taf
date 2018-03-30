package com.anecon.taf.gettingStarted.test;

import com.anecon.taf.client.data.read.CsvParser;
import com.anecon.taf.client.data.read.Table;
import com.anecon.taf.client.data.read.TableParser;
import com.anecon.taf.gettingStarted.EspoTestCaseBase;
import com.anecon.taf.gettingStarted.data.AccountDataProvider;
import com.anecon.taf.gettingStarted.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.anecon.taf.gettingStarted.data.AccountDataProvider.ACCOUNTS_FILE_NAME;
import static com.anecon.taf.gettingStarted.data.AccountDataProvider.WEBSITES_FILE_NAME;

public class UiAccountsTestCase extends EspoTestCaseBase {
    public static final String WEBSITES_URLS_FILE_NAME = "websiteUrls.csv";
    private static final Logger log = LoggerFactory.getLogger(AccountDataProvider.class);

    @Test
    public void testSearchForExistingAccount() {
        final Account account = data().espo().account().createAccount();

        Assert.assertNotNull(account, "Could account be created via REST?");

        apps().espo().ui().login(LOGIN_USERNAME, LOGIN_PASSWORD);

        apps().espo().ui().accounts().openTab().searchAccounts(account.getName());

        Assert.assertTrue(apps().espo().ui().accounts().isAccountDisplayed(account.getName()), "Is the account being displayed?");
    }

    @Test
    public void testCreateAccount() {
        apps().espo().ui().login(LOGIN_USERNAME, LOGIN_PASSWORD);

        final Account account = randomAccount("UI");
        fillAccountDetailsOnGUI(account);

        apps().espo().ui().accounts().openTab().searchAccounts(account.getName());

        Assert.assertTrue(apps().espo().ui().accounts().isAccountDisplayed(account.getName()), "Is the account being displayed?");
    }

    @Test
    public void testCreateAndThenRemoveAllAccounts() {
        final Account account0 = data().espo().account().createAccount();
        final Account account1 = data().espo().account().createAccount();
        final Account account2 = data().espo().account().createAccount();

        Assert.assertNotNull(account0, "Could account be created via REST?");
        Assert.assertNotNull(account1, "Could account be created via REST?");
        Assert.assertNotNull(account2, "Could account be created via REST?");

        apps().espo().ui().login(LOGIN_USERNAME, LOGIN_PASSWORD);

        apps().espo().ui().accounts().openTab()
            .clickSelectAllCheckbox()
            .clickActionsButton()
            .clickRemoveAll()
            .acceptAlert();

        Assert.assertTrue(apps().espo().ui().accounts().isAccountListEmpty(), "Is the list empty?");
    }

    @Test
    public void testCreateAndEditAccountDataDriven() {
        apps().espo().ui().login(LOGIN_USERNAME, LOGIN_PASSWORD);

        // this part is data driven approach
        Account account = readAccountsFromCSVFile(ACCOUNTS_FILE_NAME).get(0);
        fillAccountDetailsOnGUI(account);

        String url = "http://www.anecon.com/";
        editWebSiteOnGUI(url);

        List<String> urlsFromCSV = readWebSiteUrlsFromCSV(WEBSITES_FILE_NAME);

        String urlFromGUI = apps().espo().ui().accounts().getWebsiteUrl();

        // clear test data - i.e. remove all the accounts
        apps().espo().ui().accounts().openTab()
            .clickSelectAllCheckbox()
            .clickActionsButton()
            .clickRemoveAll()
            .acceptAlert();

        Assert.assertTrue(urlFromGUI.equals(urlsFromCSV.get(0)), "The expected and the real website url differ.");
    }


    private void editWebSiteOnGUI(String url) {
        apps().espo().ui().accounts().editWebSiteInput(url);
    }

    private void fillAccountDetailsOnGUI(Account account) {
        apps().espo().ui().accounts().openTab().clickAccountCreate()
            .fillName(account.getName())
            .fillEmailAddress(account.getEmailAddress())
            .fillPhoneNumber(account.getPhoneNumber())
            .fillWebsite(account.getWebsite())
            .clickSave();
    }

    private List<Account> readAccountsFromCSVFile (String fileName){
        List<List<String>> data = extractDataFromCSV(fileName);
        List<Account> accounts = data2Accounts(data);

        return accounts;
    }

    private List<Account> data2Accounts(List<List<String>> data) {
        List<Account> result = new ArrayList<>();

        for (List<String> entry : data) {
            Account account = new Account();
            account.setName(entry.get(0));
            account.setEmailAddress(entry.get(1));
            account.setPhoneNumber(entry.get(2));
            account.setWebsite(entry.get(3));
            result.add(account);
        }

        return result;
    }

    private List<String> data2URLs(List<List<String>> data) {
        List<String> result = new ArrayList<>();

        for (List<String> entry : data) {
            result.add(entry.get(0));
        }

        return result;
    }


    private List<List<String>> extractDataFromCSV (String fileName) {
        List<List<String>> data = null;

        try (InputStream in = getClass().getClassLoader().getResourceAsStream(fileName)) {
            TableParser parser = new CsvParser(in);
            Table table = parser.readFile();
            data = table.getData();
        } catch (IOException e) {
            log.error("Could load data or entire file: {}", ACCOUNTS_FILE_NAME, e);
        }

        return data;
    }

    private List<String> readWebSiteUrlsFromCSV(String fileName) {
        List<List<String>> data = extractDataFromCSV(fileName);
        List<String> urls = data2URLs(data);

        return urls;
    }


}
