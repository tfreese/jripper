/**
 * Created: 09.03.2013
 */

package de.freese.jripper.core.cddb;

import java.util.List;
import java.util.Objects;
import de.freese.jripper.core.model.Album;

/**
 * ResponseCodes der CDDB - Server.
 *
 * @author Thomas Freese
 */
public class CddbResponse
{
    /**
     * Mehrere Genres gefunden.
     */
    public static final int EXACT_MATCHES = 210;

    /**
     * Nicht exakte DiskID.
     */
    public static final int INEXACT_MATCHES = 211;

    /**
     * Nur ein Genre gefunden.
     */
    public static final int MATCH = 200;

    /**
     * No match for disc ID
     */
    public static final int NO_MATCH = 202;

    /**
    *
    */
    private Album album = null;

    /**
     *
     */
    private String errorMessage = null;

    /**
     *
     */
    private List<String> genres = null;

    /**
     * Erstellt ein neues {@link CddbResponse} Object.
     */
    public CddbResponse()
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
     * @return String
     */
    public String getErrorMessage()
    {
        return this.errorMessage;
    }

    /**
     * @return List<String>
     */
    public List<String> getGenres()
    {
        return this.genres;
    }

    /**
     * @param album {@link Album}
     */
    public void setAlbum(final Album album)
    {
        this.album = Objects.requireNonNull(album, "album required");
    }

    /**
     * @param errorMessage String
     */
    public void setErrorMessage(final String errorMessage)
    {
        this.errorMessage = Objects.requireNonNull(errorMessage, "errorMessage required");
    }

    /**
     * @param genres List<String>
     */
    public void setGenres(final List<String> genres)
    {
        this.genres = Objects.requireNonNull(genres, "genres required");
    }
}
