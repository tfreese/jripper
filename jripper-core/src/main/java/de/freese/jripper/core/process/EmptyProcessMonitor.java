package de.freese.jripper.core.process;

/**
 * @author Thomas Freese
 * @since 21.10.2013
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
