// Created: 21.10.2013
package de.freese.jripper.core.callback;

/**
 * @author Thomas Freese
 */
public class EmptyCallback implements ProcessCallback, LoggerCallback {
    @Override
    public void log(final String line) {
        // Empty
    }

    @Override
    public void process(final String line) {
        // Empty
    }
}
