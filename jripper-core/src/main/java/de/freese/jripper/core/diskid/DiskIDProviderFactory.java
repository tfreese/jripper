/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core.diskid;

import java.util.ServiceLoader;
import org.apache.commons.lang3.SystemUtils;

/**
 * Zentrale Klasse für die Bereitstellung der DiskID.<br>
 * Je nach Betriebssystem wird die entsprechende Implementierung verwendet.
 *
 * @author Thomas Freese
 */
public final class DiskIDProviderFactory
{
    /**
     * 
     */
    private static final ServiceLoader<DiskIDProvider> SERVICE_LOADER = ServiceLoader.load(DiskIDProvider.class);

    /**
     * Je nach Betriebssystem wird die entsprechende Implementierung geliefert.
     * 
     * @return {@link DiskIDProvider}
     */
    public static DiskIDProvider getInstance()
    {
        DiskIDProvider impl = null;

        for (DiskIDProvider diskID : SERVICE_LOADER)
        {
            if (diskID.supportsOS(SystemUtils.OS_NAME))
            {
                impl = diskID;
                break;
            }
        }

        if (impl == null)
        {
            throw new NullPointerException("no diskID found for " + SystemUtils.OS_NAME);
        }

        return impl;
    }

    /**
     * Erstellt ein neues {@link DiskIDProviderFactory} Object.
     */
    private DiskIDProviderFactory()
    {
        super();
    }
}
