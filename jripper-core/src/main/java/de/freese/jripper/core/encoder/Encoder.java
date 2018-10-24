/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core.encoder;

import de.freese.jripper.core.OSProvider;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.process.ProcessMonitor;
import java.io.File;

/**
 * Interface für die möglichen Encoder.
 * 
 * @author Thomas Freese
 */
public interface Encoder extends OSProvider
{
	/**
	 * @param album {@link Album}
	 * @param directory {@link File}
	 * @param monitor {@link ProcessMonitor}
	 * @throws Exception Falls was schief geht.
	 */
	public void encode(Album album, File directory, ProcessMonitor monitor) throws Exception;

	/**
	 * @return {@link EncoderFormat}
	 */
	public EncoderFormat getFormat();
}
