// Created: 25.02.2013
package de.freese.jripper.core;

import java.util.function.Predicate;

/**
 * @author Thomas Freese
 */
@FunctionalInterface
public interface OSProvider extends Predicate<String> {
    boolean supportsOS(String os);

    @Override
    default boolean test(final String s) {
        return supportsOS(s);
    }
}
