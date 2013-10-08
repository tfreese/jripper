/**
 * Created: 07.10.2013
 */

package de.freese.jripper.core.process;

import java.io.InputStream;

/**
 * Interface für alle JobMonitore.
 * 
 * @author Thomas Freese
 */
public interface IProcessMonitor
{
	/**
	 * Zeile des Process {@link InputStream}.
	 * 
	 * @param line String
	 */
	public void monitorProcess(String line);

	/**
	 * Beliebiger Text.
	 * 
	 * @param line String
	 */
	public void monitorText(String line);
}
