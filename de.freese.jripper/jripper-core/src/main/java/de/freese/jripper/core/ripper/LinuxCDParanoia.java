/**
 * Created: 02.03.2013
 */

package de.freese.jripper.core.ripper;

import de.freese.jripper.core.process.AbstractProcess;
import de.freese.jripper.core.process.IProcessMonitor;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.SystemUtils;

/**
 * Linux Implementierung mit dem Programm "cdparanoia".
 * 
 * @author Thomas Freese
 */
public class LinuxCDParanoia extends AbstractProcess implements IRipper
{
	/**
	 * Erstellt ein neues {@link LinuxCDParanoia} Object.
	 */
	public LinuxCDParanoia()
	{
		super();
	}

	/**
	 * @see de.freese.jripper.core.ripper.IRipper#rip(java.lang.String, java.io.File, de.freese.jripper.core.process.IProcessMonitor)
	 */
	@Override
	public void rip(final String device, final File directory, final IProcessMonitor monitor) throws Exception
	{
		List<String> command = new ArrayList<>();
		command.add("cdparanoia");
		command.add("-w");
		command.add("-B"); // Batch, Jeder Track in eine Datei
		// command.add("-v");
		command.add("-d");
		command.add(device);

		execute(command, directory, monitor);
	}

	/**
	 * @see de.freese.jripper.core.IOSProvider#supportsOS(java.lang.String)
	 */
	@Override
	public boolean supportsOS(final String os)
	{
		return SystemUtils.IS_OS_LINUX;
	}
}
