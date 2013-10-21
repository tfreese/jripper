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
public class Album implements IAlbum
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
	 * @see de.freese.jripper.core.model.IAlbum#getArtist()
	 */
	@Override
	public String getArtist()
	{
		return this.artist;
	}

	/**
	 * @see de.freese.jripper.core.model.IAlbum#getComment()
	 */
	@Override
	public String getComment()
	{
		return StringUtils.defaultIfBlank(this.comment, "");
	}

	/**
	 * @see de.freese.jripper.core.model.IAlbum#getDiskID()
	 */
	@Override
	public DiskID getDiskID()
	{
		return this.diskID;
	}

	/**
	 * @see de.freese.jripper.core.model.IAlbum#getDiskNumber()
	 */
	@Override
	public int getDiskNumber()
	{
		return this.diskNumber;
	}

	/**
	 * @see de.freese.jripper.core.model.IAlbum#getGenre()
	 */
	@Override
	public String getGenre()
	{
		return this.genre;
	}

	/**
	 * @see de.freese.jripper.core.model.IAlbum#getTitle()
	 */
	@Override
	public String getTitle()
	{
		return this.title;
	}

	/**
	 * @see de.freese.jripper.core.model.IAlbum#getTotalDisks()
	 */
	@Override
	public int getTotalDisks()
	{
		return this.totalDisks;
	}

	/**
	 * @see de.freese.jripper.core.model.IAlbum#getTrack(int)
	 */
	@Override
	public Track getTrack(final int index)
	{
		return this.tracks.get(index);
	}

	/**
	 * @see de.freese.jripper.core.model.IAlbum#getTrackCount()
	 */
	@Override
	public int getTrackCount()
	{
		return this.tracks.size();
	}

	/**
	 * @see de.freese.jripper.core.model.IAlbum#getYear()
	 */
	@Override
	public int getYear()
	{
		return this.year;
	}

	/**
	 * @see de.freese.jripper.core.model.IAlbum#isCompilation()
	 */
	@Override
	public boolean isCompilation()
	{
		return StringUtils.isBlank(this.artist);
	}

	/**
	 * @see de.freese.jripper.core.model.IAlbum#iterator()
	 */
	@Override
	public Iterator<Track> iterator()
	{
		return this.tracks.iterator();
	}

	/**
	 * @see de.freese.jripper.core.model.IAlbum#setArtist(java.lang.String)
	 */
	@Override
	public void setArtist(final String artist)
	{
		this.artist = artist;
	}

	/**
	 * @see de.freese.jripper.core.model.IAlbum#setComment(java.lang.String)
	 */
	@Override
	public void setComment(final String comment)
	{
		this.comment = comment;
	}

	/**
	 * @see de.freese.jripper.core.model.IAlbum#setDiskNumber(int)
	 */
	@Override
	public void setDiskNumber(final int diskNumber)
	{
		this.diskNumber = diskNumber;
	}

	/**
	 * @see de.freese.jripper.core.model.IAlbum#setGenre(java.lang.String)
	 */
	@Override
	public void setGenre(final String genre)
	{
		this.genre = genre;
	}

	/**
	 * @see de.freese.jripper.core.model.IAlbum#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(final String title)
	{
		this.title = title;
	}

	/**
	 * @see de.freese.jripper.core.model.IAlbum#setTotalDisks(int)
	 */
	@Override
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
	 * @see de.freese.jripper.core.model.IAlbum#setYear(int)
	 */
	@Override
	public void setYear(final int year)
	{
		this.year = year;
	}
}
