// Created: 25.02.2013
package de.freese.jripper.core.diskid;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.freese.jripper.core.JRipperUtils;
import de.freese.jripper.core.Settings;
import de.freese.jripper.core.model.DiskID;
import de.freese.jripper.core.process.AbstractProcess;
import de.freese.jripper.core.process.ProcessMonitor;

/**
 * Linux Implementierung mit dem Programm "cd-discid".
 *
 * @author Thomas Freese
 */
public class DiskIDProviderLinux extends AbstractProcess implements DiskIDProvider, ProcessMonitor
{
    /**
     *
     */
    private StringBuilder sb;

    /**
     * @see de.freese.jripper.core.diskid.DiskIDProvider#getDiskID(java.lang.String)
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

        return new DiskID(id);
    }

    /**
     * @see de.freese.jripper.core.process.ProcessMonitor#monitorProcess(java.lang.String)
     */
    @Override
    public void monitorProcess(final String line)
    {
        this.sb.append(line);
    }

    /**
     * @see de.freese.jripper.core.process.ProcessMonitor#monitorText(java.lang.String)
     */
    @Override
    public void monitorText(final String line)
    {
        // Empty
    }

    /**
     * @see de.freese.jripper.core.OSProvider#supportsOS(java.lang.String)
     */
    @Override
    public boolean supportsOS(final String os)
    {
        return JRipperUtils.isLinux();
    }
}
