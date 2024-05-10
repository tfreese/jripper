// Created: 20.10.2013
package de.freese.jripper.core.genre;

import java.util.Set;

import de.freese.jripper.core.OSProvider;

/**
 * @author Thomas Freese
 */
public interface GenreProvider extends OSProvider {
    /**
     * Liefert mögliche Genres.
     */
    Set<String> getGenres() throws Exception;
}
