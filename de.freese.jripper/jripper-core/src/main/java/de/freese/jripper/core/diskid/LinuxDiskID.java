/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core.diskid;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;

import de.freese.jripper.core.IOSProvider;
import de.freese.jripper.core.process.AbstractProcess;
import de.freese.jripper.core.process.IProcessCallback;

/**
 * Linux Implementierung mit dem Programm "cd-discid".
 * 
 * @author Thomas Freese
 */
public class LinuxDiskID extends AbstractProcess implements IDiskID, IOSProvider,
		IProcessCallback<String>
{
	/**
	 * Erstellt ein neues {@link LinuxDiskID} Object.
	 */
	public LinuxDiskID()
	{
		super();
	}

	/**
	 * @see de.freese.jripper.core.process.IProcessCallback#execute(java.lang.Process,
	 *      java.io.PrintWriter)
	 */
	@Override
	public String execute(final Process process, final PrintWriter printWriter) throws Exception
	{
		StringBuilder sb = new StringBuilder();

		try (BufferedReader inputReader =
				new BufferedReader(new InputStreamReader(process.getInputStream())))
		{
			String line = null;

			while ((line = inputReader.readLine()) != null)
			{
				sb.append(line);
			}
		}

		return sb.toString();
	}

	/**
	 * @see de.freese.jripper.core.diskid.IDiskID#getDiskID(java.lang.String)
	 */
	@Override
	public String getDiskID(final String device) throws Exception
	{
		List<String> command = new ArrayList<>();
		command.add("cd-discid");
		command.add(device);

		String id = execute(command, new File("."), null, this);

		if (id.contains("No medium"))
		{
			throw new IllegalStateException("no music cd found");
		}

		return id;
	}

	/**
	 * @see de.freese.jripper.core.IOSProvider#isSupportedOS(java.lang.String)
	 */
	@Override
	public boolean isSupportedOS(final String os)
	{
		return SystemUtils.IS_OS_LINUX;
	}
}
