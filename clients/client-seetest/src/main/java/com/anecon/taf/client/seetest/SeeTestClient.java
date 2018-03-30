package com.anecon.taf.client.seetest;

import com.anecon.taf.client.genericui.MobileUiClient;
import com.experitest.client.Client;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * The main class for interacting with SeeTest, mobile devices and applications
 *
 * @author Michael Hombauer
 */
public class SeeTestClient implements MobileUiClient<SeeTestElement, DeviceOrientation, SeeTestSearchStrategy> {

    private static final Logger log = LoggerFactory.getLogger(SeeTestClient.class);

    private final Client client;
    private final SeeTestConfiguration configuration;
    private final DeviceIdentifier deviceIdentifier;
    private String application;

    /**
     * Constructs this client
     *
     * @param deviceIdentifier the {@link DeviceIdentifier}
     * @param config           the {@link SeeTestConfiguration}
     */
    public SeeTestClient(DeviceIdentifier deviceIdentifier, SeeTestConfiguration config) {
        this.deviceIdentifier = deviceIdentifier;
        this.configuration = config;

        this.client = new Client(this.configuration.getHost(), this.configuration.getPort(), true);
        this.client.setReporter(this.configuration.getReporterName(), this.configuration.getReporterDirectory(),
                this.configuration.getTestName());
        this.client.setDevice(deviceIdentifier.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(String application) {
        this.application = application;
        log.debug("Starting application '{}' on device '{}'", application, deviceIdentifier);
        if (application.equals(this.client.getCurrentApplicationName())) {
            log.debug("Application '{}' already started", application);
        }
        client.launch(application, configuration.isStartInstrumented(), configuration.isKillAppOnStart());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        log.debug("Stopping application {}", application);
        client.applicationClose(application);

        log.debug("Shutting down SeeTest client");
        // As of a support meeting with Experitest this is the right way to tear down the client
        client.generateReport(true);
    }

    /**
     * Installs an application on the device
     *
     * @param pathToApplicationFile The path to application installation file on a local disc (e.g. APK or IPA)
     * @param instrument            Whether the application should be instrumented. If you are using iOS and want your application instrumented, you should
     *                              instrument your application using this instructions
     *                              (https://docs.experitest.com/display/public/SA/Manually+Instrumenting+iOS+Applications) and pass <code>false</code>. If
     *                              you are using Android and want your application to be instrumented pass <code>true</code>.
     * @param keepData              Whether the application data should be retained (Android only)
     * @see <a href="https://docs.experitest.com/display/public/SA/Install">SeeTest Documentation - Install</a>
     */
    @Override
    public void install(String pathToApplicationFile, boolean instrument, boolean keepData) {
        log.debug("Installing application from file {}", pathToApplicationFile);
        log.debug("Application will be instrumented: {}", instrument);
        log.debug("Data will be retained: {}", keepData);
        client.install(pathToApplicationFile, instrument, keepData);
    }

    /**
     * Uninstall an application from the device.
     *
     * @param applicationIdentifier The application to uninstall. For iOS this has to be the complete name (e.g. "your.application.ApplicationName"). For
     *                              Android this has to be the package name (e.g. "your.application").
     * @see <a href="https://docs.experitest.com/display/public/SA/Uninstall">SeeTest Documentation - Uninstall</a>
     */
    @Override
    public void uninstall(String applicationIdentifier) {
        log.debug("Uninstalling application {}", applicationIdentifier);
        client.uninstall(applicationIdentifier);
    }

    /**
     * {@inheritDoc}
     *
     * @see <a href="https://docs.experitest.com/display/public/SA/Swipe">SeeTest Documentation - Swipe</a>
     */
    @Override
    public void swipe(SeeTestSearchStrategy swipeParameters) {
        log.debug("Doing a swipe with the following parameters: {}", swipeParameters);
        client.swipe(swipeParameters.getSwipeDirection(), swipeParameters.getOffset(), swipeParameters.getSwipeTime());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void click(SeeTestElement element) {
        click(element, 1);
    }

    /**
     * {@inheritDoc}
     *
     * @see <a href="https://docs.experitest.com/display/public/SA/Click">SeeTest Documentation - Click</a>
     */
    @Override
    public void click(SeeTestElement element, int clickCount) {
        log.debug("Element {} will be clicked {} times", element, clickCount);
        client.click(element.getZone(), element.getIdentifier(), element.getIndex(), 5);
    }

    /**
     * {@inheritDoc}
     *
     * @see <a href="https://docs.experitest.com/display/public/SA/ElementListPick">SeeTest Documentation - listPick</a>
     */
    @Override
    public void clickListElement(SeeTestElement list, SeeTestElement listItem) {
        log.debug("Clicking on {} from list {}", listItem, list);
        // @formatter:off
        client.elementListPick(
                // List
                list.getZone(), list.getIdentifier(),
                // Item in the list
                listItem.getZone(), listItem.getIdentifier(), listItem.getIndex(),
                // Perform click when both elements are found
                true);
        // @formatter:on
    }

    /**
     * {@inheritDoc}
     *
     * @see <a href="https://docs.experitest.com/display/public/SA/ElementGetText">SeeTest Documentation - getText</a>
     */
    @Override
    public String getText(SeeTestElement element) {
        log.debug("Getting text from element {}", element);
        return client.elementGetText(element.getZone(), element.getIdentifier(), element.getIndex());
    }

    /**
     * Enters text into a text field (actually it pastes it). This method should be used instead of SeeTest's sendText because it can only
     * deal with ASCII characters.
     *
     * @param element The element to send the text to
     * @param text    The text to send
     */
    @Override
    public void type(SeeTestElement element, String text) {
        log.debug("Setting text \"{}\" in element {}", text, element);
        client.elementSendText(element.getZone(), element.getIdentifier(), element.getIndex(), text);
    }

    /**
     * {@inheritDoc}
     *
     * @see <a href="https://docs.experitest.com/display/public/SA/IsElementFound">SeeTest Documentation - isElementFound</a>
     */
    @Override
    public boolean isElementVisible(SeeTestElement element) {
        log.debug("Checking wether element {} is visible", element);
        return client.isElementFound(element.getZone(), element.getIdentifier(), element.getIndex());
    }

    /**
     * {@inheritDoc}
     *
     * @see <a href="https://docs.experitest.com/display/public/SA/WaitForElement">SeeTest Documentation - waitForElement</a>
     */
    @Override
    public boolean waitForElement(SeeTestElement element, int timeoutInSeconds) {
        log.debug("Waiting up to {} seconds until element {} is visible", timeoutInSeconds, element);
        return client.waitForElement(element.getZone(), element.getIdentifier(), element.getIndex(),
                timeoutInSeconds * 1000);
    }

    /**
     * {@inheritDoc}
     *
     * @see <a href="https://docs.experitest.com/display/public/SA/WaitForElementToVanish">SeeTest Documentation - waitForElementToVanish</a>
     */
    @Override
    public boolean waitForElementToVanish(SeeTestElement element, int timeoutInSeconds) {
        log.debug("Waiting up to {} seconds for element {} to vanish", timeoutInSeconds, element);
        return client.waitForElementToVanish(element.getZone(), element.getIdentifier(), element.getIndex(),
                timeoutInSeconds * 1000);
    }

    /**
     * {@inheritDoc}
     *
     * @see <a href="https://docs.experitest.com/display/public/SA/ElementGetProperty">SeeTest Documentation - getProperty</a>
     */
    @Override
    public String getProperty(SeeTestElement element, String property) {
        log.debug("Getting property \"{}\" of element {}", property, element);
        return client.elementGetProperty(element.getZone(), element.getIdentifier(), element.getIndex(), property);
    }

    public boolean swipeWhileNotFound(SeeTestSearchStrategy searchStrategy, SeeTestElement elementToFind,
                                      boolean doClick) {
        // @formatter:off
        return client.swipeWhileNotFound(
                // Search parameters
                searchStrategy.getSwipeDirection(), searchStrategy.getOffset(), searchStrategy.getSwipeTime(),
                // Element to find
                elementToFind.getZone(), elementToFind.getIdentifier(), elementToFind.getIndex(),
                // More search parameters
                searchStrategy.getDelay(), searchStrategy.getRounds(),
                // Perform click
                doClick);
        // @formatter:on
    }

    /**
     * {@inheritDoc}
     *
     * @see <a href="https://docs.experitest.com/display/public/SA/ElementSwipeWhileNotFound">SeeTest Documentation - swipeWhileNotFound</a>
     */
    @Override
    public boolean elementSwipeWhileNotFound(SeeTestSearchStrategy searchStrategy, SeeTestElement listElement,
                                             SeeTestElement elementToFind, boolean doClick) {
        log.debug("Swiping to find element {} in list {} using search strategy {}", elementToFind, listElement,
                searchStrategy);
        log.debug("Element will be clicked when found: {}", doClick);
        // @formatter:off
        return client.elementSwipeWhileNotFound(
                // List
                listElement.getZone(), listElement.getIdentifier(),
                // Search parameters
                searchStrategy.getSwipeDirection(), searchStrategy.getOffset(), searchStrategy.getSwipeTime(),
                // Element to find
                elementToFind.getZone(), elementToFind.getIdentifier(), elementToFind.getIndex(),
                // More search parameters
                searchStrategy.getDelay(), searchStrategy.getRounds(),
                // Perform click
                doClick);
        // @formatter:on
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closeKeyboard() {
        log.debug("Closing keyboard");
        client.closeKeyboard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void backSpaceButton() {
        log.debug("Sending backspace");
        client.sendText(SpecialKeyCommands.BACKSPACE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void homeButton() {
        log.debug("Pressing HOME button");
        client.sendText(SpecialKeyCommands.HOME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterButton() {
        log.debug("Sending enter");
        client.sendText(SpecialKeyCommands.ENTER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void browserBackButton() {
        log.debug("Sending browser back");
        client.sendText(SpecialKeyCommands.BROWSER_BACK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unlockDevice() {
        log.debug("Unlocking device");
        client.sendText(SpecialKeyCommands.DEVICE_UNLOCK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void wakeDevice() {
        log.debug("Waking device");
        client.sendText(SpecialKeyCommands.DEVICE_WAKE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDeviceOrientation(DeviceOrientation deviceOrientation) {
        log.debug("Setting device orientation to {}", deviceOrientation);
        client.sendText(deviceOrientation.getKeyCommand());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String takeScreenshot() {
        log.debug("Taking a screenshot");
        final Map<String, Object> commandResultMap = client.getLastCommandResultMap();
        String path = (String) commandResultMap.get("outFile");

        if (StringUtils.isEmpty(path)) {
            path = client.capture();
        }
        log.debug("Saved screenshot here: {}", path);
        return path;
    }

}
