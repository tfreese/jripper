/**
 * Created: 10.10.2013
 */

package de.freese.jripper.swing.task;

import de.freese.jripper.core.JRipperUtils;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.script.IScripter;
import de.freese.jripper.core.script.LinuxScripter;
import java.io.File;
import javax.swing.SwingWorker;

/**
 * {@link SwingWorker} für das Rippen und Codieren.
 * 
 * @author Thomas Freese
 */
public class RippingTask extends SwingWorker<Void, Void>
{
	/**
	 * 
	 */
	private final Album album;

	/**
	 * Erstellt ein neues {@link RippingTask} Object.
	 * 
	 * @param album {@link Album}
	 */
	public RippingTask(final Album album)
	{
		super();

		this.album = album;
	}

	/**
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected Void doInBackground() throws Exception
	{
		IScripter scripter = new LinuxScripter();
		File script = scripter.generate(this.album, JRipperUtils.getWorkDir(this.album));
		scripter.execute(script);

		return null;
	}

	/**
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done()
	{
		// Empty
	}
}
