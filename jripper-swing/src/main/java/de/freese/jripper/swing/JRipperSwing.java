/**
 * Created: 10.10.2013
 */
package de.freese.jripper.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.Map.Entry;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.plaf.FontUIResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jgoodies.binding.adapter.BoundedRangeAdapter;
import com.jgoodies.binding.adapter.SpinnerAdapterFactory;
import de.freese.binding.SwingBindings;
import de.freese.binding.collections.DefaultObservableList;
import de.freese.binding.property.Property;
import de.freese.binding.property.SimpleIntegerProperty;
import de.freese.binding.swing.combobox.DefaultObservableListComboBoxModel;
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

/**
 * Swing-View für den JRipper.
 *
 * @author Thomas Freese
 */
public class JRipperSwing
{
    /**
     * WindowListener zum beenden.
     *
     * @author Thomas Freese
     */
    private class MainFrameListener extends WindowAdapter
    {
        /**
         * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
         */
        @Override
        public void windowClosing(final WindowEvent e)
        {
            System.exit(0);
        }
    }

    /**
     *
     */
    public static final Logger LOGGER = LoggerFactory.getLogger("JRipperSwing");

    /**
     * @param args String[]
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            JRipperSwing view = new JRipperSwing();
            view.init();
        });
    }

    /**
     *
     */
    private de.freese.binding.collections.ObservableList<Integer> mp3BitRates = new DefaultObservableList<>(new ArrayList<>());

    /**
     *
     */
    private Property<Integer> selectedMp3BitRate = new SimpleIntegerProperty();

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
        // frame.setSize(1280, 1024);
        frame.setSize(1280, 768);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new MainFrameListener());
        // frame.setExtendedState(Frame.MAXIMIZED_BOTH);
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
        final JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane2.setOneTouchExpandable(true);

        // Album
        JPanel panelAlbum = new JPanel();
        panelAlbum.setLayout(new GridBagLayout());
        panelAlbum.setBorder(BorderFactory.createTitledBorder("Album"));

        int row = 0;

        // Artist
        panelAlbum.add(new JLabel("Artist"), new GBCBuilder(0, row));
        JTextField textField = JGoodiesComponentFactory.createTextField(albumModel.getModel(AlbumBean.PROPERTY_ARTIST));
        panelAlbum.add(textField, new GBCBuilder(GridBagConstraints.RELATIVE, row).gridwidth(9).fillHorizontal());

        row++;

        // Title
        panelAlbum.add(new JLabel("Title"), new GBCBuilder(0, row));
        textField = JGoodiesComponentFactory.createTextField(albumModel.getModel(AlbumBean.PROPERTY_TITLE));
        panelAlbum.add(textField, new GBCBuilder(GridBagConstraints.RELATIVE, row).gridwidth(9).fillHorizontal());

        row++;

        // Genre
        panelAlbum.add(new JLabel("Genre"), new GBCBuilder(0, row));
        textField = JGoodiesComponentFactory.createTextField(albumModel.getModel(AlbumBean.PROPERTY_GENRE));
        panelAlbum.add(textField, new GBCBuilder(GridBagConstraints.RELATIVE, row).gridwidth(3).fillHorizontal());
        panelAlbum.add(new JLabel("Defaults"), new GBCBuilder(GridBagConstraints.RELATIVE, row).insets(2, 20, 2, 2).anchorEast());
        panelAlbum.add(JGoodiesComponentFactory.createComboBox(albumModel.getSelectionInListGenres()), new GBCBuilder(GridBagConstraints.RELATIVE, row));

        row++;

        // Disk
        panelAlbum.add(new JLabel("Disk"), new GBCBuilder(0, row));
        JSpinner spinner = new JSpinner();
        spinner.setModel(SpinnerAdapterFactory.createNumberAdapter(albumModel.getModel(AlbumBean.PROPERTY_DISKNUMBER), 1, 1, 50, 1));
        panelAlbum.add(spinner, new GBCBuilder(GridBagConstraints.RELATIVE, row));
        panelAlbum.add(new JLabel("/"), new GBCBuilder(GridBagConstraints.RELATIVE, row));
        spinner = new JSpinner();
        spinner.setModel(SpinnerAdapterFactory.createNumberAdapter(albumModel.getModel(AlbumBean.PROPERTY_TOTALDISKS), 1, 1, 50, 1));
        panelAlbum.add(spinner, new GBCBuilder(GridBagConstraints.RELATIVE, row));

        row++;

        // Year
        panelAlbum.add(new JLabel("Year"), new GBCBuilder(0, row));
        spinner = new JSpinner();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        spinner.setModel(SpinnerAdapterFactory.createNumberAdapter(albumModel.getModel(AlbumBean.PROPERTY_YEAR), currentYear, 1900, 3000, 1));
        spinner.setEditor(new JSpinner.NumberEditor(spinner, "0000"));
        panelAlbum.add(spinner, new GBCBuilder(GridBagConstraints.RELATIVE, row).gridwidth(3));

        // NumberFormat numberFormat = NumberFormat.getIntegerInstance();
        // numberFormat.setGroupingUsed(false);
        // JFormattedTextField formattedTextField = BasicComponentFactory.createIntegerField(albumModel.getModel(AlbumBean.PROPERTY_YEAR), numberFormat);
        // panelAlbum.add(formattedTextField, new GBCBuilder(GridBagConstraints.RELATIVE, row).gridwidth(3));//.fillHorizontal());
        row++;

        // Comment
        panelAlbum.add(new JLabel("Comment"), new GBCBuilder(0, row));
        JTextArea textArea = JGoodiesComponentFactory.createTextArea(albumModel.getModel(AlbumBean.PROPERTY_COMMENT));
        textArea.setRows(10);
        panelAlbum.add(new JScrollPane(textArea), new GBCBuilder(GridBagConstraints.RELATIVE, row).gridwidth(9).fillBoth());

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

        splitPane2.setRightComponent(new JScrollPane(table));

        splitPane.setLeftComponent(splitPane2);

        SwingUtilities.invokeLater(() -> splitPane2.setDividerLocation(0.5D));
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

        Settings settings = Settings.getInstance();
        SettingsModel model = new SettingsModel(settings);

        // Device
        panel.add(new JLabel("Device"), new GBCBuilder(0, 0));
        JTextField textField = JGoodiesComponentFactory.createTextField(model.getModel(SettingsBean.PROPERTY_DEVICE));
        panel.add(textField, new GBCBuilder(1, 0).fillHorizontal());

        // Work. Dir.
        panel.add(new JLabel("Work.-Dir."), new GBCBuilder(0, 1));
        textField = JGoodiesComponentFactory.createTextField(model.getModel(SettingsBean.PROPERTY_WORKDIR));
        panel.add(textField, new GBCBuilder(1, 1).fillHorizontal());
        JButton button = new JButton(new ActionChooseWorkDir(splitPane.getParent(), model.getModel(SettingsBean.PROPERTY_WORKDIR)));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        panel.add(button, new GBCBuilder(2, 1));

        // FLAC
        JPanel panelFlac = new JPanel();
        panelFlac.setLayout(new GridBagLayout());
        panelFlac.setBorder(BorderFactory.createTitledBorder("Flac"));

        // Enabled
        panelFlac.add(JGoodiesComponentFactory.createCheckBox(model.getModel(SettingsBean.PROPERTY_FLAC_ENBLED), "Enabled"), new GBCBuilder(0, 0));

        // Compression
        panelFlac.add(new JLabel("Compression"), new GBCBuilder(0, 1));
        JSlider slider = new JSlider();
        slider.setModel(new BoundedRangeAdapter(model.getModel(SettingsBean.PROPERTY_FLAC_COMPRESSION), 0, 0, 8));
        slider.setMajorTickSpacing(2);
        // slider.setSnapToTicks(true);
        slider.setPaintLabels(true);
        @SuppressWarnings("unchecked")
        Dictionary<Integer, JLabel> labelTable = slider.getLabelTable(); // new Hashtable<>();
        labelTable.put(0, new JLabel("fast"));
        labelTable.put(8, new JLabel("best"));
        slider.setLabelTable(labelTable);
        panelFlac.add(slider, new GBCBuilder(1, 1).fillHorizontal());

        panel.add(panelFlac, new GBCBuilder(0, 2).gridwidth(3).fillHorizontal());

        // MP3
        JPanel panelMP3 = new JPanel();
        panelMP3.setLayout(new GridBagLayout());
        panelMP3.setBorder(BorderFactory.createTitledBorder("MP3"));

        // Enabled
        panelMP3.add(JGoodiesComponentFactory.createCheckBox(model.getModel(SettingsBean.PROPERTY_MP3_ENBLED), "Enabled"), new GBCBuilder(0, 0));

        // Bitrate
        panelMP3.add(new JLabel("Bitrate"), new GBCBuilder(0, 1));

        this.mp3BitRates.addAll(settings.getMp3BitRates());

        JComboBox<Integer> comboBox = new JComboBox<>();
        comboBox.setModel(new DefaultObservableListComboBoxModel<>(this.mp3BitRates));

        SwingBindings.bindBidirectional(comboBox, this.selectedMp3BitRate);
        this.selectedMp3BitRate.addListener((observable, oldValue, newValue) -> settings.setMp3Bitrate(newValue));
        this.selectedMp3BitRate.setValue(this.mp3BitRates.get(0));

        panelMP3.add(comboBox, new GBCBuilder(1, 1).fillHorizontal());

        panel.add(panelMP3, new GBCBuilder(0, 3).gridwidth(3).fillHorizontal());

        // Alles nach oben drücken
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
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        }
        catch (Exception ex)
        {
            LOGGER.error(null, ex);
        }

        UIManager.put("FileChooser.useSystemIcons", Boolean.TRUE);

        // Farben
        Color color = new Color(215, 215, 215);
        UIManager.put("Table.alternatingBackground", color);
        UIManager.put("Table.alternateRowColor", color);
        UIManager.put("List.alternatingBackground", color);
        // defaults.put("Tree.alternatingBackground", color);

        // Fonts: Dialog, Monospaced, Arial
        Font font = new Font("Dialog", Font.PLAIN, 16);

        // UIDefaults defaults = UIManager.getLookAndFeelDefaults();
        UIDefaults defaults = UIManager.getDefaults();

        for (Entry<Object, Object> entry : defaults.entrySet())
        {
            Object key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof FontUIResource)
            {
                UIManager.put(key, new FontUIResource(font));
            }

            // String keyString = key.toString();
            //
            // if (keyString.endsWith(".font") || keyString.endsWith(".acceleratorFont"))
            // {
            // UIManager.put(key, font);
            // }
        }

        // Ausnahmen
        Font font_bold = font.deriveFont(Font.BOLD);
        UIManager.put("TitledBorder.font", font_bold);
    }
}
