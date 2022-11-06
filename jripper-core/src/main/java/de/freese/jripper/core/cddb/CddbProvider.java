// Created: 02.03.2013
package de.freese.jripper.core.cddb;

import de.freese.jripper.core.model.DiskID;

/**
 * Interface für einen CDDB Provider (FreeDB, MusicBrainz).
 *
 * @author Thomas Freese
 */
public interface CddbProvider
{
    /**
     * Liefert die Albentitel der CD.
     */
    CddbResponse queryAlbum(DiskID diskID, String genre) throws Exception;

    /**
     * Liefert die Genres der CD.
     */
    CddbResponse queryGenres(DiskID diskID) throws Exception;
}
