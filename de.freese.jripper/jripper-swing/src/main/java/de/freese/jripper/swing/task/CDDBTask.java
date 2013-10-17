/**
 * Created: 10.10.2013
 */

package de.freese.jripper.swing.task;

import de.freese.jripper.core.JRipper;
import de.freese.jripper.core.Settings;
import de.freese.jripper.core.cddb.ICDDBProvider;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.model.DiskID;
import de.freese.jripper.swing.JRipperSwing;
import de.freese.jripper.swing.model.AlbumBean;
import de.freese.jripper.swing.model.AlbumModel;
import java.util.List;
import javax.swing.SwingWorker;

/**
 * {@link SwingWorker} für die CDDB Query.
 * 
 * @author Thomas Freese
 */
public class CDDBTask extends SwingWorker<Album, Void>
{
	/**
	 * 
	 */
	private final AlbumModel albumModel;

	/**
	 * Erstellt ein neues {@link CDDBTask} Object.
	 * 
	 * @param albumModel {@link AlbumModel}
	 */
	public CDDBTask(final AlbumModel albumModel)
	{
		super();

		this.albumModel = albumModel;
	}

	/**
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected Album doInBackground() throws Exception
	{
		String device = Settings.getInstance().getDevice();
		DiskID diskID = JRipper.getInstance().getDiskIDProvider().getDiskID(device);

		ICDDBProvider cddbService = JRipper.getInstance().getCDDBProvider();
		List<String> genres = cddbService.queryCDDB(diskID);
		Album album = cddbService.readCDDB(diskID, genres.get(0));

		return album;
	}

	/**
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done()
	{
		try
		{
			Album album = get();

			this.albumModel.setBean(new AlbumBean(album));
		}
		catch (Exception ex)
		{
			JRipperSwing.LOGGER.error(null, ex);
		}
	}
}
