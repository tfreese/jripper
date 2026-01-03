// Created: 20.10.2013
package de.freese.jripper.core.genre;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import de.freese.jripper.core.JRipperUtils;
import de.freese.jripper.core.Settings;
import de.freese.jripper.core.process.AbstractProcess;

/**
 * Returns the Genres from the lame-Definition.
 *
 * @author Thomas Freese
 */
public class GenreProviderLinux extends AbstractProcess implements GenreProvider {
    @Override
    public Set<String> getGenres() throws Exception {
        final Set<String> genres = new TreeSet<>();

        final List<String> command = new ArrayList<>();
        command.add("lame");
        command.add("--genre-list");

        execute(command, new File(Settings.getInstance().getWorkDir()), line -> {
            final String[] splits = line.split(" ", 2);

            genres.add(splits[1]);
        });

        if (getLogger().isDebugEnabled()) {
            getLogger().debug(genres.toString());
        }

        return genres;
    }

    @Override
    public boolean supportsOS(final String os) {
        return JRipperUtils.isLinux();
    }
}
