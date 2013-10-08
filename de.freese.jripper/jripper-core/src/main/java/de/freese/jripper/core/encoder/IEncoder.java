/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core.encoder;

import de.freese.jripper.core.IOSProvider;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.process.IProcessMonitor;
import java.io.File;

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
	 * @param monitor {@link IProcessMonitor}
	 * @throws Exception Falls was schief geht.
	 */
	public void encode(Album album, File directory, IProcessMonitor monitor) throws Exception;

	/**
	 * @return {@link EncoderFormat}
	 */
	public EncoderFormat getFormat();
}
