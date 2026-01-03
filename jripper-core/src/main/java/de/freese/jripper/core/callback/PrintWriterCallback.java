// Created: 07.10.2013
package de.freese.jripper.core.callback;

import java.io.PrintWriter;
import java.util.Objects;

/**
 * @author Thomas Freese
 */
public class PrintWriterCallback implements ProcessCallback, LoggerCallback {
    private final PrintWriter printWriter;

    public PrintWriterCallback(final PrintWriter printWriter) {
        super();

        this.printWriter = Objects.requireNonNull(printWriter, "printWriter required");
    }

    @Override
    public void log(final String line) {
        process(line);
    }

    @Override
    public void process(final String line) {
        getPrintWriter().println(line);
        getPrintWriter().flush();
    }

    protected PrintWriter getPrintWriter() {
        return printWriter;
    }
}
