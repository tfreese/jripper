// Created: 23.02.2013
package de.freese.jripper.core;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Reagiert auf ENTER der Konsole und beendet das Programm.
 *
 * @author Thomas Freese
 */
public class KeyListenerErsatz implements Runnable {
    @Override
    public void run() {
        final Console console = System.console();
        Reader reader = null;

        if (console != null) {
            reader = console.reader();
        }
        else {
            reader = new InputStreamReader(System.in);
        }

        if (!(reader instanceof BufferedReader)) {
            reader = new BufferedReader(reader);
        }

        try {
            // Read Input.
            ((BufferedReader) reader).readLine();
        }
        catch (Exception _) {
            // Ignore
        }

        System.exit(-1);
    }
}
