/**
 * Created: 26.02.2013
 */

package de.freese.jripper.core.model;

/**
 * Zusammenfassendes Objekt für eine CD.
 * 
 * @author Thomas Freese
 */
public class Album
{
	/**
	 * Bei Compilations null.
	 */
	private String artist = null;

	/**
	 * 
	 */
	private String comment = null;

	/**
	 * 
	 */
	private String diskID = null;

	/**
	 * 
	 */
	private String genre = null;

	/**
	 * 
	 */
	private String title = null;

	/**
	 * 
	 */
	private int year = 0;

	/**
	 * Erstellt ein neues {@link Album} Object.
	 */
	public Album()
	{
		super();
	}

	/**
	 * Bei Compilations null.
	 * 
	 * @return String
	 */
	public String getArtist()
	{
		return this.artist;
	}

	/**
	 * @return String
	 */
	public String getComment()
	{
		return this.comment;
	}

	/**
	 * @return String
	 */
	public String getDiskID()
	{
		return this.diskID;
	}

	/**
	 * @return String
	 */
	public String getGenre()
	{
		return this.genre;
	}

	/**
	 * @return String
	 */
	public String getTitle()
	{
		return this.title;
	}

	/**
	 * @return int
	 */
	public int getYear()
	{
		return this.year;
	}

	/**
	 * Bei Compilations null.
	 * 
	 * @param artist String
	 */
	public void setArtist(final String artist)
	{
		this.artist = artist;
	}

	/**
	 * @param comment String
	 */
	public void setComment(final String comment)
	{
		this.comment = comment;
	}

	/**
	 * @param diskID String
	 */
	public void setDiskID(final String diskID)
	{
		this.diskID = diskID;
	}

	/**
	 * @param genre String
	 */
	public void setGenre(final String genre)
	{
		this.genre = genre;
	}

	/**
	 * @param title String
	 */
	public void setTitle(final String title)
	{
		this.title = title;
	}

	/**
	 * @param year int
	 */
	public void setYear(final int year)
	{
		this.year = year;
	}
}
