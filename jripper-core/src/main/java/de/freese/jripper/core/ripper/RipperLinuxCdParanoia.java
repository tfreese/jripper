// Created: 02.03.2013
package de.freese.jripper.core.ripper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.freese.jripper.core.JRipperUtils;
import de.freese.jripper.core.process.AbstractProcess;
import de.freese.jripper.core.process.ProcessMonitor;

/**
 * Linux Implementierung mit dem Programm "cdparanoia".
 *
 * @author Thomas Freese
 */
public class RipperLinuxCdParanoia extends AbstractProcess implements Ripper {
    /**
     * @see de.freese.jripper.core.ripper.Ripper#rip(java.lang.String, java.io.File, de.freese.jripper.core.process.ProcessMonitor)
     */
    @Override
    public void rip(final String device, final File directory, final ProcessMonitor monitor) throws Exception {
        List<String> command = new ArrayList<>();
        command.add("cdparanoia");
        command.add("-w");
        command.add("-B"); // Batch, jeder Track in eine Datei
        // command.add("-v");
        // command.add("-S 2"); // Nur 2x Geschwindigkeit.
        command.add("-z "); // Keine Fehler akzeptieren.
        // command.add("-Z "); // Disable Paranoia.
        command.add("-d");
        command.add(device);
        // command.add(1);
        // command.add(track01.cdda.wav);

        execute(command, directory, monitor);
    }

    /**
     * @see de.freese.jripper.core.OSProvider#supportsOS(java.lang.String)
     */
    @Override
    public boolean supportsOS(final String os) {
        return JRipperUtils.isLinux();
    }
}
