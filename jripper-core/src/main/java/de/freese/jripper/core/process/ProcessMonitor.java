// Created: 07.10.2013
package de.freese.jripper.core.process;

/**
 * @author Thomas Freese
 */
public interface ProcessMonitor {
    void monitorProcess(String line);

    void monitorText(String line);
}
