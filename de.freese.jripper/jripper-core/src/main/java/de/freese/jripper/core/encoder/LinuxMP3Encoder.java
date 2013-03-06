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
 * Linux Implementierung mit dem Programm "lame".
 * 
 * @author Thomas Freese
 */
public class LinuxMP3Encoder extends AbstractProcess implements IEncoder
{
	/**
	 * Erstellt ein neues {@link LinuxMP3Encoder} Object.
	 */
	public LinuxMP3Encoder()
	{
		super();
	}

	// Invalid field value: 'DISCID=b111140e'. Ignored
	// Invalid field value: 'DISCNUMBER=1'. Ignored
	// Invalid field value: 'TOTALDISCS=1'. Ignored
	// Invalid field value: 'DISCTOTAL=1'. Ignored
	// LAME 3.99.5 64bits (http://lame.sf.net)
	// Using polyphase lowpass filter, transition band: 20094 Hz - 20627 Hz
	// Encoding ../wav/track01.cdda.wav
	// to Karat (Vierzehn Karat - Ihre Größten Hits) - 01 - Der Blaue Planet.mp3
	// Encoding as 44.1 kHz j-stereo MPEG-1 Layer III (4.4x) 320 kbps qval=0
	// Frame | CPU time/estim | REAL time/estim | play/CPU | ETA
	// 0/ ( 0%)| 0:00/ : | 0:00/ : | x| :
	// 05:25--------------------------------------------------------------------------
	// kbps % %
	// 0.0 [A[A[A
	// 0/12453 ( 0%)| 0:00/ 0:00| 0:00/ 0:00| 0.0000x| 0:00
	// 05:25--------------------------------------------------------------------------
	// kbps % %
	// 0.0 [A[A[A
	// 100/12453 ( 1%)| 0:00/ 0:21| 0:00/ 0:22| 15.366x| 0:22
	// 05:22-------------------------------------------------------------------------
	// kbps LR MS % long switch short %
	// 320.0 32.0 68.0 91.0 5.0 4.0 [A[A[A
	// 200/12453 ( 2%)| 0:00/ 0:26| 0:00/ 0:27| 12.150x| 0:26
	// --05:20------------------------------------------------------------------------
	// kbps LR MS % long switch short %
	// 320.0 65.5 34.5 82.2 10.0 7.8 [A[A[A
	// 300/12453 ( 2%)| 0:00/ 0:28| 0:00/ 0:29| 11.358x| 0:28
	// --05:17------------------------------------------------------------------------
	// kbps LR MS % long switch short %
	// 320.0 74.3 25.7 78.7 11.7 9.7 [A[A[A
	// 400/12453 ( 3%)| 0:00/ 0:29| 0:00/ 0:30| 10.884x| 0:29
	/**
	 * @see de.freese.jripper.core.encoder.IEncoder#encode(de.freese.jripper.core.model.Album, java.io.File, java.io.PrintWriter)
	 */
	@Override
	public void encode(final Album album, final File directory, final PrintWriter printWriter) throws Exception
	{
		String diskID = album.getDiskID().split(" ")[0];
		List<String> mp3Files = new ArrayList<>();
		List<String> command = new ArrayList<>();

		for (Track track : album)
		{
			command.clear();
			command.add("lame");
			command.add("-m");
			command.add("j");
			command.add("-q");
			command.add("0");
			// command.add("-s");
			// command.add("44.1");
			command.add("-b");
			command.add("320");
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
			mp3Files.add(mp3File);

			command.add(mp3File);

			execute(command, directory, printWriter, null);

			// Überprüfung -> gibs anscheinend nicht für mp3.
			// command.clear();
			// command.add("flac");
			// command.add("-t");
			// command.add(flacFile);
			// execute(command, directory, printWriter, null);

			// mp3info "$OUTPUT"
		}

		// Replay-Gain.
		printWriter.printf("\n%s\n", "Generiere Replay-Gain...");
		printWriter.flush();
		command.clear();
		command.add("mp3gain");
		command.addAll(mp3Files);
		// command.add("*.mp3");
		execute(command, directory, printWriter, null);
	}

	/**
	 * @see de.freese.jripper.core.encoder.IEncoder#getFormat()
	 */
	@Override
	public EncoderFormat getFormat()
	{
		return EncoderFormat.mp3;
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
