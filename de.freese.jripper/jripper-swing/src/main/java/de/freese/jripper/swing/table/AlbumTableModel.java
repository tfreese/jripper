/**
 * Created: 10.10.2013
 */

package de.freese.jripper.swing.table;

import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.model.Track;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * {@link TableModel} des Albums.
 * 
 * @author Thomas Freese
 */
public class AlbumTableModel extends AbstractTableModel
{
	/**
	 *
	 */
	private static final long serialVersionUID = 3590076395406196810L;

	/**
	 * 
	 */
	private Album album = null;

	/**
	 * Erstellt ein neues {@link AlbumTableModel} Object.
	 */
	public AlbumTableModel()
	{
		super();
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount()
	{
		return 4;
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(final int column)
	{
		switch (column)
		{
			case 0:
				return "No.";
			case 1:
				return "Artist";
			case 2:
				return "Title";
			case 3:
				return "Time";
			default:
				return "?";
		}
	}

	/**
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount()
	{
		return this.album == null ? 0 : this.album.getTrackCount();
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex)
	{
		Track track = this.album.getTrack(rowIndex);
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
	 * @param album {@link Album}
	 */
	public void setAlbum(final Album album)
	{
		this.album = album;

		fireTableDataChanged();
	}
}
