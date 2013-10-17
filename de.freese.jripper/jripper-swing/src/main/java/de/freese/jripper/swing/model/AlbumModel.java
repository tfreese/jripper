/**
 * Created: 17.10.2013
 */

package de.freese.jripper.swing.model;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.common.collect.ArrayListModel;
import com.jgoodies.common.collect.ObservableList;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.model.Track;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * {@link PresentationModel} vom {@link Album}.
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
	 * Erstellt ein neues {@link AlbumModel} Object.
	 */
	public AlbumModel()
	{
		super();

		this.listModelTracks = new ArrayListModel<>();

		getModel(AlbumBean.PROPERTY_ARTIST).addValueChangeListener(new PropertyChangeListener()
		{
			/**
			 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
			 */
			@Override
			public void propertyChange(final PropertyChangeEvent evt)
			{
				// Damit geändertert Artist richtig gerendert wird, nich schön die Lösung aber selten ;-)
				fillListModel();
			}
		});
	}

	/**
	 * 
	 */
	private void fillListModel()
	{
		this.listModelTracks.clear();

		for (Track track : getBean().getAlbum())
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
	 * @see com.jgoodies.binding.PresentationModel#setBean(java.lang.Object)
	 */
	@Override
	public void setBean(final AlbumBean newBean)
	{
		super.setBean(newBean);

		fillListModel();
	}
}
