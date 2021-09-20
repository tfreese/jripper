// Created: 21.10.2013
package de.freese.jripper.core.process;

/**
 * Leerer {@link ProcessMonitor}.
 *
 * @author Thomas Freese
 */
public class EmptyProcessMonitor implements ProcessMonitor
{
    /**
     * @see de.freese.jripper.core.process.ProcessMonitor#monitorProcess(java.lang.String)
     */
    @Override
    public void monitorProcess(final String line)
    {
        // Empty
    }

    /**
     * @see de.freese.jripper.core.process.ProcessMonitor#monitorText(java.lang.String)
     */
    @Override
    public void monitorText(final String line)
    {
        // Empty
    }
}
