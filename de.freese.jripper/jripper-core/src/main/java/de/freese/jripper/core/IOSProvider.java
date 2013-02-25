/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core;

/**
 * Interface für alle Betriebssystem gestützen Services.
 * 
 * @author Thomas Freese
 */
public interface IOSProvider
{
	/**
	 * Liefert true, wenn das Betriebssystem unterstützt wird.
	 * 
	 * @param os String
	 * @return String
	 */
	public boolean isSupportedOS(String os);
}
