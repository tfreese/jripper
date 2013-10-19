/**
 * Created: 11.10.2013
 */

package de.freese.jripper.core.script;

import de.freese.jripper.core.Settings;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.model.Track;
import de.freese.jripper.core.process.AbstractProcess;
import de.freese.jripper.core.process.LoggerProcessMonitor;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * {@link IScripter} für Linux.
 * 
 * @author Thomas Freese
 */
public class LinuxScripter extends AbstractProcess implements IScripter
{
	/**
	 * Erstellt ein neues {@link LinuxScripter} Object.
	 */
	public LinuxScripter()
	{
		super();
	}

	/**
	 * @see de.freese.jripper.core.script.IScripter#execute(java.io.File)
	 */
	@Override
	public void execute(final File script) throws Exception
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @see de.freese.jripper.core.script.IScripter#generate(de.freese.jripper.core.model.Album, java.io.File)
	 */
	@Override
	public File generate(final Album album, final File folder) throws Exception
	{
		Settings settings = Settings.getInstance();

		File script = new File(folder, StringUtils.replace(album.getTitle(), " ", "-") + ".sh");

		if (script.exists())
		{
			script.delete();
		}

		try (PrintWriter pw = new PrintWriter(script))
		{
			// Shebang
			pw.println("#!/bin/sh");
			pw.println();

			writeProgramChecks(pw, "cdparanoia");

			if (settings.isFlacEnabled())
			{
				pw.println();
				writeProgramChecks(pw, "flac");
				pw.println();
				writeProgramChecks(pw, "metaflac");
			}

			if (settings.isMp3Enabled())
			{
				pw.println();
				writeProgramChecks(pw, "lame");
				pw.println();
				writeProgramChecks(pw, "mp3val");
				pw.println();
				writeProgramChecks(pw, "mp3gain");
			}

			// Variablen
			pw.println();
			pw.println("BASE_DIR=$PWD");
			// pw.printf("BASE_DIR=%s/\n", folder.getAbsoluteFile());

			// cdparanoia
			writeRip(pw);

			// flac
			if (settings.isFlacEnabled())
			{
				pw.println();
				writeFLAC(pw, album);
			}

			// mp3
			if (settings.isMp3Enabled())
			{
				pw.println();
				writeMP3(pw, album);
			}

			// Shell offen lassen
			pw.println();
			// pw.println("$SHELL");
		}

		List<String> command = new ArrayList<>();
		command.add("chmod");
		command.add("+x");
		command.add(script.getAbsolutePath());
		execute(command, script.getParentFile(), new LoggerProcessMonitor(getLogger()));

		return script;
	}

	/**
	 * @param pw {@link PrintWriter}
	 * @param album {@link Album}
	 */
	private void writeFLAC(final PrintWriter pw, final Album album)
	{
		String diskID = album.getDiskID().getID();
		// List<String> files = new ArrayList<>();

		pw.println("mkdir -p $BASE_DIR/flac");
		pw.println("cd $BASE_DIR/flac");
		pw.println("rm -f *.flac");

		for (Track track : album)
		{
			pw.println();
			pw.print("$FLAC");
			pw.print(String.format(" -%d", Settings.getInstance().getFlacCompression()));
			pw.print(" -V");
			pw.print(" -f");
			pw.print(" -w");
			// pw.print(" --sample-rate=44.1");
			pw.println(" --replay-gain \\");
			pw.printf("\t$BASE_DIR/wav/track%02d.cdda.wav \\\n", track.getNumber());
			pw.printf("\t--tag=ARTIST=\"%s\" \\\n", track.getArtist());
			pw.printf("\t--tag=TITLE=\"%s\" \\\n", track.getTitle());
			pw.printf("\t--tag=ALBUM=\"%s\" \\\n", album.getTitle());
			pw.printf("\t--tag=TRACKNUMBER=%d \\\n", track.getNumber());
			pw.printf("\t--tag=GENRE=\"%s\" \\\n", album.getGenre());
			pw.printf("\t--tag=DATE=%d \\\n", album.getYear());
			pw.printf("\t--tag=COMMENT=\"%s\" \\\n", album.getComment());
			pw.printf("\t--tag=TOTALTRACKS=%d \\\n", album.getTrackCount());
			pw.printf("\t--tag=TRACKTOTAL=%d \\\n", album.getTrackCount()); // Für Player-Kompatibilität
			pw.printf("\t--tag=DISCNUMBER=%d \\\n", album.getDiskNumber());
			pw.printf("\t--tag=TOTALDISCS=%d \\\n", album.getTotalDisks());
			pw.printf("\t--tag=DISCTOTAL=%d \\\n", album.getTotalDisks()); // Für Player-Kompatibilität
			pw.printf("\t--tag=DISKID=%s \\\n", diskID);

			String flacFile = String.format("\"%s (%s) - %02d - %s.flac\"", track.getArtist(), album.getTitle(), track.getNumber(), track.getTitle());
			// files.add(flacFile);

			pw.printf("\t-o %s\n", flacFile);
		}

		pw.println();
		pw.println("echo");
		pw.println("echo Überprüfung");
		pw.println("$FLAC -tw *.flac");

		pw.println();
		pw.println("echo");
		pw.println("echo Replay-Gain");
		pw.println("$METAFLAC --add-replay-gain *.flac");
	}

	/**
	 * @param pw {@link PrintWriter}
	 * @param album {@link Album}
	 */
	private void writeMP3(final PrintWriter pw, final Album album)
	{
		String diskID = album.getDiskID().getID();
		// List<String> files = new ArrayList<>();

		pw.println("mkdir -p $BASE_DIR/mp3");
		pw.println("cd $BASE_DIR/mp3");
		pw.println("rm -f *.mp3");

		for (Track track : album)
		{
			pw.println();
			pw.print("$LAME");
			pw.print(" -m j");
			pw.print(" -q 0");
			pw.print(" -p");
			// pw.print(" -s 44.1");
			pw.print(String.format(" -b %d", Settings.getInstance().getMp3Bitrate()));
			pw.print(" --replaygain-accurate");
			pw.print(" --add-id3v2");
			pw.print(" --pad-id3v2");
			pw.println(" --ignore-tag-errors \\");
			pw.printf("\t--ta \"%s\" \\\n", track.getArtist());
			pw.printf("\t--tl \"%s\" \\\n", album.getTitle());
			pw.printf("\t--tt \"%s\" \\\n", track.getTitle());
			pw.printf("\t--tg \"%s\" \\\n", album.getGenre());
			pw.printf("\t--ty \"%d\" \\\n", album.getYear());
			pw.printf("\t--tn \"%d/%d\" \\\n", track.getNumber(), album.getTrackCount());
			pw.printf("\t--tc \"%s\" \\\n", album.getComment());
			pw.printf("\t--tv \"DISCID=%s\" \\\n", diskID);
			pw.printf("\t--tv \"DISCNUMBER=%d\" \\\n", album.getDiskNumber());
			pw.printf("\t--tv \"TOTALDISCS=%d\" \\\n", album.getTotalDisks());
			pw.printf("\t--tv \"DISCTOTAL=%d\" \\\n", album.getTotalDisks());  // Für Player-Kompatibilität.
			pw.printf("\t$BASE_DIR/wav/track%02d.cdda.wav", track.getNumber());

			String flacFile = String.format("\"%s (%s) - %02d - %s.mp3\"", track.getArtist(), album.getTitle(), track.getNumber(), track.getTitle());
			// files.add(flacFile);

			pw.printf("\t%s\n", flacFile);
		}

		pw.println();
		pw.println("echo");
		pw.println("echo Überprüfung");
		pw.println("$MP3VAL *.mp3 -f -nb");

		pw.println();
		pw.println("echo");
		pw.println("echo Replay-Gain");
		pw.println("$MP3GAIN -r -k *.mp3");	 // -a
	}

	/**
	 * @param pw {@link PrintWriter}
	 * @param programm String
	 */
	private void writeProgramChecks(final PrintWriter pw, final String programm)
	{
		pw.printf("%s=\"$(which %s)\"\n", StringUtils.upperCase(programm), programm);
		pw.println("if [ $? != 0 ]; then");
		pw.printf("\techo \"%s not installed\"\n", programm);
		pw.println("\texit 5;");
		pw.printf("elif [ ! -x $%s ]; then\n", StringUtils.upperCase(programm));
		pw.printf("\techo \"%s not executable\"\n", programm);
		pw.println("\texit 5;");
		pw.println("fi");
	}

	/**
	 * @param pw {@link PrintWriter}
	 */
	private void writeRip(final PrintWriter pw)
	{
		pw.println();
		pw.println("read -p \"(r)ippen oder (e)ncoden: \" re");
		pw.println("if [ \"$re\" = \"r\" ]; then");
		pw.println("\tmkdir -p $BASE_DIR/wav");
		pw.println("\tcd $BASE_DIR/wav");
		pw.println("\trm -f *.wav");
		pw.printf("\t$CDPARANOIA -w -B -d %s\n", Settings.getInstance().getDevice());
		pw.println("fi");
	}
}
