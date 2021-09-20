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
     *
     * @return String
     */
    String getArtist();

    /**
     * @return String
     */
    String getComment();

    /**
     * @return {@link DiskID}
     */
    DiskID getDiskID();

    /**
     * @return int
     */
    int getDiskNumber();

    /**
     * @return String
     */
    String getGenre();

    /**
     * @return String
     */
    String getTitle();

    /**
     * @return int
     */
    int getTotalDisks();

    /**
     * Liefert den {@link Track} am Index.
     *
     * @param index int
     *
     * @return {@link Track}
     */
    Track getTrack(int index);

    /**
     * Liefert die Anzahl der Tracks.
     *
     * @return int
     */
    int getTrackCount();

    /**
     * @return int
     */
    int getYear();

    /**
     * Liefert true, wenn das Album eine Compilation ist.
     *
     * @return boolean
     */
    boolean isCompilation();

    /**
     * Bei Compilations null.
     *
     * @param artist String
     */
    void setArtist(final String artist);

    /**
     * @param comment String
     */
    void setComment(final String comment);

    /**
     * @param diskNumber int
     */
    void setDiskNumber(final int diskNumber);

    /**
     * @param genre String
     */
    void setGenre(final String genre);

    /**
     * @param title String
     */
    void setTitle(final String title);

    /**
     * @param totalDisks int
     */
    void setTotalDisks(final int totalDisks);

    /**
     * @param year int
     */
    void setYear(final int year);
}
