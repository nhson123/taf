package com.anecon.taf.gettingStarted.ui.accounts;

import com.anecon.taf.client.selenium.WebDriverClient;
import com.anecon.taf.client.selenium.WebLocator;
import com.anecon.taf.core.reporting.Keyword;
import com.anecon.taf.core.util.SleepReasons;
import com.anecon.taf.core.util.Sleeper;
import com.anecon.taf.core.util.Waiter;
import com.anecon.taf.gettingStarted.ui.base.UiKeywords;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountsUiKeywords extends UiKeywords {
    private static final Logger log = LoggerFactory.getLogger(AccountsUiKeywords.class);

    private static final WebLocator ACCOUNT_TAB = WebLocator.css("a[href='#Account']");
    private static final WebLocator SEARCH_FIELD = WebLocator.name("textFilter");
    private static final WebLocator SEARCH_BUTTON = WebLocator.css("button[data-action='search']");
    private static final WebLocator CREATE_ACCOUNT_BUTTON = WebLocator.css("a[href='#Account/create'][data-action='create']");
    private static final WebLocator SELECT_ALL_CHECKBOX = WebLocator.css("input.select-all");
    private static final WebLocator ACTIONS_BUTTON = WebLocator.css("button.actions-button");
    private static final WebLocator REMOVE_ALL_BUTTON = WebLocator.css("a.mass-action[data-action='remove']");
    private static final WebLocator ACCOUNTS_LIST_ROW = WebLocator.css("tr.list-row");
    private static final WebLocator ACCOUNT_WEBSITE_INPUT = WebLocator.xpath("//input[@name='website']");
    private static final WebLocator EDIT_ACCOUNT_BUTTON = WebLocator.xpath("//div/button[@data-action='edit']");
    private static final WebLocator SAVE_ACCOUNT_BUTTON = WebLocator.xpath("//div/button[@data-action='save']");
    private static final WebLocator OPPORTUNITIES_BUTTON = WebLocator.xpath("//div/button[href='#Opportunity']");

    public AccountsUiKeywords(WebDriverClient client) {
        super(client);
    }

    @Keyword("Open accounts tab")
    public AccountsUiKeywords openTab() {
        Waiter.waitFor(() -> client.element(ACCOUNT_TAB).isDisplayed());

        log.info("Clicking on the accounts tab");
        client.element(ACCOUNT_TAB).click();

        return this;
    }

    @Keyword("Search accounts")
    public void searchAccounts(String queryText) {
        Waiter.waitFor(() -> client.element(SEARCH_FIELD).isDisplayed());

        log.info("Filling search field with: {}", queryText);
        client.element(SEARCH_FIELD).setText(queryText);

        log.info("Clicking on search button");
        client.element(SEARCH_BUTTON).click();
    }

    @Keyword("Check if account is displayed")
    public boolean isAccountDisplayed(String name) {
        log.info("Checking if account \"{}\" is being displayed", name);

        Waiter.waitFor(() -> client.elements(ACCOUNTS_LIST_ROW).size() > 0);

        boolean isDisplayed;
        try {
            isDisplayed = client.element(WebLocator.css("a[title='" + name + "']")).isDisplayed();
        } catch (NoSuchElementException e) {
            isDisplayed = false;
        }

        return isDisplayed;
    }

    @Keyword("Check if account list is empty")
    public boolean isAccountListEmpty() {
        log.info("Waiting for list to be empty");
        Waiter.waitFor(() -> !client.element(ACTIONS_BUTTON).isDisplayed());

        log.info("Checking if account list is empty");
        return client.elements(ACCOUNTS_LIST_ROW).size() == 0;
    }

    @Keyword("Click select all checkbox")
    public AccountsUiKeywords clickSelectAllCheckbox() {
        Waiter.waitFor(() -> client.element(SELECT_ALL_CHECKBOX).isDisplayed());

        log.info("Clicking on the select all checkbox");
        client.element(SELECT_ALL_CHECKBOX).click();

        return this;
    }

    @Keyword("Click actions button")
    public AccountsUiKeywords clickActionsButton() {
        log.info("Clicking on actions button");
        client.element(ACTIONS_BUTTON).click();

        return this;
    }
    @Keyword("Click oppotunities button")
    public AccountsUiKeywords clickOppoturnities() {
        log.info("Clicking on oppotunities button");
        client.element(OPPORTUNITIES_BUTTON).click();

        return this;
    }

    @Keyword("Click remove all button")
    public AccountsUiKeywords clickRemoveAll() {
        log.info("Clicking on the remove all button");
        client.element(REMOVE_ALL_BUTTON).click();

        return this;
    }

    @Keyword("Accept alert")
    public void acceptAlert() {
        log.info("Accepting alert");
        client.prompt().acceptAlert();
    }

    @Keyword("Click create account button")
    public CreateAccountUiKeywords clickAccountCreate() {
        Waiter.waitFor(() -> client.element(CREATE_ACCOUNT_BUTTON).isDisplayed());

        log.info("Clicking on create new account button");
        client.element(CREATE_ACCOUNT_BUTTON).click();

        return new CreateAccountUiKeywords(client);
    }

    @Keyword("Clear and edit the value of the input field: 'website' ")
    public AccountsUiKeywords editWebSiteInput(String url) {
        // click the edit button
        client.element(AccountsUiKeywords.EDIT_ACCOUNT_BUTTON).click();

        client.element(AccountsUiKeywords.ACCOUNT_WEBSITE_INPUT).click();
        client.element(AccountsUiKeywords.ACCOUNT_WEBSITE_INPUT).setText("");
        client.element(AccountsUiKeywords.ACCOUNT_WEBSITE_INPUT).setText(url);

        // save the edit, otherwise it will not be in the DOM
        client.element(AccountsUiKeywords.SAVE_ACCOUNT_BUTTON).click();

        return this;
    }

    @Keyword("Reads the walue of the websitge field and returns it")
    public String getWebsiteUrl() {
        Sleeper.sleep(1, SleepReasons.UI);

        // click the edit button
        client.element(AccountsUiKeywords.EDIT_ACCOUNT_BUTTON).click();
        String urlOnGUI = client.element(AccountsUiKeywords.ACCOUNT_WEBSITE_INPUT).getAttribute("value");
        return urlOnGUI;
    }

}
