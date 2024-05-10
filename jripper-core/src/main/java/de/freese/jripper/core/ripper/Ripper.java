// Created: 02.03.2013
package de.freese.jripper.core.ripper;

import java.io.File;

import de.freese.jripper.core.OSProvider;
import de.freese.jripper.core.process.ProcessMonitor;

/**
 * @author Thomas Freese
 */
public interface Ripper extends OSProvider {
    void rip(String device, File directory, ProcessMonitor monitor) throws Exception;
}
