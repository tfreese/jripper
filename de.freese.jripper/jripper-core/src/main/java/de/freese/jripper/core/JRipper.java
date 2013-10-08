/**
 * Created: 07.10.2013
 */

package de.freese.jripper.core;

import de.freese.jripper.core.diskid.IDiskIDProvider;
import de.freese.jripper.core.encoder.IEncoder;
import de.freese.jripper.core.ripper.IRipper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Zentrale Klasse.
 * 
 * @author Thomas Freese
 */
public class JRipper
{
	/**
	 * 
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger("JRipper");

	/**
	 * 
	 */
	private IDiskIDProvider diskIDProvider = null;
	/**
	 * 
	 */
	private IEncoder encoderFLAC = null;
	/**
	 * 
	 */
	private IEncoder encoderMP3 = null;
	/**
	 * 
	 */
	private IRipper ripper = null;

	/**
	 * Erstellt ein neues {@link JRipper} Object.
	 */
	public JRipper()
	{
		super();
	}

	/**
	 * @return {@link IDiskIDProvider}
	 */
	public IDiskIDProvider getDiskIDProvider()
	{
		return this.diskIDProvider;
	}

	/**
	 * @return {@link IEncoder}
	 */
	public IEncoder getEncoderFLAC()
	{
		return this.encoderFLAC;
	}

	/**
	 * @return {@link IEncoder}
	 */
	public IEncoder getEncoderMP3()
	{
		return this.encoderMP3;
	}

	/**
	 * @return {@link IRipper}
	 */
	public IRipper getRipper()
	{
		return this.ripper;
	}

	/**
	 * @param diskIDProvider {@link IDiskIDProvider}
	 */
	public void setDiskIDProvider(final IDiskIDProvider diskIDProvider)
	{
		this.diskIDProvider = diskIDProvider;
	}

	/**
	 * @param encoderFLAC {@link IEncoder}
	 */
	public void setEncoderFLAC(final IEncoder encoderFLAC)
	{
		this.encoderFLAC = encoderFLAC;
	}

	/**
	 * @param encoderMP3 {@link IEncoder}
	 */
	public void setEncoderMP3(final IEncoder encoderMP3)
	{
		this.encoderMP3 = encoderMP3;
	}

	/**
	 * @param ripper {@link IRipper}
	 */
	public void setRipper(final IRipper ripper)
	{
		this.ripper = ripper;
	}
}
