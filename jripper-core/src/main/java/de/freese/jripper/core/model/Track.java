/**
 * Created: 01.03.2013
 */
package de.freese.jripper.core.model;

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * Informationen über einen Titel einer CD.
 *
 * @author Thomas Freese
 */
public class Track
{
    /**
     *
     */
    private Album album;

    /**
     * Nur bei Compilations gefüllt.
     */
    private String artist;

    /**
     *
     */
    private int number;

    /**
     *
     */
    private int seconds;

    /**
     *
     */
    private String title;

    /**
     * Erstellt ein neues {@link Track} Object.
     */
    Track()
    {
        super();
    }

    /**
     * @return {@link Album}
     */
    public Album getAlbum()
    {
        return this.album;
    }

    /**
     * Nur bei Compilations gefüllt.
     *
     * @return String
     */
    public String getArtist()
    {
        if (StringUtils.isBlank(this.artist))
        {
            return getAlbum().getArtist();
        }

        return this.artist;
    }

    /**
     * @return int
     */
    public int getNumber()
    {
        return this.number;
    }

    /**
     * @return int
     */
    public int getSeconds()
    {
        return this.seconds;
    }

    /**
     * @return String
     */
    public String getTitle()
    {
        return this.title;
    }

    /**
     * @param album {@link Album}
     */
    void setAlbum(final Album album)
    {
        this.album = Objects.requireNonNull(album, "album required");
    }

    /**
     * Nur bei Compilations gefüllt.
     *
     * @param artist String
     */
    public void setArtist(final String artist)
    {
        this.artist = Objects.requireNonNull(artist, "artist required");
    }

    /**
     * @param number int
     */
    void setNumber(final int number)
    {
        this.number = number;
    }

    /**
     * @param seconds int
     */
    void setSeconds(final int seconds)
    {
        this.seconds = seconds;
    }

    /**
     * @param title String
     */
    public void setTitle(final String title)
    {
        this.title = title;
    }
}
