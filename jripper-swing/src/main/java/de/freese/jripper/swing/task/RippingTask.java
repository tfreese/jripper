package de.freese.jripper.swing.task;

import de.freese.jripper.core.JRipperUtils;
import de.freese.jripper.core.model.Album;
import de.freese.jripper.core.script.ScriptGenerator;
import de.freese.jripper.core.script.ScriptGeneratorLinux;

import javax.swing.SwingWorker;
import java.io.File;
import java.util.Objects;

/**
 * {@link SwingWorker} for Ripping and Coding.
 *
 * @author Thomas Freese
 * @since 10.10.2013
 */
public class RippingTask extends SwingWorker<Void, Void> {
    private final Album album;

    public RippingTask(final Album album) {
        super();

        this.album = Objects.requireNonNull(album, "album required");
    }

    @Override
    protected Void doInBackground() throws Exception {
        final ScriptGenerator scriptGenerator = new ScriptGeneratorLinux();
        final File script = scriptGenerator.generate(album, JRipperUtils.getWorkDir(album));
        scriptGenerator.execute(script);

        return null;
    }

    @Override
    protected void done() {
        // Empty
    }
}
