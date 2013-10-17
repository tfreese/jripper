/**
 * Created: 10.10.2013
 */

package de.freese.jripper.swing.action;

import de.freese.jripper.swing.model.AlbumModel;
import de.freese.jripper.swing.task.CDDBTask;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * {@link Action} für die CDDB Query.
 * 
 * @author Thomas Freese
 */
public class ActionCDDBQuery extends AbstractAction
{
	/**
	 *
	 */
	private static final long serialVersionUID = -2636145895592666145L;

	/**
	 * 
	 */
	private final AlbumModel albumModel;

	/**
	 * Erstellt ein neues {@link ActionCDDBQuery} Object.
	 * 
	 * @param albumModel {@link AlbumModel}
	 */
	public ActionCDDBQuery(final AlbumModel albumModel)
	{
		super();

		this.albumModel = albumModel;

		putValue(NAME, "CDDB Query");
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		CDDBTask task = new CDDBTask(this.albumModel);
		task.execute();
	}
}
