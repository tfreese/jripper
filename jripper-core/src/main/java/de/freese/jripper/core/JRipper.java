/**
 * Created: 07.10.2013
 */

package de.freese.jripper.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.freese.jripper.core.cddb.FreeDBProvider;
import de.freese.jripper.core.cddb.ICDDBProvider;
import de.freese.jripper.core.diskid.DiskIDProvider;
import de.freese.jripper.core.diskid.IDiskIDProvider;
import de.freese.jripper.core.encoder.Encoder;
import de.freese.jripper.core.encoder.EncoderFormat;
import de.freese.jripper.core.encoder.IEncoder;
import de.freese.jripper.core.genre.IGenreProvider;
import de.freese.jripper.core.genre.LinuxGenreProvider;
import de.freese.jripper.core.ripper.IRipper;
import de.freese.jripper.core.ripper.Ripper;

/**
 * Zentrale Klasse.
 *
 * @author Thomas Freese
 */
public class JRipper
{
    /**
     *
     */
    private static final JRipper INSTANCE = new JRipper();

    /**
     *
     */
    public static final Logger LOGGER = LoggerFactory.getLogger("JRipper");

    /**
     * @return {@link JRipper}
     */
    public static JRipper getInstance()
    {
        return INSTANCE;
    }

    /**
     *
     */
    private ICDDBProvider cddbProvider = null;

    /**
     *
     */
    private IDiskIDProvider diskIDProvider = null;
    /**
     *
     */
    private IEncoder encoderFLAC = null;

    /**
     *
     */
    private IEncoder encoderMP3 = null;

    /**
     *
     */
    private IGenreProvider genreProvider = null;

    /**
     *
     */
    private IRipper ripper = null;

    /**
     * Erstellt ein neues {@link JRipper} Object.
     */
    private JRipper()
    {
        super();
    }

    /**
     * @return {@link ICDDBProvider}
     */
    public ICDDBProvider getCDDBProvider()
    {
        if (this.cddbProvider == null)
        {
            this.cddbProvider = new FreeDBProvider();
        }

        return this.cddbProvider;
    }

    /**
     * @return {@link IDiskIDProvider}
     */
    public IDiskIDProvider getDiskIDProvider()
    {
        if (this.diskIDProvider == null)
        {
            this.diskIDProvider = DiskIDProvider.getInstance();
        }

        return this.diskIDProvider;
    }

    /**
     * @return {@link IEncoder}
     */
    public IEncoder getEncoderFLAC()
    {
        if (this.encoderFLAC == null)
        {
            this.encoderFLAC = Encoder.getInstance(EncoderFormat.flac);
        }

        return this.encoderFLAC;
    }

    /**
     * @return {@link IEncoder}
     */
    public IEncoder getEncoderMP3()
    {
        if (this.encoderMP3 == null)
        {
            this.encoderMP3 = Encoder.getInstance(EncoderFormat.mp3);
        }

        return this.encoderMP3;
    }

    /**
     * @return {@link IGenreProvider}
     */
    public IGenreProvider getGenreProvider()
    {
        if (this.genreProvider == null)
        {
            this.genreProvider = new LinuxGenreProvider();
        }

        return this.genreProvider;
    }

    /**
     * @return {@link Logger}
     */
    public Logger getLogger()
    {
        return JRipper.LOGGER;
    }

    /**
     * @return {@link IRipper}
     */
    public IRipper getRipper()
    {
        if (this.ripper == null)
        {
            this.ripper = Ripper.getInstance();
        }

        return this.ripper;
    }

    /**
     * @param cddbProvider {@link ICDDBProvider}
     */
    public void setCDDBProvider(final ICDDBProvider cddbProvider)
    {
        this.cddbProvider = cddbProvider;
    }

    /**
     * @param diskIDProvider {@link IDiskIDProvider}
     */
    public void setDiskIDProvider(final IDiskIDProvider diskIDProvider)
    {
        this.diskIDProvider = diskIDProvider;
    }

    /**
     * @param encoderFLAC {@link IEncoder}
     */
    public void setEncoderFLAC(final IEncoder encoderFLAC)
    {
        this.encoderFLAC = encoderFLAC;
    }

    /**
     * @param encoderMP3 {@link IEncoder}
     */
    public void setEncoderMP3(final IEncoder encoderMP3)
    {
        this.encoderMP3 = encoderMP3;
    }

    /**
     * @param genreProvider {@link IGenreProvider}
     */
    public void setGenreProvider(final IGenreProvider genreProvider)
    {
        this.genreProvider = genreProvider;
    }

    /**
     * @param ripper {@link IRipper}
     */
    public void setRipper(final IRipper ripper)
    {
        this.ripper = ripper;
    }
}
