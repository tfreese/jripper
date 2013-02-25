/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core.diskid;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.freese.jripper.core.util.CDDetector;

/**
 * Testklasse für die {@link IDiskIDService}.
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
			IDiskIDService service = DiskID.getService();
			Assert.assertNotNull(service);
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
	public void testOSServiceLinux()
	{
		if (!SystemUtils.IS_OS_LINUX)
		{
			return;
		}

		IDiskIDService service = new LinuxDiskIDService();

		try
		{
			String id = service.getDiskID(CDDetector.detectCDDVD());
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
