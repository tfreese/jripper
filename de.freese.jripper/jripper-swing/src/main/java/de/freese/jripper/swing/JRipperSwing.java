/**
 * Created: 10.10.2013
 */

package de.freese.jripper.swing;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import de.freese.jripper.core.Settings;
import de.freese.jripper.swing.action.ActionCDDBQuery;
import de.freese.jripper.swing.action.ActionRipping;
import de.freese.jripper.swing.model.AlbumBean;
import de.freese.jripper.swing.model.AlbumModel;
import de.freese.jripper.swing.model.SettingsBean;
import de.freese.jripper.swing.model.SettingsModel;
import de.freese.jripper.swing.table.AlbumTableModel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.NumberFormat;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Swing-View für den JRipper.
 * 
 * @author Thomas Freese
 */
public class JRipperSwing
{
	/**
	 * 
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger("JRipperSwing");

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
		frame.setLayout(new BorderLayout());

		AlbumModel albumModel = new AlbumModel();

		initMenue(frame, albumModel);
		initSettings(frame);
		initAlbum(frame, albumModel);

		frame.setVisible(true);
	}

	/**
	 * @param container {@link Container}
	 * @param albumModel {@link AlbumModel}
	 */
	@SuppressWarnings("unchecked")
	private void initAlbum(final Container container, final AlbumModel albumModel)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		// Album
		JPanel panelAlbum = new JPanel();
		panelAlbum.setLayout(new GridBagLayout());
		panelAlbum.setBorder(BorderFactory.createTitledBorder("Album"));

		// Artist
		GridBagConstraints gbc = new GBCBuilder(0, 0);
		panelAlbum.add(new JLabel("Artist"), gbc);

		gbc = new GBCBuilder(1, 0).fillHorizontal();
		panelAlbum.add(BasicComponentFactory.createTextField(albumModel.getModel(AlbumBean.PROPERTY_ARTIST)), gbc);

		// Title
		gbc = new GBCBuilder(0, 1);
		panelAlbum.add(new JLabel("Title"), gbc);

		gbc = new GBCBuilder(1, 1).fillHorizontal();
		panelAlbum.add(BasicComponentFactory.createTextField(albumModel.getModel(AlbumBean.PROPERTY_TITLE)), gbc);

		// Year
		gbc = new GBCBuilder(0, 2);
		panelAlbum.add(new JLabel("Year"), gbc);

		gbc = new GBCBuilder(1, 2).fillHorizontal();
		NumberFormat numberFormat = NumberFormat.getIntegerInstance();
		numberFormat.setGroupingUsed(false);
		panelAlbum.add(BasicComponentFactory.createIntegerField(albumModel.getModel(AlbumBean.PROPERTY_YEAR), numberFormat), gbc);

		// Comment
		gbc = new GBCBuilder(0, 3);
		panelAlbum.add(new JLabel("Comment"), gbc);

		gbc = new GBCBuilder(1, 3).fillBoth();
		JTextArea textArea = BasicComponentFactory.createTextArea(albumModel.getModel(AlbumBean.PROPERTY_COMMENT));
		textArea.setRows(10);
		panelAlbum.add(new JScrollPane(textArea), gbc);

		gbc = new GBCBuilder(0, 0).fillHorizontal();
		panel.add(panelAlbum, gbc);

		// Tabelle
		AlbumTableModel tableModel = new AlbumTableModel(albumModel.getListModelTracks());

		JTable table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(tableModel);

		// GridBagConstraints gbc = new GBCBuilder(0, 0).anchorCenter().fillBoth();
		// panel.add(new JScrollPane(table), gbc);
		gbc = new GBCBuilder(0, 1).fillBoth();
		panel.add(new JScrollPane(table), gbc);

		container.add(panel, BorderLayout.CENTER);
	}

	/**
	 * @param container {@link Container}
	 * @param albumModel {@link AlbumModel}
	 */
	private void initMenue(final Container container, final AlbumModel albumModel)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		// Dummy
		GridBagConstraints gbc = new GBCBuilder(0, 0).weightx(1);
		panel.add(Box.createGlue(), gbc);

		gbc = new GBCBuilder(1, 0).anchorCenter().gridwidth(2).fillHorizontal();
		panel.add(new JButton(new ActionCDDBQuery(albumModel)), gbc);

		// Dummy
		gbc = new GBCBuilder(3, 0).weightx(1);
		panel.add(Box.createGlue(), gbc);

		gbc = new GBCBuilder(4, 0).anchorCenter().gridwidth(2).fillHorizontal();
		panel.add(new JButton(new ActionRipping(albumModel)), gbc);

		// Dummy
		gbc = new GBCBuilder(6, 0).weightx(1);
		panel.add(Box.createGlue(), gbc);

		container.add(panel, BorderLayout.NORTH);
	}

	/**
	 * @param container {@link Container}
	 */
	private void initSettings(final Container container)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Settings"));

		SettingsModel model = new SettingsModel(Settings.getInstance());

		GridBagConstraints gbc = new GBCBuilder(0, 0).fillHorizontal();
		JTextField textField = BasicComponentFactory.createTextField(model.getModel(SettingsBean.PROPERTY_DEVICE));
		panel.add(textField, gbc);

		gbc = new GBCBuilder(0, 1).fillHorizontal();
		textField = BasicComponentFactory.createTextField(model.getModel(SettingsBean.PROPERTY_WORKDIR));
		panel.add(textField, gbc);

		gbc = new GBCBuilder(0, 2).fillHorizontal();
		@SuppressWarnings("unchecked")
		JComboBox<Integer> comboBox = BasicComponentFactory.createComboBox(model.getSelectionInListMp3Bitrate());
		panel.add(comboBox, gbc);

		// Alles nach oben drücken;
		gbc = new GBCBuilder(0, GridBagConstraints.RELATIVE).fillVertical();
		panel.add(Box.createGlue(), gbc);

		// gbc = new GBCBuilder(5, 1).anchorCenter().gridwidth(2).gridheight(5).fillBoth();
		// container.add(panel, gbc);
		container.add(panel, BorderLayout.EAST);
	}
}
