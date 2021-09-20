// Created: 25.02.2013
package de.freese.jripper.core;

/**
 * Interface für alle Betriebssystem gestützen Services.
 *
 * @author Thomas Freese
 */
@FunctionalInterface
public interface OSProvider
{
    /**
     * Liefert true, wenn das Betriebssystem unterstützt wird.
     *
     * @param os String
     *
     * @return String
     */
    boolean supportsOS(String os);
}
