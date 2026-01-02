// Created: 18.10.2013
package de.freese.jripper.swing.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.Serial;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;

/**
 * {@link Action} fot the Working-Directory.
 *
 * @author Thomas Freese
 */
public class ActionChooseWorkDir extends AbstractAction {
    @Serial
    private static final long serialVersionUID = 3262325088354448846L;

    private final Component parent;
    private final transient Consumer<String> workDirConsumer;
    private final transient Supplier<String> workDirSupplier;

    public ActionChooseWorkDir(final Component parent, final Supplier<String> workDirSupplier, final Consumer<String> workDirConsumer) {
        super("...");

        this.parent = parent;
        this.workDirSupplier = Objects.requireNonNull(workDirSupplier, "workDirSupplier required");
        this.workDirConsumer = Objects.requireNonNull(workDirConsumer, "workDirConsumer required");
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(workDirSupplier.get()));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        final int returnVal = fileChooser.showDialog(parent, "Work.-Dir.");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            workDirConsumer.accept(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }
}
