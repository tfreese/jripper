// Created: 21.10.2013
package de.freese.jripper.core.process;

/**
 * @author Thomas Freese
 */
public class EmptyProcessMonitor implements ProcessMonitor {
    @Override
    public void monitorProcess(final String line) {
        // Empty
    }

    @Override
    public void monitorText(final String line) {
        // Empty
    }
}
