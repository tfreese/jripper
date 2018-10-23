/**
 * Created: 16.10.2013
 */

package de.freese.jripper.swing.model;

import com.jgoodies.binding.PresentationModel;
import de.freese.jripper.core.Settings;

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
     * Erstellt ein neues {@link SettingsModel} Object.
     *
     * @param settings {@link Settings}
     */
    public SettingsModel(final Settings settings)
    {
        super(new SettingsBean(settings));
    }
}
