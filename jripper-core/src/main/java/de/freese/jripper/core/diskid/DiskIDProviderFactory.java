/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core.diskid;

import java.util.ServiceLoader;

import de.freese.jripper.core.JRipperUtils;

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
            if (diskID.supportsOS(JRipperUtils.getOsName()))
            {
                impl = diskID;
                break;
            }
        }

        if (impl == null)
        {
            throw new NullPointerException("no diskID found for " + JRipperUtils.getOsName());
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
