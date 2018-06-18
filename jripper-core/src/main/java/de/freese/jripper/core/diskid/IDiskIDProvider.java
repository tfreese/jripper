/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core.diskid;

import de.freese.jripper.core.IOSProvider;
import de.freese.jripper.core.model.DiskID;

/**
 * Liefert die DiskID der CD für die CDDB Abfrage.
 *
 * @author Thomas Freese
 */
public interface IDiskIDProvider extends IOSProvider
{
    /**
     * Liefert die DiskID der CD.
     *
     * @param device String
     * @return {@link DiskID}
     * @throws Exception Falls was schief geht.
     */
    public DiskID getDiskID(String device) throws Exception;
}
