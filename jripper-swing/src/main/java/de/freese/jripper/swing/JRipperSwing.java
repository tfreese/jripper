// Created: 10.10.2013
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
import java.time.LocalDate;
import java.util.Dictionary;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.plaf.FontUIResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.freese.binding.SwingBindings;
import de.freese.binding.collections.DefaultObservableList;
import de.freese.binding.collections.ObservableList;
import de.freese.binding.property.Property;
import de.freese.binding.property.SimpleBooleanProperty;
import de.freese.binding.property.SimpleIntegerProperty;
import de.freese.binding.property.SimpleObjectProperty;
import de.freese.binding.property.SimpleStringProperty;
import de.freese.binding.swing.combobox.DefaultObservableListComboBoxModel;
import de.freese.jripper.core.Settings;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.model.AlbumImpl;
import de.freese.jripper.core.model.Track;
import de.freese.jripper.swing.action.ActionCddbQuery;
import de.freese.jripper.swing.action.ActionChooseWorkDir;
import de.freese.jripper.swing.action.ActionRipping;
import de.freese.jripper.swing.table.AlbumTableModel;
import de.freese.jripper.swing.table.AlbumTableRenderer;
import de.freese.jripper.swing.task.LoadGenresTask;

/**
 * Swing-View for the JRipper.
 *
 * @author Thomas Freese
 */
public class JRipperSwing {
    public static final Logger LOGGER = LoggerFactory.getLogger("JRipperSwing");

    private static JFrame frame;

    /**
     * @author Thomas Freese
     */
    private static final class MainFrameListener extends WindowAdapter {
        @Override
        public void windowClosing(final WindowEvent event) {
            System.exit(0);
        }
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            final JRipperSwing view = new JRipperSwing();
            view.init();
        });
    }

    private final Property<Album> albumProperty = new SimpleObjectProperty<>(this, "album", new AlbumImpl());

    private final ObservableList<Track> albumTracks = new DefaultObservableList<>();

    private Album getAlbum() {
        return this.albumProperty.getValue();
    }

    private void init() {
        initUIDefaults();

        frame = new JFrame();
        frame.setTitle("JRipper");
        // frame.setSize(1024, 768);
        // frame.setSize(1280, 1024);
        frame.setSize(1280, 768);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new MainFrameListener());
        // frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        initMenue(frame, this.albumProperty);

        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setOneTouchExpandable(true);
        frame.add(splitPane, BorderLayout.CENTER);

        initSettings(splitPane);
        initAlbum(splitPane, this.albumProperty);

        frame.setVisible(true);
        splitPane.setDividerLocation(0.75D);
    }

    private void initAlbum(final JSplitPane splitPane, final Property<Album> albumProperty) {
        final Property<String> artistTextFieldProperty = new SimpleStringProperty();
        final Property<String> titleTextFieldProperty = new SimpleStringProperty();
        final Property<String> genreTextFieldProperty = new SimpleStringProperty();
        final Property<Integer> diskNumberSpinnerProperty = new SimpleIntegerProperty();
        final Property<Integer> totalDisksSpinnerProperty = new SimpleIntegerProperty();
        final Property<Integer> yearSpinnerProperty = new SimpleIntegerProperty();
        final Property<String> commentTextAreaProperty = new SimpleStringProperty();

        this.albumProperty.addListener((observable, oldValue, newAlbum) -> {
            artistTextFieldProperty.setValue(newAlbum.getArtist());
            titleTextFieldProperty.setValue(newAlbum.getTitle());
            genreTextFieldProperty.setValue(newAlbum.getGenre());
            diskNumberSpinnerProperty.setValue(newAlbum.getDiskNumber());
            totalDisksSpinnerProperty.setValue(newAlbum.getTotalDisks());
            yearSpinnerProperty.setValue(newAlbum.getYear());
            commentTextAreaProperty.setValue(newAlbum.getComment());

            this.albumTracks.clear();

            for (Track track : newAlbum) {
                this.albumTracks.add(track);
            }
        });

        final JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane2.setOneTouchExpandable(true);

        // Album
        final JPanel panelAlbum = new JPanel();
        panelAlbum.setLayout(new GridBagLayout());
        panelAlbum.setBorder(BorderFactory.createTitledBorder("Album"));

        int row = 0;

        // Artist
        panelAlbum.add(new JLabel("Artist"), GbcBuilder.of(0, row));
        final JTextField artistTextField = new JTextField();

        SwingBindings.bindBidirectional(artistTextField, artistTextFieldProperty);
        artistTextFieldProperty.addListener((observable, oldValue, newValue) -> getAlbum().setArtist(newValue));

        panelAlbum.add(artistTextField, GbcBuilder.of(GridBagConstraints.RELATIVE, row).gridWidth(9).fillHorizontal());

        row++;

        // Title
        panelAlbum.add(new JLabel("Title"), GbcBuilder.of(0, row));
        final JTextField titleTextField = new JTextField();

        SwingBindings.bindBidirectional(titleTextField, titleTextFieldProperty);
        titleTextFieldProperty.addListener((observable, oldValue, newValue) -> getAlbum().setTitle(newValue));

        panelAlbum.add(titleTextField, GbcBuilder.of(GridBagConstraints.RELATIVE, row).gridWidth(9).fillHorizontal());

        row++;

        // Genre
        final Property<String> genreComboBoxProperty = new SimpleStringProperty();
        final ObservableList<String> genresObservableList = new DefaultObservableList<>();

        panelAlbum.add(new JLabel("Genre"), GbcBuilder.of(0, row));
        final JTextField genreTextField = new JTextField();

        SwingBindings.bindBidirectional(genreTextField, genreTextFieldProperty);
        genreTextFieldProperty.addListener((observable, oldValue, newValue) -> getAlbum().setGenre(newValue));

        panelAlbum.add(genreTextField, GbcBuilder.of(GridBagConstraints.RELATIVE, row).gridWidth(3).fillHorizontal());
        panelAlbum.add(new JLabel("Defaults"), GbcBuilder.of(GridBagConstraints.RELATIVE, row).insets(2, 20, 2, 2).anchorEast());

        final JComboBox<String> comboBoxGenres = new JComboBox<>();
        comboBoxGenres.setModel(new DefaultObservableListComboBoxModel<>(genresObservableList));
        // comboBox.setSelectedItem(genresObservableList.getFirst());

        SwingBindings.bindToProperty(comboBoxGenres, genreComboBoxProperty);
        genreComboBoxProperty.addListener((observable, oldValue, newValue) -> {
            genreTextField.setText(newValue);
            getAlbum().setGenre(newValue);
        });

        panelAlbum.add(comboBoxGenres, GbcBuilder.of(GridBagConstraints.RELATIVE, row));

        final LoadGenresTask loadGenresTask = new LoadGenresTask(genresObservableList);
        loadGenresTask.execute();

        row++;

        // Disk
        panelAlbum.add(new JLabel("Disk"), GbcBuilder.of(0, row));
        final JSpinner diskNumberSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));

        SwingBindings.bindBidirectional(diskNumberSpinner, diskNumberSpinnerProperty);
        diskNumberSpinnerProperty.addListener((observable, oldValue, newValue) -> getAlbum().setDiskNumber(newValue));

        panelAlbum.add(diskNumberSpinner, GbcBuilder.of(GridBagConstraints.RELATIVE, row));
        panelAlbum.add(new JLabel("/"), GbcBuilder.of(GridBagConstraints.RELATIVE, row));

        final JSpinner totalDiskspinner = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));

        SwingBindings.bindBidirectional(totalDiskspinner, totalDisksSpinnerProperty);
        totalDisksSpinnerProperty.addListener((observable, oldValue, newValue) -> getAlbum().setTotalDisks(newValue));

        panelAlbum.add(totalDiskspinner, GbcBuilder.of(GridBagConstraints.RELATIVE, row));

        row++;

        // Year
        panelAlbum.add(new JLabel("Year"), GbcBuilder.of(0, row));
        final int currentYear = LocalDate.now().getYear();
        final JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(currentYear, 1900, 3000, 1));
        yearSpinner.setEditor(new JSpinner.NumberEditor(yearSpinner, "0000"));

        SwingBindings.bindBidirectional(yearSpinner, yearSpinnerProperty);
        yearSpinnerProperty.addListener((observable, oldValue, newValue) -> getAlbum().setYear(newValue));

        panelAlbum.add(yearSpinner, GbcBuilder.of(GridBagConstraints.RELATIVE, row).gridWidth(3));

        // NumberFormat numberFormat = NumberFormat.getIntegerInstance();
        // numberFormat.setGroupingUsed(false);
        // JFormattedTextField formattedTextField = BasicComponentFactory.createIntegerField(albumModel.getModel(AlbumBean.PROPERTY_YEAR), numberFormat);
        // panelAlbum.add(formattedTextField, new GbcBuilder(GridBagConstraints.RELATIVE, row).gridWidth(3));//.fillHorizontal());
        row++;

        // Comment
        panelAlbum.add(new JLabel("Comment"), GbcBuilder.of(0, row));
        final JTextArea commentTextArea = new JTextArea();
        commentTextArea.setRows(10);

        SwingBindings.bindBidirectional(commentTextArea, commentTextAreaProperty);
        commentTextAreaProperty.addListener((observable, oldValue, newValue) -> getAlbum().setComment(newValue));

        panelAlbum.add(new JScrollPane(commentTextArea), GbcBuilder.of(GridBagConstraints.RELATIVE, row).gridWidth(9).fillBoth());

        splitPane2.setLeftComponent(panelAlbum);

        // Tabelle
        final AlbumTableModel tableModel = new AlbumTableModel(this.albumTracks);

        final JTable table = new JTable();
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

    private void initMenue(final Container container, final Property<Album> albumProperty) {
        final JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        // Dummy
        panel.add(Box.createGlue(), GbcBuilder.of(0, 0).weightX(1));

        JButton button = new JButton(new ActionCddbQuery(albumProperty));
        panel.add(button, GbcBuilder.of(1, 0).anchorCenter().gridWidth(2).fillHorizontal());

        // Dummy
        panel.add(Box.createGlue(), GbcBuilder.of(3, 0).weightX(1));

        button = new JButton(new ActionRipping(albumProperty));
        panel.add(button, GbcBuilder.of(4, 0).anchorCenter().gridWidth(2).fillHorizontal());

        // Dummy
        panel.add(Box.createGlue(), GbcBuilder.of(6, 0).weightX(1));

        container.add(panel, BorderLayout.NORTH);
    }

    private void initSettings(final JSplitPane splitPane) {
        final JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Settings"));

        final Settings settings = Settings.getInstance();

        // Device
        panel.add(new JLabel("Device"), GbcBuilder.of(0, 0));

        final Property<String> deviceProperty = new SimpleStringProperty();

        final JTextField textFieldDevice = new JTextField(settings.getDevice());

        SwingBindings.bindBidirectional(textFieldDevice, deviceProperty);
        deviceProperty.addListener((observable, oldValue, newValue) -> settings.setDevice(newValue));

        panel.add(textFieldDevice, GbcBuilder.of(1, 0).fillHorizontal());

        // Work. Dir.
        panel.add(new JLabel("Work.-Dir."), GbcBuilder.of(0, 1));

        final Property<String> workDirProperty = new SimpleStringProperty();

        final JTextField textFieldWorkDir = new JTextField(settings.getWorkDir());

        SwingBindings.bindBidirectional(textFieldWorkDir, workDirProperty);
        workDirProperty.addListener((observable, oldValue, newValue) -> settings.setWorkDir(newValue));

        panel.add(textFieldWorkDir, GbcBuilder.of(1, 1).fillHorizontal());

        final JButton button = new JButton(new ActionChooseWorkDir(splitPane.getParent(), workDirProperty));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        panel.add(button, GbcBuilder.of(2, 1));

        // FLAC
        final JPanel panelFlac = new JPanel();
        panelFlac.setLayout(new GridBagLayout());
        panelFlac.setBorder(BorderFactory.createTitledBorder("Flac"));

        // Enabled
        final Property<Boolean> flacEnabledProperty = new SimpleBooleanProperty();
        final JCheckBox checkBoxFlac = new JCheckBox("Enabled");
        checkBoxFlac.setSelected(settings.isFlacEnabled());

        SwingBindings.bindToProperty(checkBoxFlac, flacEnabledProperty);
        flacEnabledProperty.addListener((observable, oldValue, newValue) -> settings.setFlacEnabled(newValue));

        panelFlac.add(checkBoxFlac, GbcBuilder.of(0, 0));

        // Compression
        final Property<Integer> flacCompressionProperty = new SimpleIntegerProperty();

        panelFlac.add(new JLabel("Compression"), GbcBuilder.of(0, 1));

        final JSlider slider = new JSlider(0, 8, settings.getFlacCompression());
        slider.setMajorTickSpacing(2);
        // slider.setSnapToTicks(true);
        slider.setPaintLabels(true);

        @SuppressWarnings("unchecked") final Dictionary<Integer, JLabel> labelTable = slider.getLabelTable();
        labelTable.put(0, new JLabel("fast"));
        labelTable.put(8, new JLabel("best"));
        slider.setLabelTable(labelTable);

        SwingBindings.bindToProperty(slider, flacCompressionProperty);
        flacCompressionProperty.addListener((observable, oldValue, newValue) -> settings.setFlacCompression(newValue));

        panelFlac.add(slider, GbcBuilder.of(1, 1).fillHorizontal());

        panel.add(panelFlac, GbcBuilder.of(0, 2).gridWidth(3).fillHorizontal());

        // MP3
        final JPanel panelMP3 = new JPanel();
        panelMP3.setLayout(new GridBagLayout());
        panelMP3.setBorder(BorderFactory.createTitledBorder("MP3"));

        // Enabled
        final Property<Boolean> mp3EnabledProperty = new SimpleBooleanProperty();
        final JCheckBox checkBoxMp3 = new JCheckBox("Enabled");
        checkBoxMp3.setSelected(settings.isMp3Enabled());

        SwingBindings.bindToProperty(checkBoxMp3, mp3EnabledProperty);
        mp3EnabledProperty.addListener((observable, oldValue, newValue) -> settings.setMp3Enabled(newValue));

        panelMP3.add(checkBoxMp3, GbcBuilder.of(0, 0));

        // Bitrate
        final ObservableList<Integer> mp3BitRatesObservableList = new DefaultObservableList<>(settings.getMp3BitRates());
        final Property<Integer> mp3BitRateProperty = new SimpleIntegerProperty();

        panelMP3.add(new JLabel("Bitrate"), GbcBuilder.of(0, 1));

        final JComboBox<Integer> comboBox = new JComboBox<>();
        comboBox.setModel(new DefaultObservableListComboBoxModel<>(mp3BitRatesObservableList));
        comboBox.setSelectedItem(mp3BitRatesObservableList.getFirst());

        SwingBindings.bindToProperty(comboBox, mp3BitRateProperty);
        mp3BitRateProperty.addListener((observable, oldValue, newValue) -> settings.setMp3Bitrate(newValue));

        panelMP3.add(comboBox, GbcBuilder.of(1, 1).fillHorizontal());

        panel.add(panelMP3, GbcBuilder.of(0, 3).gridWidth(3).fillHorizontal());

        // Push all up
        panel.add(Box.createGlue(), GbcBuilder.of(0, GridBagConstraints.RELATIVE).fillVertical());

        splitPane.setRightComponent(panel);
    }

    private void initUIDefaults() {
        // try {
        //     // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //     // UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        //     // UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        // }
        // catch (Exception ex) {
        //     LOGGER.error(ex.getMessage(), ex);
        // }

        UIManager.put("FileChooser.useSystemIcons", Boolean.TRUE);

        // Farben
        final Color color = new Color(215, 215, 215);
        UIManager.put("Table.alternatingBackground", color);
        UIManager.put("Table.alternateRowColor", color);
        UIManager.put("List.alternatingBackground", color);
        // defaults.put("Tree.alternatingBackground", color);

        // Fonts: Dialog, Monospaced, Arial
        final Font font = new Font("Dialog", Font.PLAIN, 16);

        // UIDefaults defaults = UIManager.getLookAndFeelDefaults();
        final UIDefaults defaults = UIManager.getDefaults();

        for (Entry<Object, Object> entry : defaults.entrySet()) {
            final Object key = entry.getKey();
            final Object value = entry.getValue();

            if (value instanceof FontUIResource) {
                UIManager.put(key, new FontUIResource(font));
            }

            // String keyString = key.toString();
            //
            // if (keyString.endsWith(".font") || keyString.endsWith(".acceleratorFont")) {
            // UIManager.put(key, font);
            // }
        }

        // Ausnahmen
        final Font fontBold = font.deriveFont(Font.BOLD);
        UIManager.put("TitledBorder.font", fontBold);
    }
}
