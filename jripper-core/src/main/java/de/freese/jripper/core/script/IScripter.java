/**
 * Created: 11.10.2013
 */

package de.freese.jripper.core.script;

import de.freese.jripper.core.model.Album;
import java.io.File;

/**
 * Interface für einen Script-Generator.
 * 
 * @author Thomas Freese
 */
public interface IScripter
{
	/**
	 * Führt das Skript aus.
	 * 
	 * @param script {@link File}
	 * @throws Exception Falls was schief geht.
	 */
	public void execute(File script) throws Exception;

	/**
	 * Erstellt das Skript mit Ausführungsrechten.
	 * 
	 * @param album {@link Album}
	 * @param folder {@link File}
	 * @return {@link File}
	 * @throws Exception Falls was schief geht.
	 */
	public File generate(Album album, File folder) throws Exception;
}
