// Created: 10.10.2013
package de.freese.jripper.swing.action;

import java.awt.event.ActionEvent;
import java.io.Serial;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.freese.binding.property.Property;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.swing.task.CddbQueryTask;

/**
 * {@link Action} für die CDDB Query.
 *
 * @author Thomas Freese
 */
public class ActionCddbQuery extends AbstractAction {
    @Serial
    private static final long serialVersionUID = -2636145895592666145L;

    private transient final Property<Album> albumProperty;

    public ActionCddbQuery(final Property<Album> albumProperty) {
        super();

        this.albumProperty = albumProperty;

        putValue(NAME, "CDDB Query");
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        CddbQueryTask task = new CddbQueryTask(this.albumProperty);
        task.execute();
    }
}
