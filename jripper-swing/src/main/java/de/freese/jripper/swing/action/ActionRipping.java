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
 * {@link Action} for Ripping and Coding.
 *
 * @author Thomas Freese
 */
public class ActionRipping extends AbstractAction {
    @Serial
    private static final long serialVersionUID = -4794748623915093242L;

    private final transient Property<Album> albumProperty;

    public ActionRipping(final Property<Album> albumProperty) {
        super("Rip / Encode");

        this.albumProperty = albumProperty;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        final Album album = this.albumProperty.getValue();

        final RippingTask task = new RippingTask(album);
        task.execute();
    }
}
