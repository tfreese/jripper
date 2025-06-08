// Created: 07.10.2013
package de.freese.jripper.core.process;

import java.io.PrintWriter;

/**
 * @author Thomas Freese
 */
public class PrintWriterProcessMonitor implements ProcessMonitor {
    private final PrintWriter printWriter;

    public PrintWriterProcessMonitor(final PrintWriter printWriter) {
        super();

        this.printWriter = printWriter;
    }

    @Override
    public void monitorProcess(final String line) {
        getPrintWriter().println(line);
        getPrintWriter().flush();
    }

    @Override
    public void monitorText(final String line) {
        getPrintWriter().println(line);
        getPrintWriter().flush();
    }

    protected PrintWriter getPrintWriter() {
        return printWriter;
    }
}
