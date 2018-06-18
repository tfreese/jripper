/**
 * Created: 17.10.2013
 */

package de.freese.jripper.swing.model;

import java.util.Set;
import javax.swing.ListModel;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.common.collect.ArrayListModel;
import com.jgoodies.common.collect.ObservableList;
import de.freese.jripper.core.JRipper;
import de.freese.jripper.core.genre.IGenreProvider;
import de.freese.jripper.core.model.Track;
import de.freese.jripper.swing.JRipperSwing;

/**
 * {@link PresentationModel} von {@link AlbumBean}.
 *
 * @author Thomas Freese
 */
public class AlbumModel extends PresentationModel<AlbumBean>
{
    /**
     *
     */
    private static final long serialVersionUID = 1231784491376321488L;

    /**
     *
     */
    private final ObservableList<Track> listModelTracks;

    /**
     *
     */
    private final SelectionInList<String> selectionInListGenres;

    /**
     * Erstellt ein neues {@link AlbumModel} Object.
     */
    @SuppressWarnings("unchecked")
    public AlbumModel()
    {
        super();

        this.listModelTracks = new ArrayListModel<>();

        // Damit der geänderte Artist richtig gerendert wird, ist nicht schön die Lösung aber dafür selten ;-)
        // @formatter:off
        getModel(AlbumBean.PROPERTY_ARTIST).addValueChangeListener(event ->
            fillListModel()
        );
        // @formatter:on

        // Genres
        SelectionInList<String> selectionInList = null;

        try
        {
            IGenreProvider genreProvider = JRipper.getInstance().getGenreProvider();
            Set<String> genres = genreProvider.getGenres();
            ListModel<String> genreList = new ArrayListModel<>(genres);
            selectionInList = new SelectionInList<>(genreList, getModel(AlbumBean.PROPERTY_GENRE));
        }
        catch (Exception ex)
        {
            selectionInList = new SelectionInList<>();

            JRipperSwing.LOGGER.error(null, ex);
        }

        this.selectionInListGenres = selectionInList;
    }

    /**
     *
     */
    private void fillListModel()
    {
        this.listModelTracks.clear();

        for (Track track : getBean())
        {
            this.listModelTracks.add(track);
        }
    }

    /**
     * @return {@link ObservableList}<Track>
     */
    public ObservableList<Track> getListModelTracks()
    {
        return this.listModelTracks;
    }

    /**
     * @return SelectionInList<String>
     */
    public SelectionInList<String> getSelectionInListGenres()
    {
        return this.selectionInListGenres;
    }

    /**
     * @see com.jgoodies.binding.PresentationModel#setBean(java.lang.Object)
     */
    @Override
    public void setBean(final AlbumBean newBean)
    {
        super.setBean(newBean);

        fillListModel();
    }
}
