// Created: 07.10.2013
package de.freese.jripper.core.callback;

/**
 * @author Thomas Freese
 */
@FunctionalInterface
public interface ProcessCallback {
    void process(String line);
}
