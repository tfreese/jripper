/**
 * Created: 10.10.2013
 */

package de.freese.jripper.swing.table;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import de.freese.jripper.core.model.Track;
import javax.swing.ListModel;
import javax.swing.table.TableModel;

/**
 * {@link TableModel} des Albums.
 * 
 * @author Thomas Freese
 */
public class AlbumTableModel extends AbstractTableAdapter<Track>
{
	/**
	 *
	 */
	private static final long serialVersionUID = 3590076395406196810L;

	/**
	 * Erstellt ein neues {@link AlbumTableModel} Object.
	 * 
	 * @param listModel {@link ListModel}
	 */
	public AlbumTableModel(final ListModel<Track> listModel)
	{
		super(listModel, "No.", "Artist", "Title", "Time");
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex)
	{
		Track track = getRow(rowIndex);
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
				// int seconds = track.getSeconds() - (minutes * 60);
				int seconds = track.getSeconds() % 60;
				value = String.format("%d:%02d", minutes, seconds);
				break;

			default:
				break;
		}

		return value;
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex)
	{
		switch (columnIndex)
		{
			case 1:
				return getRow(rowIndex).getAlbum().isCompilation();
			case 2:
				return true;
			default:
				return false;
		}
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex)
	{
		Track track = getRow(rowIndex);

		switch (columnIndex)
		{
			case 1:
				track.setArtist(aValue.toString());
				break;
			case 2:
				track.setTitle(aValue.toString());
				break;
			default:
				break;
		}

		fireTableCellUpdated(rowIndex, columnIndex);
	}
}
