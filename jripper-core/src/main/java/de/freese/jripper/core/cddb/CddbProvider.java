// Created: 02.03.2013
package de.freese.jripper.core.cddb;

import de.freese.jripper.core.model.DiskId;

/**
 * Interface für einen CDDB Provider (FreeDB, MusicBrainz).
 *
 * @author Thomas Freese
 */
public interface CddbProvider {
    /**
     * Liefert die Titel der CD.
     */
    CddbResponse queryAlbum(DiskId diskID, String genre) throws Exception;

    /**
     * Liefert die Genres der CD.
     */
    CddbResponse queryGenres(DiskId diskID) throws Exception;
}
