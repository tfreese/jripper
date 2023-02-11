// Created: 02.03.2013
package de.freese.jripper.core.encoder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.freese.jripper.core.JRipperUtils;
import de.freese.jripper.core.Settings;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.model.Track;
import de.freese.jripper.core.process.AbstractProcess;
import de.freese.jripper.core.process.ProcessMonitor;

/**
 * Linux Implementierung mit dem Programm "lame", für Überprüfung mit "mp3val" und für den ReplayGain mit "mp3gain".
 *
 * @author Thomas Freese
 */
public class EncoderLinuxMp3 extends AbstractProcess implements Encoder {
    /**
     * @see de.freese.jripper.core.encoder.Encoder#encode(de.freese.jripper.core.model.Album, java.io.File, de.freese.jripper.core.process.ProcessMonitor)
     */
    @Override
    public void encode(final Album album, final File directory, final ProcessMonitor monitor) throws Exception {
        String diskID = album.getDiskID().getID();
        List<String> mp3Files = new ArrayList<>();
        List<String> command = new ArrayList<>();

        for (Track track : album) {
            command.clear();
            command.add("lame");
            command.add("-m");
            command.add("j");
            command.add("-q");
            command.add("0");
            command.add("-p");
            // command.add("-s");
            // command.add("44.1");
            command.add("-b");
            command.add(Integer.toString(Settings.getInstance().getMp3Bitrate()));
            command.add("--replaygain-accurate");
            command.add("--add-id3v2");
            command.add("--pad-id3v2");
            command.add("--ignore-tag-errors");
            command.add("--ta");
            command.add(track.getArtist());
            command.add("--tl");
            command.add(album.getTitle());
            command.add("--tt");
            command.add(track.getTitle());
            command.add("--tg");
            command.add(album.getGenre());
            command.add("--ty");
            command.add(Integer.toString(album.getYear()));
            command.add("--tn");
            command.add(String.format("%d/%d", track.getNumber(), album.getTrackCount()));
            command.add("--tc");
            command.add(album.getComment());
            command.add("--tv");
            command.add(String.format("DISCID=%s", diskID));
            command.add("--tv");
            command.add(String.format("DISCNUMBER=%s", album.getDiskNumber()));
            command.add("--tv");
            command.add(String.format("TOTALDISCS=%s", album.getTotalDisks()));
            command.add("--tv");
            command.add(String.format("DISCTOTAL=%s", album.getTotalDisks()));  // Für Player-Kompatibilität.
            command.add(String.format("../wav/track%02d.cdda.wav", track.getNumber()));

            String mp3File = String.format("%s (%s) - %02d - %s.mp3", track.getArtist(), album.getTitle(), track.getNumber(), track.getTitle());
            mp3File = JRipperUtils.validateFileName(mp3File);
            mp3Files.add(mp3File);

            command.add(mp3File);

            execute(command, directory, monitor);

            // Überprüfung.
            command.clear();
            command.add("mp3val");
            command.add(mp3File);
            command.add("-f");
            command.add("-nb");

            execute(command, directory, monitor);

            // mp3info "$OUTPUT"
        }

        // Replay-Gain.
        monitor.monitorText(String.format("%n%s%n", "Generiere Replay-Gain..."));
        command.clear();
        command.add("mp3gain");
        command.add("-r");
        command.add("-k");
        // command.add("-a");
        command.addAll(mp3Files);
        // command.add("*.mp3");

        execute(command, directory, monitor);
    }

    /**
     * @see de.freese.jripper.core.encoder.Encoder#getFormat()
     */
    @Override
    public EncoderFormat getFormat() {
        return EncoderFormat.MP3;
    }

    /**
     * @see de.freese.jripper.core.OSProvider#supportsOS(java.lang.String)
     */
    @Override
    public boolean supportsOS(final String os) {
        return JRipperUtils.isLinux();
    }
}
