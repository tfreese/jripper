// Created: 25.02.2013
package de.freese.jripper.core.diskid;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.freese.jripper.core.JRipperUtils;
import de.freese.jripper.core.Settings;
import de.freese.jripper.core.model.DiskId;
import de.freese.jripper.core.process.AbstractProcess;

/**
 * Linux Implementation with "cd-discid".
 *
 * @author Thomas Freese
 */
public class DiskIDProviderLinux extends AbstractProcess implements DiskIDProvider {
    @Override
    public DiskId getDiskID(final String device) throws Exception {
        final List<String> command = new ArrayList<>();
        command.add("cd-discid");
        command.add(device);

        final StringBuilder sb = new StringBuilder();

        execute(command, new File(Settings.getInstance().getWorkDir()), sb::append);

        final String id = sb.toString();

        getLogger().debug(id);

        if (id.contains("No medium")) {
            throw new IllegalStateException("no music cd found");
        }

        return new DiskId(id);
    }

    @Override
    public boolean supportsOS(final String os) {
        return JRipperUtils.isLinux();
    }
}
