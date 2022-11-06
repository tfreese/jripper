// Created: 07.10.2013
package de.freese.jripper.core.process;

/**
 * Interface für alle JobMonitore.
 *
 * @author Thomas Freese
 */
public interface ProcessMonitor
{
    /**
     * Zeile des Process.
     */
    void monitorProcess(String line);

    /**
     * Beliebiger Text.
     */
    void monitorText(String line);
}
