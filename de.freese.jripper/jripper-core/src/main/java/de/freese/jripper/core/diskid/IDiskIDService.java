/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core.diskid;

import de.freese.jripper.core.IOSProvider;

/**
 * Liefert die DiskID der CD für die CDDB Abfrage.
 * 
 * @author Thomas Freese
 */
public interface IDiskIDService extends IOSProvider
{
	/**
	 * Liefert die DiskID der CD.
	 * 
	 * @param device String
	 * @return String
	 * @throws Exception Falls was schief geht.
	 */
	public String getDiskID(String device) throws Exception;
}
