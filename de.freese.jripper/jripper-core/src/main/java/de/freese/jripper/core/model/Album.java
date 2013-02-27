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
	 * 
	 */
	private String diskID = null;

	/**
	 * 
	 */
	private String genre = null;

	/**
	 * Erstellt ein neues {@link Album} Object.
	 */
	public Album()
	{
		super();
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
}
