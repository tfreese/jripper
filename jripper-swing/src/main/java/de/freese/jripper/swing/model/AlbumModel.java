/**
 * Created: 17.10.2013
 */

package de.freese.jripper.swing.model;

import java.util.Set;
import javax.swing.ListModel;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.common.collect.ArrayListModel;
import de.freese.jripper.core.JRipper;
import de.freese.jripper.core.genre.GenreProvider;
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
    private final SelectionInList<String> selectionInListGenres;

    /**
     * Erstellt ein neues {@link AlbumModel} Object.
     */
    @SuppressWarnings("unchecked")
    public AlbumModel()
    {
        super();

        // Genres
        SelectionInList<String> selectionInList = null;

        try
        {
            GenreProvider genreProvider = JRipper.getInstance().getGenreProvider();
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
     * @return SelectionInList<String>
     */
    public SelectionInList<String> getSelectionInListGenres()
    {
        return this.selectionInListGenres;
    }
}
