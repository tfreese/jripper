/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.freese.jripper.core.ripper.IRipper;
import de.freese.jripper.core.ripper.Ripper;

/**
 * Testklasse für die {@link IRipper}.
 * 
 * @author Thomas Freese
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRipper
{
	/**
	 * Erstellt ein neues {@link TestRipper} Object.
	 */
	public TestRipper()
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
			IRipper ripper = Ripper.getInstance();
			Assert.assertNotNull(ripper);
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
		// TODO Test für Ripper !?
		// if (!SystemUtils.IS_OS_LINUX)
		// {
		// return;
		// }
		//
		// IDiskID service = new LinuxDiskID();
		//
		// try
		// {
		// String id = service.getDiskID(JRipperUtils.detectCDDVD());
		// Assert.assertNotNull(id);
		// }
		// catch (IllegalStateException ex)
		// {
		// // Keine CD im Laufwerk.
		// }
		// catch (Exception ex)
		// {
		// Assert.fail();
		// }
	}
}
