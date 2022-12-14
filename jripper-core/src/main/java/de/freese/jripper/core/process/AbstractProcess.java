// Created: 02.03.2013
package de.freese.jripper.core.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import de.freese.jripper.core.JRipper;
import org.slf4j.Logger;

/**
 * Basisklasse für alle Implementierungen die den {@link ProcessBuilder} verwenden.
 *
 * @author Thomas Freese
 */
public abstract class AbstractProcess
{
    public final Logger logger = JRipper.getInstance().getLogger();// LoggerFactory.getLogger(getClass());

    private static Thread createShutDownHook(final Process process)
    {
        return new Thread()
        {
            /**
             * @see java.lang.Thread#run()
             */
            @Override
            public void run()
            {
                if (process != null)
                {
                    process.destroy();
                }
            }
        };
    }

    protected AbstractProcess()
    {
        super();
    }

    protected void execute(final List<String> command, final File directory, final ProcessMonitor monitor) throws Exception
    {
        if (!directory.exists())
        {
            directory.mkdirs();
        }

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(directory);
        processBuilder.command(command);
        processBuilder.redirectErrorStream(true);
        // Map<String, String> env = processBuilder.environment();
        // env.put("tommy", "true");

        Process process = processBuilder.start();

        Thread hook = createShutDownHook(process);
        Runtime.getRuntime().addShutdownHook(hook);

        try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)))
        {
            String line = null;

            while ((line = inputReader.readLine()) != null)
            {
                monitor.monitorProcess(line);
            }
        }

        int exitVal = process.waitFor();

        process.destroy();
        Runtime.getRuntime().removeShutdownHook(hook);

        if (exitVal != 0)
        {
            throw new IllegalStateException("return code: " + exitVal);
        }
    }

    protected Logger getLogger()
    {
        return this.logger;
    }
}
