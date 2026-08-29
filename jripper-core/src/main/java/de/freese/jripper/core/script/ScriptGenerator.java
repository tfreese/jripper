package de.freese.jripper.core.script;

import java.io.File;

import de.freese.jripper.core.model.Album;

/**
 * @author Thomas Freese
 * @since 11.10.2013
 */
public interface ScriptGenerator {
    /**
     * Execute the Script.
     */
    void execute(File script) throws Exception;

    /**
     * Create the Script.
     */
    File generate(Album album, File folder) throws Exception;
}
