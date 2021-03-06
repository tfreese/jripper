/**
 * Created: 07.10.2013
 */

package de.freese.jripper.core.process;

import java.io.PrintWriter;

/**
 * {@link ProcessMonitor} für einen {@link PrintWriter}.
 *
 * @author Thomas Freese
 */
public class PrintWriterProcessMonitor implements ProcessMonitor
{
    /**
     * 
     */
    private final PrintWriter printWriter;

    /**
     * Erstellt ein neues {@link PrintWriterProcessMonitor} Object.
     * 
     * @param printWriter {@link PrintWriter}
     */
    public PrintWriterProcessMonitor(final PrintWriter printWriter)
    {
        super();

        this.printWriter = printWriter;
    }

    /**
     * @return {@link PrintWriter}
     */
    protected PrintWriter getPrintWriter()
    {
        return this.printWriter;
    }

    /**
     * @see de.freese.jripper.core.process.ProcessMonitor#monitorProcess(java.lang.String)
     */
    @SuppressWarnings("resource")
    @Override
    public void monitorProcess(final String line)
    {
        getPrintWriter().println(line);
        getPrintWriter().flush();
    }

    /**
     * @see de.freese.jripper.core.process.ProcessMonitor#monitorText(java.lang.String)
     */
    @SuppressWarnings("resource")
    @Override
    public void monitorText(final String line)
    {
        getPrintWriter().println(line);
        getPrintWriter().flush();
    }
}
