/**
 * Created: 07.10.2013
 */

package de.freese.jripper.core.process;

import java.io.PrintWriter;

/**
 * {@link IProcessMonitor} für einen {@link PrintWriter}.
 * 
 * @author Thomas Freese
 */
public class PrintWriterProcessMonitor implements IProcessMonitor
{
	/**
	 * 
	 */
	private final PrintWriter printWriter;

	/**
	 * Erstellt ein neues {@link PrintWriterProcessMonitor} Object.
	 * 
	 * @param printWriter {@link PrintWriter}
	 */
	public PrintWriterProcessMonitor(final PrintWriter printWriter)
	{
		super();

		this.printWriter = printWriter;
	}

	/**
	 * @return {@link PrintWriter}
	 */
	protected PrintWriter getPrintWriter()
	{
		return this.printWriter;
	}

	/**
	 * @see de.freese.jripper.core.process.IProcessMonitor#monitorProcess(java.lang.String)
	 */
	@Override
	public void monitorProcess(final String line)
	{
		this.printWriter.println(line);
		this.printWriter.flush();
	}

	/**
	 * @see de.freese.jripper.core.process.IProcessMonitor#monitorText(java.lang.String)
	 */
	@Override
	public void monitorText(final String line)
	{
		this.printWriter.println(line);
		this.printWriter.flush();
	}
}
