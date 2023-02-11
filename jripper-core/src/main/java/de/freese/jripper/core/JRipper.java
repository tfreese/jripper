// Created: 07.10.2013
package de.freese.jripper.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.freese.jripper.core.cddb.CddbProvider;
import de.freese.jripper.core.cddb.CddbProviderGnuDb;
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
public final class JRipper {
    private static final JRipper INSTANCE = new JRipper();

    private static final Logger LOGGER = LoggerFactory.getLogger("JRipper");

    public static JRipper getInstance() {
        return INSTANCE;
    }

    private CddbProvider cddbProvider;

    private DiskIDProvider diskIDProvider;

    private Encoder encoderFlac;

    private Encoder encoderMp3;

    private GenreProvider genreProvider;

    private Ripper ripper;

    private JRipper() {
        super();
    }

    public CddbProvider getCddbProvider() {
        if (this.cddbProvider == null) {
            this.cddbProvider = new CddbProviderGnuDb();
        }

        return this.cddbProvider;
    }

    public DiskIDProvider getDiskIDProvider() {
        if (this.diskIDProvider == null) {
            this.diskIDProvider = DiskIDProviderFactory.getInstance();
        }

        return this.diskIDProvider;
    }

    public Encoder getEncoderFlac() {
        if (this.encoderFlac == null) {
            this.encoderFlac = EncoderFactory.getInstance(EncoderFormat.FLAC);
        }

        return this.encoderFlac;
    }

    public Encoder getEncoderMp3() {
        if (this.encoderMp3 == null) {
            this.encoderMp3 = EncoderFactory.getInstance(EncoderFormat.MP3);
        }

        return this.encoderMp3;
    }

    public GenreProvider getGenreProvider() {
        if (this.genreProvider == null) {
            this.genreProvider = new GenreProviderLinux();
        }

        return this.genreProvider;
    }

    public Logger getLogger() {
        return JRipper.LOGGER;
    }

    public Ripper getRipper() {
        if (this.ripper == null) {
            this.ripper = RipperFactory.getInstance();
        }

        return this.ripper;
    }

    public void setCddbProvider(final CddbProvider cddbProvider) {
        this.cddbProvider = cddbProvider;
    }

    public void setDiskIDProvider(final DiskIDProvider diskIDProvider) {
        this.diskIDProvider = diskIDProvider;
    }

    public void setEncoderFlac(final Encoder encoderFLAC) {
        this.encoderFlac = encoderFLAC;
    }

    public void setEncoderMp3(final Encoder encoderMP3) {
        this.encoderMp3 = encoderMP3;
    }

    public void setGenreProvider(final GenreProvider genreProvider) {
        this.genreProvider = genreProvider;
    }

    public void setRipper(final Ripper ripper) {
        this.ripper = ripper;
    }
}
