// Created: 25.02.2013
package de.freese.jripper.core.encoder;

import java.io.File;

import de.freese.jripper.core.OSProvider;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.process.ProcessMonitor;

/**
 * Interface für die möglichen Encoder.
 *
 * @author Thomas Freese
 */
public interface Encoder extends OSProvider {
    void encode(Album album, File directory, ProcessMonitor monitor) throws Exception;

    EncoderFormat getFormat();
}
