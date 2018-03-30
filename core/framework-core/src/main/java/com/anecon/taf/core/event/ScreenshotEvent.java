package com.anecon.taf.core.event;

import com.anecon.taf.core.EventBusHolder;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ScreenshotEvent {
    private final List<Path> screenshots = new ArrayList<>();

    public static List<Path> collect() {
        final ScreenshotEvent event = new ScreenshotEvent();
        EventBusHolder.post(event);

        return event.getScreenshots();
    }

    public void addScreenshot(File file) {
        screenshots.add(file.toPath());
    }

    public void addScreenshot(Path path) {
        screenshots.add(path);
    }

    public List<Path> getScreenshots() {
        return new ArrayList<>(screenshots);
    }
}