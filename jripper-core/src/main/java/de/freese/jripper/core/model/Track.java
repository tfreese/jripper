// Created: 01.03.2013
package de.freese.jripper.core.model;

import java.util.Objects;

/**
 * Informationen über einen Titel einer CD.
 *
 * @author Thomas Freese
 */
public class Track {
    private Album album;
    /**
     * Nur bei Compilations gefüllt.
     */
    private String artist;
    private int number;
    private int seconds;
    private String title;

    Track() {
        super();
    }

    public Album getAlbum() {
        return this.album;
    }

    /**
     * Nur bei Compilations gefüllt.
     */
    public String getArtist() {
        if (this.artist == null || this.artist.isBlank()) {
            return getAlbum().getArtist();
        }

        return this.artist;
    }

    public int getNumber() {
        return this.number;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public String getTitle() {
        return this.title;
    }

    /**
     * Nur bei Compilations gefüllt.
     */
    public void setArtist(final String artist) {
        this.artist = Objects.requireNonNull(artist, "artist required");
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    void setAlbum(final Album album) {
        this.album = Objects.requireNonNull(album, "album required");
    }

    void setNumber(final int number) {
        this.number = number;
    }

    void setSeconds(final int seconds) {
        this.seconds = seconds;
    }
}
