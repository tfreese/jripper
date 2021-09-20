// Created: 24.10.2018
package de.freese.jripper.swing.table;

import java.util.List;

import de.freese.binding.collections.ObservableList;
import de.freese.binding.swing.table.AbstractObservableListTableModel;
import de.freese.jripper.core.model.Track;

/**
 * @author Thomas Freese
 */
public class AlbumTableModel extends AbstractObservableListTableModel<Track>
{
    /**
     *
     */
    private static final long serialVersionUID = -9186435568274885834L;

    /**
     * Erstellt ein neues {@link AlbumTableModel} Object.
     *
     * @param list {@link ObservableList}
     */
    public AlbumTableModel(final ObservableList<Track> list)
    {
        super(List.of("No.", "Artist", "Title", "Time"), list);
    }

    /**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex)
    {
        Track track = getObjectAt(rowIndex);
        Object value = null;

        switch (columnIndex)
        {
            case 0:
                value = track.getNumber();
                break;
            case 1:
                value = track.getArtist();
                break;
            case 2:
                value = track.getTitle();
                break;
            case 3:
                int minutes = track.getSeconds() / 60;
                int seconds = track.getSeconds() % 60;

                value = String.format("%d:%02d", minutes, seconds);
                break;

            default:
                break;
        }

        return value;
    }
}
