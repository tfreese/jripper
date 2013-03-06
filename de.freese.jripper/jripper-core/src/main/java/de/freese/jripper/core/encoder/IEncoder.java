/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core.encoder;

import java.io.File;
import java.io.PrintWriter;

import de.freese.jripper.core.IOSProvider;
import de.freese.jripper.core.model.Album;

/**
 * Interface für die möglichen Encoder.
 * 
 * @author Thomas Freese
 */
public interface IEncoder extends IOSProvider
{
	/**
	 * @param album {@link Album}
	 * @param directory {@link File}
	 * @param printWriter {@link PrintWriter}
	 * @throws Exception Falls was schief geht.
	 */
	public void encode(Album album, File directory, PrintWriter printWriter) throws Exception;

	/**
	 * @return {@link EncoderFormat}
	 */
	public EncoderFormat getFormat();
}