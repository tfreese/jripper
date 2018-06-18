/**
 * Created: 02.03.2013
 */

package de.freese.jripper.core.ripper;

import de.freese.jripper.core.IOSProvider;
import de.freese.jripper.core.process.IProcessMonitor;
import java.io.File;

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
	 * @param directory {@link File}
	 * @param monitor {@link IProcessMonitor}
	 * @throws Exception Falls was schief geht.
	 */
	public void rip(String device, File directory, IProcessMonitor monitor) throws Exception;
}
