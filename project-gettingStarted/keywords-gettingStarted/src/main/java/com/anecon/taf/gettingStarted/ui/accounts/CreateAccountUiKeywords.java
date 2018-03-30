package com.anecon.taf.gettingStarted.ui.accounts;

import com.anecon.taf.client.selenium.WebDriverClient;
import com.anecon.taf.client.selenium.WebLocator;
import com.anecon.taf.core.reporting.Keyword;
import com.anecon.taf.core.util.Waiter;
import com.anecon.taf.gettingStarted.ui.base.UiKeywords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateAccountUiKeywords extends UiKeywords {
    private static final Logger log = LoggerFactory.getLogger(CreateAccountUiKeywords.class);

    private static final WebLocator NAME_FIELD = WebLocator.name("name");
    private static final WebLocator EMAIL_ADDRESS_FIELD = WebLocator.css("input.email-address");
    private static final WebLocator PHONE_NUMBER_FIELD = WebLocator.css("input.phone-number");
    private static final WebLocator WEBSITE_FIELD = WebLocator.name("website");
    private static final WebLocator SAVE_BUTTON = WebLocator.css("button[data-action='save']");
    private static final WebLocator EDIT_BUTTON = WebLocator.css("button[data-action='edit']");

    CreateAccountUiKeywords(WebDriverClient client) {
        super(client);
    }

    @Keyword("Fill name field")
    public CreateAccountUiKeywords fillName(String name) {
        Waiter.waitFor(() -> client.element(NAME_FIELD).isDisplayed());

        log.info("Filling name text field with: {}", name);
        client.element(NAME_FIELD).setText(name);

        return this;
    }

    @Keyword("Fill email address field")
    public CreateAccountUiKeywords fillEmailAddress(String emailAddress) {
        Waiter.waitFor(() -> client.element(EMAIL_ADDRESS_FIELD).isDisplayed());

        log.info("Filling email address text field with: {}", emailAddress);
        client.element(EMAIL_ADDRESS_FIELD).setText(emailAddress);

        return this;
    }

    @Keyword("Fill phone number field")
    public CreateAccountUiKeywords fillPhoneNumber(String phoneNumber) {
        Waiter.waitFor(() -> client.element(PHONE_NUMBER_FIELD).isDisplayed());

        log.info("Filling phone number text field with: {}", phoneNumber);
        client.element(PHONE_NUMBER_FIELD).setText(phoneNumber);

        return this;
    }

    @Keyword("Fill website field")
    public CreateAccountUiKeywords fillWebsite(String website) {
        Waiter.waitFor(() -> client.element(NAME_FIELD).isDisplayed());

        log.info("Filling website text field with: {}", website);
        client.element(WEBSITE_FIELD).setText(website);

        return this;
    }

    @Keyword("Click save button")
    public void clickSave() {
        log.info("Clicking on save button");
        client.element(SAVE_BUTTON).click();

        Waiter.waitFor(() -> client.element(EDIT_BUTTON).isDisplayed());
    }
}
