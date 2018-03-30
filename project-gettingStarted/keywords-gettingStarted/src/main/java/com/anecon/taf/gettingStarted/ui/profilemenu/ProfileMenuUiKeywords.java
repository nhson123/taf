package com.anecon.taf.gettingStarted.ui.profilemenu;

import com.anecon.taf.client.selenium.WebDriverClient;
import com.anecon.taf.client.selenium.WebLocator;
import com.anecon.taf.core.reporting.Keyword;
import com.anecon.taf.core.util.Waiter;
import com.anecon.taf.gettingStarted.ui.base.UiKeywords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileMenuUiKeywords extends UiKeywords {
    private static final Logger log = LoggerFactory.getLogger(ProfileMenuUiKeywords.class);

    private static final WebLocator PROFILE_DROPDOWN_TOGGLE = WebLocator.id("nav-menu-dropdown");
    private static final WebLocator USERNAME_LINK = WebLocator.css("a[href^='#User/view/']");

    public ProfileMenuUiKeywords(WebDriverClient selenium) {
        super(selenium);
    }

    @Keyword("Click menu button")
    public ProfileMenuUiKeywords clickMenu() {
        Waiter.waitFor(() -> client.element(PROFILE_DROPDOWN_TOGGLE).isDisplayed());

        log.info("Opening profile menu");
        client.element(PROFILE_DROPDOWN_TOGGLE).click();

        return this;
    }

    public String getUsername() {
        log.info("Reading username from UI");

        return client.element(USERNAME_LINK).getText();
    }
}
