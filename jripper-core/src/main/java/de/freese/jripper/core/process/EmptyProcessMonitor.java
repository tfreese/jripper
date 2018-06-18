/**
 * Created: 21.10.2013
 */

package de.freese.jripper.core.process;

/**
 * Leerer {@link IProcessMonitor}.
 * 
 * @author Thomas Freese
 */
public class EmptyProcessMonitor implements IProcessMonitor
{
	/**
	 * Erstellt ein neues {@link EmptyProcessMonitor} Object.
	 */
	public EmptyProcessMonitor()
	{
		super();
	}

	/**
	 * @see de.freese.jripper.core.process.IProcessMonitor#monitorProcess(java.lang.String)
	 */
	@Override
	public void monitorProcess(final String line)
	{
		// Empty
	}

	/**
	 * @see de.freese.jripper.core.process.IProcessMonitor#monitorText(java.lang.String)
	 */
	@Override
	public void monitorText(final String line)
	{
		// Empty
	}
}
