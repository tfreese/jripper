// Created: 02.03.2013
package de.freese.jripper.core.cddb;

import de.freese.jripper.core.model.DiskId;

/**
 * Interface for a CDDB Provider (FreeDB, MusicBrainz).
 *
 * @author Thomas Freese
 */
public interface CddbProvider {
    /**
     * CD-Title.
     */
    CddbResponse queryAlbum(DiskId diskID, String genre) throws Exception;

    /**
     * CD-Genres.
     */
    CddbResponse queryGenres(DiskId diskID) throws Exception;
}
