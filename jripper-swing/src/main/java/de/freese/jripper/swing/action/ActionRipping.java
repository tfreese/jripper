// Created: 10.10.2013
package de.freese.jripper.swing.action;

import java.awt.event.ActionEvent;

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
public class ActionRipping extends AbstractAction
{
    /**
     *
     */
    private static final long serialVersionUID = -4794748623915093242L;
    /**
     *
     */
    private final Property<Album> albumProperty;

    /**
     * Erstellt ein neues {@link ActionRipping} Object.
     *
     * @param albumProperty {@link Property}
     */
    public ActionRipping(final Property<Album> albumProperty)
    {
        super();

        this.albumProperty = albumProperty;

        putValue(NAME, "Rip / Encode");
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event)
    {
        Album album = this.albumProperty.getValue();

        RippingTask task = new RippingTask(album);
        task.execute();
    }
}
