/**
 * Created: 02.03.2013
 */

package de.freese.jripper.core.ripper;

import java.io.File;
import java.io.PrintWriter;

import de.freese.jripper.core.IOSProvider;
import de.freese.jripper.core.model.Album;

/**
 * Interface für einen CD Ripper.
 * 
 * @author Thomas Freese
 */
public interface IRipper extends IOSProvider
{
	/**
	 * Auslesen der CD in das Verzeichnis.
	 * 
	 * @param device String
	 * @param album {@link Album}
	 * @param directory {@link File}
	 * @param printWriter {@link PrintWriter}
	 * @throws Exception Falls was schief geht.
	 */
	public void rip(String device, Album album, File directory, PrintWriter printWriter)
		throws Exception;
}
