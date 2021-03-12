/**
 * Created: 11.10.2013
 */
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
     * 
     * @param script {@link File}
     * @throws Exception Falls was schief geht.
     */
    public void execute(File script) throws Exception;

    /**
     * Erstellt das Skript mit Ausführungsrechten.
     * 
     * @param album {@link Album}
     * @param folder {@link File}
     * @return {@link File}
     * @throws Exception Falls was schief geht.
     */
    public File generate(Album album, File folder) throws Exception;
}
