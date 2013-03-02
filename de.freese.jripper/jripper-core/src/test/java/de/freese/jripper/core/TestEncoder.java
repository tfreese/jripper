/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.freese.jripper.core.encoder.Encoder;
import de.freese.jripper.core.encoder.EncoderFormat;
import de.freese.jripper.core.encoder.IEncoder;

/**
 * Testklasse für die {@link IEncoder}.
 * 
 * @author Thomas Freese
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestEncoder
{
	/**
	 * Erstellt ein neues {@link TestEncoder} Object.
	 */
	public TestEncoder()
	{
		super();
	}

	/**
	 * Linux.
	 */
	@Test
	public void testLinuxFlac()
	{
		if (!SystemUtils.IS_OS_LINUX)
		{
			return;
		}

		try
		{
			IEncoder encoder = Encoder.getInstance(EncoderFormat.flac);
			Assert.assertNotNull(encoder);
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
	public void testLinuxMP3()
	{
		if (!SystemUtils.IS_OS_LINUX)
		{
			return;
		}

		try
		{
			IEncoder encoder = Encoder.getInstance(EncoderFormat.mp3);
			Assert.assertNotNull(encoder);
		}
		catch (Exception ex)
		{
			Assert.fail();
		}
	}
}
