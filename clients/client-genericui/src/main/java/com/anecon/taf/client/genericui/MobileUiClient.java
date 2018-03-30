package com.anecon.taf.client.genericui;

/**
 * A somewhat SeeTest specific interface for interacting with mobile devices and applications.
 * <p>
 * This interface does not extend {@link GenericUiClient} because a lot of the methods don't make sense with
 * this interface.
 *
 * @param <ELEMENTLOCATOR>    the element locator to use
 * @param <DEVICEORIENTATION> the enum containing device orientations (e.g. landscape or portrait)
 * @author Michael Hombauer
 */
public interface MobileUiClient<ELEMENTLOCATOR, DEVICEORIENTATION, SEARCHSTRATEGY> {

    /**
     * Install an application on the device
     *
     * @param pathToApplicationFile The path to application installation file on a local disc (e.g. APK or IPA)
     * @param instrument            Whether the application should be instrumented
     * @param keepData              Whether the application data should be retained (Android only)
     */
    void install(String pathToApplicationFile, boolean instrument, boolean keepData);

    /**
     * Uninstall an application from the device.
     *
     * @param applicationIdentifier The application to uninstall
     */
    void uninstall(String applicationIdentifier);

    /**
     * Start the application with the given name. This can also be a URL to start a browser.
     *
     * @param application The application's activity name or a URL
     */
    void start(String application);

    /**
     * Stop the application and tear down the client
     */
    void stop();

    /**
     * Do a swipe
     *
     * @param swipeParameters The swipe parameters
     */
    void swipe(SEARCHSTRATEGY swipeParameters);

    /**
     * Click on an element
     *
     * @param element The element to click
     */
    void click(ELEMENTLOCATOR element);

    /**
     * Click on an element <code>clickCount</code> times
     *
     * @param element    The element to click
     * @param clickCount How often to click
     */
    void click(ELEMENTLOCATOR element, int clickCount);

    /**
     * Click on an element in a list (e.g. in list views)
     *
     * @param list     The list containing the items
     * @param listItem The item in the list to click
     */
    void clickListElement(ELEMENTLOCATOR list, ELEMENTLOCATOR listItem);

    /**
     * Get the text of an element (e.g. labels, input fields, etc.)
     *
     * @param element The element to get the text from
     * @return The text of the element
     */
    String getText(ELEMENTLOCATOR element);

    /**
     * Enters text into a text field
     *
     * @param element The element to send the text to
     * @param text    The text to send
     */
    void type(ELEMENTLOCATOR element, String text);

    /**
     * Returns wether an element is visible
     *
     * @param element The element to check for
     * @return true if the element is visible (on screen), false otherwise
     */
    boolean isElementVisible(ELEMENTLOCATOR element);

    /**
     * Wait up to <code>timeoutInSeconds</code> seconds for an element to be visible (on screen)
     *
     * @param element          The element to wait for
     * @param timeoutInSeconds The maximum number of seconds to wait
     * @return true if the element is visible, false otherwise
     */
    boolean waitForElement(ELEMENTLOCATOR element, int timeoutInSeconds);

    /**
     * Wait up to <code>timeoutInSeconds</code> seconds for an element to vanish
     *
     * @param element          The element to wait for
     * @param timeoutInSeconds The maximum number of seconds to wait
     * @return true if the element is no longer visible, false otherwise
     */
    boolean waitForElementToVanish(ELEMENTLOCATOR element, int timeoutInSeconds);

    /**
     * Get the property value of the element
     *
     * @param element  The element from which to get a property
     * @param property The property to get
     * @return The value of the property
     */
    String getProperty(ELEMENTLOCATOR element, String property);

    /**
     * Swipe on the screen to find an element using a search strategy (swipe direction and speed)
    boolean swipeWhileNotFound(SEARCHSTRATEGY searchStrategy, ELEMENTLOCATOR elementToFind, boolean doClick);

    /**
     * Swipe in a list to find an element using a search strategy (swipe direction and speed)
     *
     * @param searchStrategy The search strategy to use
     * @param listElement    The list in which to swipe
     * @param elementToFind  The element to look
     * @param doClick        Whether to click the element if it was found
     * @return true if <code>elementToFind</code> was found, false otherwise
     */
    boolean elementSwipeWhileNotFound(SEARCHSTRATEGY searchStrategy, ELEMENTLOCATOR listElement,
                                      ELEMENTLOCATOR elementToFind, boolean doClick);

    /**
     * Closes the keyboard
     */
    void closeKeyboard();

    /**
     * Presses the back button
     */
    void backSpaceButton();

    /**
     * Presses the home button
     */
    void homeButton();

    /**
     * Presses the enter button
     */
    void enterButton();

    /**
     * Presses the browser back button
     */
    void browserBackButton();

    /**
     * Unlocks the device
     */
    void unlockDevice();

    /**
     * Wakes the device up
     */
    void wakeDevice();

    /**
     * Sets the device's screen orientation (e.g. landscape or portrait)
     *
     * @param deviceOrientation the desired orientation
     */
    void setDeviceOrientation(DEVICEORIENTATION deviceOrientation);

    /**
     * Takes a screenshot and returns the path where screenshot is stored. The screenshot must NOT be deleted as it may be included in the
     * report of the mobile automation tool
     *
     * @return The path to the screenshot
     */
    String takeScreenshot();
}
