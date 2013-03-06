/**
 * Created: 02.03.2013
 */

package de.freese.jripper.core.cddb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import de.freese.jripper.core.JRipperUtils;
import de.freese.jripper.core.model.Album;

/**
 * CDDB Provider für FreeDB.
 * 
 * @author Thomas Freese
 */
public class FreeDB implements ICDDBProvider
{
	// /**
	// *
	// */
	// private static final Pattern RECORD_DGENRE = Pattern.compile("DGENRE=(.*)");
	//
	// /**
	// *
	// */
	// private static final Pattern RECORD_DTITLE = Pattern.compile("DTITLE=(.*)");
	//
	// /**
	// *
	// */
	// private static final Pattern RECORD_DYEAR = Pattern.compile("DYEAR=(.*)");
	//
	// /**
	// *
	// */
	// private static final Pattern RECORD_EXTD = Pattern.compile("EXTD=(.*)");
	//
	// /**
	// *
	// */
	// private static final Pattern RECORD_TTITLE = Pattern.compile("TTITLE(\\d+)=(.*)");

	/**
	 * 
	 */
	private static final String SERVER = "freedb.freedb.org";

	/**
	 * 
	 */
	private final String DEFAULT_REQUEST;

	/**
	 * Erstellt ein neues {@link FreeDB} Object.
	 */
	public FreeDB()
	{
		super();

		StringBuilder sb = new StringBuilder();
		sb.append("&hello=").append(USER);
		sb.append("+").append(HOST);
		sb.append("+jRipper+1.0.0&proto=6");
		this.DEFAULT_REQUEST = sb.toString();
	}

	/**
	 * Beispiel Query:<br>
	 * http://freedb.freedb.org/~cddb/cddb.cgi?cmd=cddb+query+7c0b8b0b+11+150+23115+
	 * 42165+60015+79512+101560+118757+136605+159492+176067+198875+2957&hello=user+
	 * hostname+program+version&proto=3(6)
	 * 
	 * @see de.freese.jripper.core.cddb.ICDDBProvider#query(java.lang.String)
	 */
	@Override
	public List<String> query(final String diskID) throws Exception
	{
		StringBuilder sb = new StringBuilder();
		sb.append("/~cddb/cddb.cgi?cmd=cddb+query");

		String[] splits = diskID.split("[ ]");

		for (String split : splits)
		{
			sb.append("+").append(split);
		}

		sb.append(this.DEFAULT_REQUEST);

		URL url = new URL("http", SERVER, PORT, sb.toString());
		URLConnection connection = url.openConnection();
		List<String> genres = new ArrayList<>();

		try (BufferedReader reader =
				new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8")))
		{
			// Erste Zeile ignorieren
			String line = reader.readLine();
			JRipperUtils.LOGGER.debug(line);

			while ((line = reader.readLine()) != null)
			{
				JRipperUtils.LOGGER.debug(line);

				splits = line.split("[ ]");

				if (splits.length < 2)
				{
					continue;
				}

				String genre = StringUtils.trim(splits[0]);
				genres.add(genre);
			}
		}

		return genres;
	}

	/**
	 * @see de.freese.jripper.core.cddb.ICDDBProvider#read(java.lang.String, java.lang.String)
	 */
	@Override
	public Album read(final String diskID, final String genre) throws Exception
	{
		StringBuilder sb = new StringBuilder();
		sb.append("/~cddb/cddb.cgi?cmd=cddb+read");
		sb.append("+").append(genre);

		String[] splits = diskID.split("[ ]");
		sb.append("+").append(splits[0]);

		sb.append(this.DEFAULT_REQUEST);

		URL url = new URL("http", SERVER, PORT, sb.toString());
		URLConnection connection = url.openConnection();

		Album album = new Album();
		album.setDiskID(diskID);

		try (BufferedReader reader =
				new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8")))
		{
			String line = null;

			while ((line = reader.readLine()) != null)
			{
				JRipperUtils.LOGGER.debug(line);

				if (line.startsWith("DTITLE"))
				{
					splits = line.split("[=]");
					splits = splits[1].split("[/]");
					album.setArtist(WordUtils.capitalize(StringUtils.trim(splits[0])));
					album.setTitle(WordUtils.capitalize(StringUtils.trim(splits[1])));
				}
				else if (line.startsWith("DYEAR"))
				{
					splits = line.split("[=]");
					album.setYear(Integer.parseInt(StringUtils.trim(splits[1])));
				}
				else if (line.startsWith("DGENRE"))
				{
					// "Richtiges" Genre auslesen.
					splits = line.split("[=]");
					album.setGenre(WordUtils.capitalize(StringUtils.trim(splits[1])));
				}
				else if (line.startsWith("TTITLE"))
				{
					splits = line.split("[=]");

					String trackArtist = null;
					String trackTitle = null;

					if (splits[1].contains("/"))
					{
						// Annahme Compilation.
						splits = splits[1].split("[/]");
						trackArtist = WordUtils.capitalize(StringUtils.trim(splits[0]));
						trackTitle = WordUtils.capitalize(StringUtils.trim(splits[1]));
					}
					else
					{
						// Annahme Album.
						splits = splits[1].split("[ ]");

						if (StringUtils.isNumeric(splits[0]))
						{
							splits[0] = "";
						}

						// splits[1].replaceAll("[\\d+]", "");
						trackTitle =
								WordUtils
										.capitalize(StringUtils.trim(StringUtils.join(splits, " ")));
					}

					album.addTrack(trackArtist, trackTitle);
				}
				else if (line.startsWith("EXTD"))
				{
					splits = line.split("[=]");

					if (splits.length == 1)
					{
						// Kein Kommentar.
						continue;
					}

					album.setComment(WordUtils.capitalize(StringUtils.trim(splits[1])));
				}
			}
		}

		return album;
	}
}