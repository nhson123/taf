package com.anecon.taf.client.seetest;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Identifies a device. The string here is used by SeeTest to select the device to use
 *
 * @author Michael Hombauer
 * @see <a href="https://docs.experitest.com/display/public/SA/SetDevice">SeeTest Documentation - SetDevice</a>
 */
public enum DeviceIdentifier {

    // This is just an example
    IOS_IPAD_AIR_2("ios_app:iPad Mini");

    private String id;

    private DeviceIdentifier(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
