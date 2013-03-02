/**
 * Created: 02.03.2013
 */

package de.freese.jripper.core.cddb;

import java.util.List;

import de.freese.jripper.core.model.Album;

/**
 * Interface für einen CDDB Provider (FreeDB, MusicBrainz).
 * 
 * @author Thomas Freese
 */
public interface ICDDBProvider
{
	/**
	 * 
	 */
	public static final String HOST = "localhost";

	/**
	 * 
	 */
	public static final int PORT = 80;

	/**
	 * 
	 */
	public static final String USER = "anonymous";

	/**
	 * Liefert die Generes der CD.
	 * 
	 * @param diskID String
	 * @return {@link List}
	 * @throws Exception Falls was schief geht.
	 */
	public List<String> query(String diskID) throws Exception;

	/**
	 * Liefert die Albentitel der CD.
	 * 
	 * @param diskID String
	 * @param genre String
	 * @return {@link List}
	 * @throws Exception Falls was schief geht.
	 */
	public Album read(String diskID, String genre) throws Exception;
}
