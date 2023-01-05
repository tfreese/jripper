// Created: 26.02.2013
package de.freese.jripper.console;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import de.freese.jripper.core.JRipper;
import de.freese.jripper.core.JRipperUtils;
import de.freese.jripper.core.Settings;
import de.freese.jripper.core.cddb.CddbResponse;
import de.freese.jripper.core.encoder.Encoder;
import de.freese.jripper.core.encoder.EncoderLinuxMp3;
import de.freese.jripper.core.encoder.LameProcessMonitor;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.model.AlbumImpl;
import de.freese.jripper.core.model.DiskId;
import de.freese.jripper.core.model.Track;
import de.freese.jripper.core.process.PrintWriterProcessMonitor;
import de.freese.jripper.core.process.ProcessMonitor;
import de.freese.jripper.core.ripper.Ripper;

/**
 * Console-View für den JRipper.
 *
 * @author Thomas Freese
 */
public class JRipperConsole
{
    public static void main(final String[] args) throws UnsupportedEncodingException
    {
        JRipperConsole console = new JRipperConsole();
        console.showMainMenu();
    }

    private final PrintWriter printWriter;

    private final BufferedReader reader;

    private Album album;

    public JRipperConsole() throws UnsupportedEncodingException
    {
        super();

        Console console = System.console();

        if (console != null)
        {
            if (console.reader() instanceof BufferedReader bufferedReader)
            {
                this.reader = bufferedReader;
            }
            else
            {
                this.reader = new BufferedReader(console.reader());
            }

            this.printWriter = console.writer();
        }
        else
        {
            // In Eclipse kann Console null sein.
            this.reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            this.printWriter = new PrintWriter(System.out, true, StandardCharsets.UTF_8);
        }
    }

    public void showMainMenu()
    {
        print("%s\n", "*****************");
        print("%s\n", "JRipper Hauptmenü");
        print("%s\n", "*****************");

        print("%s%s%s \t%s\n", AnsiCodes.ANSI_CYAN, "1", AnsiCodes.ANSI_RESET, "FreeDB abfragen");
        String workDir = Settings.getInstance().getWorkDir();

        if (this.album != null)
        {
            print("%s%s%s \t%s\n", AnsiCodes.ANSI_CYAN, "2", AnsiCodes.ANSI_RESET, "Album bearbeiten");
            print("%s%s%s \t%s%s/%s/wav\n", AnsiCodes.ANSI_CYAN, "3", AnsiCodes.ANSI_RESET, "CD auslesen -> ", workDir, this.album.getTitle());
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
            print("%s%s%s \t%s%s/%s/flac\n", AnsiCodes.ANSI_CYAN, "4", AnsiCodes.ANSI_RESET, "flac erzeugen -> ", workDir, this.album.getTitle());
            print("%s%s%s \t%s%s/%s/map3\n", AnsiCodes.ANSI_CYAN, "5", AnsiCodes.ANSI_RESET, "mp3 erzeugen -> ", workDir, this.album.getTitle());
        }

        print("\n");
        print("%s%s%s \t%s\n", AnsiCodes.ANSI_CYAN, "q", AnsiCodes.ANSI_RESET, "Beenden");

        String input = null;

        try
        {
            input = getInput();

            switch (input)
            {
                case "1":
                    this.album = null;
                    DiskId diskID = getDiskID();
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
                    print("%s%s \t%s%s\n", AnsiCodes.ANSI_RED, input, "Unbekannte Eingabe", AnsiCodes.ANSI_RESET);
                    break;
            }
        }
        catch (Exception ex)
        {
            // String message = ex.getMessage();
            // message = ex.toString();
            // message = StringUtils.isNotBlank(message) ? message : ex.toString();
            print("%s%s%s", AnsiCodes.ANSI_RED, ex.toString(), AnsiCodes.ANSI_RESET);
        }

        if ("2".equals(input))
        {
            // Notwendig um aus den switch-case herauszukommen.
            showEditMenu();
        }
        else
        {
            showMainMenu();
        }
    }

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

    private DiskId getDiskID() throws Exception
    {
        String device = Settings.getInstance().getDevice();

        return JRipper.getInstance().getDiskIDProvider().getDiskID(device);
    }

    private String getInput() throws Exception
    {
        print("%s%s%s: ", AnsiCodes.ANSI_GREEN, "Eingabe", AnsiCodes.ANSI_RESET);

        return this.reader.readLine();
    }

    private void print(final String format, final Object... params)
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
                    case AnsiCodes.ANSI_CYAN, AnsiCodes.ANSI_GREEN, AnsiCodes.ANSI_RED, AnsiCodes.ANSI_RESET -> params[i] = "";
                    default ->
                    {
                        // Empty
                    }
                }
            }
        }

        this.printWriter.printf(format, params);
        this.printWriter.flush();
    }

    private String queryCDDB(final DiskId diskID) throws Exception
    {
        CddbResponse cddbResponse = JRipper.getInstance().getCddbProvider().queryGenres(diskID);

        return cddbResponse.getGenres().get(0);
    }

    private Album readCDDB(final DiskId diskID, final String genre) throws Exception
    {
        CddbResponse cddbResponse = JRipper.getInstance().getCddbProvider().queryAlbum(diskID, genre);

        return cddbResponse.getAlbum();
    }

    private void rip(final Album album, final PrintWriter printWriter) throws Exception
    {
        String device = Settings.getInstance().getDevice();
        Ripper ripper = JRipper.getInstance().getRipper();
        File directory = JRipperUtils.getWavDir(album, true);

        ripper.rip(device, directory, new PrintWriterProcessMonitor(printWriter));
    }

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
            print("%2d. %s %s%n", track.getNumber(), String.format("%-35s", track.getArtist()).replace(' ', '.'), track.getTitle());
        }
    }

    private void showEditMenu()
    {
        showAlbum(this.album);

        print("%s\n", "*****************");
        print("%s\n", "Album Edit-Menü");
        print("%s\n", "*****************");

        print("%s%s%s \t%s\n", AnsiCodes.ANSI_CYAN, "aa", AnsiCodes.ANSI_RESET, "Album Artist");
        print("%s%s%s \t%s\n", AnsiCodes.ANSI_CYAN, "at", AnsiCodes.ANSI_RESET, "Album Titel");
        print("%s%s%s \t%s\n", AnsiCodes.ANSI_CYAN, "ag", AnsiCodes.ANSI_RESET, "Album Genre");
        print("%s%s%s \t%s\n", AnsiCodes.ANSI_CYAN, "ay", AnsiCodes.ANSI_RESET, "Album Year");
        print("%s%s%s \t%s\n", AnsiCodes.ANSI_CYAN, "ac", AnsiCodes.ANSI_RESET, "Album Comment");
        print("%s%s%s \t%s\n", AnsiCodes.ANSI_CYAN, "adn", AnsiCodes.ANSI_RESET, "Album Disk Number");
        print("%s%s%s \t%s\n", AnsiCodes.ANSI_CYAN, "atd", AnsiCodes.ANSI_RESET, "Album Total Disks");
        print("%s%s%s \t%s\n", AnsiCodes.ANSI_CYAN, "ta(number)", AnsiCodes.ANSI_RESET, "Track Artist");
        print("%s%s%s \t%s\n", AnsiCodes.ANSI_CYAN, "tt(number)", AnsiCodes.ANSI_RESET, "Track Title");

        print("\n");
        print("%s%s%s \t%s\n", AnsiCodes.ANSI_CYAN, "h", AnsiCodes.ANSI_RESET, "Hauptmenü");

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
                        input = JRipperUtils.trim(input);
                        int index = Integer.parseInt(input) - 1;

                        print("%s - ", "Neuer Wert");
                        input = getInput();
                        ((AlbumImpl) this.album).setTrackArtist(index, input);
                    }
                    else if (input.startsWith("tt"))
                    {
                        input = input.replace("tt", "").replace(".", "");
                        input = JRipperUtils.trim(input);
                        int index = Integer.parseInt(input) - 1;

                        print("%s - ", "Neuer Wert");
                        input = getInput();
                        ((AlbumImpl) this.album).setTrackTitle(index, input);
                    }
                    else
                    {
                        print("%s%s \t%s%s\n", AnsiCodes.ANSI_RED, input, "Unbekannte Eingabe", AnsiCodes.ANSI_RESET);
                    }
                    
                    break;
            }
        }
        catch (Exception ex)
        {
            // String message = ex.getMessage();
            // message = ex.toString();
            // message = StringUtils.isNotBlank(message) ? message : ex.toString();
            print("%s%s%s", AnsiCodes.ANSI_RED, ex.toString(), AnsiCodes.ANSI_RESET);
        }

        if ("h".equals(input))
        {
            // Notwendig um aus den switch-case herauszukommen.
            showMainMenu();
        }
        else
        {
            showEditMenu();
        }
    }
}
