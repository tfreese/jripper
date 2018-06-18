/**
 * Created: 07.10.2013
 */

package de.freese.jripper.core.process;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link IProcessMonitor} der mehrere Monitore zusammenfasst.
 * 
 * @author Thomas Freese
 */
public class CompositeProgressMonitor implements IProcessMonitor
{
	/**
	 * 
	 */
	private final List<IProcessMonitor> monitore;

	/**
	 * Erstellt ein neues {@link CompositeProgressMonitor} Object.
	 */
	public CompositeProgressMonitor()
	{
		super();

		this.monitore = new ArrayList<>();
	}

	/**
	 * Hinzufügern neuer Monitore.
	 * 
	 * @param monitor {@link IProcessMonitor}
	 * @param monitore {@link IProcessMonitor}[]
	 */
	public void addMonitor(final IProcessMonitor monitor, final IProcessMonitor...monitore)
	{
		this.monitore.add(monitor);

		for (IProcessMonitor pm : monitore)
		{
			this.monitore.add(pm);
		}
	}

	/**
	 * @see de.freese.jripper.core.process.IProcessMonitor#monitorProcess(java.lang.String)
	 */
	@Override
	public void monitorProcess(final String line)
	{
		for (IProcessMonitor monitor : this.monitore)
		{
			monitor.monitorProcess(line);
		}
	}

	/**
	 * @see de.freese.jripper.core.process.IProcessMonitor#monitorText(java.lang.String)
	 */
	@Override
	public void monitorText(final String line)
	{
		for (IProcessMonitor monitor : this.monitore)
		{
			monitor.monitorText(line);
		}
	}
}
