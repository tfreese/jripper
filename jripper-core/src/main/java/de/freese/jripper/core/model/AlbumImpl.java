/**
 * Created: 26.02.2013
 */
package de.freese.jripper.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Zusammenfassendes Objekt für eine CD.
 *
 * @author Thomas Freese
 */
public class AlbumImpl implements Album
{
    /**
    *
    */
    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumImpl.class);

    /**
     * Bei Compilations null.
     */
    private String artist;

    /**
     *
     */
    private String comment;

    /**
     *
     */
    private final DiskID diskID;

    /**
     *
     */
    private int diskNumber = 1;

    /**
     *
     */
    private String genre;

    /**
     *
     */
    private String title;

    /**
     *
     */
    private int totalDisks = 1;

    /**
     *
     */
    private List<Track> tracks = new ArrayList<>();

    /**
     *
     */
    private int year;

    /**
     * Erstellt ein neues {@link AlbumImpl} Object.
     */
    public AlbumImpl()
    {
        super();

        this.diskID = null;
    }

    /**
     * Erstellt ein neues {@link AlbumImpl} Object.
     *
     * @param diskID {@link DiskID}
     */
    public AlbumImpl(final DiskID diskID)
    {
        super();

        this.diskID = Objects.requireNonNull(diskID, "diskID required");
    }

    /**
     * @param artist String; Nur bei Compilations != null.
     * @param title String
     */
    public void addTrack(final String artist, final String title)
    {
        int trackIndex = this.tracks.size();

        Track track = new Track();
        track.setAlbum(this);
        track.setArtist(artist);
        track.setTitle(title);
        track.setNumber(trackIndex + 1);
        track.setSeconds(getDiskID().getTrackSeconds(trackIndex));

        this.tracks.add(track);
    }

    /**
     * @see de.freese.jripper.core.model.Album#getArtist()
     */
    @Override
    public String getArtist()
    {
        return this.artist;
    }

    /**
     * @see de.freese.jripper.core.model.Album#getComment()
     */
    @Override
    public String getComment()
    {
        return Objects.toString(this.comment, "");
    }

    /**
     * @see de.freese.jripper.core.model.Album#getDiskID()
     */
    @Override
    public DiskID getDiskID()
    {
        return this.diskID;
    }

    /**
     * @see de.freese.jripper.core.model.Album#getDiskNumber()
     */
    @Override
    public int getDiskNumber()
    {
        return this.diskNumber;
    }

    /**
     * @see de.freese.jripper.core.model.Album#getGenre()
     */
    @Override
    public String getGenre()
    {
        return this.genre;
    }

    /**
     * @see de.freese.jripper.core.model.Album#getTitle()
     */
    @Override
    public String getTitle()
    {
        return this.title;
    }

    /**
     * @see de.freese.jripper.core.model.Album#getTotalDisks()
     */
    @Override
    public int getTotalDisks()
    {
        return this.totalDisks;
    }

    /**
     * @see de.freese.jripper.core.model.Album#getTrack(int)
     */
    @Override
    public Track getTrack(final int index)
    {
        return this.tracks.get(index);
    }

    /**
     * @see de.freese.jripper.core.model.Album#getTrackCount()
     */
    @Override
    public int getTrackCount()
    {
        return this.tracks.size();
    }

    /**
     * @see de.freese.jripper.core.model.Album#getYear()
     */
    @Override
    public int getYear()
    {
        return this.year;
    }

    /**
     * @see de.freese.jripper.core.model.Album#isCompilation()
     */
    @Override
    public boolean isCompilation()
    {
        return (this.artist == null) || this.artist.isBlank();
    }

    /**
     * @see de.freese.jripper.core.model.Album#iterator()
     */
    @Override
    public Iterator<Track> iterator()
    {
        return this.tracks.iterator();
    }

    /**
     * @see de.freese.jripper.core.model.Album#setArtist(java.lang.String)
     */
    @Override
    public void setArtist(final String artist)
    {
        this.artist = artist;

        LOGGER.debug("artist = {}", artist);
    }

    /**
     * @see de.freese.jripper.core.model.Album#setComment(java.lang.String)
     */
    @Override
    public void setComment(final String comment)
    {
        this.comment = comment;

        LOGGER.debug("comment = {}", comment);
    }

    /**
     * @see de.freese.jripper.core.model.Album#setDiskNumber(int)
     */
    @Override
    public void setDiskNumber(final int diskNumber)
    {
        this.diskNumber = diskNumber;

        LOGGER.debug("diskNumber = {}", diskNumber);
    }

    /**
     * @see de.freese.jripper.core.model.Album#setGenre(java.lang.String)
     */
    @Override
    public void setGenre(final String genre)
    {
        this.genre = genre;

        LOGGER.debug("genre = {}", genre);
    }

    /**
     * @see de.freese.jripper.core.model.Album#setTitle(java.lang.String)
     */
    @Override
    public void setTitle(final String title)
    {
        this.title = title;

        LOGGER.debug("title = {}", title);
    }

    /**
     * @see de.freese.jripper.core.model.Album#setTotalDisks(int)
     */
    @Override
    public void setTotalDisks(final int totalDisks)
    {
        this.totalDisks = totalDisks;

        LOGGER.debug("totalDisks = {}", totalDisks);
    }

    /**
     * @param index int, beginnend mit 0
     * @param artist String
     */
    public void setTrackArtist(final int index, final String artist)
    {
        Track track = this.tracks.get(index);
        track.setArtist(artist);

        LOGGER.debug("index/artist = {}/{}", index, artist);
    }

    /**
     * @param index int, beginnend mit 0
     * @param title String
     */
    public void setTrackTitle(final int index, final String title)
    {
        Track track = this.tracks.get(index);
        track.setTitle(title);

        LOGGER.debug("index/title = {}/{}", index, title);
    }

    /**
     * @see de.freese.jripper.core.model.Album#setYear(int)
     */
    @Override
    public void setYear(final int year)
    {
        this.year = year;

        LOGGER.debug("year = {}", year);
    }
}
