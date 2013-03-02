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
	private static final ServiceLoader<IDiskID> SERVICE_LOADER = ServiceLoader.load(IDiskID.class);

	/**
	 * Je nach Betriebssystem wird die entsprechende Implementierung geliefert.
	 * 
	 * @return {@link IDiskID}
	 */
	public static IDiskID getInstance()
	{
		IDiskID impl = null;

		for (IDiskID diskID : SERVICE_LOADER)
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
	 * Erstellt ein neues {@link DiskID} Object.
	 */
	private DiskID()
	{
		super();
	}
}
