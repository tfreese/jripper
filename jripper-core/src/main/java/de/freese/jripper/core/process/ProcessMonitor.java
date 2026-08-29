package de.freese.jripper.core.process;

/**
 * @author Thomas Freese
 * @since 07.10.2013
 */
public interface ProcessMonitor {
    /**
     * Zeile des Process.
     */
    void monitorProcess(String line);

    /**
     * Beliebiger Text.
     */
    void monitorText(String line);
}
