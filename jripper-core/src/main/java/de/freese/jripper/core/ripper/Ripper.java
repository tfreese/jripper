/**
 * Created: 02.03.2013
 */
package de.freese.jripper.core.ripper;

import java.io.File;
import de.freese.jripper.core.OSProvider;
import de.freese.jripper.core.process.ProcessMonitor;

/**
 * Interface für einen CD Ripper.
 *
 * @author Thomas Freese
 */
public interface Ripper extends OSProvider
{
    /**
     * Auslesen der CD in das Verzeichnis.
     * 
     * @param device String
     * @param directory {@link File}
     * @param monitor {@link ProcessMonitor}
     * @throws Exception Falls was schief geht.
     */
    public void rip(String device, File directory, ProcessMonitor monitor) throws Exception;
}
