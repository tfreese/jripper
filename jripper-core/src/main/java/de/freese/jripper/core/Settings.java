// Created: 07.10.2013
package de.freese.jripper.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thomas Freese
 */
public final class Settings {
    private static final Settings INSTANCE = new Settings();

    public static Settings getInstance() {
        return INSTANCE;
    }

    private final Logger logger = LoggerFactory.getLogger(Settings.class);
    private final List<Integer> mp3BitRates;

    private String device;
    /**
     * 0-8
     */
    private int flacCompression = 8;
    private boolean flacEnabled = true;
    private int framesPerSecond = 75;
    private int mp3Bitrate = 320;
    private boolean mp3Enabled = true;
    private String workDir;

    private Settings() {
        super();

        setWorkDir(System.getProperty("java.io.tmpdir") + "/jRipper");

        setDevice(JRipperUtils.detectCdDevice());
        setFramesPerSecond(75);
        setFlacEnabled(true);
        setFlacCompression(8);
        setMp3Bitrate(320);
        setMp3Enabled(false);

        mp3BitRates = List.of(320, 256, 224, 192, 160, 128, 112, 96, 80, 64, 56, 48, 40, 32);
    }

    public String getDevice() {
        return device;
    }

    /**
     * 0-8
     */
    public int getFlacCompression() {
        return flacCompression;
    }

    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    public List<Integer> getMp3BitRates() {
        return mp3BitRates;
    }

    public int getMp3Bitrate() {
        return mp3Bitrate;
    }

    public String getWorkDir() {
        return workDir;
    }

    public boolean isFlacEnabled() {
        return flacEnabled;
    }

    public boolean isMp3Enabled() {
        return mp3Enabled;
    }

    public void setDevice(final String device) {
        this.device = device;

        getLogger().debug("device = {}", device);
    }

    /**
     * 0-8
     */
    public void setFlacCompression(final int flacCompression) {
        this.flacCompression = flacCompression;

        getLogger().debug("flacCompression = {}", flacCompression);
    }

    public void setFlacEnabled(final boolean flacEnabled) {
        this.flacEnabled = flacEnabled;

        getLogger().debug("flacEnabled = {}", flacEnabled);
    }

    public void setFramesPerSecond(final int framesPerSecond) {
        this.framesPerSecond = framesPerSecond;

        getLogger().debug("framesPerSecond = {}", framesPerSecond);
    }

    public void setMp3Bitrate(final int mp3Bitrate) {
        this.mp3Bitrate = mp3Bitrate;

        getLogger().debug("mp3Bitrate = {}", mp3Bitrate);
    }

    public void setMp3Enabled(final boolean mp3Enabled) {
        this.mp3Enabled = mp3Enabled;

        getLogger().debug("mp3Enabled = {}", mp3Enabled);
    }

    public void setWorkDir(final String workDir) {
        this.workDir = workDir;

        getLogger().debug("workDir = {}", workDir);
    }

    private Logger getLogger() {
        return logger;
    }
}
