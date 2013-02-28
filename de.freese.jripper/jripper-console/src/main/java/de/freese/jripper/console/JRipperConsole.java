/**
 * Created: 26.02.2013
 */

package de.freese.jripper.console;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import de.freese.jripper.core.diskid.DiskID;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.util.CDDetector;

/**
 * Console-View für den JRipper.
 * 
 * @author Thomas Freese
 */
public class JRipperConsole implements IAnsiCodes
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
	private Album album = null;

	/**
	 * 
	 */
	private final boolean isDevelopment;

	/**
	 * 
	 */
	private final PrintStream printStream;

	/**
	 * 
	 */
	private final BufferedReader reader;

	/**
	 * Erstellt ein neues {@link JRipperConsole} Object.
	 */
	public JRipperConsole()
	{
		super();

		this.printStream = System.out;

		Console console = System.console();
		Reader reader = null;

		if (console != null)
		{
			reader = console.reader();
		}
		else
		{
			// In Eclipse kann Console null sein.
			reader = new InputStreamReader(System.in);
		}

		if (!(reader instanceof BufferedReader))
		{
			reader = new BufferedReader(reader);
		}

		this.reader = (BufferedReader) reader;

		this.isDevelopment = System.getProperty("dev.env") == null ? false : true;
	}

	/**
	 * Liefert dein eingegebenen Wert von der Konsole.
	 * 
	 * @return String
	 * @throws Exception Falls was schief geht.
	 */
	private String getInput() throws Exception
	{
		print("%s%s%s: ", ANSI_GREEN, "Eingabe", ANSI_RESET);

		String line = this.reader.readLine();

		return line;
	}

	/**
	 * @param format String
	 * @param params Object[]
	 * @see String#format(String, Object...)
	 */
	private void print(final String format, final Object...params)
	{
		if (this.isDevelopment)
		{
			// In der Entwicklungsumgebung die ANSI-Codes entfernen.
			for (int i = 0; i < params.length; i++)
			{
				switch (params[i].toString())
				{
					case ANSI_CYAN:
					case ANSI_GREEN:
					case ANSI_RED:
					case ANSI_RESET:
						params[i] = "";
						break;

					default:
						break;
				}
			}
		}

		this.printStream.format(format, params);
	}

	/**
	 * http://freedb.freedb.org/~cddb/cddb.cgi?cmd=cddb+query+7c0b8b0b+11+150+23115+
	 * 42165+60015+79512+101560+118757+136605+159492+176067+198875+2957&hello=user+
	 * hostname+program+version&proto=3(6)
	 * 
	 * @return String
	 * @throws Exception Falls was schief geht.
	 */
	private String queryFreeDB() throws Exception
	{
		String device = CDDetector.detectCDDVD();
		String diskID = DiskID.getService().getDiskID(device);

		this.album = new Album();
		this.album.setDiskID(diskID);

		StringBuilder sb = new StringBuilder();
		sb.append("/~cddb/cddb.cgi?cmd=cddb+query");

		String[] splits = this.album.getDiskID().split("[ ]");

		for (String split : splits)
		{
			sb.append("+").append(split);
		}

		sb.append("&hello=anonymous+localhost+jRipper+0.0.1-SNAPSHOP&proto=6");

		URL url = new URL("http", "freedb.freedb.org", 80, sb.toString());
		URLConnection connection = url.openConnection();
		String genre = null;

		try (BufferedReader reader =
				new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8")))
		{
			String line = null;
			int i = 0;

			while ((line = reader.readLine()) != null)
			{
				System.out.println(line);

				if (i == 1)
				{
					splits = line.split("[ ]");
					genre = StringUtils.trim(splits[0]);
				}

				i++;
			}

			print("%s: %s\n", "Genre", genre);
		}

		return genre;
	}

	/**
	 * @param genre String
	 * @throws Exception Falls was schief geht.
	 */
	private void readFreeDB(final String genre) throws Exception
	{
		StringBuilder sb = new StringBuilder();
		sb.append("/~cddb/cddb.cgi?cmd=cddb+read");
		sb.append("+").append(genre);

		String[] splits = this.album.getDiskID().split("[ ]");
		sb.append("+").append(splits[0]);

		sb.append("&hello=anonymous+localhost+jRipper+0.0.1-SNAPSHOP&proto=6");

		URL url = new URL("http", "freedb.freedb.org", 80, sb.toString());
		URLConnection connection = url.openConnection();

		try (BufferedReader reader =
				new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8")))
		{
			String line = null;

			while ((line = reader.readLine()) != null)
			{
				System.out.println(line);

				if (line.startsWith("DTITLE"))
				{
					splits = line.split("[=]");
					splits = splits[1].split("[/]");
					this.album.setArtist(WordUtils.capitalize(StringUtils.trim(splits[0])));
					this.album.setTitle(WordUtils.capitalize(StringUtils.trim(splits[1])));
				}
				else if (line.startsWith("DYEAR"))
				{
					splits = line.split("[=]");
					this.album.setYear(Integer.parseInt(StringUtils.trim(splits[1])));
				}
				else if (line.startsWith("DGENRE"))
				{
					// "Richtiges" Genre auslesen.
					splits = line.split("[=]");
					this.album.setGenre(WordUtils.capitalize(StringUtils.trim(splits[1])));
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

					// TODO Add Track
				}
				else if (line.startsWith("EXTD"))
				{
					splits = line.split("[=]");
					this.album.setComment(WordUtils.capitalize(StringUtils.trim(splits[1])));
				}
			}
		}
	}

	/**
	 * 
	 */
	public void showMainMenu()
	{
		print("%s\n", "*****************");
		print("%s\n", "JRipper Hauptmenü");
		print("%s\n", "*****************");

		print("%s%s%s \t%s\n", ANSI_CYAN, "1", ANSI_RESET, "FreeDB auslesen");
		print("%s%s%s \t%s\n", ANSI_CYAN, "q", ANSI_RESET, "Beenden");

		try
		{
			String input = getInput();

			switch (input)
			{
				case "1":
					String genre = queryFreeDB();
					readFreeDB(genre);
					break;

				case "q":
					return;

				default:
					print("%s%s \t%s%s\n", ANSI_RED, input, "Unbekannte Eingabe", ANSI_RESET);
					break;
			}
		}
		catch (Exception ex)
		{
			print("%s%s%s", ANSI_RED, ex.getMessage(), ANSI_RESET);
		}

		showMainMenu();
	}
}
