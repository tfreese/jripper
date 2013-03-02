/**
 * Created: 02.03.2013
 */

package de.freese.jripper.core.ripper;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;

import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.process.AbstractProcess;

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
	 * @see de.freese.jripper.core.IOSProvider#isSupportedOS(java.lang.String)
	 */
	@Override
	public boolean isSupportedOS(final String os)
	{
		return SystemUtils.IS_OS_LINUX;
	}

	/**
	 * @see de.freese.jripper.core.ripper.IRipper#rip(java.lang.String,
	 *      de.freese.jripper.core.model.Album, java.io.File, java.io.PrintWriter)
	 */
	@Override
	public void rip(final String device, final Album album, final File directory,
					final PrintWriter printWriter) throws Exception
	{
		List<String> command = new ArrayList<>();
		command.add("cdparanoia");
		command.add("-w");
		command.add("-B");

		execute(command, directory, printWriter, null);
	}
}
