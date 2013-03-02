/**
 * Created: 02.03.2013
 */

package de.freese.jripper.core.encoder;

import java.io.File;
import java.io.PrintWriter;

import org.apache.commons.lang3.SystemUtils;

import de.freese.jripper.core.model.Album;

/**
 * Linux Implementierung mit dem Programm "lame".
 * 
 * @author Thomas Freese
 */
public class LinuxMP3Encoder implements IEncoder
{
	/**
	 * Erstellt ein neues {@link LinuxMP3Encoder} Object.
	 */
	public LinuxMP3Encoder()
	{
		super();
	}

	/**
	 * @see de.freese.jripper.core.encoder.IEncoder#encode(de.freese.jripper.core.model.Album,
	 *      java.io.File, java.io.PrintWriter)
	 */
	@Override
	public void encode(final Album album, final File directory, final PrintWriter printWriter)
		throws Exception
	{
		throw new UnsupportedOperationException("must implement");
	}

	/**
	 * @see de.freese.jripper.core.encoder.IEncoder#getFormat()
	 */
	@Override
	public EncoderFormat getFormat()
	{
		return EncoderFormat.mp3;
	}

	/**
	 * @see de.freese.jripper.core.IOSProvider#isSupportedOS(java.lang.String)
	 */
	@Override
	public boolean isSupportedOS(final String os)
	{
		return SystemUtils.IS_OS_LINUX;
	}
}
