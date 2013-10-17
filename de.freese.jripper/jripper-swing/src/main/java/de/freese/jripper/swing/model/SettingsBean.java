/**
 * Created: 16.10.2013
 */

package de.freese.jripper.swing.model;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.common.bean.Bean;
import com.jgoodies.common.collect.ArrayListModel;
import com.jgoodies.common.collect.ObservableList;
import de.freese.jripper.core.Settings;
import de.freese.jripper.swing.JRipperSwing;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.ListModel;
import org.slf4j.Logger;

/**
 * {@link Bean} für die {@link Settings}.
 * 
 * @author Thomas Freese
 */
public class SettingsBean extends Model// Bean
{
	/**
	 * 
	 */
	public static final String PROPERTY_DEVICE = "device";

	/**
	 * 
	 */
	public static final String PROPERTY_FPS = "framesPerSecond";

	/**
	 * 
	 */
	public static final String PROPERTY_MP3BITRATE = "mp3Bitrate";

	/**
	 * 
	 */
	public static final String PROPERTY_WORKDIR = "workDir";

	/**
	 *
	 */
	private static final long serialVersionUID = -6983595958713155054L;

	/**
	 * 
	 */
	private ObservableList<Integer> listModelMp3Bitrate = null;

	/**
	 * 
	 */
	private final Logger logger = JRipperSwing.LOGGER;

	/**
	 * 
	 */
	private final Settings settings;

	/**
	 * Erstellt ein neues {@link SettingsBean} Object.
	 * 
	 * @param settings {@link Settings}
	 */
	SettingsBean(final Settings settings)
	{
		super();

		this.settings = settings;

		this.listModelMp3Bitrate = new ArrayListModel<>();
		this.listModelMp3Bitrate.addAll(Arrays.asList(32, 40, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320));
		Collections.sort(this.listModelMp3Bitrate, Collections.reverseOrder());
	}

	/**
	 * @return String
	 */
	public String getDevice()
	{
		return this.settings.getDevice();
	}

	/**
	 * @return int
	 */
	public int getFramesPerSecond()
	{
		return this.settings.getFramesPerSecond();
	}

	/**
	 * @return {@link ObservableList}<Integer>
	 */
	@SuppressWarnings("unchecked")
	public ListModel<Integer> getListModelMp3Bitrate()
	{
		return this.listModelMp3Bitrate;
	}

	/**
	 * @return int
	 */
	public int getMp3Bitrate()
	{
		return this.settings.getMp3Bitrate();
	}

	/**
	 * Liefert das Arbeitsverzeichnis.
	 * 
	 * @return String
	 */
	public String getWorkDir()
	{
		return this.settings.getWorkDir();
	}

	/**
	 * @param device String
	 */
	public void setDevice(final String device)
	{
		Object oldValue = getDevice();
		this.settings.setDevice(device);

		if (this.logger.isDebugEnabled())
		{
			this.logger.debug("oldValue={}, newValue={}", oldValue, device);
		}

		firePropertyChange(PROPERTY_DEVICE, oldValue, device);
	}

	/**
	 * @param framesPerSecond int
	 */
	public void setFramesPerSecond(final int framesPerSecond)
	{
		Object oldValue = getFramesPerSecond();
		this.settings.setFramesPerSecond(framesPerSecond);

		if (this.logger.isDebugEnabled())
		{
			this.logger.debug("oldValue={}, newValue={}", oldValue, framesPerSecond);
		}

		firePropertyChange(PROPERTY_FPS, oldValue, framesPerSecond);
	}

	/**
	 * @param mp3Bitrate int
	 */
	public void setMp3Bitrate(final int mp3Bitrate)
	{
		Object oldValue = getMp3Bitrate();
		this.settings.setMp3Bitrate(mp3Bitrate);

		if (this.logger.isDebugEnabled())
		{
			this.logger.debug("oldValue={}, newValue={}", oldValue, mp3Bitrate);
		}

		firePropertyChange(PROPERTY_MP3BITRATE, oldValue, mp3Bitrate);
	}

	/**
	 * Setzt das Arbeitsverzeichnis.
	 * 
	 * @param workDir String
	 */
	public void setWorkDir(final String workDir)
	{
		Object oldValue = getWorkDir();
		this.settings.setWorkDir(workDir);

		if (this.logger.isDebugEnabled())
		{
			this.logger.debug("oldValue={}, newValue={}", oldValue, workDir);
		}

		firePropertyChange(PROPERTY_WORKDIR, oldValue, workDir);
	}
}
