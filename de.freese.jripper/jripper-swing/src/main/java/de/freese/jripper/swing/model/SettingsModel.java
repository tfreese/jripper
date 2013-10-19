/**
 * Created: 16.10.2013
 */

package de.freese.jripper.swing.model;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.SelectionInList;
import de.freese.jripper.core.Settings;

/**
 * {@link PresentationModel} der {@link Settings}.
 * 
 * @author Thomas Freese
 */
public class SettingsModel extends PresentationModel<SettingsBean>
{
	/**
	 *
	 */
	private static final long serialVersionUID = -7242694957205982571L;

	/**
	 * 
	 */
	private final SelectionInList<Integer> selectionInListMp3Bitrate;

	/**
	 * Erstellt ein neues {@link SettingsModel} Object.
	 * 
	 * @param settings {@link Settings}
	 */
	public SettingsModel(final Settings settings)
	{
		super(new SettingsBean(settings));

		this.selectionInListMp3Bitrate = new SelectionInList<>(getBean().getListModelMp3Bitrate(), getModel(SettingsBean.PROPERTY_MP3_BITRATE));
	}

	/**
	 * @return {@link SelectionInList}<Integer>
	 */
	public SelectionInList<Integer> getSelectionInListMp3Bitrate()
	{
		return this.selectionInListMp3Bitrate;
	}
}
