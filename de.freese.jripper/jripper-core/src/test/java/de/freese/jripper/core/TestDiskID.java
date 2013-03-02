/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.freese.jripper.core.diskid.DiskID;
import de.freese.jripper.core.diskid.IDiskID;
import de.freese.jripper.core.diskid.LinuxDiskID;

/**
 * Testklasse für die {@link IDiskID}.
 * 
 * @author Thomas Freese
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDiskID
{
	/**
	 * Erstellt ein neues {@link TestDiskID} Object.
	 */
	public TestDiskID()
	{
		super();
	}

	/**
	 * Liefert je nach Betriebssystem die passende Implementierung.
	 */
	@Test
	public void testGetService()
	{
		try
		{
			IDiskID diskID = DiskID.getInstance();
			Assert.assertNotNull(diskID);
		}
		catch (Exception ex)
		{
			Assert.fail();
		}
	}

	/**
	 * Linux.
	 */
	@Test
	public void testLinux()
	{
		if (!SystemUtils.IS_OS_LINUX)
		{
			return;
		}

		IDiskID diskID = new LinuxDiskID();

		try
		{
			String id = diskID.getDiskID(JRipperUtils.detectCDDVD());
			Assert.assertNotNull(id);
		}
		catch (IllegalStateException ex)
		{
			// Keine CD im Laufwerk.
		}
		catch (Exception ex)
		{
			Assert.fail();
		}
	}
}
