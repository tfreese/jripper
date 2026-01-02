// Created: 25.02.2013
package de.freese.jripper.core.diskid;

import de.freese.jripper.core.OSProvider;
import de.freese.jripper.core.model.DiskId;

/**
 * Returns the DiskId of the CD for the CDDB Query.
 *
 * @author Thomas Freese
 */
public interface DiskIDProvider extends OSProvider {
    DiskId getDiskID(String device) throws Exception;
}
