package de.freese.jripper.core.ripper;

import java.io.File;

import de.freese.jripper.core.OSProvider;
import de.freese.jripper.core.callback.ProcessCallback;

/**
 * @author Thomas Freese
 * @since 02.03.2013
 */
public interface Ripper extends OSProvider {
    void rip(String device, File directory, ProcessCallback monitor) throws Exception;
}
