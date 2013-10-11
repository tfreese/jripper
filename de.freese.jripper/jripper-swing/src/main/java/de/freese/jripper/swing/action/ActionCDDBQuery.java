/**
 * Created: 10.10.2013
 */

package de.freese.jripper.swing.action;

import de.freese.jripper.swing.task.CDDBTask;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;

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
	private final JTable table;

	/**
	 * Erstellt ein neues {@link ActionCDDBQuery} Object.
	 * 
	 * @param table {@link JTable}
	 */
	public ActionCDDBQuery(final JTable table)
	{
		super();

		this.table = table;

		putValue(NAME, "CDDB Query");
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		CDDBTask task = new CDDBTask(this.table);
		task.execute();
	}
}
