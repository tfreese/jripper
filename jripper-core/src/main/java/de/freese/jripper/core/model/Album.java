// Created: 20.10.2013
package de.freese.jripper.core.model;

/**
 * Zusammenfassendes Objekt für eine CD.
 *
 * @author Thomas Freese
 */
public interface Album extends Iterable<Track>
{
    /**
     * Bei Compilations null.
     */
    String getArtist();

    String getComment();

    DiskID getDiskID();

    int getDiskNumber();

    String getGenre();

    String getTitle();

    int getTotalDisks();

    Track getTrack(int index);

    int getTrackCount();

    int getYear();

    boolean isCompilation();

    /**
     * Bei Compilations null.
     */
    void setArtist(final String artist);

    void setComment(final String comment);

    void setDiskNumber(final int diskNumber);

    void setGenre(final String genre);

    void setTitle(final String title);

    void setTotalDisks(final int totalDisks);

    void setYear(final int year);
}
