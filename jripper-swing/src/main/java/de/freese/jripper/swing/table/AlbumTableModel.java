// Created: 24.10.2018
package de.freese.jripper.swing.table;

import java.io.Serial;
import java.util.List;

import de.freese.binding.collections.ObservableList;
import de.freese.binding.swing.table.AbstractObservableListTableModel;
import de.freese.jripper.core.model.Track;

/**
 * @author Thomas Freese
 */
public class AlbumTableModel extends AbstractObservableListTableModel<Track> {
    @Serial
    private static final long serialVersionUID = -9186435568274885834L;

    public AlbumTableModel(final ObservableList<Track> list) {
        super(List.of("No.", "Artist", "Title", "Time"), list);
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        final Track track = getObjectAt(rowIndex);
        Object value = null;

        switch (columnIndex) {
            case 0 -> value = track.getNumber();
            case 1 -> value = track.getArtist();
            case 2 -> value = track.getTitle();
            case 3 -> {
                final int minutes = track.getSeconds() / 60;
                final int seconds = track.getSeconds() % 60;
                value = String.format("%d:%02d", minutes, seconds);
            }
            default -> {
                // Empty
            }
        }

        return value;
    }
}
