/**
 * Created: 10.10.2013
 */

package de.freese.jripper.swing.task;

import de.freese.jripper.core.Settings;
import de.freese.jripper.core.cddb.FreeDBProvider;
import de.freese.jripper.core.cddb.ICDDBProvider;
import de.freese.jripper.core.diskid.DiskIDProvider;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.model.DiskID;
import de.freese.jripper.swing.table.AlbumTableModel;
import java.util.List;
import javax.swing.JTable;
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
	private final JTable table;

	/**
	 * Erstellt ein neues {@link CDDBTask} Object.
	 * 
	 * @param table {@link JTable}
	 */
	public CDDBTask(final JTable table)
	{
		super();

		this.table = table;
	}

	/**
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected Album doInBackground() throws Exception
	{
		String device = Settings.getInstance().getDevice();
		DiskID diskID = DiskIDProvider.getInstance().getDiskID(device);

		ICDDBProvider cddbService = new FreeDBProvider();
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
			AlbumTableModel tableModel = (AlbumTableModel) this.table.getModel();
			tableModel.setAlbum(album);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
