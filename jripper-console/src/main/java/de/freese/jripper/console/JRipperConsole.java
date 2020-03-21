/**
 * Created: 26.02.2013
 */

package de.freese.jripper.console;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.apache.commons.lang3.StringUtils;
import de.freese.jripper.core.JRipper;
import de.freese.jripper.core.JRipperUtils;
import de.freese.jripper.core.Settings;
import de.freese.jripper.core.cddb.CddbResponse;
import de.freese.jripper.core.encoder.Encoder;
import de.freese.jripper.core.encoder.EncoderLinuxMp3;
import de.freese.jripper.core.encoder.LameProcessMonitor;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.model.AlbumImpl;
import de.freese.jripper.core.model.DiskID;
import de.freese.jripper.core.model.Track;
import de.freese.jripper.core.process.PrintWriterProcessMonitor;
import de.freese.jripper.core.process.ProcessMonitor;
import de.freese.jripper.core.ripper.Ripper;

/**
 * Console-View für den JRipper.
 *
 * @author Thomas Freese
 */
public class JRipperConsole implements IAnsiCodes
{
    /**
     * @param args String[]
     * @throws UnsupportedEncodingException Falls was schief geht.
     */
    public static void main(final String[] args) throws UnsupportedEncodingException
    {
        JRipperConsole console = new JRipperConsole();
        console.showMainMenu();
    }

    /**
     *
     */
    private Album album = null;

    /**
     *
     */
    private final PrintWriter printWriter;

    /**
     *
     */
    private final BufferedReader reader;

    /**
     * Erstellt ein neues {@link JRipperConsole} Object.
     *
     * @throws UnsupportedEncodingException Falls was schief geht.
     */
    @SuppressWarnings("resource")
    public JRipperConsole() throws UnsupportedEncodingException
    {
        super();

        Console console = System.console();

        if (console != null)
        {
            if (!(console.reader() instanceof BufferedReader))
            {
                this.reader = new BufferedReader(console.reader());
            }
            else
            {
                this.reader = (BufferedReader) console.reader();
            }

            this.printWriter = console.writer();
        }
        else
        {
            // In Eclipse kann Console null sein.
            this.reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
            this.printWriter = new PrintWriter(System.out);
        }
    }

    /**
     * @param album {@link Album}
     * @param printWriter {@link PrintWriter}
     * @param encoder {@link Encoder}
     * @param directory {@link File}
     * @throws Exception Falls was schief geht.
     */
    private void encode(final Album album, final PrintWriter printWriter, final Encoder encoder, final File directory) throws Exception
    {
        ProcessMonitor monitor = null;

        if (encoder instanceof EncoderLinuxMp3)
        {
            monitor = new LameProcessMonitor(printWriter);
        }
        else
        {
            monitor = new PrintWriterProcessMonitor(printWriter);
        }

        encoder.encode(album, directory, monitor);
    }

    /**
     * Auslesen der Disc-ID.<br>
     *
     * @return {@link DiskID}
     * @throws Exception Falls was schief geht.
     */
    private DiskID getDiskID() throws Exception
    {
        String device = Settings.getInstance().getDevice();
        DiskID diskID = JRipper.getInstance().getDiskIDProvider().getDiskID(device);

        return diskID;
    }

    /**
     * Liefert den eingegebenen Wert von der Konsole.
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
        if (JRipperUtils.isDevelopment())
        {
            // In der Entwicklungsumgebung die ANSI-Codes entfernen.
            for (int i = 0; i < params.length; i++)
            {
                if (params[i] == null)
                {
                    continue;
                }

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

        this.printWriter.printf(format, params);
        this.printWriter.flush();
    }

    /**
     * Abfragen der CDDB nach den Genres.<br>
     *
     * @param diskID {@link DiskID}
     * @return String
     * @throws Exception Falls was schief geht.
     */
    private String queryCDDB(final DiskID diskID) throws Exception
    {
        CddbResponse cddbResponse = JRipper.getInstance().getCddbProvider().queryGenres(diskID);

        return cddbResponse.getGenres().get(0);
    }

    /**
     * Abfragen der CDDB nach dem Album.<br>
     *
     * @param diskID {@link DiskID}
     * @param genre String
     * @return {@link Album}
     * @throws Exception Falls was schief geht.
     */
    private Album readCDDB(final DiskID diskID, final String genre) throws Exception
    {
        CddbResponse cddbResponse = JRipper.getInstance().getCddbProvider().queryAlbum(diskID, genre);

        return cddbResponse.getAlbum();
    }

    /**
     * @param album {@link Album}
     * @param printWriter {@link PrintWriter}
     * @throws Exception Falls was schief geht.
     */
    private void rip(final Album album, final PrintWriter printWriter) throws Exception
    {
        String device = Settings.getInstance().getDevice();
        Ripper ripper = JRipper.getInstance().getRipper();
        File directory = JRipperUtils.getWavDir(album, true);

        ripper.rip(device, directory, new PrintWriterProcessMonitor(printWriter));
    }

    /**
     * Zeigt den Inhalt des Albums.
     *
     * @param album {@link Album}
     */
    private void showAlbum(final Album album)
    {
        print("%s\n", "*****************");
        print("%s\n", "Album Inhalt");
        print("%s\n", "*****************");

        print("%-15s%s\n", "Artist", this.album.getArtist());
        print("%-15s%s\n", "Title", album.getTitle());
        print("%-15s%s\n", "Genre", album.getGenre());
        print("%-15s%d\n", "Year", album.getYear());
        print("%-15s%d\n", "Disk Number", album.getDiskNumber());
        print("%-15s%d\n", "Total Disks", album.getTotalDisks());
        print("%-15s%s\n", "Comment", album.getComment());
        print("\n");

        for (Track track : album)
        {
            // print("%2d. %s %s\n", track.getNumber(), String.format("%-35s", track.getArtist()).replace(' ', '.'), track.getTitle());
            print("%2d. %s %s\n", track.getNumber(), StringUtils.rightPad(track.getArtist(), 35, '.'), track.getTitle());
            // print("%2d. %-35s %s\n", track.getNumber(), track.getArtist(), track.getTitle());
        }
    }

    /**
     *
     */
    private void showEditMenu()
    {
        showAlbum(this.album);

        print("%s\n", "*****************");
        print("%s\n", "Album Editmenü");
        print("%s\n", "*****************");

        print("%s%s%s \t%s\n", ANSI_CYAN, "aa", ANSI_RESET, "Album Artist");
        print("%s%s%s \t%s\n", ANSI_CYAN, "at", ANSI_RESET, "Album Titel");
        print("%s%s%s \t%s\n", ANSI_CYAN, "ag", ANSI_RESET, "Album Genre");
        print("%s%s%s \t%s\n", ANSI_CYAN, "ay", ANSI_RESET, "Album Year");
        print("%s%s%s \t%s\n", ANSI_CYAN, "ac", ANSI_RESET, "Album Comment");
        print("%s%s%s \t%s\n", ANSI_CYAN, "adn", ANSI_RESET, "Album Disk Number");
        print("%s%s%s \t%s\n", ANSI_CYAN, "atd", ANSI_RESET, "Album Total Disks");
        print("%s%s%s \t%s\n", ANSI_CYAN, "ta(number)", ANSI_RESET, "Track Artist");
        print("%s%s%s \t%s\n", ANSI_CYAN, "tt(number)", ANSI_RESET, "Track Title");

        print("\n");
        print("%s%s%s \t%s\n", ANSI_CYAN, "h", ANSI_RESET, "Hauptmenü");

        String input = null;

        try
        {
            input = getInput();

            switch (input)
            {
                case "aa":
                    print("%s - ", "Neuer Wert");
                    input = getInput();
                    this.album.setArtist(input);
                    break;

                case "at":
                    print("%s - ", "Neuer Wert");
                    input = getInput();
                    this.album.setTitle(input);
                    break;

                case "ag":
                    print("%s - ", "Neuer Wert");
                    input = getInput();
                    this.album.setGenre(input);
                    break;

                case "ay":
                    print("%s - ", "Neuer Wert");
                    input = getInput();
                    this.album.setYear(Integer.parseInt(input));
                    break;

                case "ac":
                    print("%s - ", "Neuer Wert");
                    input = getInput();
                    this.album.setComment(input);
                    break;

                case "adn":
                    print("%s - ", "Neuer Wert");
                    input = getInput();
                    this.album.setDiskNumber(Integer.parseInt(input));
                    break;

                case "atd":
                    print("%s - ", "Neuer Wert");
                    input = getInput();
                    this.album.setTotalDisks(Integer.parseInt(input));
                    break;

                case "h":
                    break;

                default:
                    if (input.startsWith("ta"))
                    {
                        input = input.replace("ta", "").replace(".", "");
                        input = StringUtils.trim(input);
                        int index = Integer.parseInt(input) - 1;

                        print("%s - ", "Neuer Wert");
                        input = getInput();
                        ((AlbumImpl) this.album).setTrackArtist(index, input);
                    }
                    else if (input.startsWith("tt"))
                    {
                        input = input.replace("tt", "").replace(".", "");
                        input = StringUtils.trim(input);
                        int index = Integer.parseInt(input) - 1;

                        print("%s - ", "Neuer Wert");
                        input = getInput();
                        ((AlbumImpl) this.album).setTrackTitle(index, input);
                    }
                    else
                    {
                        print("%s%s \t%s%s\n", ANSI_RED, input, "Unbekannte Eingabe", ANSI_RESET);
                    }
                    break;
            }
        }
        catch (Exception ex)
        {
            // String message = ex.getMessage();
            // message = ex.toString();
            // message = StringUtils.isNotBlank(message) ? message : ex.toString();
            print("%s%s%s", ANSI_RED, ex.toString(), ANSI_RESET);
        }

        if ("h".equals(input))
        {
            // Notwendig um aus den switch-case rauszukommen.
            showMainMenu();
        }
        else
        {
            showEditMenu();
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

        print("%s%s%s \t%s\n", ANSI_CYAN, "1", ANSI_RESET, "FreeDB abfragen");
        String workDir = Settings.getInstance().getWorkDir();

        if (this.album != null)
        {
            print("%s%s%s \t%s\n", ANSI_CYAN, "2", ANSI_RESET, "Album bearbeiten");
            print("%s%s%s \t%s%s/%s/wav\n", ANSI_CYAN, "3", ANSI_RESET, "CD auslesen -> ", workDir, this.album.getTitle());
        }

        File wavDir = null;

        try
        {
            wavDir = JRipperUtils.getWavDir(this.album, false);
        }
        catch (Exception ex)
        {
            // Ignore
        }

        if ((wavDir != null) && wavDir.exists() && (this.album != null) && (this.album.getTrackCount() > 0))
        {
            print("%s%s%s \t%s%s/%s/flac\n", ANSI_CYAN, "4", ANSI_RESET, "flac erzeugen -> ", workDir, this.album.getTitle());
            print("%s%s%s \t%s%s/%s/map3\n", ANSI_CYAN, "5", ANSI_RESET, "mp3 erzeugen -> ", workDir, this.album.getTitle());
        }

        print("\n");
        print("%s%s%s \t%s\n", ANSI_CYAN, "q", ANSI_RESET, "Beenden");

        String input = null;

        try
        {
            input = getInput();

            switch (input)
            {
                case "1":
                    this.album = null;
                    DiskID diskID = getDiskID();
                    String genre = queryCDDB(diskID);
                    this.album = readCDDB(diskID, genre);
                    showAlbum(this.album);
                    break;

                case "2":
                    break;

                case "3":
                    rip(this.album, this.printWriter);
                    break;

                case "4":
                    File flacDir = JRipperUtils.getFlacDir(this.album, true);
                    encode(this.album, this.printWriter, JRipper.getInstance().getEncoderFlac(), flacDir);
                    break;

                case "5":
                    File mp3Dir = JRipperUtils.getMp3Dir(this.album, true);
                    encode(this.album, this.printWriter, JRipper.getInstance().getEncoderMp3(), mp3Dir);
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
            // String message = ex.getMessage();
            // message = ex.toString();
            // message = StringUtils.isNotBlank(message) ? message : ex.toString();
            print("%s%s%s", ANSI_RED, ex.toString(), ANSI_RESET);
        }

        if ("2".equals(input))
        {
            // Notwendig um aus den switch-case rauszukommen.
            showEditMenu();
        }
        else
        {
            showMainMenu();
        }
    }
}
