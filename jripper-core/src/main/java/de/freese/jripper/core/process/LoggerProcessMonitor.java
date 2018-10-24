/**
 * Created: 07.10.2013
 */

package de.freese.jripper.core.process;

import java.io.PrintWriter;
import org.slf4j.Logger;

/**
 * {@link ProcessMonitor} für einen {@link PrintWriter}.
 * 
 * @author Thomas Freese
 */
public class LoggerProcessMonitor implements ProcessMonitor
{
	/**
	 * 
	 */
	private final Logger logger;

	/**
	 * Erstellt ein neues {@link LoggerProcessMonitor} Object.
	 * 
	 * @param logger {@link Logger}
	 */
	public LoggerProcessMonitor(final Logger logger)
	{
		super();

		this.logger = logger;
	}

	/**
	 * @return {@link Logger}
	 */
	protected Logger getLogger()
	{
		return this.logger;
	}

	/**
	 * @see de.freese.jripper.core.process.ProcessMonitor#monitorProcess(java.lang.String)
	 */
	@Override
	public void monitorProcess(final String line)
	{
		getLogger().info(line);
	}

	/**
	 * @see de.freese.jripper.core.process.ProcessMonitor#monitorText(java.lang.String)
	 */
	@Override
	public void monitorText(final String line)
	{
		getLogger().info(line);
	}
}
