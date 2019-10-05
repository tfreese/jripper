/**
 * Created: 10.10.2013
 */

package de.freese.jripper.swing.task;

import java.io.File;
import java.util.Objects;
import javax.swing.SwingWorker;
import de.freese.jripper.core.JRipperUtils;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.script.ScriptGenerator;
import de.freese.jripper.core.script.ScriptGeneratorLinux;

/**
 * {@link SwingWorker} für das Rippen und Codieren.
 *
 * @author Thomas Freese
 */
public class RippingTask extends SwingWorker<Void, Void>
{
    /**
     *
     */
    private final Album album;

    /**
     * Erstellt ein neues {@link RippingTask} Object.
     *
     * @param album {@link Album}
     */
    public RippingTask(final Album album)
    {
        super();

        this.album = Objects.requireNonNull(album, "album required");
    }

    /**
     * @see javax.swing.SwingWorker#doInBackground()
     */
    @Override
    protected Void doInBackground() throws Exception
    {
        ScriptGenerator scriptGenerator = new ScriptGeneratorLinux();
        File script = scriptGenerator.generate(this.album, JRipperUtils.getWorkDir(this.album));
        scriptGenerator.execute(script);

        return null;
    }

    /**
     * @see javax.swing.SwingWorker#done()
     */
    @Override
    protected void done()
    {
        // Empty
    }
}
