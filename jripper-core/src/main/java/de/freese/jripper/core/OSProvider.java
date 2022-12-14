// Created: 25.02.2013
package de.freese.jripper.core;

import java.util.function.Predicate;

/**
 * Interface für alle Betriebssystem gestützten Services.
 *
 * @author Thomas Freese
 */
@FunctionalInterface
public interface OSProvider extends Predicate<String>
{
    @Override
    default boolean test(String s)
    {
        return supportsOS(s);
    }

    boolean supportsOS(String os);
}
