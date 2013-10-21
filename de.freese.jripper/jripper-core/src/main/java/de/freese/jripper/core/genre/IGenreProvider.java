/**
 * Created: 20.10.2013
 */

package de.freese.jripper.core.genre;

import de.freese.jripper.core.IOSProvider;
import de.freese.jripper.core.model.DiskID;
import java.util.Set;

/**
 * Liefert mögliche Genres.
 * 
 * @author Thomas Freese
 */
public interface IGenreProvider extends IOSProvider
{
	/**
	 * Liefert mögliche Genres.
	 * 
	 * @return {@link DiskID}
	 * @throws Exception Falls was schief geht.
	 */
	public Set<String> getGenres() throws Exception;
}
