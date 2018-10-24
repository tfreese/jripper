/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core.diskid;

import de.freese.jripper.core.OSProvider;
import de.freese.jripper.core.model.DiskID;

/**
 * Liefert die DiskID der CD für die CDDB Abfrage.
 *
 * @author Thomas Freese
 */
public interface DiskIDProvider extends OSProvider
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
