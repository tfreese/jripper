/**
 * Created: 18.10.2013
 */

package de.freese.jripper.swing.action;

import com.jgoodies.binding.value.ValueModel;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;

/**
 * {@link Action} für die Auswahl des Arbeitsverzeichnisses.
 * 
 * @author Thomas Freese
 */
public class ActionChooseWorkDir extends AbstractAction
{
	/**
	 *
	 */
	private static final long serialVersionUID = 3262325088354448846L;

	/**
	 * 
	 */
	private final Component parent;
	/**
	 * 
	 */
	private final ValueModel valueModel;

	/**
	 * Erstellt ein neues {@link ActionChooseWorkDir} Object.
	 * 
	 * @param parent {@link Component}
	 * @param valueModel {@link ValueModel}
	 */
	public ActionChooseWorkDir(final Component parent, final ValueModel valueModel)
	{
		super("\u2026");

		this.parent = parent;
		this.valueModel = valueModel;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(this.valueModel.getValue().toString()));
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		ValueModel valueModel = this.valueModel;
		// Trigger trigger = new Trigger();
		// ValueModel valueModel = new BufferedValueModel(this.valueModel, trigger);

		int returnVal = fileChooser.showDialog(this.parent, "Work.-Dir.");

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			valueModel.setValue(fileChooser.getSelectedFile().getAbsolutePath());
			// trigger.triggerCommit();
		}
	}
}
