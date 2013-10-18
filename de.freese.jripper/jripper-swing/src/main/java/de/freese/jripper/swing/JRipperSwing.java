/**
 * Created: 10.10.2013
 */

package de.freese.jripper.swing;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import de.freese.jripper.core.Settings;
import de.freese.jripper.swing.action.ActionCDDBQuery;
import de.freese.jripper.swing.action.ActionChooseWorkDir;
import de.freese.jripper.swing.action.ActionRipping;
import de.freese.jripper.swing.model.AlbumBean;
import de.freese.jripper.swing.model.AlbumModel;
import de.freese.jripper.swing.model.SettingsBean;
import de.freese.jripper.swing.model.SettingsModel;
import de.freese.jripper.swing.table.AlbumTableModel;
import de.freese.jripper.swing.table.AlbumTableRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.NumberFormat;
import java.util.Map.Entry;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
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
		initUIDefaults();

		JFrame frame = new JFrame();
		frame.setTitle("JRipper");
		// frame.setSize(1024, 768);
		frame.setSize(1280, 1024);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());

		AlbumModel albumModel = new AlbumModel();

		initMenue(frame, albumModel);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setOneTouchExpandable(true);
		frame.add(splitPane, BorderLayout.CENTER);

		initSettings(splitPane);
		initAlbum(splitPane, albumModel);

		frame.setVisible(true);
		splitPane.setDividerLocation(0.75D);
	}

	/**
	 * @param splitPane {@link JSplitPane}
	 * @param albumModel {@link AlbumModel}
	 */
	@SuppressWarnings("unchecked")
	private void initAlbum(final JSplitPane splitPane, final AlbumModel albumModel)
	{
		// JPanel panel = new JPanel();
		// panel.setLayout(new GridBagLayout());

		JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane2.setOneTouchExpandable(true);

		// Album
		JPanel panelAlbum = new JPanel();
		panelAlbum.setLayout(new GridBagLayout());
		panelAlbum.setBorder(BorderFactory.createTitledBorder("Album"));

		// Artist
		panelAlbum.add(new JLabel("Artist"), new GBCBuilder(0, 0));
		JTextField textField = BasicComponentFactory.createTextField(albumModel.getModel(AlbumBean.PROPERTY_ARTIST));
		panelAlbum.add(textField, new GBCBuilder(1, 0).fillHorizontal());

		// Title
		panelAlbum.add(new JLabel("Title"), new GBCBuilder(0, 1));
		textField = BasicComponentFactory.createTextField(albumModel.getModel(AlbumBean.PROPERTY_TITLE));
		panelAlbum.add(textField, new GBCBuilder(1, 1).fillHorizontal());

		// Year
		panelAlbum.add(new JLabel("Year"), new GBCBuilder(0, 2));
		NumberFormat numberFormat = NumberFormat.getIntegerInstance();
		numberFormat.setGroupingUsed(false);
		JFormattedTextField formattedTextField = BasicComponentFactory.createIntegerField(albumModel.getModel(AlbumBean.PROPERTY_YEAR), numberFormat);
		panelAlbum.add(formattedTextField, new GBCBuilder(1, 2).fillHorizontal());

		// Comment
		panelAlbum.add(new JLabel("Comment"), new GBCBuilder(0, 3));
		JTextArea textArea = BasicComponentFactory.createTextArea(albumModel.getModel(AlbumBean.PROPERTY_COMMENT));
		textArea.setRows(10);
		panelAlbum.add(new JScrollPane(textArea), new GBCBuilder(1, 3).fillBoth());

		// panel.add(panelAlbum, new GBCBuilder(0, 0).fillBoth());
		splitPane2.setLeftComponent(panelAlbum);

		// Tabelle
		AlbumTableModel tableModel = new AlbumTableModel(albumModel.getListModelTracks());

		JTable table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(tableModel);
		table.setDefaultRenderer(Object.class, new AlbumTableRenderer());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(table.getFont().getSize() + 4);

		table.getColumnModel().getColumn(0).setMinWidth(40);
		table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.getColumnModel().getColumn(3).setMinWidth(60);
		table.getColumnModel().getColumn(3).setMaxWidth(60);

		// panel.add(new JScrollPane(table), new GBCBuilder(0, 1).fillBoth().weighty(2.0D));
		splitPane2.setRightComponent(new JScrollPane(table));

		splitPane.setLeftComponent(splitPane2);
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
		panel.add(Box.createGlue(), new GBCBuilder(0, 0).weightx(1));
		JButton button = new JButton(new ActionCDDBQuery(albumModel));
		panel.add(button, new GBCBuilder(1, 0).anchorCenter().gridwidth(2).fillHorizontal());

		// Dummy
		panel.add(Box.createGlue(), new GBCBuilder(3, 0).weightx(1));
		button = new JButton(new ActionRipping(albumModel));
		panel.add(button, new GBCBuilder(4, 0).anchorCenter().gridwidth(2).fillHorizontal());

		// Dummy
		panel.add(Box.createGlue(), new GBCBuilder(6, 0).weightx(1));

		container.add(panel, BorderLayout.NORTH);
	}

	/**
	 * @param splitPane {@link JSplitPane}
	 */
	private void initSettings(final JSplitPane splitPane)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Settings"));

		SettingsModel model = new SettingsModel(Settings.getInstance());

		// Device
		panel.add(new JLabel("Device"), new GBCBuilder(0, 0));
		JTextField textField = BasicComponentFactory.createTextField(model.getModel(SettingsBean.PROPERTY_DEVICE));
		panel.add(textField, new GBCBuilder(1, 0).fillHorizontal());

		// Work. Dir.
		panel.add(new JLabel("Work.-Dir."), new GBCBuilder(0, 1));
		textField = BasicComponentFactory.createTextField(model.getModel(SettingsBean.PROPERTY_WORKDIR));
		panel.add(textField, new GBCBuilder(1, 1).fillHorizontal());
		JButton button = new JButton(new ActionChooseWorkDir(splitPane.getParent(), model.getModel(SettingsBean.PROPERTY_WORKDIR)));
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setFocusPainted(false);
		button.setMargin(new Insets(0, 0, 0, 0));
		panel.add(button, new GBCBuilder(2, 1));

		// Bitrate
		panel.add(new JLabel("Bitrate"), new GBCBuilder(0, 2));

		@SuppressWarnings("unchecked")
		JComboBox<Integer> comboBox = BasicComponentFactory.createComboBox(model.getSelectionInListMp3Bitrate());
		panel.add(comboBox, new GBCBuilder(1, 2).fillHorizontal());

		// Alles nach oben drücken;
		panel.add(Box.createGlue(), new GBCBuilder(0, GridBagConstraints.RELATIVE).fillVertical());

		splitPane.setRightComponent(panel);
	}

	/**
	 * 
	 */
	private void initUIDefaults()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception ex)
		{
			LOGGER.error(null, ex);
		}

		UIDefaults defaults = UIManager.getLookAndFeelDefaults();

		UIManager.put("FileChooser.useSystemIcons", Boolean.TRUE);

		// Farben
		Color color = new Color(215, 215, 215);
		defaults.put("Table.alternatingBackground", color);
		defaults.put("Table.alternateRowColor", color);
		defaults.put("List.alternatingBackground", color);
		// defaults.put("Tree.alternatingBackground", color);

		// Fonts: Dialog, Monospaced, Arial
		Font font = new Font("Dialog", Font.PLAIN, 16);

		for (Entry<Object, Object> entry : UIManager.getDefaults().entrySet())
		{
			Object key = entry.getKey();
			// Object value = entry.getValue();

			String keyString = key.toString();

			if (keyString.endsWith(".font") || keyString.endsWith(".acceleratorFont"))
			{
				UIManager.put(key, font);
			}
		}

		// Ausnahmen
		Font font_bold = font.deriveFont(Font.BOLD);
		UIManager.put("TitledBorder.font", font_bold);
	}
}
