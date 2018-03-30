package com.anecon.taf.core.reporter.audio;

import com.anecon.taf.core.reporter.ReportingException;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundPlayer {
    private SoundPlayer() {
        // no instantiation allowed
    }

    public static void play(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream must not be null!");
        }

        try {
            final Clip clip = AudioSystem.getClip();
            final AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));
            clip.open(audioInputStream);

            clip.start();

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    event.getLine().close();
                    clip.close();
                }
            });
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            throw new ReportingException("Could not play sound from stream " + inputStream, e);
        }
    }
}
