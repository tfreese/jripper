// Created: 07.10.2013
package de.freese.jripper.core.process;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Freese
 */
public class CompositeProgressMonitor implements ProcessMonitor {
    private final List<ProcessMonitor> monitore;

    public CompositeProgressMonitor() {
        super();

        this.monitore = new ArrayList<>();
    }

    public void addMonitor(final ProcessMonitor monitor) {
        if (!this.monitore.contains(monitor)) {
            this.monitore.add(monitor);
        }
    }

    @Override
    public void monitorProcess(final String line) {
        this.monitore.forEach(m -> m.monitorProcess(line));
    }

    @Override
    public void monitorText(final String line) {
        this.monitore.forEach(m -> m.monitorText(line));
    }
}
