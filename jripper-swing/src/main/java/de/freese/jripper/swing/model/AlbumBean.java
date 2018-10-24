/**
 * Created: 17.10.2013
 */

package de.freese.jripper.swing.model;

import java.util.Iterator;
import org.slf4j.Logger;
import com.jgoodies.binding.beans.Model;
import com.jgoodies.common.bean.Bean;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.model.DiskID;
import de.freese.jripper.core.model.Track;
import de.freese.jripper.swing.JRipperSwing;

/**
 * {@link Bean} für das {@link Album}.
 *
 * @author Thomas Freese
 */
public class AlbumBean extends Model implements Album// Bean
{
    /**
     *
     */
    public static final String PROPERTY_ARTIST = "artist";

    /**
     *
     */
    public static final String PROPERTY_COMMENT = "comment";

    /**
     *
     */
    public static final String PROPERTY_DISKNUMBER = "diskNumber";

    /**
     *
     */
    public static final String PROPERTY_GENRE = "genre";

    /**
     *
     */
    public static final String PROPERTY_TITLE = "title";

    /**
     *
     */
    public static final String PROPERTY_TOTALDISKS = "totalDisks";

    /**
     *
     */
    public static final String PROPERTY_YEAR = "year";
    /**
     *
     */
    private static final long serialVersionUID = 9196811641238029075L;

    /**
     *
     */
    private final Album album;

    /**
     *
     */
    private final Logger logger = JRipperSwing.LOGGER;

    /**
     * Erstellt ein neues {@link AlbumBean} Object.
     *
     * @param album {@link Album}
     */
    public AlbumBean(final Album album)
    {
        super();

        this.album = album;
    }

    /**
     * @see de.freese.jripper.core.model.Album#getArtist()
     */
    @Override
    public String getArtist()
    {
        return this.album.getArtist();
    }

    /**
     * @see de.freese.jripper.core.model.Album#getComment()
     */
    @Override
    public String getComment()
    {
        return this.album.getComment();
    }

    /**
     * @see de.freese.jripper.core.model.Album#getDiskID()
     */
    @Override
    public DiskID getDiskID()
    {
        return this.album.getDiskID();
    }

    /**
     * @see de.freese.jripper.core.model.Album#getDiskNumber()
     */
    @Override
    public int getDiskNumber()
    {
        return this.album.getDiskNumber();
    }

    /**
     * @see de.freese.jripper.core.model.Album#getGenre()
     */
    @Override
    public String getGenre()
    {
        return this.album.getGenre();
    }

    /**
     * @see de.freese.jripper.core.model.Album#getTitle()
     */
    @Override
    public String getTitle()
    {
        return this.album.getTitle();
    }

    /**
     * @see de.freese.jripper.core.model.Album#getTotalDisks()
     */
    @Override
    public int getTotalDisks()
    {
        return this.album.getTotalDisks();
    }

    /**
     * @see de.freese.jripper.core.model.Album#getTrack(int)
     */
    @Override
    public Track getTrack(final int index)
    {
        return this.album.getTrack(index);
    }

    /**
     * @see de.freese.jripper.core.model.Album#getTrackCount()
     */
    @Override
    public int getTrackCount()
    {
        return this.album.getTrackCount();
    }

    /**
     * @see de.freese.jripper.core.model.Album#getYear()
     */
    @Override
    public int getYear()
    {
        return this.album.getYear();
    }

    /**
     * @see de.freese.jripper.core.model.Album#isCompilation()
     */
    @Override
    public boolean isCompilation()
    {
        return this.album.isCompilation();
    }

    /**
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<Track> iterator()
    {
        return this.album.iterator();
    }

    /**
     * @see de.freese.jripper.core.model.Album#setArtist(java.lang.String)
     */
    @Override
    public void setArtist(final String artist)
    {
        Object oldValue = getArtist();
        this.album.setArtist(artist.trim());

        if (this.logger.isDebugEnabled())
        {
            this.logger.debug("oldValue={}, newValue={}", oldValue, artist);
        }

        // Alle Tracks aktualisieren.
        // for (Track track : this.album)
        // {
        // track.setArtist(artist);
        // }

        firePropertyChange(PROPERTY_ARTIST, oldValue, artist);
    }

    /**
     * @see de.freese.jripper.core.model.Album#setComment(java.lang.String)
     */
    @Override
    public void setComment(final String comment)
    {
        Object oldValue = getComment();
        this.album.setComment(comment.trim());

        if (this.logger.isDebugEnabled())
        {
            this.logger.debug("oldValue={}, newValue={}", oldValue, comment);
        }

        firePropertyChange(PROPERTY_COMMENT, oldValue, comment);
    }

    /**
     * @see de.freese.jripper.core.model.Album#setDiskNumber(int)
     */
    @Override
    public void setDiskNumber(final int diskNumber)
    {
        Object oldValue = getDiskNumber();
        this.album.setDiskNumber(diskNumber);

        if (this.logger.isDebugEnabled())
        {
            this.logger.debug("oldValue={}, newValue={}", oldValue, diskNumber);
        }

        firePropertyChange(PROPERTY_DISKNUMBER, oldValue, diskNumber);
    }

    /**
     * @see de.freese.jripper.core.model.Album#setGenre(java.lang.String)
     */
    @Override
    public void setGenre(final String genre)
    {
        Object oldValue = getGenre();
        this.album.setGenre(genre.trim());

        if (this.logger.isDebugEnabled())
        {
            this.logger.debug("oldValue={}, newValue={}", oldValue, genre);
        }

        firePropertyChange(PROPERTY_GENRE, oldValue, genre);
    }

    /**
     * @see de.freese.jripper.core.model.Album#setTitle(java.lang.String)
     */
    @Override
    public void setTitle(final String title)
    {
        Object oldValue = getTitle();
        this.album.setTitle(title.trim());

        if (this.logger.isDebugEnabled())
        {
            this.logger.debug("oldValue={}, newValue={}", oldValue, title);
        }

        firePropertyChange(PROPERTY_TITLE, oldValue, title);
    }

    /**
     * @see de.freese.jripper.core.model.Album#setTotalDisks(int)
     */
    @Override
    public void setTotalDisks(final int totalDisks)
    {
        Object oldValue = getTotalDisks();
        this.album.setTotalDisks(totalDisks);

        if (this.logger.isDebugEnabled())
        {
            this.logger.debug("oldValue={}, newValue={}", oldValue, totalDisks);
        }

        firePropertyChange(PROPERTY_TOTALDISKS, oldValue, totalDisks);
    }

    /**
     * @see de.freese.jripper.core.model.Album#setYear(int)
     */
    @Override
    public void setYear(final int year)
    {
        Object oldValue = getYear();
        this.album.setYear(year);

        if (this.logger.isDebugEnabled())
        {
            this.logger.debug("oldValue={}, newValue={}", oldValue, year);
        }

        firePropertyChange(PROPERTY_YEAR, oldValue, year);
    }
}
