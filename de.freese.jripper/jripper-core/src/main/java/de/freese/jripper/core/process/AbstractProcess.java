/**
 * Created: 02.03.2013
 */

package de.freese.jripper.core.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Basisklasse für alle Implementierungen die den {@link ProcessBuilder} verwenden.
 * 
 * @author Thomas Freese
 */
public abstract class AbstractProcess
{
	/**
	 * Erstellt ein neues {@link AbstractProcess} Object.
	 */
	public AbstractProcess()
	{
		super();
	}

	/**
	 * @param process {@link Process}
	 * @return {@link Thread}
	 */
	private Thread createShutDownHook(final Process process)
	{
		Thread t = new Thread()
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
		};

		return t;
	}

	/**
	 * @param directory File
	 * @param command {@link List}
	 * @param monitor {@link IProcessMonitor}
	 * @throws Exception Falls was schief geht.
	 */
	protected void execute(final List<String> command, final File directory, final IProcessMonitor monitor) throws Exception
	{
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.directory(directory);
		processBuilder.command(command);
		processBuilder.redirectErrorStream(true);
		// Map<String, String> env = processBuilder.environment();
		// env.put("tommy", "true");
		Process process = processBuilder.start();

		Thread hook = createShutDownHook(process);
		Runtime.getRuntime().addShutdownHook(hook);

		try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream())))
		{
			String line = null;

			while ((line = inputReader.readLine()) != null)
			{
				monitor.monitorProcess(line);
			}
		}

		int exitVal = process.waitFor();

		process.destroy();
		Runtime.getRuntime().removeShutdownHook(hook);

		if (exitVal != 0)
		{
			throw new IllegalStateException("return code: " + exitVal);
		}
	}
}
