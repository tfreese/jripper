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
public class CDParanoia
{
	/**
	 * @param args String[]
	 * @throws Exception Falls was schief geht.
	 */
	public static void main(final String[] args) throws Exception
	{
		new Thread(new KeyListenerErsatz()).start();

		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.directory(new File("."));
		processBuilder.redirectErrorStream(true);
		processBuilder.command("cdparanoia", "-w", "-B");// -v, -Q
		// Map<String, String> env = processBuilder.environment();
		// env.put("ipps", "true");
		final Process process = processBuilder.start();

		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			/**
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run()
			{
				if (process != null)
				{
					process.destroy();
				}
			}
		});

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
	 * Erstellt ein neues {@link CDParanoia} Object.
	 */
	private CDParanoia()
	{
		super();
	}
}
