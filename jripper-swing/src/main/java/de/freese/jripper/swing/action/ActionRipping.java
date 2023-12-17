// Created: 10.10.2013
package de.freese.jripper.swing.action;

import java.awt.event.ActionEvent;
import java.io.Serial;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.freese.binding.property.Property;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.swing.task.RippingTask;

/**
 * {@link Action} für das Rippen und Codieren.
 *
 * @author Thomas Freese
 */
public class ActionRipping extends AbstractAction {
    @Serial
    private static final long serialVersionUID = -4794748623915093242L;

    private final transient Property<Album> albumProperty;

    public ActionRipping(final Property<Album> albumProperty) {
        super();

        this.albumProperty = albumProperty;

        putValue(NAME, "Rip / Encode");
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        final Album album = this.albumProperty.getValue();

        final RippingTask task = new RippingTask(album);
        task.execute();
    }
}
