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
import de.freese.jripper.core.process.ProcessMonitor;

/**
 * Liefert die Genres der lame-Definition.
 *
 * @author Thomas Freese
 */
public class GenreProviderLinux extends AbstractProcess implements GenreProvider, ProcessMonitor {
    private final Set<String> genres = new TreeSet<>();

    @Override
    public Set<String> getGenres() throws Exception {
        this.genres.clear();

        final List<String> command = new ArrayList<>();
        command.add("lame");
        command.add("--genre-list");

        execute(command, new File(Settings.getInstance().getWorkDir()), this);

        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this.genres.toString());
        }

        return this.genres;
    }

    @Override
    public void monitorProcess(final String line) {
        final String[] splits = line.split(" ", 2);

        this.genres.add(splits[1]);
    }

    @Override
    public void monitorText(final String line) {
        // Empty
    }

    @Override
    public boolean supportsOS(final String os) {
        return JRipperUtils.isLinux();
    }
}
