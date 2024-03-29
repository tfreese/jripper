// Created: 10.10.2013
package de.freese.jripper.swing.task;

import java.util.Objects;
import java.util.Set;

import javax.swing.SwingWorker;

import de.freese.binding.collections.ObservableList;
import de.freese.jripper.core.JRipper;
import de.freese.jripper.core.genre.GenreProvider;
import de.freese.jripper.swing.JRipperSwing;

/**
 * {@link SwingWorker} um die Genre-Auswahl zu laden.
 *
 * @author Thomas Freese
 */
public class LoadGenresTask extends SwingWorker<Set<String>, Void> {
    private final ObservableList<String> genresObservableList;

    public LoadGenresTask(final ObservableList<String> genresObservableList) {
        super();

        this.genresObservableList = Objects.requireNonNull(genresObservableList, "genresObservableList required");
    }

    @Override
    protected Set<String> doInBackground() throws Exception {
        final GenreProvider genreProvider = JRipper.getInstance().getGenreProvider();

        return genreProvider.getGenres();
    }

    @Override
    protected void done() {
        try {
            final Set<String> genres = get();

            this.genresObservableList.clear();
            this.genresObservableList.addAll(genres);
        }
        catch (Exception ex) {
            JRipperSwing.LOGGER.error(ex.getMessage(), ex);
        }
    }
}
