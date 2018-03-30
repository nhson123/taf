package com.anecon.taf.client.selenium;

import com.anecon.taf.client.ui.api.*;
import com.anecon.taf.client.ui.api.Dimension;
import com.anecon.taf.client.ui.api.table.Column;
import com.anecon.taf.client.ui.api.table.Row;
import com.anecon.taf.client.ui.api.table.Table;
import com.anecon.taf.core.EventBusHolder;
import com.anecon.taf.core.event.ScreenshotEvent;
import com.google.common.eventbus.Subscribe;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WebDriverClient implements BrowserClient<WebLocator> {
    private static final Logger log = LoggerFactory.getLogger(WebDriverClient.class);

    private final WebDriver webDriver;
    private final SeleniumWindow window;

    WebDriverClient(WebDriver webDriver) {
        log.debug("Creating WebDriverClient");

        this.webDriver = webDriver;
        this.window = new SeleniumWindow();

        EventBusHolder.register(window);
    }

    @Override
    public void quit() {
        log.debug("Closing WebDriver");

        EventBusHolder.unregister(window);
        webDriver.quit();
    }

    @Override
    public Window window() {
        return window;
    }

    @Override
    public Element<WebLocator> element(WebLocator locator) {
        return new SeleniumElement(locator);
    }

    @Override
    public Elements<WebLocator> elements(WebLocator locator) {
        return new SeleniumElements(locator);
    }

    @Override
    public Table<WebLocator> table(WebLocator tableLocator, WebLocator rowLocator) {
        return new SeleniumTable(tableLocator, rowLocator);
    }

    @Override
    public void go(String url) {
        log.trace("Navigating to URL: {}", url);

        webDriver.get(url);
    }

    @Override
    public String getCurrentUrl() {
        final String currentUrl = webDriver.getCurrentUrl();
        log.trace("Retrieving current URL: {}", currentUrl);
        return currentUrl;
    }

    @Override
    public void refresh() {
        log.trace("Reloading page");

        webDriver.navigate().refresh();
    }

    @Override
    public Optional<Object> executeScript(String script, Object... args) {
        if (webDriver instanceof JavascriptExecutor) {
            log.trace("Executing JavaScript:\n{}\n\nwith args: {}", script, args);
            return Optional.ofNullable(((JavascriptExecutor) webDriver).executeScript(script, args));
        } else {
            log.warn("Executing scripts isn't supported by the current browser");
            return Optional.empty();
        }
    }

    @Override
    public History history() {
        return new SeleniumHistory();
    }

    @Override
    public Prompt prompt() {
        return new SeleniumPrompt();
    }

    private class SeleniumTable extends SeleniumElement implements Table<WebLocator> {
        private final WebLocator rowLocator;

        private SeleniumTable(WebLocator tableLocator, WebLocator rowLocator) {
            super(tableLocator);
            this.rowLocator = rowLocator;
        }

        @Override
        public Row<WebLocator> getRow(Column<WebLocator> column, String value) {
            final Optional<Row<WebLocator>> optionalRow = getOptionalRow(column, value);

            if (optionalRow.isPresent()) {
                return optionalRow.get();
            } else {
                throw new NoSuchElementException("Couldn't find row with text " + value + " in column with index " + column.getIndex());
            }
        }

        @Override
        public Row<WebLocator> getRow(int index) {
            return getRows().get(index);
        }

        @Override
        public Row<WebLocator> getRow(WebLocator rowLocator) {
            return new SeleniumRow(webElement.findElement(rowLocator.getBy()));
        }

        @Override
        public Row<WebLocator> getHeader(WebLocator headerLocator) {
            return getRow(headerLocator);
        }

        @Override
        public List<Row<WebLocator>> getRows() {
            return webElement.findElements(rowLocator.getBy()).stream().map(SeleniumRow::new).collect(Collectors.toList());
        }

        @Override
        public boolean rowExists(Column<WebLocator> column, String value) {
            return getOptionalRow(column, value).isPresent();
        }

        private Optional<Row<WebLocator>> getOptionalRow(Column<WebLocator> column, String value) {
            return getRows().stream().filter(r -> r.getCell(column).getText().equals(value)).findFirst();
        }
    }

    private class SeleniumRow extends SeleniumElement implements Row<WebLocator> {
        private SeleniumRow(WebElement webElement) {
            super(webElement);
        }

        @Override
        public Element<WebLocator> getCell(Column<WebLocator> column) {
            return new SeleniumElement(webElement.findElements(column.getColumnsLocator().getBy()).get(column.getIndex()));
        }
    }

    private class SeleniumElement implements Element<WebLocator> {
        final WebElement webElement;

        private SeleniumElement(WebLocator locator) {
            this(webDriver.findElement(locator.getBy()));
        }

        private SeleniumElement(WebElement webElement) {
            this.webElement = webElement;
        }

        @Override
        public boolean isSelected() {
            log.trace("Retrieving select status for element: {}", webElement);
            final boolean selected = webElement.isSelected();
            log.trace("Selected: {}", selected);

            return selected;
        }

        @Override
        public String getAttribute(String attributeName) {
            log.trace("Retrieving attribute {} from element: {}", attributeName, webElement);
            final String attribute = webElement.getAttribute(attributeName);
            log.trace("{}: {}", attributeName, attribute);
            return attribute;
        }

        @Override
        public String getText() {
            log.trace("Retrieving text from element: {}", webElement);
            final String text = webElement.getText();
            log.trace("Text: {}", text);
            return text;
        }

        @Override
        public String getElementType() {
            log.trace("Retrieving tag name from element: {}", webElement);
            final String tagName = webElement.getTagName();
            log.trace("Tag name: {}", tagName);
            return tagName;
        }

        @Override
        public boolean isEnabled() {
            log.trace("Retrieving enabled status from element: {}", webElement);
            final boolean enabled = webElement.isEnabled();
            log.trace("Enabled: {}", enabled);
            return enabled;
        }

        @Override
        public boolean isDisplayed() {
            log.trace("Retrieving displayed status from element: {}", webElement);
            try {
                final boolean displayed = webElement.isDisplayed();
                log.trace("Displayed: {}", displayed);
                return displayed;
            } catch (NoSuchElementException e) {
                log.trace("Displayed: Couldn't find element");
                return false;
            }
        }

        @Override
        public void click() {
            log.trace("Clicking on element: {}", webElement);
            webElement.click();
        }

        @Override
        public void clear() {
            log.trace("Clearing element: {}", webElement);
            webElement.clear();
        }

        @Override
        public void sendKeys(CharSequence... keys) {
            log.trace("Sending keys {} to element: {}", keys, webElement);
            webElement.sendKeys(keys);
        }

        @Override
        public File takeScreenshot() {
            log.trace("Taking screenshot");
            return webElement.getScreenshotAs(OutputType.FILE);
        }

        @Override
        public void selectDropdown(String visibleText) {
            final Select select = new Select(webElement);
            select.selectByVisibleText(visibleText);
        }

        @Override
        public Element getChild(WebLocator locator) {
            return new SeleniumElement(webElement.findElement(locator.getBy()));
        }
    }

    private class SeleniumElements implements Elements<WebLocator> {
        private final List<Interactable> elements;

        private SeleniumElements(WebLocator webLocator) {
            elements = webDriver.findElements(webLocator.getBy())
                    .stream().map(SeleniumElement::new).collect(Collectors.toList());
        }

        @Override
        public void click() {
            elements.forEach(Interactable::click);
        }

        @Override
        public void clear() {
            elements.forEach(Interactable::clear);
        }

        @Override
        public void sendKeys(CharSequence... keys) {
            elements.forEach(e -> e.sendKeys(keys));
        }

        @Override
        public void selectDropdown(String visibleText) {
            elements.forEach(e -> e.selectDropdown(visibleText));
        }

        @Override
        public int size() {
            return elements.size();
        }
    }

    private class SeleniumWindow implements Window {
        private SeleniumWindow() {

        }

        @Override
        public void close() {
            log.debug("Closing browser window");

            webDriver.close();
        }

        @Override
        public String getTitle() {
            log.trace("Retrieving window title");
            final String title = webDriver.getTitle();
            log.trace("Title: {}", title);
            return title;
        }

        @Override
        public void maximize() {
            log.trace("Maximizing browser window");
            webDriver.manage().window().maximize();
        }

        @Override
        public void fullscreen() {
            log.trace("Setting browser window to fullscreen");
            webDriver.manage().window().fullscreen();
        }

        @Override
        public Dimension getSize() {
            log.trace("Retrieving window size");
            final org.openqa.selenium.Dimension size = webDriver.manage().window().getSize();
            log.trace("Window size - width: {}, height: {}", size.width, size.height);
            return new Dimension(size.width, size.height);
        }

        @Override
        public void setSize(Dimension size) {
            log.trace("Setting window size to {}", size);
            webDriver.manage().window().setSize(new org.openqa.selenium.Dimension(size.getWidth(), size.getHeight()));
        }

        @Override
        public Optional<File> takeScreenshot() {
            if (webDriver instanceof TakesScreenshot) {
                return Optional.of(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));
            } else {
                return Optional.empty();
            }
        }

        @Subscribe
        public void addScreenshotToEvent(ScreenshotEvent event) {
            event.addScreenshot(takeScreenshot().orElseThrow(() -> new UnexpectedClientException("Couldn't take screenshot")));
        }
    }

    private class SeleniumHistory implements History {
        private SeleniumHistory() {

        }

        @Override
        public void back() {
            log.trace("Navigating back");
            webDriver.navigate().back();
        }

        @Override
        public void forward() {
            log.trace("Navigating forward");
            webDriver.navigate().forward();
        }
    }

    private class SeleniumPrompt implements Prompt {
        private SeleniumPrompt() {

        }

        @Override
        public void dismissAlert() {
            log.trace("Dismissing alert");
            webDriver.switchTo().alert().dismiss();
        }

        @Override
        public void acceptAlert() {
            log.trace("Accepting alert");
            webDriver.switchTo().alert().accept();
        }

        @Override
        public String getAlertText() {
            log.trace("Retrieving alert text");
            final String text = webDriver.switchTo().alert().getText();
            log.trace("Text: {}", text);
            return text;
        }

        @Override
        public void sendAlertText(String keys) {
            log.trace("Send keys to alert: {}", keys);
            webDriver.switchTo().alert().sendKeys(keys);
        }

        @Override
        public boolean isDisplayed() {
            log.trace("Checking if alert is displayed...");
            try {
                webDriver.switchTo().alert();
                log.trace("...alert is displayed");
                return true;
            } catch (NoAlertPresentException e) {
                log.trace("Alert isn't displayed");
                return false;
            }
        }
    }
}
