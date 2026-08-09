package de.freese.jripper.swing.task;

import de.freese.jripper.core.JRipper;
import de.freese.jripper.core.Settings;
import de.freese.jripper.core.cddb.CddbProvider;
import de.freese.jripper.core.cddb.CddbResponse;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.model.DiskId;
import de.freese.jripper.swing.JRipperSwing;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * {@link SwingWorker} for the CDDB Query.
 *
 * @author Thomas Freese
 * @since 10.10.2013
 */
public class CddbQueryTask extends SwingWorker<CddbResponse, Void> {
    private final Consumer<Album> albumConsumer;

    public CddbQueryTask(final Consumer<Album> albumConsumer) {
        super();

        this.albumConsumer = Objects.requireNonNull(albumConsumer, "albumConsumer required");
    }

    @Override
    protected CddbResponse doInBackground() throws Exception {
        final String device = Settings.getInstance().getDevice();
        final DiskId diskID = JRipper.getInstance().getDiskIDProvider().getDiskID(device);

        final CddbProvider cddbService = JRipper.getInstance().getCddbProvider();
        CddbResponse cddbResponse = cddbService.queryGenres(diskID);

        if (cddbResponse.getErrorMessage() != null) {
            return cddbResponse;
        }

        cddbResponse = cddbService.queryAlbum(diskID, cddbResponse.getGenres().getFirst());

        return cddbResponse;
    }

    @Override
    protected void done() {
        try {
            final CddbResponse cddbResponse = get();

            if (cddbResponse.getErrorMessage() != null) {
                final String message = cddbResponse.getErrorMessage();
                JOptionPane.showMessageDialog(JRipperSwing.getFrame(), message, "Warning", JOptionPane.WARNING_MESSAGE);

                return;
            }

            final Album album = cddbResponse.getAlbum();

            albumConsumer.accept(album);
        } catch (final InterruptedException ex) {
            JRipperSwing.LOGGER.error(ex.getMessage(), ex);

            // Restore interrupted state.
            Thread.currentThread().interrupt();
        } catch (final Exception ex) {
            JRipperSwing.LOGGER.error(ex.getMessage(), ex);
        }
    }
}
