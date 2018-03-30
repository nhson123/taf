package com.anecon.taf.client.selenium;

import com.anecon.taf.client.ui.api.UnexpectedClientException;
import org.openqa.selenium.By;

public class WebLocator {
    private final By by;

    private WebLocator(WebLocatorStrategy locatorStrategy, String locator) {
        switch (locatorStrategy) {
            case ID:
                by = By.id(locator);
                break;
            case CLASS_NAME:
                by = By.className(locator);
                break;
            case CSS:
                by = By.cssSelector(locator);
                break;
            case XPATH:
                by = By.xpath(locator);
                break;
            case LINK_TEXT:
                by = By.linkText(locator);
                break;
            case PARTIAL_LINK_TEXT:
                by = By.partialLinkText(locator);
                break;
            case TAG_NAME:
                by = By.tagName(locator);
                break;
            case NAME:
                by = By.name(locator);
                break;
            default:
                throw new UnexpectedClientException("Can't create locators for " + locatorStrategy);
        }
    }

    public static WebLocator id(String id) {
        return new WebLocator(WebLocatorStrategy.ID, id);
    }

    public static WebLocator className(String className) {
        return new WebLocator(WebLocatorStrategy.CLASS_NAME, className);
    }

    public static WebLocator xpath(String xpath) {
        return new WebLocator(WebLocatorStrategy.XPATH, xpath);
    }

    public static WebLocator css(String css) {
        return new WebLocator(WebLocatorStrategy.CSS, css);
    }

    public static WebLocator linkText(String linkText) {
        return new WebLocator(WebLocatorStrategy.LINK_TEXT, linkText);
    }

    public static WebLocator partialLinkText(String partialLinkText) {
        return new WebLocator(WebLocatorStrategy.PARTIAL_LINK_TEXT, partialLinkText);
    }

    public static WebLocator tagName(String tagName) {
        return new WebLocator(WebLocatorStrategy.TAG_NAME, tagName);
    }

    public static WebLocator name(String name) {
        return new WebLocator(WebLocatorStrategy.NAME, name);
    }

    By getBy() {
        return by;
    }

    @Override
    public String toString() {
        return by.toString();
    }

    private enum WebLocatorStrategy {
        ID, CLASS_NAME, CSS, XPATH, LINK_TEXT, PARTIAL_LINK_TEXT, TAG_NAME, NAME
    }
}
