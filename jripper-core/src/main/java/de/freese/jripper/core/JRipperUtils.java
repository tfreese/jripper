// Created: 02.03.2013
package de.freese.jripper.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import de.freese.jripper.core.model.Album;

/**
 * Util-Klasse.
 *
 * @author Thomas Freese
 */
public final class JRipperUtils
{
    public static String capitalize(final String value)
    {
        if ((value == null) || value.isBlank())
        {
            return value;
        }

        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    /**
     * Löscht das Verzeichnis rekursiv inklusive Dateien und Unterverzeichnisse.
     */
    public static void deleteDirectoryRecursive(final Path path) throws IOException
    {
        if (!Files.exists(path))
        {
            return;
        }

        if (!Files.isDirectory(path))
        {
            throw new IllegalArgumentException("path is not a directory: " + path);
        }

        Files.walkFileTree(path, new SimpleFileVisitor<>()
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
     * Liefert das Verzeichnis für die flac-Dateien.<br>
     * Format: Arbeitsverzeichnis/ALBUMTITEL/flac
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
     * Format: Arbeitsverzeichnis/ALBUMTITEL/mp3
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

    public static String getOsName()
    {
        return System.getProperty("os.name");
    }

    /**
     * Liefert das Verzeichnis für die wav-Dateien.<br>
     * Format: Arbeitsverzeichnis/ALBUMTITEL/wav
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
     * Format: Arbeitsverzeichnis/ALBUMTITEL
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
     */
    public static boolean isDevelopment()
    {
        return System.getProperty("dev.env") != null;
    }

    public static boolean isLinux()
    {
        String os = getOsName().toLowerCase();

        return os.contains("linux");
    }

    public static boolean isNumeric(final CharSequence cs)
    {
        if ((cs == null) || cs.isEmpty())
        {
            return false;
        }

        final int length = cs.length();

        for (int i = 0; i < length; i++)
        {
            if (!Character.isDigit(cs.charAt(i)))
            {
                return false;
            }
        }

        return true;
    }

    public static boolean isWindows()
    {
        String os = getOsName().toLowerCase();

        return os.startsWith("win");
    }

    public static String normalizeSpace(final String value)
    {
        if ((value == null) || value.isBlank())
        {
            return null;
        }

        String str = value;

        while (str.contains("  "))
        {
            str = str.replace("  ", " ");
        }

        return str;
    }

    public static String trim(final String value)
    {
        if ((value == null) || value.isBlank())
        {
            return null;
        }

        return value.strip();
    }

    public static String uncapitalize(final String value)
    {
        if ((value == null) || value.isBlank())
        {
            return value;
        }

        return value.substring(0, 1).toLowerCase() + value.substring(1);
    }

    /**
     * Liefert einen gültigen Dateinamen ohne mehrfache Spaces, '/' und andere Sonderzeichen.
     */
    public static String validateFileName(final String fileName)
    {
        String name = fileName.replace("/", "_");

        name = normalizeSpace(name);

        return name;
    }

    /**
     * Versucht in Abhängigkeit des Betriebssystems das CD/DVD-Laufwerk zu finden und liefert das erste CD/DVD-Laufwerk.
     */
    static String detectCdDevice()
    {
        String drive = null;

        if (isLinux())
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
        else if (isWindows())
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
     * Erzeugt, wenn nicht vorhanden, das Verzeichnis für die Dateien.<br>
     * Vorhandene Daten werden gelöscht.
     * Format: Arbeitsverzeichnis/ALBUMTITEL/PFAD<br>
     */
    private static void createOrCleanDir(final Album album, final File directory) throws IOException
    {
        if (directory.exists())
        {
            deleteDirectoryRecursive(directory.toPath());
        }
        else
        {
            directory.mkdirs();
        }
    }

    private JRipperUtils()
    {
        super();
    }
}
