/**
 * Created: 10.10.2013
 */

package de.freese.jripper.swing;

import de.freese.jripper.swing.action.ActionCDDBQuery;
import de.freese.jripper.swing.action.ActionRipping;
import de.freese.jripper.swing.table.AlbumTableModel;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	 * Liefert default GridBagConstraints mit fill=BOTH, anchor=CENTER und Insets mit (5, 5, 5, 5).
	 * 
	 * @param col int, siehe auch Konstanten in GridBagConstraints
	 * @param row int, siehe auch Konstanten in GridBagConstraints
	 * @return {@link GridBagConstraints}
	 */
	public static GridBagConstraints getGBC(final int col, final int row)
	{
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = col;
		constraints.gridy = row;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.ipadx = 0;
		constraints.ipady = 0;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(5, 5, 5, 5);

		return constraints;
	}

	/**
	 * @param container {@link Container}
	 * @param table {@link JTable}
	 */

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

		frame.setLayout(new GridBagLayout());

		JTable table = new JTable();
		table.setModel(new AlbumTableModel());

		initMenue(frame, table);
		initSettings(frame);

		GridBagConstraints gbc = getGBC(0, 1);
		gbc.gridwidth = 4;
		frame.add(new JScrollPane(table), gbc);

		frame.setVisible(true);
	}

	/**
	 * @param container {@link Container}
	 * @param table {@link JTable}
	 */
	private void initMenue(final Container container, final JTable table)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(new JButton(new ActionCDDBQuery(table)));
		panel.add(new JButton(new ActionRipping(table)));

		GridBagConstraints gbc = getGBC(0, 0);
		gbc.gridwidth = 6;
		gbc.weighty = 0.0D;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
		container.add(panel, gbc);
	}

	/**
	 * @param container {@link Container}
	 */
	private void initSettings(final Container container)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Settings"));

		panel.add(new JLabel("Work Dir.:"));

		GridBagConstraints gbc = getGBC(5, 1);
		container.add(panel, gbc);
	}
}
