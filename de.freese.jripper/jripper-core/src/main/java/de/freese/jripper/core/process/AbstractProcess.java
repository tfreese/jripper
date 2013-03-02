/**
 * Created: 02.03.2013
 */

package de.freese.jripper.core.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Basisklasse für alle Implementierungen die den {@link ProcessBuilder} verwenden.
 * 
 * @author Thomas Freese
 */
public abstract class AbstractProcess
{
	/**
	 * Ausgabe des Processes auf der Console.
	 * 
	 * @author Thomas Freese
	 */
	private static class ConsoleProcessCallback implements IProcessCallback<Void>
	{
		/**
		 * Erstellt ein neues {@link ConsoleProcessCallback} Object.
		 */
		private ConsoleProcessCallback()
		{
			super();
		}

		/**
		 * @see de.freese.jripper.core.process.IProcessCallback#execute(java.lang.Process,
		 *      java.io.PrintWriter)
		 */
		@Override
		public Void execute(final Process process, final PrintWriter printWriter) throws Exception
		{
			try (BufferedReader inputReader =
					new BufferedReader(new InputStreamReader(process.getInputStream())))
			{
				String line = null;

				while ((line = inputReader.readLine()) != null)
				{
					if (StringUtils.isBlank(line))
					{
						continue;
					}

					printWriter.println(line);
					printWriter.flush();
				}
			}

			return null;
		}
	}

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
	protected Thread createShutDownHook(final Process process)
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
	 * @param printWriter {@link PrintWriter}
	 * @param callback {@link IProcessCallback}
	 * @return Object
	 * @throws Exception Falls was schief geht.
	 */
	protected <T> T execute(final List<String> command, final File directory,
							final PrintWriter printWriter, final IProcessCallback<T> callback)
		throws Exception
	{
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.directory(directory);
		processBuilder.command(command);
		processBuilder.redirectErrorStream(true);
		// Map<String, String> env = processBuilder.environment();
		// env.put("ipps", "true");
		Process process = processBuilder.start();

		Thread hook = createShutDownHook(process);
		Runtime.getRuntime().addShutdownHook(hook);

		T value = null;

		if (callback == null)
		{
			IProcessCallback<Void> cb = new ConsoleProcessCallback();
			cb.execute(process, printWriter);
		}
		else
		{
			value = callback.execute(process, printWriter);
		}

		int exitVal = process.waitFor();

		process.destroy();
		Runtime.getRuntime().removeShutdownHook(hook);

		if (exitVal != 0)
		{
			throw new IllegalStateException("return code: " + exitVal);
		}

		return value;
	}
}
