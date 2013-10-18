/**
 * Created: 26.02.2013
 */

package de.freese.jripper.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Zusammenfassendes Objekt für eine CD.
 * 
 * @author Thomas Freese
 */
public class Album implements Iterable<Track>
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
	private final DiskID diskID;

	/**
	 * 
	 */
	private int diskNumber = 1;

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
	private int totalDisks = 1;

	/**
	 * 
	 */
	private List<Track> tracks = new ArrayList<>();

	/**
	 * 
	 */
	private int year = 0;

	/**
	 * Erstellt ein neues {@link Album} Object.
	 * 
	 * @param diskID {@link DiskID}
	 */
	public Album(final DiskID diskID)
	{
		super();

		this.diskID = diskID;
	}

	/**
	 * @param artist String; Nur bei Compilations != null.
	 * @param title String
	 */
	public void addTrack(final String artist, final String title)
	{
		int trackIndex = this.tracks.size();

		Track track = new Track();
		track.setAlbum(this);
		track.setArtist(artist);
		track.setTitle(title);
		track.setNumber(trackIndex + 1);
		track.setSeconds(getDiskID().getTrackSeconds(trackIndex));

		this.tracks.add(track);
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
		return StringUtils.defaultIfBlank(this.comment, "");
	}

	/**
	 * @return {@link DiskID}
	 */
	public DiskID getDiskID()
	{
		return this.diskID;
	}

	/**
	 * @return int
	 */
	public int getDiskNumber()
	{
		return this.diskNumber;
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
	public int getTotalDisks()
	{
		return this.totalDisks;
	}

	/**
	 * Liefert den {@link Track} am Index.
	 * 
	 * @param index int
	 * @return {@link Track}
	 */
	public Track getTrack(final int index)
	{
		return this.tracks.get(index);
	}

	/**
	 * Liefert die Anzahl der Tracks.
	 * 
	 * @return int
	 */
	public int getTrackCount()
	{
		return this.tracks.size();
	}

	/**
	 * @return int
	 */
	public int getYear()
	{
		return this.year;
	}

	/**
	 * Liefert true, wenn das Album eine Compilation ist.
	 * 
	 * @return boolean
	 */
	public boolean isCompilation()
	{
		return StringUtils.isBlank(this.artist);
	}

	/**
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Track> iterator()
	{
		return this.tracks.iterator();
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
	 * @param diskNumber int
	 */
	public void setDiskNumber(final int diskNumber)
	{
		this.diskNumber = diskNumber;
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
	 * @param totalDisks int
	 */
	public void setTotalDisks(final int totalDisks)
	{
		this.totalDisks = totalDisks;
	}

	/**
	 * @param index int, beginnend mit 0
	 * @param artist String
	 */
	public void setTrackArtist(final int index, final String artist)
	{
		Track track = this.tracks.get(index);
		track.setArtist(artist);
	}

	/**
	 * @param index int, beginnend mit 0
	 * @param title String
	 */
	public void setTrackTitle(final int index, final String title)
	{
		Track track = this.tracks.get(index);
		track.setTitle(title);
	}

	/**
	 * @param year int
	 */
	public void setYear(final int year)
	{
		this.year = year;
	}
}
