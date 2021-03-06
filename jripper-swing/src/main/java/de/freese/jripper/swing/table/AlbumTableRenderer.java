/**
 * Created: 18.10.2013
 */
package de.freese.jripper.swing.table;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

/**
 * {@link TableModel} des Albums.
 *
 * @author Thomas Freese
 */
public class AlbumTableRenderer extends DefaultTableCellRenderer
{
    /**
     *
     */
    private static final long serialVersionUID = 8126334909300540593L;

    /**
     * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    @Override
    public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row,
                                                   final int column)
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        switch (column)
        {
            case 0:
            case 3:
                setHorizontalAlignment(SwingConstants.RIGHT);
                break;
            case 1:
            case 2:
                setHorizontalAlignment(SwingConstants.LEFT);
                break;
            default:
                break;
        }

        return this;
    }
}
