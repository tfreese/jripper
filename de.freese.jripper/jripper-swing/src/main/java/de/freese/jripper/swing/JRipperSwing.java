/**
 * Created: 10.10.2013
 */

package de.freese.jripper.swing;

import de.freese.jripper.swing.action.ActionCDDBQuery;
import de.freese.jripper.swing.table.AlbumTableModel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Swing-View für den JRipper.
 * 
 * @author Thomas Freese
 */
public class JRipperSwing
{
	/**
	 * @param args String[]
	 */
	public static void main(final String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			/**
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run()
			{
				JRipperSwing view = new JRipperSwing();
				view.init();
			}
		});
	}

	/**
	 * Erstellt ein neues {@link JRipperSwing} Object.
	 */
	public JRipperSwing()
	{
		super();
	}

	/**
	 * Initialisierung der GUI.
	 */
	private void init()
	{
		JFrame frame = new JFrame();
		frame.setTitle("JRipper");
		frame.setSize(1024, 768);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		frame.setLayout(new BorderLayout());

		JTable table = new JTable();
		table.setModel(new AlbumTableModel());

		frame.add(new JButton(new ActionCDDBQuery(table)), BorderLayout.NORTH);
		frame.add(new JScrollPane(table), BorderLayout.CENTER);

		frame.setVisible(true);
	}
}
