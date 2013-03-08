/**
 * Created: 02.03.2013
 */

package de.freese.jripper.core;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.freese.jripper.core.model.Album;

/**
 * Util-Klasse.
 * 
 * @author Thomas Freese
 */
public final class JRipperUtils
{
	/**
	 * 
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger("JRipper");

	/**
	 * Erzeugt, wenn nicht vorhanden, das Verzeichnis für die Dateien.<br>
	 * Format: ${user.dir}/ALBUMTITEL/PFAD<br>
	 * Vorhandenen Daten werden gelöscht.
	 * 
	 * @param album {@link Album}
	 * @param directory {@link File}
	 * @throws IOException Falls was schief geht.
	 */
	private static void createOrDeleteDir(final Album album, final File directory) throws IOException
	{
		if (directory.exists())
		{
			FileUtils.cleanDirectory(directory);
		}
		else
		{
			directory.mkdirs();
		}
	}

	/**
	 * Versucht in Abhängigkeit des Betriebssystems das CD/DVD Laufwerk zu finden und liefert das erste CD/DVD-Laufwerk.
	 * 
	 * @return String
	 */
	public static String detectCDDVD()
	{
		String drive = null;

		if (SystemUtils.IS_OS_LINUX)
		{
			File f = new File("/dev/dvd");

			if (f.exists() && f.canRead())
			{
				drive = f.getAbsolutePath();
			}

			if (drive == null)
			{
				f = new File("/dev/cdrom");

				if (f.exists() && f.canRead())
				{
					drive = f.getAbsolutePath();
				}
			}
		}
		else if (SystemUtils.IS_OS_WINDOWS)
		{
			// FileSystemView fsv = FileSystemView.getFileSystemView();
			//
			// for (File root : File.listRoots())
			// {
			// if ("CD Drive".equals(fsv.getSystemTypeDescription(root)))
			// {
			// File cd = root;
			//
			// String description = " is empty";
			//
			// if (cd.exists())
			// {
			// description = " contains CD '" + fsv.getSystemDisplayName(cd) + "'";
			// }
			//
			// System.out.println("Drive " + cd + description);
			// }
			// }
			//
			// FileSystem fs = FileSystems.getDefault();
			//
			// for (Path rootPath : fs.getRootDirectories())
			// {
			// try
			// {
			// FileStore store = Files.getFileStore(rootPath);
			// System.out.println(rootPath + ": " + store.type());
			// }
			// catch (IOException ex)
			// {
			// System.out.println(rootPath + ": " + "<error getting store details>");
			// }
			// }
		}

		if (drive == null)
		{
			throw new NullPointerException("no cd/dvd drive found");
		}

		return drive;
	}

	/**
	 * Erzeugt, wenn nicht vorhanden, das Verzeichnis für die Dateien.<br>
	 * Format: ${user.dir}/ALBUMTITEL/PFAD
	 * 
	 * @param album {@link Album}
	 * @param subDir String
	 * @return {@link File}
	 * @throws IOException Falls was schief geht.
	 */
	private static File getDir(final Album album, final String subDir) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		// sb.append(System.getProperty("user.dir"));
		sb.append(System.getProperty("java.io.tmpdir"));

		if (!isDevelopment())
		{
			sb.append(File.separator).append(album.getTitle());
		}

		sb.append(File.separator).append(subDir);

		File dir = new File(sb.toString());

		return dir;
	}

	/**
	 * Liefert das Verzeichnis für die flac-Dateien.<br>
	 * Format: ${user.dir}/ALBUMTITEL/flac
	 * 
	 * @param album {@link Album}
	 * @param createOrDelete boolean; Erzeugt das Verzeichnis oder löscht vorhandene Dateien.
	 * @return {@link File}
	 * @throws IOException Falls was schief geht.
	 */
	public static File getFlacDir(final Album album, final boolean createOrDelete) throws IOException
	{
		File dir = getDir(album, "flac");

		if (createOrDelete)
		{
			createOrDeleteDir(album, dir);
		}

		return dir;
	}

	/**
	 * Liefert das Verzeichnis für die mp3-Dateien.<br>
	 * Format: ${user.dir}/ALBUMTITEL/mp3
	 * 
	 * @param album {@link Album}
	 * @param createOrDelete boolean; Erzeugt das Verzeichnis oder löscht vorhandene Dateien.
	 * @return {@link File}
	 * @throws IOException Falls was schief geht.
	 */
	public static File getMP3Dir(final Album album, final boolean createOrDelete) throws IOException
	{
		File dir = getDir(album, "mp3");

		if (createOrDelete)
		{
			createOrDeleteDir(album, dir);
		}

		return dir;
	}

	/**
	 * Liefert das Verzeichnis für die wav-Dateien.<br>
	 * Format: ${user.dir}/ALBUMTITEL/wav
	 * 
	 * @param album {@link Album}
	 * @param createOrDelete boolean; Erzeugt das Verzeichnis oder löscht vorhandene Dateien.
	 * @return {@link File}
	 * @throws IOException Falls was schief geht.
	 */
	public static File getWavDir(final Album album, final boolean createOrDelete) throws IOException
	{
		File dir = getDir(album, "wav");

		if (createOrDelete)
		{
			createOrDeleteDir(album, dir);
		}

		return dir;
	}

	/**
	 * Liefert true, wenn JRipper in der IDE ausgeführt wird.
	 * 
	 * @return boolean
	 */
	public static boolean isDevelopment()
	{
		return System.getProperty("dev.env") == null ? false : true;
	}

	/**
	 * Erstellt ein neues {@link JRipperUtils} Object.
	 */
	private JRipperUtils()
	{
		super();
	}
}
