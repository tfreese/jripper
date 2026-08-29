package de.freese.jripper.core.genre;

import java.util.Set;

import de.freese.jripper.core.OSProvider;

/**
 * @author Thomas Freese
 * @since 20.10.2013
 */
public interface GenreProvider extends OSProvider {
    /**
     * Returns available Genres.
     */
    Set<String> getGenres() throws Exception;
}
