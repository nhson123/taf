package com.anecon.taf.core.util;

import com.anecon.taf.core.AutomationFrameworkException;
import com.anecon.taf.core.EventBusHolder;
import com.anecon.taf.core.event.ScreenshotEvent;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Screenshotter {
    private static final Logger log = LoggerFactory.getLogger(Screenshotter.class);
    public static final Screenshotter INSTANCE = new Screenshotter();

    private Screenshotter() {

    }

    public static void init() {
        EventBusHolder.register(INSTANCE);
    }

    public static String takeScreenshotBase64() {
        log.debug("Taking screenshot using AWT robot...");
        BufferedImage image = null;
        try {
            final Rectangle screenSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            log.trace("Display size: {}", screenSize);
            image = new Robot().createScreenCapture(screenSize);
            log.debug("...screenshot taken");
        } catch (AWTException e) {
            throw new AutomationFrameworkException("Couldn't take screenshot");
        }

        return imgToBase64String(image, "png");
    }

    @Subscribe
    public static void screenshotEvent(ScreenshotEvent event) {
        log.debug("Taking screenshot using AWT robot...");
        try {
            final Rectangle screenSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            log.trace("Display size: {}", screenSize);
            BufferedImage image = new Robot().createScreenCapture(screenSize);
            log.debug("...screenshot taken");

            final File imgFile = File.createTempFile("awtScreenshot", ".png");
            ImageIO.write(image, "png", imgFile);
            event.addScreenshot(imgFile);
        } catch (AWTException | IOException e) {
            throw new AutomationFrameworkException("Couldn't take screenshot");
        }
    }

    private static String imgToBase64String(RenderedImage img, String formatName) {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, formatName, Base64.getEncoder().wrap(os));
            return os.toString(StandardCharsets.ISO_8859_1.name());
        } catch (final IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }
}
