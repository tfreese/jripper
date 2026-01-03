// Created: 11.10.2013
package de.freese.jripper.core.script;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import de.freese.jripper.core.JRipperUtils;
import de.freese.jripper.core.Settings;
import de.freese.jripper.core.callback.EmptyCallback;
import de.freese.jripper.core.callback.ProcessLoggerCallback;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.model.Track;
import de.freese.jripper.core.process.AbstractProcess;

/**
 * @author Thomas Freese
 */
public class ScriptGeneratorLinux extends AbstractProcess implements ScriptGenerator {
    @Override
    public void execute(final File script) throws Exception {
        final List<String> command = new ArrayList<>();
        // command.add("konsole");
        // // command.add("--nofork");
        // command.add("--new-tab");
        // command.add("--hold");
        // command.add("-e");
        // command.add(script.getAbsolutePath());

        command.add("xterm"); // System.getenv().get("TERM")
        command.add("-bg");
        command.add("black");
        command.add("-fg");
        command.add("white");
        command.add("-sl");
        command.add("10000");
        command.add("-geometry");
        command.add("250x70");
        command.add("-hold");
        command.add("-e");
        command.add(script.getAbsolutePath());

        execute(command, script.getParentFile(), new EmptyCallback());
    }

    @Override
    public File generate(final Album album, final File folder) throws Exception {
        final Settings settings = Settings.getInstance();

        final File script = new File(folder, getPath(album) + ".sh");

        if (script.exists()) {
            Files.delete(script.toPath());
        }

        try (PrintWriter pw = new PrintWriter(script, StandardCharsets.UTF_8)) {
            // Shebang
            pw.println("#!/bin/sh");
            pw.println();

            writeProgramChecks(pw, "cdparanoia");

            if (settings.isFlacEnabled()) {
                pw.println();
                writeProgramChecks(pw, "flac");
                pw.println();
                writeProgramChecks(pw, "metaflac");
            }

            if (settings.isMp3Enabled()) {
                pw.println();
                writeProgramChecks(pw, "lame");
                pw.println();
                writeProgramChecks(pw, "mp3val");
                pw.println();
                writeProgramChecks(pw, "mp3gain");
            }

            // Variables
            pw.println();
            pw.println("BASE_DIR=$PWD");
            // pw.printf("BASE_DIR=%s/\n", folder.getAbsoluteFile());

            // cdparanoia
            writeRip(pw);

            // flac
            if (settings.isFlacEnabled()) {
                pw.println();
                writeFLAC(pw, album);
            }

            // mp3
            if (settings.isMp3Enabled()) {
                pw.println();
                writeMP3(pw, album);
            }

            // Keep the Shell open.
            pw.println();
            // pw.println("$SHELL");
            pw.println("exit");
        }

        final List<String> command = new ArrayList<>();
        command.add("chmod");
        command.add("+x");
        command.add(script.getAbsolutePath());
        execute(command, script.getParentFile(), new ProcessLoggerCallback(getLogger()));

        return script;
    }

    /**
     * Create Path from Album.
     */
    private String getPath(final Album album) {
        // String path = StringUtils.replace(album.getTitle(), " ", "-");
        // path = StringUtils.replace(path, "/", "-");
        //
        // return path;
        return album.getTitle();
    }

    private void writeFLAC(final PrintWriter pw, final Album album) {
        final String diskID = album.getDiskID().getID();
        // List<String> files = new ArrayList<>();

        pw.println("mkdir -p \"$BASE_DIR\"/flac");
        pw.println("cd \"$BASE_DIR\"/flac");
        pw.println("rm -f ./*.flac");

        for (Track track : album) {
            pw.println();
            pw.print("$FLAC");
            pw.print(String.format(" -%d", Settings.getInstance().getFlacCompression()));
            pw.print(" -V"); // verify
            pw.print(" -f"); // force
            pw.print(" -w"); // warnings-as-errors
            // pw.print(" --sample-rate=44.1");
            pw.println(" --replay-gain \\");
            pw.printf("\t\"$BASE_DIR\"/wav/track%02d.cdda.wav \\%n", track.getNumber());
            pw.printf("\t--tag=ARTIST=\"%s\" \\%n", track.getArtist());
            pw.printf("\t--tag=TITLE=\"%s\" \\%n", track.getTitle());
            pw.printf("\t--tag=ALBUM=\"%s\" \\%n", album.getTitle());
            pw.printf("\t--tag=GENRE=\"%s\" \\%n", album.getGenre());
            pw.printf("\t--tag=DATE=%d \\%n", album.getYear());
            pw.printf("\t--tag=COMMENT=\"%s\" \\%n", album.getComment());
            pw.printf("\t--tag=TRACKNUMBER=%d \\%n", track.getNumber());
            pw.printf("\t--tag=TOTALTRACKS=%d \\%n", album.getTrackCount());
            pw.printf("\t--tag=TRACKTOTAL=%d \\%n", album.getTrackCount()); // For Player-Compatibility.
            pw.printf("\t--tag=DISCNUMBER=%d \\%n", album.getDiskNumber());
            pw.printf("\t--tag=TOTALDISCS=%d \\%n", album.getTotalDisks());
            pw.printf("\t--tag=DISCTOTAL=%d \\%n", album.getTotalDisks()); // For Player-Compatibility.
            pw.printf("\t--tag=DISKID=%s \\%n", diskID);

            String flacFile = String.format("\"%s (%s) - %02d - %s.flac\"", track.getArtist(), album.getTitle(), track.getNumber(), track.getTitle());
            flacFile = JRipperUtils.validateFileName(flacFile);
            // files.add(flacFile);

            pw.printf("\t-o %s%n", flacFile);
        }

        pw.println();
        pw.println("echo");
        pw.println("echo \"Überprüfung ...\"");
        pw.println("$FLAC -tw ./*.flac");

        pw.println();
        pw.println("echo");
        pw.println("echo \"Replay-Gain ...\"");
        pw.println("$METAFLAC --add-replay-gain ./*.flac");
        pw.println("echo \"...done\"");
    }

    private void writeMP3(final PrintWriter pw, final Album album) {
        final String diskID = album.getDiskID().getID();
        // List<String> files = new ArrayList<>();

        pw.println("mkdir -p \"$BASE_DIR\"/mp3");
        pw.println("cd \"$BASE_DIR\"/mp3");
        pw.println("rm -f ./*.mp3");

        for (Track track : album) {
            pw.println();
            pw.print("$LAME");
            pw.print(" -m j"); // Mode = Joint-Stereo
            // pw.print(" -q 0"); // VBR
            pw.print(" -p"); // CRC Error-Protection
            // pw.print(" -s 44.1"); // Sampling-Rate
            pw.print(String.format(" -b %d", Settings.getInstance().getMp3Bitrate())); // CBR
            pw.print(" --replaygain-accurate");
            pw.print(" --add-id3v2");
            pw.print(" --pad-id3v2");
            pw.println(" --ignore-tag-errors \\");
            pw.printf("\t--ta \"%s\" \\%n", track.getArtist());
            pw.printf("\t--tl \"%s\" \\%n", album.getTitle());
            pw.printf("\t--tt \"%s\" \\%n", track.getTitle());
            pw.printf("\t--tg \"%s\" \\%n", album.getGenre());
            pw.printf("\t--ty \"%d\" \\%n", album.getYear());
            pw.printf("\t--tn \"%d/%d\" \\%n", track.getNumber(), album.getTrackCount());
            pw.printf("\t--tc \"%s\" \\%n", album.getComment());
            pw.printf("\t--tv \"DISCID=%s\" \\%n", diskID);
            pw.printf("\t--tv \"DISCNUMBER=%d\" \\%n", album.getDiskNumber());
            pw.printf("\t--tv \"TOTALDISCS=%d\" \\%n", album.getTotalDisks());
            pw.printf("\t--tv \"DISCTOTAL=%d\" \\%n", album.getTotalDisks());  // For Player-Compatibility.
            pw.printf("\t\"$BASE_DIR\"/wav/track%02d.cdda.wav", track.getNumber());

            String mp3File = String.format("\"%s (%s) - %02d - %s.mp3\"", track.getArtist(), album.getTitle(), track.getNumber(), track.getTitle());
            mp3File = JRipperUtils.validateFileName(mp3File);
            // files.add(mp3File);

            pw.printf("\t%s%n", mp3File);
        }

        pw.println();
        pw.println("echo");
        pw.println("echo \"Überprüfung ...\"");
        pw.println("$MP3VAL -f -nb ./*.mp3");

        pw.println();
        pw.println("echo");
        pw.println("echo \"Replay-Gain ...\"");
        pw.println("$MP3GAIN -r -k ./*.mp3");     // -a
        pw.println("echo \"...done\"");
    }

    private void writeProgramChecks(final PrintWriter pw, final String programm) {
        pw.printf("%s=\"$(which %s)\"%n", programm.toUpperCase(), programm);
        pw.println("if [ $? != 0 ]; then");
        pw.printf("\techo \"%s not installed\"%n", programm);
        pw.println("\texit 5;");
        pw.printf("elif [ ! -x \"$%s\" ]; then%n", programm.toUpperCase());
        pw.printf("\techo \"%s not executable\"%n", programm);
        pw.println("\texit 5;");
        pw.println("fi");
        pw.printf("readonly %s%n", programm.toUpperCase());
    }

    private void writeRip(final PrintWriter pw) {
        pw.println();
        pw.println("read -p \"(r)ippen oder (e)ncoden: \" re");
        pw.println("if [ \"$re\" = \"r\" ]; then");
        pw.println("\tmkdir -p \"$BASE_DIR\"/wav");
        pw.println("\tcd \"$BASE_DIR\"/wav");
        pw.println("\trm -f ./*.wav");
        pw.printf("\t$CDPARANOIA -w -B -z -d %s%n", Settings.getInstance().getDevice()); // -S 2 = Read with 2x Speed.
        pw.println("fi");
    }
}
