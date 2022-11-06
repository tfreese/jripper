// Created: 25.02.2013
package de.freese.jripper.core;

/**
 * Interface für alle Betriebssystem gestützten Services.
 *
 * @author Thomas Freese
 */
@FunctionalInterface
public interface OSProvider
{
    boolean supportsOS(String os);
}
