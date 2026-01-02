// Created: 10.10.2013
package de.freese.jripper.swing.action;

import java.awt.event.ActionEvent;
import java.io.Serial;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.freese.jripper.core.model.Album;
import de.freese.jripper.swing.task.CddbQueryTask;

/**
 * {@link Action} for the CDDB Query.
 *
 * @author Thomas Freese
 */
public class ActionCddbQuery extends AbstractAction {
    @Serial
    private static final long serialVersionUID = -2636145895592666145L;

    private final transient Consumer<Album> albumConsumer;

    public ActionCddbQuery(final Consumer<Album> albumConsumer) {
        super("CDDB Query");

        this.albumConsumer = albumConsumer;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        final CddbQueryTask task = new CddbQueryTask(albumConsumer);
        task.execute();
    }
}
