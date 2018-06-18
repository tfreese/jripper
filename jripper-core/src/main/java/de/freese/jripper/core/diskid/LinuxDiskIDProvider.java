/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core.diskid;

import de.freese.jripper.core.Settings;
import de.freese.jripper.core.model.DiskID;
import de.freese.jripper.core.process.AbstractProcess;
import de.freese.jripper.core.process.IProcessMonitor;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.SystemUtils;

/**
 * Linux Implementierung mit dem Programm "cd-discid".
 * 
 * @author Thomas Freese
 */
public class LinuxDiskIDProvider extends AbstractProcess implements IDiskIDProvider, IProcessMonitor
{
	/**
	 * 
	 */
	private StringBuilder sb = null;

	/**
	 * Erstellt ein neues {@link LinuxDiskIDProvider} Object.
	 */
	public LinuxDiskIDProvider()
	{
		super();
	}

	/**
	 * @see de.freese.jripper.core.diskid.IDiskIDProvider#getDiskID(java.lang.String)
	 */
	@Override
	public DiskID getDiskID(final String device) throws Exception
	{
		List<String> command = new ArrayList<>();
		command.add("cd-discid");
		command.add(device);

		this.sb = new StringBuilder();

		execute(command, new File(Settings.getInstance().getWorkDir()), this);

		String id = this.sb.toString();

		getLogger().debug(id);

		if (id.contains("No medium"))
		{
			throw new IllegalStateException("no music cd found");
		}

		DiskID diskID = new DiskID(id);

		return diskID;
	}

	/**
	 * @see de.freese.jripper.core.process.IProcessMonitor#monitorProcess(java.lang.String)
	 */
	@Override
	public void monitorProcess(final String line)
	{
		this.sb.append(line);
	}

	/**
	 * @see de.freese.jripper.core.process.IProcessMonitor#monitorText(java.lang.String)
	 */
	@Override
	public void monitorText(final String line)
	{
		// Empty
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
