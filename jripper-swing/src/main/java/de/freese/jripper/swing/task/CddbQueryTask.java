/**
 * Created: 10.10.2013
 */

package de.freese.jripper.swing.task;

import java.util.Objects;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import de.freese.binding.property.Property;
import de.freese.jripper.core.JRipper;
import de.freese.jripper.core.Settings;
import de.freese.jripper.core.cddb.CddbProvider;
import de.freese.jripper.core.cddb.CddbResponse;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.model.DiskID;
import de.freese.jripper.swing.JRipperSwing;

/**
 * {@link SwingWorker} für die CDDB Query.
 *
 * @author Thomas Freese
 */
public class CddbQueryTask extends SwingWorker<CddbResponse, Void>
{
    /**
     *
     */
    private final Property<Album> albumProperty;

    /**
     * Erstellt ein neues {@link CddbQueryTask} Object.
     *
     * @param albumProperty {@link Property}
     */
    public CddbQueryTask(final Property<Album> albumProperty)
    {
        super();

        this.albumProperty = Objects.requireNonNull(albumProperty, "albumProperty required");
    }

    /**
     * @see javax.swing.SwingWorker#doInBackground()
     */
    @Override
    protected CddbResponse doInBackground() throws Exception
    {
        String device = Settings.getInstance().getDevice();
        DiskID diskID = JRipper.getInstance().getDiskIDProvider().getDiskID(device);

        CddbProvider cddbService = JRipper.getInstance().getCddbProvider();
        CddbResponse cddbResponse = cddbService.queryGenres(diskID);

        if (cddbResponse.getErrorMessage() != null)
        {
            // Fehler -> Abbruch
            return cddbResponse;
        }

        cddbResponse = cddbService.queryAlbum(diskID, cddbResponse.getGenres().get(0));

        return cddbResponse;
    }

    /**
     * @see javax.swing.SwingWorker#done()
     */
    @Override
    protected void done()
    {
        try
        {
            CddbResponse cddbResponse = get();

            if (cddbResponse.getErrorMessage() != null)
            {
                String message = cddbResponse.getErrorMessage();
                JOptionPane.showMessageDialog(JRipperSwing.frame, message, "Warning", JOptionPane.WARNING_MESSAGE);

                return;
            }

            Album album = cddbResponse.getAlbum();

            this.albumProperty.setValue(album);
        }
        catch (Exception ex)
        {
            JRipperSwing.LOGGER.error(null, ex);
        }
    }
}
