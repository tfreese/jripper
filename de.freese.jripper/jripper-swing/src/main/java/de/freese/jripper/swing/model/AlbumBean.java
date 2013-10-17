/**
 * Created: 17.10.2013
 */

package de.freese.jripper.swing.model;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.common.bean.Bean;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.model.Track;
import de.freese.jripper.swing.JRipperSwing;
import org.slf4j.Logger;

/**
 * {@link Bean} für das {@link Album}.
 * 
 * @author Thomas Freese
 */
public class AlbumBean extends Model// Bean
{
	/**
	 * 
	 */
	public static final String PROPERTY_ARTIST = "artist";

	/**
	 * 
	 */
	public static final String PROPERTY_COMMENT = "comment";

	/**
	 * 
	 */
	public static final String PROPERTY_TITLE = "title";

	/**
	 * 
	 */
	public static final String PROPERTY_YEAR = "year";

	/**
	 *
	 */
	private static final long serialVersionUID = 9196811641238029075L;

	/**
	 * 
	 */
	private final Album album;

	/**
	 * 
	 */
	private final Logger logger = JRipperSwing.LOGGER;

	/**
	 * Erstellt ein neues {@link AlbumBean} Object.
	 * 
	 * @param album {@link Album}
	 */
	public AlbumBean(final Album album)
	{
		super();

		this.album = album;
	}

	/**
	 * @return {@link Album}
	 */
	public Album getAlbum()
	{
		return this.album;
	}

	/**
	 * Bei Compilations null.
	 * 
	 * @return String
	 */
	public String getArtist()
	{
		return this.album.getArtist();
	}

	/**
	 * @return String
	 */
	public String getComment()
	{
		return this.album.getComment();
	}

	/**
	 * @return String
	 */
	public String getTitle()
	{
		return this.album.getTitle();
	}

	/**
	 * @return int
	 */
	public int getYear()
	{
		return this.album.getYear();
	}

	/**
	 * Bei Compilations null.
	 * 
	 * @param artist String
	 */
	public void setArtist(final String artist)
	{
		Object oldValue = getArtist();
		this.album.setArtist(artist);

		if (this.logger.isDebugEnabled())
		{
			this.logger.debug("oldValue={}, newValue={}", oldValue, artist);
		}

		// Alle Tracks aktualisieren.
		for (Track track : this.album)
		{
			track.setArtist(artist);
		}

		firePropertyChange(PROPERTY_ARTIST, oldValue, artist);
	}

	/**
	 * @param comment String
	 */
	public void setComment(final String comment)
	{
		Object oldValue = getComment();
		this.album.setComment(comment);

		if (this.logger.isDebugEnabled())
		{
			this.logger.debug("oldValue={}, newValue={}", oldValue, comment);
		}

		firePropertyChange(PROPERTY_COMMENT, oldValue, comment);
	}

	/**
	 * @param title String
	 */
	public void setTitle(final String title)
	{
		Object oldValue = getTitle();
		this.album.setTitle(title);

		if (this.logger.isDebugEnabled())
		{
			this.logger.debug("oldValue={}, newValue={}", oldValue, title);
		}

		firePropertyChange(PROPERTY_TITLE, oldValue, title);
	}

	/**
	 * @param year int
	 */
	public void setYear(final int year)
	{
		Object oldValue = getYear();
		this.album.setYear(year);

		if (this.logger.isDebugEnabled())
		{
			this.logger.debug("oldValue={}, newValue={}", oldValue, year);
		}

		firePropertyChange(PROPERTY_YEAR, oldValue, year);
	}
}
