/**
 * Created: 01.03.2013
 */

package de.freese.jripper.core.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Informationen für einen Titel einer CD.
 * 
 * @author Thomas Freese
 */
public class Track
{
	/**
	 * 
	 */
	private Album album = null;

	/**
	 * Nur bei Compilations gefüllt.
	 */
	private String artist = null;

	/**
	 * 
	 */
	private int number = 0;

	/**
	 * 
	 */
	private String title = null;

	/**
	 * Erstellt ein neues {@link Track} Object.
	 */
	Track()
	{
		super();
	}

	/**
	 * @return String
	 */
	public String getArtist()
	{
		// Nur bei Compilations gefüllt.
		if (StringUtils.isBlank(this.artist))
		{
			return this.album.getArtist();
		}

		return this.artist;
	}

	/**
	 * @return int
	 */
	public int getNumber()
	{
		return this.number;
	}

	/**
	 * @return String
	 */
	public String getTitle()
	{
		return this.title;
	}

	/**
	 * @param album {@link Album}
	 */
	void setAlbum(final Album album)
	{
		this.album = album;
	}

	/**
	 * @param artist String
	 */
	void setArtist(final String artist)
	{
		this.artist = artist;
	}

	/**
	 * @param number int
	 */
	void setNumber(final int number)
	{
		this.number = number;
	}

	/**
	 * @param title String
	 */
	void setTitle(final String title)
	{
		this.title = title;
	}
}
