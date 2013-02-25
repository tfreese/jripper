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
public final class DiskID
{
	/**
	 * 
	 */
	private static final ServiceLoader<IDiskIDService> serviceLoader = ServiceLoader
			.load(IDiskIDService.class);

	/**
	 * Je nach Betriebssystem wird die entsprechende Implementierung geliefert.
	 * 
	 * @return {@link IDiskIDService}
	 */
	public static IDiskIDService getService()
	{
		IDiskIDService diskIDService = null;

		for (IDiskIDService service : DiskID.serviceLoader)
		{
			if (service.isSupportedOS(SystemUtils.OS_NAME))
			{
				diskIDService = service;
				break;
			}
		}

		if (diskIDService == null)
		{
			throw new NullPointerException("no service found for" + SystemUtils.OS_NAME);
		}

		return diskIDService;
	}

	/**
	 * Erstellt ein neues {@link DiskID} Object.
	 */
	private DiskID()
	{
		super();
	}
}
