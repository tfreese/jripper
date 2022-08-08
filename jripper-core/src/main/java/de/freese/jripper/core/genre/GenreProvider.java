// Created: 20.10.2013
package de.freese.jripper.core.genre;

import java.util.Set;

import de.freese.jripper.core.OSProvider;
import de.freese.jripper.core.model.DiskID;

/**
 * Liefert mögliche Genres.
 *
 * @author Thomas Freese
 */
public interface GenreProvider extends OSProvider
{
    /**
     * Liefert mögliche Genres.
     *
     * @return {@link DiskID}
     *
     * @throws Exception Falls was schiefgeht.
     */
    Set<String> getGenres() throws Exception;
}
