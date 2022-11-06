// Created: 11.10.2013
package de.freese.jripper.core.script;

import java.io.File;

import de.freese.jripper.core.model.Album;

/**
 * Interface für einen Script-Generator.
 *
 * @author Thomas Freese
 */
public interface ScriptGenerator
{
    /**
     * Führt das Skript aus.
     */
    void execute(File script) throws Exception;

    /**
     * Erstellt das Skript mit Ausführungsrechten.
     */
    File generate(Album album, File folder) throws Exception;
}
