/**
 * Created: 02.03.2013
 */

package de.freese.jripper.core.encoder;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;

import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.model.Track;
import de.freese.jripper.core.process.AbstractProcess;

/**
 * Linux Implementierung mit dem Programm "flac" und für den ReplayGain mit "metaflac".
 * 
 * @author Thomas Freese
 */
public class LinuxFlacEncoder extends AbstractProcess implements IEncoder
{
	/**
	 * Erstellt ein neues {@link LinuxFlacEncoder} Object.
	 */
	public LinuxFlacEncoder()
	{
		super();
	}

	/**
	 * @see de.freese.jripper.core.encoder.IEncoder#encode(de.freese.jripper.core.model.Album,
	 *      java.io.File, java.io.PrintWriter)
	 */
	@Override
	public void encode(final Album album, final File directory, final PrintWriter printWriter)
		throws Exception
	{
		List<String> command = new ArrayList<>();

		for (Track track : album)
		{
			command.clear();
			command.add("flac");
			command.add("-8");
			command.add("-V");
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
			command.add("--tag=DISCNUMBER=" + album.getDiskNumber());
			command.add("--tag=TOTALDISCS=" + album.getTotalDisks());

			String flacFile =
					String.format("%s (%s) - %02d - %s.flac", track.getArtist(), album.getTitle(),
							track.getNumber(), track.getTitle());

			command.add("-o");
			command.add(flacFile);

			execute(command, directory, printWriter, null);

			// Überprüfung.
			command.clear();
			command.add("flac");
			command.add("-t");
			command.add(flacFile);
			execute(command, directory, printWriter, null);

			// metaflac --list "$flacFile"
		}

		// Replay-Gain.
		printWriter.printf("\n%s\n", "Generiere Replay-Gain...");
		printWriter.flush();
		command.clear();
		command.add("metaflac");
		command.add("--add-replay-gain");
		command.add("*.flac");
		execute(command, directory, printWriter, null);
	}

	/**
	 * @see de.freese.jripper.core.encoder.IEncoder#getFormat()
	 */
	@Override
	public EncoderFormat getFormat()
	{
		return EncoderFormat.flac;
	}

	/**
	 * @see de.freese.jripper.core.IOSProvider#isSupportedOS(java.lang.String)
	 */
	@Override
	public boolean isSupportedOS(final String os)
	{
		return SystemUtils.IS_OS_LINUX;
	}
}
