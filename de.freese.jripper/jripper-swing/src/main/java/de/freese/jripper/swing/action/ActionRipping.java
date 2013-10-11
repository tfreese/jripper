/**
 * Created: 10.10.2013
 */

package de.freese.jripper.swing.action;

import de.freese.jripper.core.model.Album;
import de.freese.jripper.swing.table.AlbumTableModel;
import de.freese.jripper.swing.task.RippingTask;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;

/**
 * {@link Action} für das Rippen und Codieren.
 * 
 * @author Thomas Freese
 */
public class ActionRipping extends AbstractAction
{
	/**
	 *
	 */
	private static final long serialVersionUID = -4794748623915093242L;
	/**
	 * 
	 */
	private final JTable table;

	/**
	 * Erstellt ein neues {@link ActionRipping} Object.
	 * 
	 * @param table {@link JTable}
	 */
	public ActionRipping(final JTable table)
	{
		super();

		this.table = table;

		putValue(NAME, "Rip + Encode");
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		AlbumTableModel tableModel = (AlbumTableModel) this.table.getModel();
		Album album = tableModel.getAlbum();

		RippingTask task = new RippingTask(album);
		task.execute();
	}
}
