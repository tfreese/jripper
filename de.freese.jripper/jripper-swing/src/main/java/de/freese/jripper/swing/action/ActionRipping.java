/**
 * Created: 10.10.2013
 */

package de.freese.jripper.swing.action;

import de.freese.jripper.core.model.IAlbum;
import de.freese.jripper.swing.model.AlbumModel;
import de.freese.jripper.swing.task.RippingTask;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

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
	private final AlbumModel albumModel;

	/**
	 * Erstellt ein neues {@link ActionRipping} Object.
	 * 
	 * @param albumModel {@link AlbumModel}
	 */
	public ActionRipping(final AlbumModel albumModel)
	{
		super();

		this.albumModel = albumModel;

		putValue(NAME, "Rip / Encode");
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		IAlbum album = this.albumModel.getBean();

		RippingTask task = new RippingTask(album);
		task.execute();
	}
}
