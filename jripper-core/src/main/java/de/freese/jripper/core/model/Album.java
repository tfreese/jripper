// Created: 20.10.2013
package de.freese.jripper.core.model;

/**
 * CD-Descriptor.
 *
 * @author Thomas Freese
 */
public interface Album extends Iterable<Track> {
    /**
     * Bei Compilations null.
     */
    String getArtist();

    String getComment();

    DiskId getDiskID();

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
    void setArtist(String artist);

    void setComment(String comment);

    void setDiskNumber(int diskNumber);

    void setGenre(String genre);

    void setTitle(String title);

    void setTotalDisks(int totalDisks);

    void setYear(int year);
}
