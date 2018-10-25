/**
 * Created: 10.10.2013
 */

package de.freese.jripper.swing.task;

import java.util.List;
import java.util.Objects;
import javax.swing.SwingWorker;
import de.freese.binding.property.Property;
import de.freese.jripper.core.JRipper;
import de.freese.jripper.core.Settings;
import de.freese.jripper.core.cddb.CDDBProvider;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.model.DiskID;
import de.freese.jripper.swing.JRipperSwing;

/**
 * {@link SwingWorker} für die CDDB Query.
 *
 * @author Thomas Freese
 */
public class CDDBTask extends SwingWorker<Album, Void>
{
    /**
     *
     */
    private final Property<Album> albumProperty;

    /**
     * Erstellt ein neues {@link CDDBTask} Object.
     *
     * @param albumProperty {@link Property}
     */
    public CDDBTask(final Property<Album> albumProperty)
    {
        super();

        this.albumProperty = Objects.requireNonNull(albumProperty, "albumProperty required");
    }

    /**
     * @see javax.swing.SwingWorker#doInBackground()
     */
    @Override
    protected Album doInBackground() throws Exception
    {
        String device = Settings.getInstance().getDevice();
        DiskID diskID = JRipper.getInstance().getDiskIDProvider().getDiskID(device);

        CDDBProvider cddbService = JRipper.getInstance().getCDDBProvider();
        List<String> genres = cddbService.queryGenres(diskID);
        Album album = cddbService.queryAlbum(diskID, genres.get(0));

        return album;
    }

    /**
     * @see javax.swing.SwingWorker#done()
     */
    @Override
    protected void done()
    {
        try
        {
            Album album = get();

            this.albumProperty.setValue(album);
        }
        catch (Exception ex)
        {
            JRipperSwing.LOGGER.error(null, ex);
        }
    }
}
