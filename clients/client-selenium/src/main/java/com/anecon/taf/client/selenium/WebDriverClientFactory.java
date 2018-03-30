package com.anecon.taf.client.selenium;

import com.anecon.taf.client.ui.api.UnexpectedClientException;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.selendroid.client.SelendroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class WebDriverClientFactory {
    private static final Logger log = LoggerFactory.getLogger(WebDriverClientFactory.class);
    private static final Map<Browser, DesiredCapabilities> DEFAULT_CAPABILITIES = new HashMap<>();

    private final Browser browser;
    private final DesiredCapabilities desiredCapabilities;

    static {
        DEFAULT_CAPABILITIES.put(Browser.INTERNET_EXPLORER, DesiredCapabilities.internetExplorer());
        DEFAULT_CAPABILITIES.put(Browser.CHROME, DesiredCapabilities.chrome());
        DEFAULT_CAPABILITIES.put(Browser.FIREFOX, DesiredCapabilities.firefox());
        DEFAULT_CAPABILITIES.put(Browser.ANDROID, DesiredCapabilities.android());
    }

    private WebDriverClientFactory(Browser browser) {
        if (!DEFAULT_CAPABILITIES.containsKey(browser)) {
            throw new UnexpectedClientException("No default capabilities for browser " + browser);
        }

        this.browser = browser;
        desiredCapabilities = DEFAULT_CAPABILITIES.get(browser);
    }

    public static WebDriverClientFactory withBrowser(Browser browser) {
        return new WebDriverClientFactory(browser);
    }

    public WebDriverClientFactory withCapability(String name, String value) {
        desiredCapabilities.setCapability(name, value);
        return this;
    }

    public Grid grid(String gridUrl) throws MalformedURLException {
        return new Grid(gridUrl);
    }

    public WebDriverClient create() {
        log.trace("Creating local WebDriver for browser {} with capabilities {}", browser, desiredCapabilities);

        final WebDriver webDriver;
        switch (browser) {
            case INTERNET_EXPLORER:
                InternetExplorerDriverManager.getInstance().setup();
                webDriver = new InternetExplorerDriver(desiredCapabilities);
                break;
            case CHROME:
                ChromeDriverManager.getInstance().setup();
                webDriver = new ChromeDriver(desiredCapabilities);
                break;
            case FIREFOX:
                FirefoxDriverManager.getInstance().setup();
                webDriver = new FirefoxDriver(desiredCapabilities);
                break;
            case ANDROID:
                try {
                    webDriver = new SelendroidDriver(desiredCapabilities);
                } catch (Exception e) {
                    throw new UnexpectedClientException("Couldn't create Selendroid driver", e);
                }
                break;
            default:
                throw new UnexpectedClientException("Unknown browser " + browser);
        }
        return new WebDriverClient(webDriver);
    }

    public class Grid {
        private final URL gridUrl;

        private Grid(String gridUrl) throws MalformedURLException {
            this.gridUrl = new URL(gridUrl);
        }

        public WebDriverClient create() {
            log.trace("Creating Grid WebDriver at {} with capabilities {}", gridUrl, desiredCapabilities);

            return new WebDriverClient(new RemoteWebDriver(gridUrl, desiredCapabilities));
        }
    }
}
