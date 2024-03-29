// Created: 25.02.2013
package de.freese.jripper.console;

/**
 * Interface für die Farbgebung auf der Console.
 *
 * @author Thomas Freese
 */
@SuppressWarnings("checkstyle:AvoidEscapedUnicodeCharacters")
public final class AnsiCodes {
    static final String ANSI_BLACK = "\u001B[30m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_CYAN = "\u001B[36m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_PURPLE = "\u001B[35m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_WHITE = "\u001B[37m";
    static final String ANSI_YELLOW = "\u001B[33m";

    private AnsiCodes() {
        super();
    }
}
