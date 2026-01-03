// Created: 26.02.2013
package de.freese.jripper.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CD-Descriptor.
 *
 * @author Thomas Freese
 */
public class AlbumImpl implements Album {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumImpl.class);

    private final DiskId diskID;
    private final List<Track> tracks = new ArrayList<>();
    /**
     * Null for Compilations.
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

        diskID = null;
    }

    public AlbumImpl(final DiskId diskID) {
        super();

        this.diskID = Objects.requireNonNull(diskID, "diskID required");
    }

    /**
     * @param artist String; Null for Compilations.
     */
    public void addTrack(final String artist, final String title) {
        final int trackIndex = tracks.size();

        final Track track = new Track();
        track.setAlbum(this);
        track.setArtist(artist == null ? getArtist() : artist);
        track.setTitle(title);
        track.setNumber(trackIndex + 1);
        track.setSeconds(getDiskID().getTrackSeconds(trackIndex));

        tracks.add(track);
    }

    @Override
    public String getArtist() {
        return artist;
    }

    @Override
    public String getComment() {
        return Objects.toString(comment, "");
    }

    @Override
    public DiskId getDiskID() {
        return diskID;
    }

    @Override
    public int getDiskNumber() {
        return diskNumber;
    }

    @Override
    public String getGenre() {
        return genre;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getTotalDisks() {
        return totalDisks;
    }

    @Override
    public Track getTrack(final int index) {
        return tracks.get(index);
    }

    @Override
    public int getTrackCount() {
        return tracks.size();
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public boolean isCompilation() {
        return artist == null || artist.isBlank();
    }

    @Override
    public Iterator<Track> iterator() {
        return tracks.iterator();
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
     * @param index int, 0 based
     */
    public void setTrackArtist(final int index, final String artist) {
        final Track track = tracks.get(index);
        track.setArtist(artist);

        LOGGER.debug("index/artist = {}/{}", index, artist);
    }

    /**
     * @param index int, 0 based
     */
    public void setTrackTitle(final int index, final String title) {
        final Track track = tracks.get(index);
        track.setTitle(title);

        LOGGER.debug("index/title = {}/{}", index, title);
    }

    @Override
    public void setYear(final int year) {
        this.year = year;

        LOGGER.debug("year = {}", year);
    }
}
