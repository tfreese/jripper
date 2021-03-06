/**
 * Created: 02.03.2013
 */

package de.freese.jripper.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import de.freese.jripper.core.model.Album;

/**
 * Util-Klasse.
 *
 * @author Thomas Freese
 */
public final class JRipperUtils
{
    /**
     * Erzeugt, wenn nicht vorhanden, das Verzeichnis für die Dateien.<br>
     * Format: ArbeitsVerzeichnis/ALBUMTITEL/PFAD<br>
     * Vorhandenen Daten werden gelöscht.
     *
     * @param album {@link Album}
     * @param directory {@link File}
     * @throws IOException Falls was schief geht.
     */
    private static void createOrCleanDir(final Album album, final File directory) throws IOException
    {
        if (directory.exists())
        {
            deleteDirectoryRecursiv(directory.toPath());
        }
        else
        {
            directory.mkdirs();
        }
    }

    /**
     * Löscht das Verzeichnis rekursiv inklusive Dateien und Unterverzeichnisse.
     *
     * @param path {@link Path}
     * @throws IOException Falls was schief geht.
     */
    public static void deleteDirectoryRecursiv(final Path path) throws IOException
    {
        if (!Files.exists(path))
        {
            return;
        }

        if (!Files.isDirectory(path))
        {
            throw new IllegalArgumentException("path is not a dirctory: " + path);
        }

        Files.walkFileTree(path, new SimpleFileVisitor<Path>()
        {
            /**
             * @see java.nio.file.SimpleFileVisitor#postVisitDirectory(java.lang.Object, java.io.IOException)
             */
            @Override
            public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException
            {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

            /**
             * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
             */
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException
            {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Versucht in Abhängigkeit des Betriebssystems das CD/DVD Laufwerk zu finden und liefert das erste CD/DVD-Laufwerk.
     *
     * @return String
     */
    static String detectCdDevice()
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

            if (drive == null)
            {
                f = new File("/dev/sr0");

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

        return drive;
    }

    /**
     * Liefert das Verzeichnis für die flac-Dateien.<br>
     * Format: ArbeitsVerzeichnis/ALBUMTITEL/flac
     *
     * @param album {@link Album}
     * @param createOrDelete boolean; Erzeugt das Verzeichnis oder löscht vorhandene Dateien.
     * @return {@link File}
     * @throws IOException Falls was schief geht.
     */
    public static File getFlacDir(final Album album, final boolean createOrDelete) throws IOException
    {
        File dir = new File(getWorkDir(album), "flac");

        if (createOrDelete)
        {
            createOrCleanDir(album, dir);
        }

        return dir;
    }

    /**
     * Liefert das Verzeichnis für die mp3-Dateien.<br>
     * Format: ArbeitsVerzeichnis/ALBUMTITEL/mp3
     *
     * @param album {@link Album}
     * @param createOrDelete boolean; Erzeugt das Verzeichnis oder löscht vorhandene Dateien.
     * @return {@link File}
     * @throws IOException Falls was schief geht.
     */
    public static File getMp3Dir(final Album album, final boolean createOrDelete) throws IOException
    {
        File dir = new File(getWorkDir(album), "mp3");

        if (createOrDelete)
        {
            createOrCleanDir(album, dir);
        }

        return dir;
    }

    /**
     * Liefert das Verzeichnis für die wav-Dateien.<br>
     * Format: ArbeitsVerzeichnis/ALBUMTITEL/wav
     *
     * @param album {@link Album}
     * @param createOrDelete boolean; Erzeugt das Verzeichnis oder löscht vorhandene Dateien.
     * @return {@link File}
     * @throws IOException Falls was schief geht.
     */
    public static File getWavDir(final Album album, final boolean createOrDelete) throws IOException
    {
        File dir = new File(getWorkDir(album), "wav");

        if (createOrDelete)
        {
            createOrCleanDir(album, dir);
        }

        return dir;
    }

    /**
     * Erzeugt, wenn nicht vorhanden, das Verzeichnis für die Dateien.<br>
     * Format: ArbeitsVerzeichnis/ALBUMTITEL
     *
     * @param album {@link Album}
     * @return {@link File}
     */
    public static File getWorkDir(final Album album)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Settings.getInstance().getWorkDir());
        // sb.append(File.separator).append(StringUtils.replace(album.getTitle(), " ", "-"));
        sb.append(File.separator).append(album.getTitle());
        sb.append("-CD").append(album.getDiskNumber());

        File dir = new File(sb.toString());

        if (!dir.exists())
        {
            dir.mkdirs();
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
     * Liefert einen gültigen Dateinamen ohne mehrfache Spaces, '/' und andere Sonderzeichen.
     *
     * @param fileName String
     * @return String
     */
    public static String validateFileName(final String fileName)
    {
        String name = StringUtils.replace(fileName, "/", "_");
        name = StringUtils.normalizeSpace(name);

        return name;
    }

    /**
     * Erstellt ein neues {@link JRipperUtils} Object.
     */
    private JRipperUtils()
    {
        super();
    }
}
