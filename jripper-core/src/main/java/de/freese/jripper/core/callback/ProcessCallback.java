package de.freese.jripper.core.callback;

/**
 * @author Thomas Freese
 * @since 07.10.2013
 */
@FunctionalInterface
public interface ProcessCallback {
    void process(String line);
}
