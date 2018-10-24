/**
 * Created: 07.10.2013
 */

package de.freese.jripper.core;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Klasse mit der Konfiguation.
 *
 * @author Thomas Freese
 */
public final class Settings
{
    /**
     *
     */
    private static final Settings INSTANCE = new Settings();

    /**
     * @return {@link Settings}
     */
    public static Settings getInstance()
    {
        return INSTANCE;
    }

    /**
     * Laufwerk
     */
    private String device = null;

    /**
     * 0-8
     */
    private int flacCompression = 8;

    /**
     *
     */
    private boolean flacEnabled = true;

    /**
     *
     */
    private int framesPerSecond = 75;

    /**
     *
     */
    private final Logger logger = LoggerFactory.getLogger(Settings.class);;

    /**
     *
     */
    private int mp3Bitrate = 320;

    /**
     *
     */
    private List<Integer> mp3BitRates = null;
    /**
     *
     */
    private boolean mp3Enabled = true;

    /**
     * Arbeitsverzeichnis
     */
    private String workDir = null;

    /**
     * Erstellt ein neues {@link Settings} Object.
     */
    private Settings()
    {
        super();

        setWorkDir(System.getProperty("java.io.tmpdir") + "/jripper");
        // setWorkDir(System.getProperty("user.home") + "/mediathek/neues/jripper");
        // setWorkDir(System.getProperty("user.dir") + "/jripper");
        // setWorkDir(System.getProperty("user.home") + "/jripper");

        setDevice(JRipperUtils.detectCDDVD());
        setFramesPerSecond(75);
        setFlacEnabled(true);
        setFlacCompression(8);
        setMp3Bitrate(320);
        setMp3Enabled(false);

        this.mp3BitRates = List.of(320, 256, 224, 192, 160, 128, 112, 96, 80, 64, 56, 48, 40, 32);
    }

    /**
     * @return String
     */
    public String getDevice()
    {
        return this.device;
    }

    /**
     * 0-8
     *
     * @return int
     */
    public int getFlacCompression()
    {
        return this.flacCompression;
    }

    /**
     * @return int
     */
    public int getFramesPerSecond()
    {
        return this.framesPerSecond;
    }

    /**
     * @return {@link Logger}
     */
    protected Logger getLogger()
    {
        return this.logger;
    }

    /**
     * @return int
     */
    public int getMp3Bitrate()
    {
        return this.mp3Bitrate;
    }

    /**
     * Liefert eine Liste aller möglichen MP3 Bit-Raten.
     *
     * @return {@link List}
     */
    public List<Integer> getMp3BitRates()
    {
        return this.mp3BitRates;
    }

    /**
     * Liefert das Arbeitsverzeichnis.
     *
     * @return String
     */
    public String getWorkDir()
    {
        return this.workDir;
    }

    /**
     * @return boolean
     */
    public boolean isFlacEnabled()
    {
        return this.flacEnabled;
    }

    /**
     * @return boolean
     */
    public boolean isMp3Enabled()
    {
        return this.mp3Enabled;
    }

    /**
     * @param device String
     */
    public void setDevice(final String device)
    {
        this.device = device;

        getLogger().debug("device = {}", device);
    }

    /**
     * 0-8
     *
     * @param flacCompression int
     */
    public void setFlacCompression(final int flacCompression)
    {
        this.flacCompression = flacCompression;

        getLogger().debug("flacCompression = {}", flacCompression);
    }

    /**
     * @param flacEnabled boolean
     */
    public void setFlacEnabled(final boolean flacEnabled)
    {
        this.flacEnabled = flacEnabled;

        getLogger().debug("flacEnabled = {}", flacEnabled);
    }

    /**
     * @param framesPerSecond int
     */
    public void setFramesPerSecond(final int framesPerSecond)
    {
        this.framesPerSecond = framesPerSecond;

        getLogger().debug("framesPerSecond = {}", framesPerSecond);
    }

    /**
     * @param mp3Bitrate int
     */
    public void setMp3Bitrate(final int mp3Bitrate)
    {
        this.mp3Bitrate = mp3Bitrate;

        getLogger().debug("mp3Bitrate = {}", mp3Bitrate);
    }

    /**
     * @param mp3Enabled boolean
     */
    public void setMp3Enabled(final boolean mp3Enabled)
    {
        this.mp3Enabled = mp3Enabled;

        getLogger().debug("mp3Enabled = {}", mp3Enabled);
    }

    /**
     * Setzt das Arbeitsverzeichnis.
     *
     * @param workDir String
     */
    public void setWorkDir(final String workDir)
    {
        this.workDir = workDir;

        getLogger().debug("workDir = {}", workDir);
    }
}
