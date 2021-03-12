/**
 * Created: 07.10.2013
 */

package de.freese.jripper.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.freese.jripper.core.cddb.CddbProvider;
import de.freese.jripper.core.cddb.CddbProviderFreeDb;
import de.freese.jripper.core.diskid.DiskIDProvider;
import de.freese.jripper.core.diskid.DiskIDProviderFactory;
import de.freese.jripper.core.encoder.Encoder;
import de.freese.jripper.core.encoder.EncoderFactory;
import de.freese.jripper.core.encoder.EncoderFormat;
import de.freese.jripper.core.genre.GenreProvider;
import de.freese.jripper.core.genre.GenreProviderLinux;
import de.freese.jripper.core.ripper.Ripper;
import de.freese.jripper.core.ripper.RipperFactory;

/**
 * Zentrale Klasse.
 *
 * @author Thomas Freese
 */
public final class JRipper
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
    private CddbProvider cddbProvider;

    /**
     *
     */
    private DiskIDProvider diskIDProvider;
    /**
     *
     */
    private Encoder encoderFlac;

    /**
     *
     */
    private Encoder encoderMp3;

    /**
     *
     */
    private GenreProvider genreProvider;

    /**
     *
     */
    private Ripper ripper;

    /**
     * Erstellt ein neues {@link JRipper} Object.
     */
    private JRipper()
    {
        super();
    }

    /**
     * @return {@link CddbProvider}
     */
    public CddbProvider getCddbProvider()
    {
        if (this.cddbProvider == null)
        {
            this.cddbProvider = new CddbProviderFreeDb();
        }

        return this.cddbProvider;
    }

    /**
     * @return {@link DiskIDProvider}
     */
    public DiskIDProvider getDiskIDProvider()
    {
        if (this.diskIDProvider == null)
        {
            this.diskIDProvider = DiskIDProviderFactory.getInstance();
        }

        return this.diskIDProvider;
    }

    /**
     * @return {@link Encoder}
     */
    public Encoder getEncoderFlac()
    {
        if (this.encoderFlac == null)
        {
            this.encoderFlac = EncoderFactory.getInstance(EncoderFormat.FLAC);
        }

        return this.encoderFlac;
    }

    /**
     * @return {@link Encoder}
     */
    public Encoder getEncoderMp3()
    {
        if (this.encoderMp3 == null)
        {
            this.encoderMp3 = EncoderFactory.getInstance(EncoderFormat.MP3);
        }

        return this.encoderMp3;
    }

    /**
     * @return {@link GenreProvider}
     */
    public GenreProvider getGenreProvider()
    {
        if (this.genreProvider == null)
        {
            this.genreProvider = new GenreProviderLinux();
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
     * @return {@link Ripper}
     */
    public Ripper getRipper()
    {
        if (this.ripper == null)
        {
            this.ripper = RipperFactory.getInstance();
        }

        return this.ripper;
    }

    /**
     * @param cddbProvider {@link CddbProvider}
     */
    public void setCddbProvider(final CddbProvider cddbProvider)
    {
        this.cddbProvider = cddbProvider;
    }

    /**
     * @param diskIDProvider {@link DiskIDProvider}
     */
    public void setDiskIDProvider(final DiskIDProvider diskIDProvider)
    {
        this.diskIDProvider = diskIDProvider;
    }

    /**
     * @param encoderFLAC {@link Encoder}
     */
    public void setEncoderFlac(final Encoder encoderFLAC)
    {
        this.encoderFlac = encoderFLAC;
    }

    /**
     * @param encoderMP3 {@link Encoder}
     */
    public void setEncoderMp3(final Encoder encoderMP3)
    {
        this.encoderMp3 = encoderMP3;
    }

    /**
     * @param genreProvider {@link GenreProvider}
     */
    public void setGenreProvider(final GenreProvider genreProvider)
    {
        this.genreProvider = genreProvider;
    }

    /**
     * @param ripper {@link Ripper}
     */
    public void setRipper(final Ripper ripper)
    {
        this.ripper = ripper;
    }
}
