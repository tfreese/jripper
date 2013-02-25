/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core.diskid;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.apache.commons.lang3.SystemUtils;

import de.freese.jripper.core.IOSProvider;

/**
 * Linux Implementierung mit dem Programm "cd-discid".
 * 
 * @author Thomas Freese
 */
public class LinuxDiskIDService implements IDiskIDService, IOSProvider
{
	/**
	 * Erstellt ein neues {@link LinuxDiskIDService} Object.
	 */
	public LinuxDiskIDService()
	{
		super();
	}

	/**
	 * @see de.freese.jripper.core.diskid.IDiskIDService#getDiskID(java.lang.String)
	 */
	@Override
	public String getDiskID(final String device) throws Exception
	{
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.directory(new File("."));
		processBuilder.command("cd-discid", device);
		processBuilder.redirectErrorStream(true);
		// Map<String, String> env = processBuilder.environment();
		// env.put("ipps", "true");
		Process process = processBuilder.start();

		BufferedReader inputReader =
				new BufferedReader(new InputStreamReader(process.getInputStream()));

		StringBuilder sb = new StringBuilder();
		String line = null;

		while ((line = inputReader.readLine()) != null)
		{
			sb.append(line);
		}

		String id = sb.toString();

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
