/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core.util;

import java.io.File;

import org.apache.commons.lang3.SystemUtils;

/**
 * Versuch in Abhängigkeit des Betriebssystems das CD/DVD Laufwerk zu finden.
 * 
 * @author Thomas Freese
 */
public final class CDDetector
{
	/**
	 * Liefert das erste CD/DVD-Laufwerk.
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
	 * Erstellt ein neues {@link CDDetector} Object.
	 */
	private CDDetector()
	{
		super();
	}
}
