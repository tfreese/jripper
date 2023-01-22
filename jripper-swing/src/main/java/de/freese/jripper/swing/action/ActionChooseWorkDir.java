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
 * {@link Action} für die Auswahl des Arbeitsverzeichnisses.
 *
 * @author Thomas Freese
 */
public class ActionChooseWorkDir extends AbstractAction
{
    @Serial
    private static final long serialVersionUID = 3262325088354448846L;

    private final Component parent;

    private transient final Property<String> workDirProperty;

    @SuppressWarnings("checkstyle:AvoidEscapedUnicodeCharacters")
    public ActionChooseWorkDir(final Component parent, final Property<String> workDirProperty)
    {
        super("\u2026");

        this.parent = parent;
        this.workDirProperty = workDirProperty;
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event)
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(this.workDirProperty.getValue()));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = fileChooser.showDialog(this.parent, "Work.-Dir.");

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            this.workDirProperty.setValue(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }
}
