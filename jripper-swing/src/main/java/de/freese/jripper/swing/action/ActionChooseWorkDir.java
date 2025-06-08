// Created: 18.10.2013
package de.freese.jripper.swing.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.Serial;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;

import de.freese.binding.property.Property;

/**
 * {@link Action} fot the Working-Directory.
 *
 * @author Thomas Freese
 */
public class ActionChooseWorkDir extends AbstractAction {
    @Serial
    private static final long serialVersionUID = 3262325088354448846L;

    private final Component parent;
    private final transient Property<String> workDirProperty;

    public ActionChooseWorkDir(final Component parent, final Property<String> workDirProperty) {
        super("...");

        this.parent = parent;
        this.workDirProperty = workDirProperty;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(workDirProperty.getValue()));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        final int returnVal = fileChooser.showDialog(parent, "Work.-Dir.");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            workDirProperty.setValue(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }
}
