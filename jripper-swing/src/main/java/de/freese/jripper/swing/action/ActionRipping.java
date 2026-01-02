// Created: 10.10.2013
package de.freese.jripper.swing.action;

import java.awt.event.ActionEvent;
import java.io.Serial;
import java.util.Objects;
import java.util.function.Supplier;

import javax.swing.AbstractAction;
import javax.swing.Action;

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

    private final transient Supplier<Album> albumSupplier;

    public ActionRipping(final Supplier<Album> albumSupplier) {
        super("Rip / Encode");

        this.albumSupplier = Objects.requireNonNull(albumSupplier, "albumSupplier required");
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        final Album album = albumSupplier.get();

        final RippingTask task = new RippingTask(album);
        task.execute();
    }
}
