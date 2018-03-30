package com.anecon.taf.gettingStarted.ui;

import com.anecon.taf.client.selenium.Browser;
import com.anecon.taf.client.selenium.WebDriverClientFactory;
import com.anecon.taf.client.selenium.WebLocator;
import com.anecon.taf.core.Cleanable;
import com.anecon.taf.core.reporting.Keyword;
import com.anecon.taf.core.util.Waiter;
import com.anecon.taf.gettingStarted.aeonbits.EspoConfigHolder;
import com.anecon.taf.gettingStarted.model.User;
import com.anecon.taf.gettingStarted.ui.accounts.AccountsUiKeywords;
import com.anecon.taf.gettingStarted.ui.base.UiKeywords;
import com.anecon.taf.gettingStarted.ui.profilemenu.ProfileMenuUiKeywords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UiEspoKeywords extends UiKeywords implements Cleanable {
    private static final Logger log = LoggerFactory.getLogger(UiEspoKeywords.class);

    private static final WebLocator USERNAME_FIELD = WebLocator.id("field-userName");
    private static final WebLocator PASSWORD_FIELD = WebLocator.id("field-password");
    private static final WebLocator LOGIN_BUTTON= WebLocator.id("btn-login");
    private static final WebLocator OPPORTUNITIES_BUTTON= WebLocator.css("a[href='#Opportunity']");
    private static final WebLocator OPPORTUNITIES_CREATE_BUTTON= WebLocator.css("a[href='#Opportunity/create'].btn-primary");
    private static final WebLocator OPPORTUNITIES_NAME= WebLocator.name("name");
    private static final WebLocator OPPORTUNITIES_PROPABILITY= WebLocator.name("probability");
    private static final WebLocator OPPORTUNITIES_SAVE= WebLocator.xpath("//button[@data-action='save']");

    public UiEspoKeywords() {
        super(WebDriverClientFactory.withBrowser(Browser.CHROME).create());
        client.go(EspoConfigHolder.get().home());
        client.window().maximize();
        log.trace("Creating new EspoCRM UI Keywords");
    }

    @Keyword("Log in")
    public UiEspoKeywords login(User user) {
        return login(user.getUserName(), user.getPassword());
    }

    @Keyword("Log in")
    public UiEspoKeywords login(String username, String password) {
        Waiter.waitFor(() -> client.element(USERNAME_FIELD).isDisplayed());

        log.debug("Logging in with user '{}' and password '{}'", username, password);
        client.element(USERNAME_FIELD).setText(username);
        client.element(PASSWORD_FIELD).setText(password);
        client.element(LOGIN_BUTTON).click();

        return this;
    }
    @Keyword("oppoturnities")
    public UiEspoKeywords oppotunities() {
        Waiter.waitFor(() -> client.element(OPPORTUNITIES_BUTTON).isDisplayed());

        log.debug("Oppoturnities click");
        client.element(OPPORTUNITIES_BUTTON).click();

        return this;
    }

    @Keyword("opportunities/create")
    public UiEspoKeywords opportunitiesCreate() {
        Waiter.waitFor(() -> client.element(OPPORTUNITIES_CREATE_BUTTON).isDisplayed());

        log.debug("Opporturnities Create click");
        client.element(OPPORTUNITIES_CREATE_BUTTON).click();
        return this;
    }


    @Keyword("Fill opportunite")
    public UiEspoKeywords opportunitieFillOut() {
        Waiter.waitFor(() -> client.element(OPPORTUNITIES_NAME).isDisplayed());

        log.debug("Fill out the parameter of a opportunity");
        client.element(OPPORTUNITIES_NAME).setText("TestName");
        //client.element(OPPORTUNITIES_PROPABILITY).setText("20");
        client.element(OPPORTUNITIES_SAVE).click();

        return this;
    }

    public boolean loginButtonVisible() {
        return client.element(LOGIN_BUTTON).isDisplayed();
    }

    public ProfileMenuUiKeywords profileMenu() {
        return new ProfileMenuUiKeywords(client);
    }

    public AccountsUiKeywords accounts() {
        return new AccountsUiKeywords(client);
    }

    @Override
    public void cleanUp() {
        client.quit();
    }
}
