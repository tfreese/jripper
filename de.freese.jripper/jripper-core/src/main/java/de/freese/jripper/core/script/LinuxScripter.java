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
			pw.println();
			writeProgramChecks(pw, "flac");
			pw.println();
			writeProgramChecks(pw, "metaflac");
			pw.println();
			writeProgramChecks(pw, "lame");
			pw.println();
			writeProgramChecks(pw, "mp3val");
			pw.println();
			writeProgramChecks(pw, "mp3gain");

			// Variablen
			pw.println();
			pw.println("BASE_DIR=$PWD");
			// pw.printf("BASE_DIR=%s/\n", folder.getAbsoluteFile());

			// cdparanoia
			pw.println();
			pw.println("mkdir -p $BASE_DIR/wav");
			pw.println("cd $BASE_DIR/wav");
			pw.println("rm -f *.wav");
			pw.printf("$CDPARANOIA -w -B -d %s\n", Settings.getInstance().getDevice());

			// flac
			pw.println();
			writeFLAC(pw, album);

			// mp3
			pw.println();
			writeMP3(pw, album);

			// Shell offen lassen
			pw.println();
			pw.println("$SHELL");
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
		List<String> files = new ArrayList<>();

		pw.println("mkdir -p $BASE_DIR/flac");
		pw.println("cd $BASE_DIR/flac");
		pw.println("rm -f *.flac");

		for (Track track : album)
		{
			pw.println();
			pw.print("$FLAC");
			pw.print(" -8");
			pw.print(" -V");
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
			files.add(flacFile);

			pw.printf("\t-o %s\n", flacFile);
		}

		pw.println();

		pw.println("echo Überprüfung");
		pw.println("$FLAC -tw *.flac");

		pw.println();

		pw.println("echo Replay-Gain");
		pw.println("$METAFLAC --add-replay-gain *.flac");
	}

	/**
	 * @param pw {@link PrintWriter}
	 * @param album {@link Album}
	 */
	private void writeMP3(final PrintWriter pw, final Album album)
	{
		// TODO
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
}
