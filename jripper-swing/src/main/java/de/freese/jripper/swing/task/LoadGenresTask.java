// Created: 10.10.2013
package de.freese.jripper.swing.task;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.swing.SwingWorker;

import de.freese.jripper.core.JRipper;
import de.freese.jripper.core.genre.GenreProvider;
import de.freese.jripper.swing.JRipperSwing;

/**
 * {@link SwingWorker} to load the Genres.
 *
 * @author Thomas Freese
 */
public class LoadGenresTask extends SwingWorker<Set<String>, Void> {
    private final List<String> genresList;

    public LoadGenresTask(final List<String> genresList) {
        super();

        this.genresList = Objects.requireNonNull(genresList, "genresList required");
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

            genresList.clear();
            genresList.addAll(genres);
        }
        catch (InterruptedException ex) {
            JRipperSwing.LOGGER.error(ex.getMessage(), ex);

            // Restore interrupted state.
            Thread.currentThread().interrupt();
        }
        catch (Exception ex) {
            JRipperSwing.LOGGER.error(ex.getMessage(), ex);
        }
    }
}
