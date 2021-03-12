/**
 * Created: 07.10.2013
 */
package de.freese.jripper.core.process;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link ProcessMonitor} der mehrere Monitore zusammenfasst.
 *
 * @author Thomas Freese
 */
public class CompositeProgressMonitor implements ProcessMonitor
{
    /**
     * 
     */
    private final List<ProcessMonitor> monitore;

    /**
     * Erstellt ein neues {@link CompositeProgressMonitor} Object.
     */
    public CompositeProgressMonitor()
    {
        super();

        this.monitore = new ArrayList<>();
    }

    /**
     * Hinzufügern neuer Monitore.
     * 
     * @param monitor {@link ProcessMonitor}
     * @param monitore {@link ProcessMonitor}[]
     */
    public void addMonitor(final ProcessMonitor monitor, final ProcessMonitor...monitore)
    {
        this.monitore.add(monitor);

        for (ProcessMonitor pm : monitore)
        {
            this.monitore.add(pm);
        }
    }

    /**
     * @see de.freese.jripper.core.process.ProcessMonitor#monitorProcess(java.lang.String)
     */
    @Override
    public void monitorProcess(final String line)
    {
        for (ProcessMonitor monitor : this.monitore)
        {
            monitor.monitorProcess(line);
        }
    }

    /**
     * @see de.freese.jripper.core.process.ProcessMonitor#monitorText(java.lang.String)
     */
    @Override
    public void monitorText(final String line)
    {
        for (ProcessMonitor monitor : this.monitore)
        {
            monitor.monitorText(line);
        }
    }
}
