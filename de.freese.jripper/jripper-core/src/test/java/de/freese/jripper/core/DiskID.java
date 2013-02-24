/**
 * Created: 23.02.2013
 */

package de.freese.jripper.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * @author Thomas Freese
 */
public class DiskID
{
	/**
	 * @param args String[]
	 * @throws Exception Falls was schief geht.
	 */
	public static void main(final String[] args) throws Exception
	{
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.directory(new File("."));
		processBuilder.command("cd-discid", "/dev/dvd");
		processBuilder.redirectErrorStream(true);
		// Map<String, String> env = processBuilder.environment();
		// env.put("ipps", "true");
		Process process = processBuilder.start();

		BufferedReader inputReader =
				new BufferedReader(new InputStreamReader(process.getInputStream()));

		String line = null;

		while ((line = inputReader.readLine()) != null)
		{
			System.out.println(line);
		}

		int exitVal = process.waitFor();
		System.out.println("Exit value: " + exitVal);
	}

	/**
	 * Erstellt ein neues {@link DiskID} Object.
	 */
	private DiskID()
	{
		super();
	}
}
