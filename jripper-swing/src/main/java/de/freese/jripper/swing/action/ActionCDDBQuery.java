/**
 * Created: 10.10.2013
 */

package de.freese.jripper.swing.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import de.freese.binding.property.Property;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.swing.task.CDDBTask;

/**
 * {@link Action} für die CDDB Query.
 *
 * @author Thomas Freese
 */
public class ActionCDDBQuery extends AbstractAction
{
    /**
     *
     */
    private static final long serialVersionUID = -2636145895592666145L;

    /**
     * 
     */
    private final Property<Album> albumProperty;

    /**
     * Erstellt ein neues {@link ActionCDDBQuery} Object.
     * 
     * @param albumProperty {@link Property}
     */
    public ActionCDDBQuery(final Property<Album> albumProperty)
    {
        super();

        this.albumProperty = albumProperty;

        putValue(NAME, "CDDB Query");
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent e)
    {
        CDDBTask task = new CDDBTask(this.albumProperty);
        task.execute();
    }
}
