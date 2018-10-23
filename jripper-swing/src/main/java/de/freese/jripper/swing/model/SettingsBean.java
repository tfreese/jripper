/**
 * Created: 16.10.2013
 */

package de.freese.jripper.swing.model;

import org.slf4j.Logger;
import com.jgoodies.binding.beans.Model;
import com.jgoodies.common.bean.Bean;
import de.freese.jripper.core.Settings;
import de.freese.jripper.swing.JRipperSwing;

/**
 * {@link Bean} für die {@link Settings}.
 *
 * @author Thomas Freese
 */
public class SettingsBean extends Model// Bean
{
    /**
     * 
     */
    public static final String PROPERTY_DEVICE = "device";

    /**
     * 
     */
    public static final String PROPERTY_FLAC_COMPRESSION = "flacCompression";

    /**
     * 
     */
    public static final String PROPERTY_FLAC_ENBLED = "flacEnabled";

    /**
     * 
     */
    public static final String PROPERTY_FPS = "framesPerSecond";

    // /**
    // *
    // */
    // public static final String PROPERTY_MP3_BITRATE = "mp3Bitrate";

    /**
     * 
     */
    public static final String PROPERTY_MP3_ENBLED = "mp3Enabled";

    /**
     * 
     */
    public static final String PROPERTY_WORKDIR = "workDir";

    /**
     *
     */
    private static final long serialVersionUID = -6983595958713155054L;

    /**
     * 
     */
    private final Logger logger = JRipperSwing.LOGGER;

    /**
     * 
     */
    private final Settings settings;

    /**
     * Erstellt ein neues {@link SettingsBean} Object.
     * 
     * @param settings {@link Settings}
     */
    SettingsBean(final Settings settings)
    {
        super();

        this.settings = settings;
    }

    /**
     * @return String
     */
    public String getDevice()
    {
        return this.settings.getDevice();
    }

    /**
     * 0-8
     * 
     * @return int
     */
    public int getFlacCompression()
    {
        return this.settings.getFlacCompression();
    }

    /**
     * @return int
     */
    public int getFramesPerSecond()
    {
        return this.settings.getFramesPerSecond();
    }

    // /**
    // * @return int
    // */
    // public int getMp3Bitrate()
    // {
    // return this.settings.getMp3Bitrate();
    // }

    /**
     * Liefert das Arbeitsverzeichnis.
     * 
     * @return String
     */
    public String getWorkDir()
    {
        return this.settings.getWorkDir();
    }

    /**
     * @return boolean
     */
    public boolean isFlacEnabled()
    {
        return this.settings.isFlacEnabled();
    }

    /**
     * @return boolean
     */
    public boolean isMp3Enabled()
    {
        return this.settings.isMp3Enabled();
    }

    /**
     * @param device String
     */
    public void setDevice(final String device)
    {
        Object oldValue = getDevice();
        this.settings.setDevice(device);

        if (this.logger.isDebugEnabled())
        {
            this.logger.debug("oldValue={}, newValue={}", oldValue, device);
        }

        firePropertyChange(PROPERTY_DEVICE, oldValue, device);
    }

    /**
     * 0-8
     * 
     * @param flacCompression int
     */
    public void setFlacCompression(final int flacCompression)
    {
        Object oldValue = getFlacCompression();
        this.settings.setFlacCompression(flacCompression);

        if (this.logger.isDebugEnabled())
        {
            this.logger.debug("oldValue={}, newValue={}", oldValue, flacCompression);
        }

        firePropertyChange(PROPERTY_FLAC_COMPRESSION, oldValue, flacCompression);
    }

    /**
     * @param flacEnabled boolean
     */
    public void setFlacEnabled(final boolean flacEnabled)
    {
        Object oldValue = isFlacEnabled();
        this.settings.setFlacEnabled(flacEnabled);

        if (this.logger.isDebugEnabled())
        {
            this.logger.debug("oldValue={}, newValue={}", oldValue, flacEnabled);
        }

        firePropertyChange(PROPERTY_FLAC_ENBLED, oldValue, flacEnabled);
    }

    /**
     * @param framesPerSecond int
     */
    public void setFramesPerSecond(final int framesPerSecond)
    {
        Object oldValue = getFramesPerSecond();
        this.settings.setFramesPerSecond(framesPerSecond);

        if (this.logger.isDebugEnabled())
        {
            this.logger.debug("oldValue={}, newValue={}", oldValue, framesPerSecond);
        }

        firePropertyChange(PROPERTY_FPS, oldValue, framesPerSecond);
    }

    // /**
    // * @param mp3Bitrate int
    // */
    // public void setMp3Bitrate(final int mp3Bitrate)
    // {
    // Object oldValue = getMp3Bitrate();
    // this.settings.setMp3Bitrate(mp3Bitrate);
    //
    // if (this.logger.isDebugEnabled())
    // {
    // this.logger.debug("oldValue={}, newValue={}", oldValue, mp3Bitrate);
    // }
    //
    // firePropertyChange(PROPERTY_MP3_BITRATE, oldValue, mp3Bitrate);
    // }

    /**
     * @param mp3Enabled boolean
     */
    public void setMp3Enabled(final boolean mp3Enabled)
    {
        Object oldValue = isMp3Enabled();
        this.settings.setMp3Enabled(mp3Enabled);

        if (this.logger.isDebugEnabled())
        {
            this.logger.debug("oldValue={}, newValue={}", oldValue, mp3Enabled);
        }

        firePropertyChange(PROPERTY_MP3_ENBLED, oldValue, mp3Enabled);
    }

    /**
     * Setzt das Arbeitsverzeichnis.
     * 
     * @param workDir String
     */
    public void setWorkDir(final String workDir)
    {
        Object oldValue = getWorkDir();
        this.settings.setWorkDir(workDir);

        if (this.logger.isDebugEnabled())
        {
            this.logger.debug("oldValue={}, newValue={}", oldValue, workDir);
        }

        firePropertyChange(PROPERTY_WORKDIR, oldValue, workDir);
    }
}
