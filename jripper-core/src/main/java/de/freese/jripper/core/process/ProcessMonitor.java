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
     *
     * @param line String
     */
    void monitorProcess(String line);

    /**
     * Beliebiger Text.
     *
     * @param line String
     */
    void monitorText(String line);
}
