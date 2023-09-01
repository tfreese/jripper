// Created: 26.02.2013
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
public class AlbumImpl implements Album {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumImpl.class);

    private final DiskId diskID;

    private final List<Track> tracks = new ArrayList<>();
    /**
     * Bei Compilations null.
     */
    private String artist;

    private String comment;

    private int diskNumber = 1;

    private String genre;

    private String title;

    private int totalDisks = 1;

    private int year;

    public AlbumImpl() {
        super();

        this.diskID = null;
    }

    public AlbumImpl(final DiskId diskID) {
        super();

        this.diskID = Objects.requireNonNull(diskID, "diskID required");
    }

    /**
     * @param artist String; Nur bei Compilations != null.
     */
    public void addTrack(final String artist, final String title) {
        int trackIndex = this.tracks.size();

        Track track = new Track();
        track.setAlbum(this);
        track.setArtist(artist == null ? getArtist() : artist);
        track.setTitle(title);
        track.setNumber(trackIndex + 1);
        track.setSeconds(getDiskID().getTrackSeconds(trackIndex));

        this.tracks.add(track);
    }

    @Override
    public String getArtist() {
        return this.artist;
    }

    @Override
    public String getComment() {
        return Objects.toString(this.comment, "");
    }

    @Override
    public DiskId getDiskID() {
        return this.diskID;
    }

    @Override
    public int getDiskNumber() {
        return this.diskNumber;
    }

    @Override
    public String getGenre() {
        return this.genre;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public int getTotalDisks() {
        return this.totalDisks;
    }

    @Override
    public Track getTrack(final int index) {
        return this.tracks.get(index);
    }

    @Override
    public int getTrackCount() {
        return this.tracks.size();
    }

    @Override
    public int getYear() {
        return this.year;
    }

    @Override
    public boolean isCompilation() {
        return (this.artist == null) || this.artist.isBlank();
    }

    @Override
    public Iterator<Track> iterator() {
        return this.tracks.iterator();
    }

    @Override
    public void setArtist(final String artist) {
        this.artist = artist;

        LOGGER.debug("artist = {}", artist);
    }

    @Override
    public void setComment(final String comment) {
        this.comment = comment;

        LOGGER.debug("comment = {}", comment);
    }

    @Override
    public void setDiskNumber(final int diskNumber) {
        this.diskNumber = diskNumber;

        LOGGER.debug("diskNumber = {}", diskNumber);
    }

    @Override
    public void setGenre(final String genre) {
        this.genre = genre;

        LOGGER.debug("genre = {}", genre);
    }

    @Override
    public void setTitle(final String title) {
        this.title = title;

        LOGGER.debug("title = {}", title);
    }

    @Override
    public void setTotalDisks(final int totalDisks) {
        this.totalDisks = totalDisks;

        LOGGER.debug("totalDisks = {}", totalDisks);
    }

    /**
     * @param index int, beginnend mit 0
     */
    public void setTrackArtist(final int index, final String artist) {
        Track track = this.tracks.get(index);
        track.setArtist(artist);

        LOGGER.debug("index/artist = {}/{}", index, artist);
    }

    /**
     * @param index int, beginnend mit 0
     */
    public void setTrackTitle(final int index, final String title) {
        Track track = this.tracks.get(index);
        track.setTitle(title);

        LOGGER.debug("index/title = {}/{}", index, title);
    }

    @Override
    public void setYear(final int year) {
        this.year = year;

        LOGGER.debug("year = {}", year);
    }
}
