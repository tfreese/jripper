// Created: 25.02.2013
package de.freese.jripper.core;

import java.util.function.Predicate;

/**
 * Interface für alle Betriebssystem gestützten Services.
 *
 * @author Thomas Freese
 */
@FunctionalInterface
public interface OSProvider extends Predicate<String> {
    boolean supportsOS(String os);

    @Override
    default boolean test(String s) {
        return supportsOS(s);
    }
}
