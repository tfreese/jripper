/**
 * Created: 02.03.2013
 */

package de.freese.jripper.core.cddb;

import de.freese.jripper.core.model.AlbumImpl;
import de.freese.jripper.core.model.DiskID;
import java.util.List;

/**
 * Interface für einen CDDB Provider (FreeDB, MusicBrainz).
 * 
 * @author Thomas Freese
 */
public interface CDDBProvider
{
	/**
	 * Liefert die Albentitel der CD.
	 * 
	 * @param diskID {@link DiskID}
	 * @param genre String
	 * @return {@link List}
	 * @throws Exception Falls was schief geht.
	 */
	public AlbumImpl queryAlbum(DiskID diskID, String genre) throws Exception;

	/**
	 * Liefert die Genres der CD.
	 * 
	 * @param diskID {@link DiskID}
	 * @return {@link List}
	 * @throws Exception Falls was schief geht.
	 */
	public List<String> queryGenres(DiskID diskID) throws Exception;
}
