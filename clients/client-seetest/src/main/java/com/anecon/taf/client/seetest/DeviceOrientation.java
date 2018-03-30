package com.anecon.taf.client.seetest;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * An enum containing the supported device orientations. SeeTest supports landscape and portrait
 *
 * @see <a href="https://docs.experitest.com/display/public/SA/Landscape+And+Portrait">SeeTest Documentation - Landscape and Portrait</a>
 *
 * @author Michael.Hombauer
 *
 */
public enum DeviceOrientation {

    LANDSCAPE(SpecialKeyCommands.ORIENTATION_LANDSCAPE), PORTRAIT(SpecialKeyCommands.ORIENTATION_PORTRAIT);

    private final String keyCommand;

    private DeviceOrientation(String keyCommand) {
        this.keyCommand = keyCommand;
    }

    public String getKeyCommand() {
        return keyCommand;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
