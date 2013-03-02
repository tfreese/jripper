/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Testklasse für die Utilities.
 * 
 * @author Thomas Freese
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUtils
{
	/**
	 * Erstellt ein neues {@link TestUtils} Object.
	 */
	public TestUtils()
	{
		super();
	}

	/**
	 * Liefert je nach Betriebssystem das CD/DVD Laufwerk.
	 */
	@Test
	public void testDetectCDDVD()
	{
		Assert.assertNotNull(JRipperUtils.detectCDDVD());
	}
}
