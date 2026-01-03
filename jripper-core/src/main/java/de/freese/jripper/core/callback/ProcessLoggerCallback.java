// Created: 07.10.2013
package de.freese.jripper.core.callback;

import java.util.Objects;

import org.slf4j.Logger;

/**
 * @author Thomas Freese
 */
public class ProcessLoggerCallback implements ProcessCallback, LoggerCallback {
    private final Logger logger;

    public ProcessLoggerCallback(final Logger logger) {
        super();

        this.logger = Objects.requireNonNull(logger, "logger required");
    }

    @Override
    public void log(final String line) {
        getLogger().info(line);
    }

    @Override
    public void process(final String line) {
        log(line);
    }

    protected Logger getLogger() {
        return logger;
    }
}
