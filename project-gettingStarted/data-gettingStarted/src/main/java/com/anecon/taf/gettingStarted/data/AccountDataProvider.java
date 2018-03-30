package com.anecon.taf.gettingStarted.data;

import com.anecon.taf.client.data.read.CsvParser;
import com.anecon.taf.client.data.read.Table;
import com.anecon.taf.client.data.read.TableParser;
import com.anecon.taf.core.Cleanable;
import com.anecon.taf.core.dataprovider.ActionStack;
import com.anecon.taf.gettingStarted.model.Account;
import com.anecon.taf.gettingStarted.rest.AccountRestEspoKeywords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AccountDataProvider implements Cleanable {
    private static final Logger log = LoggerFactory.getLogger(AccountDataProvider.class);

    private final ActionStack undoStack = new ActionStack();
    private final AccountRestEspoKeywords accountKeywords;

    public static final String ACCOUNTS_FILE_NAME = "accounts.csv";
    public static final String WEBSITES_FILE_NAME = "websiteUrls.csv";
    private List<Account> accounts = new ArrayList<>();

    public AccountDataProvider(AccountRestEspoKeywords accountKeywords) {
        this.accountKeywords = accountKeywords;
        readAccoutsFromCSV();
    }

    public void readAccoutsFromCSV() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(ACCOUNTS_FILE_NAME)) {
            TableParser parser = new CsvParser(in);
            Table table = parser.readFile();
            List<List<String>> data = table.getData();

            for (List<String> entry : data) {
                Account account = new Account();
                account.setName(entry.get(0));
                account.setEmailAddress(entry.get(1));
                account.setPhoneNumber(entry.get(2));
                account.setWebsite(entry.get(3));

                accounts.add(account);
            }
        } catch (IOException e) {
            log.error("Could load data or entire file: {}", ACCOUNTS_FILE_NAME, e);
        }
    }

    public List<Account> createAccounts() {
        List<Account> createdAccounts = new ArrayList<>();

        for (Account account : accounts) {
            final Account createdAccount = accountKeywords.createAccount(account);
            undoStack.put(() -> accountKeywords.deleteAccount(createdAccount));

            createdAccounts.add(createdAccount);
        }

        return createdAccounts;
    }

    public Account createAccount() {
        final Account account = accountKeywords.createAccount(randomName(), "rest@test.com", "+436761234567", "test.com/api");
        undoStack.put(() -> accountKeywords.deleteAccount(account));

        return account;
    }

    @Override
    public void cleanUp() {
        undoStack.executeAll();
    }

    private String randomName() {
        return "REST Test Account " + (int) (Math.random() * 1000);
    }
}
