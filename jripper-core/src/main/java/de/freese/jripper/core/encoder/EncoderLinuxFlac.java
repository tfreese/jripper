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
 * Linux Implementation with "flac" and "metaflac".
 *
 * @author Thomas Freese
 */
public class EncoderLinuxFlac extends AbstractProcess implements Encoder {
    @Override
    public void encode(final Album album, final File directory, final ProcessMonitor monitor) throws Exception {
        final String diskID = album.getDiskID().getID();
        final List<String> flacFiles = new ArrayList<>();
        final List<String> command = new ArrayList<>();

        for (Track track : album) {
            command.clear();
            command.add("flac");
            command.add(String.format("-%d", Settings.getInstance().getFlacCompression()));
            command.add("-V");
            command.add("-f");
            command.add("-w");
            // command.add("--sample-rate=44.1");
            command.add("--replay-gain");
            command.add(String.format("../wav/track%02d.cdda.wav", track.getNumber()));
            command.add("--tag=ARTIST=" + track.getArtist());
            command.add("--tag=TITLE=" + track.getTitle());
            command.add("--tag=ALBUM=" + album.getTitle());
            command.add("--tag=TRACKNUMBER=" + track.getNumber());
            command.add("--tag=GENRE=" + album.getGenre());
            command.add("--tag=DATE=" + album.getYear());
            command.add("--tag=COMMENT=" + album.getComment());
            command.add("--tag=TOTALTRACKS=" + album.getTrackCount());
            command.add("--tag=TRACKTOTAL=" + album.getTrackCount()); // Für Player-Kompatibilität
            command.add("--tag=DISCNUMBER=" + album.getDiskNumber());
            command.add("--tag=TOTALDISCS=" + album.getTotalDisks());
            command.add("--tag=DISCTOTAL=" + album.getTotalDisks()); // Für Player-Kompatibilität
            command.add("--tag=DISKID=" + diskID);

            String flacFile = String.format("%s (%s) - %02d - %s.flac", track.getArtist(), album.getTitle(), track.getNumber(), track.getTitle());
            flacFile = JRipperUtils.validateFileName(flacFile);
            flacFiles.add(flacFile);

            command.add("-o");
            command.add(flacFile);

            execute(command, directory, monitor);

            // Überprüfung.
            command.clear();
            command.add("flac");
            command.add("-tw");
            command.add(flacFile);
            execute(command, directory, monitor);

            // metaflac --list "$flacFile"
        }

        // Replay-Gain.
        monitor.monitorText(String.format("%n%s%n", "Create Replay-Gain..."));
        command.clear();
        command.add("metaflac");
        command.add("--add-replay-gain");
        // command.add("--with-filename");
        command.addAll(flacFiles);
        // command.add("*.flac");

        execute(command, directory, monitor);
    }

    @Override
    public EncoderFormat getFormat() {
        return EncoderFormat.FLAC;
    }

    @Override
    public boolean supportsOS(final String os) {
        return JRipperUtils.isLinux();
    }
}
