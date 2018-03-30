package com.anecon.taf.client.seetest;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Contains everything SeeTest needs to identify an element
 *
 * @author Michael Hombauer
 */
public class SeeTestElement {

    /**
     * The zone in which to look for
     *
     * @see <a href="https://docs.experitest.com/display/public/SA/Zone">SeeTest Documentation - Zone</a>
     */
    private final String zone;

    /**
     * The element identifier
     *
     * @see <a href="https://docs.experitest.com/display/public/SA/Element">SeeTest Documentation - Element</a>
     */
    private final String elementIdentifier;

    /**
     * The index of the element.
     * <br>
     * In the mobile world it is possible that an ID occurs several times in a screen (e.g. in a list view). However, if that is
     * the case, one should look for other ways to exactly identify an element (e.g. the contained text).
     *
     * @see <a href="https://docs.experitest.com/display/public/SA/Index">SeeTest Documentation - Index</a>
     */
    private final int index;

    public SeeTestElement(String zone, String elementIdentifier, int index) {
        this.zone = zone;
        this.elementIdentifier = elementIdentifier;
        this.index = index;
    }

    public String getZone() {
        return zone;
    }

    public String getIdentifier() {
        return elementIdentifier;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
