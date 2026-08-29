package de.freese.jripper.core.process;

import org.slf4j.Logger;

/**
 * @author Thomas Freese
 * @since 07.10.2013
 */
public class LoggerProcessMonitor implements ProcessMonitor {
    private final Logger logger;

    public LoggerProcessMonitor(final Logger logger) {
        super();

        this.logger = logger;
    }

    @Override
    public void monitorProcess(final String line) {
        getLogger().info(line);
    }

    @Override
    public void monitorText(final String line) {
        getLogger().info(line);
    }

    protected Logger getLogger() {
        return logger;
    }
}
