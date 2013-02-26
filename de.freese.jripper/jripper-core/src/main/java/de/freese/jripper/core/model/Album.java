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
	 * @param diskID String
	 */
	public void setDiskID(final String diskID)
	{
		this.diskID = diskID;
	}
}
