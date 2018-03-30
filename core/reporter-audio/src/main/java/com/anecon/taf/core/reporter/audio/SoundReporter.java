package com.anecon.taf.core.reporter.audio;

import com.anecon.taf.core.reporter.Reporter;
import com.anecon.taf.core.reporter.TestExecutionDetails;
import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Plays audio files according to the test case status.
 */
public class SoundReporter implements Reporter {
    private static final Logger log = LoggerFactory.getLogger(SoundReporter.class);

    private static final SoundReporterProperties CONFIG =
            ConfigFactory.create(SoundReporterProperties.class, System.getProperties(), System.getenv());

    @Override
    public void report(TestExecutionDetails testExecutionDetails) {
        if (CONFIG.soundReportingEnabled()) {
            log.debug("Playing sound for audio reporting");

            switch (testExecutionDetails.getStatus()) {
                case FAILED:
                    SoundPlayer.play(getClass().getClassLoader().getResourceAsStream("sound/failed.wav"));
                    break;
                case PASSED:
                    SoundPlayer.play(getClass().getClassLoader().getResourceAsStream("sound/passed.wav"));
                    break;
                default:
                    SoundPlayer.play(getClass().getClassLoader().getResourceAsStream("sound/skipped.wav"));
                    break;
            }
        } else {
            log.debug("Sound reporting disabled - doing nothing");
        }
    }

    @Override
    public String getReporterName() {
        return getClass().getName();
    }
}
