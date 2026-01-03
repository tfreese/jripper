// Created: 25.02.2013
package de.freese.jripper.core.encoder;

import java.io.File;

import de.freese.jripper.core.OSProvider;
import de.freese.jripper.core.callback.LoggerCallback;
import de.freese.jripper.core.callback.ProcessCallback;
import de.freese.jripper.core.model.Album;

/**
 * @author Thomas Freese
 */
public interface Encoder extends OSProvider {
    void encode(Album album, File directory, ProcessCallback processCallback, LoggerCallback loggerCallback) throws Exception;

    EncoderFormat getFormat();
}
