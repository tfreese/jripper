// Created: 26.02.2013
package de.freese.jripper.console;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
public class JRipperConsole {
    public static void main(final String[] args) {
        final JRipperConsole console = new JRipperConsole();
        console.showMainMenu();
    }

    private final PrintWriter printWriter;
    private final BufferedReader reader;

    private Album album;

    public JRipperConsole() {
        super();

        final Console console = System.console();

        if (console != null) {
            if (console.reader() instanceof BufferedReader bufferedReader) {
                reader = bufferedReader;
            }
            else {
                reader = new BufferedReader(console.reader());
            }

            printWriter = console.writer();
        }
        else {
            // In Eclipse kann Console null sein.
            reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            printWriter = new PrintWriter(System.out, true, StandardCharsets.UTF_8);
        }
    }

    public void showMainMenu() {
        println("%s", "*****************");
        println("%s", "JRipper Hauptmenü");
        println("%s", "*****************");
        println("%s", "*****************");

        print("%s%s%s \t%s" + System.lineSeparator(), AnsiCodes.ANSI_CYAN, "1", AnsiCodes.ANSI_RESET, "FreeDB abfragen");
        final String workDir = Settings.getInstance().getWorkDir();

        if (album != null) {
            println("%s%s%s \t%s", AnsiCodes.ANSI_CYAN, "2", AnsiCodes.ANSI_RESET, "Album bearbeiten");
            println("%s%s%s \t%s%s/%s/wav", AnsiCodes.ANSI_CYAN, "3", AnsiCodes.ANSI_RESET, "CD auslesen -> ", workDir, album.getTitle());
        }

        File wavDir = null;

        try {
            wavDir = JRipperUtils.getWavDir(album, false);
        }
        catch (Exception ex) {
            // Ignore
        }

        if (wavDir != null && wavDir.exists() && album != null && album.getTrackCount() > 0) {
            println("%s%s%s \t%s%s/%s/flac", AnsiCodes.ANSI_CYAN, "4", AnsiCodes.ANSI_RESET, "flac erzeugen -> ", workDir, album.getTitle());
            println("%s%s%s \t%s%s/%s/map3", AnsiCodes.ANSI_CYAN, "5", AnsiCodes.ANSI_RESET, "mp3 erzeugen -> ", workDir, album.getTitle());
        }

        println("");
        println("%s%s%s \t%s", AnsiCodes.ANSI_CYAN, "q", AnsiCodes.ANSI_RESET, "Beenden");

        String input = null;

        try {
            input = getInput();

            switch (input) {
                case "1":
                    album = null;
                    final DiskId diskID = getDiskID();
                    final String genre = queryCDDB(diskID);
                    album = readCDDB(diskID, genre);
                    showAlbum(album);
                    break;

                case "2":
                    break;

                case "3":
                    rip(album, printWriter);
                    break;

                case "4":
                    final File flacDir = JRipperUtils.getFlacDir(album, true);
                    encode(album, printWriter, JRipper.getInstance().getEncoderFlac(), flacDir);
                    break;

                case "5":
                    final File mp3Dir = JRipperUtils.getMp3Dir(album, true);
                    encode(album, printWriter, JRipper.getInstance().getEncoderMp3(), mp3Dir);
                    break;

                case "q":
                    return;

                default:
                    println("%s%s \t%s%s", AnsiCodes.ANSI_RED, input, "Unbekannte Eingabe", AnsiCodes.ANSI_RESET);
                    break;
            }
        }
        catch (Exception ex) {
            // String message = ex.getMessage();
            // message = ex.toString();
            // message = StringUtils.isNotBlank(message) ? message : ex.toString();
            println("%s%s%s", AnsiCodes.ANSI_RED, ex.toString(), AnsiCodes.ANSI_RESET);
        }

        if ("2".equals(input)) {
            // Notwendig um aus den switch-case herauszukommen.
            showEditMenu();
        }
        else {
            showMainMenu();
        }
    }

    private void encode(final Album album, final PrintWriter printWriter, final Encoder encoder, final File directory) throws Exception {
        ProcessMonitor monitor = null;

        if (encoder instanceof EncoderLinuxMp3) {
            monitor = new LameProcessMonitor(printWriter);
        }
        else {
            monitor = new PrintWriterProcessMonitor(printWriter);
        }

        encoder.encode(album, directory, monitor);
    }

    private DiskId getDiskID() throws Exception {
        final String device = Settings.getInstance().getDevice();

        return JRipper.getInstance().getDiskIDProvider().getDiskID(device);
    }

    private String getInput() throws Exception {
        println("%s%s%s: ", AnsiCodes.ANSI_GREEN, "Eingabe", AnsiCodes.ANSI_RESET);

        return reader.readLine();
    }

    private void print(final String format, final Object... params) {
        if (JRipperUtils.isDevelopment()) {
            // In der Entwicklungsumgebung die ANSI-Codes entfernen.
            for (int i = 0; i < params.length; i++) {
                if (params[i] == null) {
                    continue;
                }

                final String ansi = params[i].toString();

                if (AnsiCodes.ANSI_CYAN.equals(ansi)
                        || AnsiCodes.ANSI_GREEN.equals(ansi)
                        || AnsiCodes.ANSI_RED.equals(ansi)
                        || AnsiCodes.ANSI_RESET.equals(ansi)) {
                    params[i] = "";
                }
            }
        }

        printWriter.printf(format, params);
        printWriter.flush();
    }

    private void println(final String format, final Object... params) {
        print(format + System.lineSeparator(), params);
    }

    private String queryCDDB(final DiskId diskID) throws Exception {
        final CddbResponse cddbResponse = JRipper.getInstance().getCddbProvider().queryGenres(diskID);

        return cddbResponse.getGenres().getFirst();
    }

    private Album readCDDB(final DiskId diskID, final String genre) throws Exception {
        final CddbResponse cddbResponse = JRipper.getInstance().getCddbProvider().queryAlbum(diskID, genre);

        return cddbResponse.getAlbum();
    }

    private void rip(final Album album, final PrintWriter printWriter) throws Exception {
        final String device = Settings.getInstance().getDevice();
        final Ripper ripper = JRipper.getInstance().getRipper();
        final File directory = JRipperUtils.getWavDir(album, true);

        ripper.rip(device, directory, new PrintWriterProcessMonitor(printWriter));
    }

    private void showAlbum(final Album album) {
        println("%s", "*****************");
        println("%s", "Album Inhalt");
        println("%s", "*****************");

        println("%-15s%s", "Artist", album.getArtist());
        println("%-15s%s", "Title", album.getTitle());
        println("%-15s%s", "Genre", album.getGenre());
        println("%-15s%d", "Year", album.getYear());
        println("%-15s%d", "Disk Number", album.getDiskNumber());
        println("%-15s%d", "Total Disks", album.getTotalDisks());
        println("%-15s%s", "Comment", album.getComment());
        print(System.lineSeparator());

        for (Track track : album) {
            println("%2d. %s %s", track.getNumber(), String.format("%-35s", track.getArtist()).replace(' ', '.'), track.getTitle());
        }
    }

    private void showEditMenu() {
        showAlbum(album);

        println("%s", "*****************");
        println("%s", "Album Edit-Menü");
        println("%s", "*****************");

        println("%s%s%s \t%s", AnsiCodes.ANSI_CYAN, "aa", AnsiCodes.ANSI_RESET, "Album Artist");
        println("%s%s%s \t%s", AnsiCodes.ANSI_CYAN, "at", AnsiCodes.ANSI_RESET, "Album Titel");
        println("%s%s%s \t%s", AnsiCodes.ANSI_CYAN, "ag", AnsiCodes.ANSI_RESET, "Album Genre");
        println("%s%s%s \t%s", AnsiCodes.ANSI_CYAN, "ay", AnsiCodes.ANSI_RESET, "Album Year");
        println("%s%s%s \t%s", AnsiCodes.ANSI_CYAN, "ac", AnsiCodes.ANSI_RESET, "Album Comment");
        println("%s%s%s \t%s", AnsiCodes.ANSI_CYAN, "adn", AnsiCodes.ANSI_RESET, "Album Disk Number");
        println("%s%s%s \t%s", AnsiCodes.ANSI_CYAN, "atd", AnsiCodes.ANSI_RESET, "Album Total Disks");
        println("%s%s%s \t%s", AnsiCodes.ANSI_CYAN, "ta(number)", AnsiCodes.ANSI_RESET, "Track Artist");
        println("%s%s%s \t%s", AnsiCodes.ANSI_CYAN, "tt(number)", AnsiCodes.ANSI_RESET, "Track Title");

        println("");
        println("%s%s%s \t%s", AnsiCodes.ANSI_CYAN, "h", AnsiCodes.ANSI_RESET, "Hauptmenü");

        String input = null;

        try {
            input = getInput();

            switch (input) {
                case "aa":
                    println("%s - ", "Neuer Wert");
                    input = getInput();
                    album.setArtist(input);
                    break;

                case "at":
                    println("%s - ", "Neuer Wert");
                    input = getInput();
                    album.setTitle(input);
                    break;

                case "ag":
                    println("%s - ", "Neuer Wert");
                    input = getInput();
                    album.setGenre(input);
                    break;

                case "ay":
                    println("%s - ", "Neuer Wert");
                    input = getInput();
                    album.setYear(Integer.parseInt(input));
                    break;

                case "ac":
                    println("%s - ", "Neuer Wert");
                    input = getInput();
                    album.setComment(input);
                    break;

                case "adn":
                    println("%s - ", "Neuer Wert");
                    input = getInput();
                    album.setDiskNumber(Integer.parseInt(input));
                    break;

                case "atd":
                    println("%s - ", "Neuer Wert");
                    input = getInput();
                    album.setTotalDisks(Integer.parseInt(input));
                    break;

                case "h":
                    break;

                default:
                    if (input.startsWith("ta")) {
                        input = input.replace("ta", "").replace(".", "");
                        input = JRipperUtils.trim(input);
                        final int index = Integer.parseInt(input) - 1;

                        println("%s - ", "Neuer Wert");
                        input = getInput();
                        ((AlbumImpl) album).setTrackArtist(index, input);
                    }
                    else if (input.startsWith("tt")) {
                        input = input.replace("tt", "").replace(".", "");
                        input = JRipperUtils.trim(input);
                        final int index = Integer.parseInt(input) - 1;

                        println("%s - ", "Neuer Wert");
                        input = getInput();
                        ((AlbumImpl) album).setTrackTitle(index, input);
                    }
                    else {
                        println("%s%s \t%s%s", AnsiCodes.ANSI_RED, input, "Unbekannte Eingabe", AnsiCodes.ANSI_RESET);
                    }

                    break;
            }
        }
        catch (Exception ex) {
            // String message = ex.getMessage();
            // message = ex.toString();
            // message = StringUtils.isNotBlank(message) ? message : ex.toString();
            println("%s%s%s", AnsiCodes.ANSI_RED, ex.toString(), AnsiCodes.ANSI_RESET);
        }

        if ("h".equals(input)) {
            // Notwendig um aus den switch-case herauszukommen.
            showMainMenu();
        }
        else {
            showEditMenu();
        }
    }
}
