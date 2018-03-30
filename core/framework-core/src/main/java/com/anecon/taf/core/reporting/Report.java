package com.anecon.taf.core.reporting;

import com.anecon.taf.core.AutomationFrameworkException;
import com.anecon.taf.core.event.ReportEvent;
import com.anecon.taf.core.event.ScreenshotEvent;
import com.anecon.taf.core.util.Screenshotter;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class Report {
    private static final Logger log = LoggerFactory.getLogger(Report.class);

    private static final ReportingConfig.ReportingConfigSpec CONFIG = ReportingConfig.get();

    private static final Path reportBaseDir = Paths.get(CONFIG.reportPath());
    private static final Path videoDir = reportBaseDir.resolve("videos");
    private static final Path screenshotDir = reportBaseDir.resolve("screenshots");

    static {
        if (ReportingConfig.get().useDefaultScreenshotter()) {
            Screenshotter.init();
        }
    }

    private static String sanitizeText(String text) {
        return text.replaceAll("(\\r\\n|\\n)", "<br>");
    }

    private static void report(LogStatus status, String description, String message) {
        ReportManager.getTest().log(status, sanitizeText(description), sanitizeText(message));

        final boolean okay = status != LogStatus.ERROR && status != LogStatus.FAIL && status != LogStatus.FATAL;
        ReportEvent.publish(description, okay);
    }

    public static void pass(String description, String message) {
        log.info(description + ": " + message);
        report(LogStatus.PASS, description, message);
    }

    public static void info(String description, String message) {
        log.info(description + ": " + message);
        report(LogStatus.INFO, description, message);
    }

    public static void skip(String description, String message) {
        log.info(description + ": " + message);
        report(LogStatus.SKIP, description, message);
    }

    public static void error(String description, String message) {
        log.error(description + ": " + message);
        report(LogStatus.FAIL, description, message);
    }

    public static void pass(String description) {
        pass(description, "");
    }

    public static void info(String description) {
        info(description, "");
    }

    public static void skip(String description) {
        skip(description, "");
    }

    public static void error(String description) {
        error(description, "");
    }

    public static void takeScreenshot(String description) {
        log.debug("Taking screenshots");
        final String screenshotText = "Screenshot: " + description;
        ScreenshotEvent.collect().forEach(img -> addImage(img, screenshotText));
    }

    public static void addVideo(Path videoPath, String message) {
        log.info("Adding video from {} to Report: {}", videoPath, message);
        final String sanitizedPath = sanitizePath(reportBaseDir.relativize(storeFile(videoPath, videoDir, ".ogg")).toString());
        final String videoTag = ReportManager.getTest().addScreencast(sanitizedPath);

        info(message, videoTag);
    }

    public static void addImage(Path screenshotPath, String message) {
        log.info("Adding screenshot from {} to Report: {}", screenshotPath, message);
        final String sanitizedPath = sanitizePath(reportBaseDir.relativize(storeFile(screenshotPath, screenshotDir, ".png")).toString());
        final String screenshotTag = ReportManager.getTest().addScreenCapture(sanitizedPath);

        info(message, screenshotTag);
    }

    /**
     * Replaces backslashes with forward slashes so href will be compatible with all browsers (href=&quot;.../screenshots\1234.png&quot; isn't)
     *
     * @param s String to sanitize
     * @return Sanitized string
     */
    private static String sanitizePath(String s) {
        return s.replace('\\', '/');
    }

    private static Path storeFile(Path file, Path targetDirectory, String extension) {
        try {
            final Path targetPath = targetDirectory.resolve(UUID.randomUUID().toString() + extension);
            log.debug("Copying file from {} to {}", file, targetPath);

            targetPath.getParent().toFile().mkdirs();
            Files.copy(file, targetPath);

            return targetPath;
        } catch (IOException e) {
            throw new AutomationFrameworkException("Couldn't copy file for report", e);
        }
    }
}