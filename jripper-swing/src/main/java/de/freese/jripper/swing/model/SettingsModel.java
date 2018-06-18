/**
 * Created: 16.10.2013
 */

package de.freese.jripper.swing.model;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.common.collect.ArrayListModel;
import com.jgoodies.common.collect.ObservableList;
import de.freese.jripper.core.Settings;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.ListModel;

/**
 * {@link PresentationModel} von {@link SettingsBean}.
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
	private final ObservableList<Integer> listModelMp3Bitrate;

	/**
	 * 
	 */
	private final SelectionInList<Integer> selectionInListMp3Bitrate;

	/**
	 * Erstellt ein neues {@link SettingsModel} Object.
	 * 
	 * @param settings {@link Settings}
	 */
	@SuppressWarnings("unchecked")
	public SettingsModel(final Settings settings)
	{
		super(new SettingsBean(settings));

		this.listModelMp3Bitrate = new ArrayListModel<>();
		this.listModelMp3Bitrate.addAll(Arrays.asList(32, 40, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320));
		Collections.sort(this.listModelMp3Bitrate, Collections.reverseOrder());

		this.selectionInListMp3Bitrate = new SelectionInList<>((ListModel<Integer>) this.listModelMp3Bitrate, getModel(SettingsBean.PROPERTY_MP3_BITRATE));
	}

	/**
	 * @return {@link SelectionInList}<Integer>
	 */
	public SelectionInList<Integer> getSelectionInListMp3Bitrate()
	{
		return this.selectionInListMp3Bitrate;
	}
}
