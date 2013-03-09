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
public final class DiskIDProvider
{
	/**
	 * 
	 */
	private static final ServiceLoader<IDiskIDProvider> SERVICE_LOADER = ServiceLoader.load(IDiskIDProvider.class);

	/**
	 * Je nach Betriebssystem wird die entsprechende Implementierung geliefert.
	 * 
	 * @return {@link IDiskIDProvider}
	 */
	public static IDiskIDProvider getInstance()
	{
		IDiskIDProvider impl = null;

		for (IDiskIDProvider diskID : SERVICE_LOADER)
		{
			if (diskID.isSupportedOS(SystemUtils.OS_NAME))
			{
				impl = diskID;
				break;
			}
		}

		if (impl == null)
		{
			throw new NullPointerException("no diskID found for" + SystemUtils.OS_NAME);
		}

		return impl;
	}

	/**
	 * Erstellt ein neues {@link DiskIDProvider} Object.
	 */
	private DiskIDProvider()
	{
		super();
	}
}
