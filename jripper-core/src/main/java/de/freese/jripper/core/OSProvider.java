package de.freese.jripper.core;

import java.util.function.Predicate;

/**
 * @author Thomas Freese
 * @since 25.02.2013
 */
@FunctionalInterface
public interface OSProvider extends Predicate<String> {
    boolean supportsOS(String os);

    @Override
    default boolean test(final String s) {
        return supportsOS(s);
    }
}
