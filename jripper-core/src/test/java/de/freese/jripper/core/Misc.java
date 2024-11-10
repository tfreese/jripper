// Created: 07.03.2013
package de.freese.jripper.core;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thomas Freese
 */
public final class Misc {
    private static final Logger LOGGER = LoggerFactory.getLogger(Misc.class);

    public static void main(final String[] args) {
        final String s = "100/12453 ( 1%)| 0:00/ 0:21| 0:00/ 0:22| 15.366x| 0:22";
        // Pattern pattern = Pattern.compile("[(\\s[\\d]{1,2}%)]?");
        // Pattern pattern = Pattern.compile("[\\(][.*][\\)]"); // \[(.*?)\], /[^(<td>)].+[^(</td>)]/;
        // Pattern pattern = Pattern.compile("^[0-9]{1,}+/[0-9]{1,}+.*"); ///\(.*\)/ versucht? Alternativ würde mir grad noch das hier einfallen: /\(.*\)/U
        final Pattern pattern = Pattern.compile("[(.*)]");
        final Matcher matcher = pattern.matcher(s);

        LOGGER.info("{}", matcher.matches());

        if (matcher.matches()) {
            LOGGER.info(matcher.group());
        }

        final int start = s.indexOf(" (");
        final int end = s.indexOf("%)");

        if (start > 0 && end > 0) {
            final String prozent = s.substring(start + 2, end).strip();
            LOGGER.info(prozent);
        }

        // Shell öffnen und Skript ausführen.
        final List<String> command = new ArrayList<>();

        command.add("konsole");
        // command.add("--nofork");
        command.add("--new-tab");
        command.add("--hold");
        command.add("-e");
        command.add("/tmp/test.sh");

        // command.add("xterm");
        // // command.add("-T");
        // // command.add("MyTest");
        // // command.add("-n");
        // // command.add("MyTest minimized");
        // command.add("-bg");
        // command.add("black");
        // command.add("-fg");
        // command.add("white");
        // command.add("-geometry");
        // command.add("250x35");
        // command.add("-hold");
        // command.add("-e");
        // command.add("/tmp/test.sh");
        // // command.add(";");
        // // command.add("le_exec");

        // try {
        // System.out.println(System.getenv().get("TERM"));
        // ProcessBuilder processBuilder = new ProcessBuilder();
        // processBuilder.directory(new File("/tmp"));
        // processBuilder.command(command);
        // // processBuilder.redirectErrorStream(true);
        // // Map<String, String> env = processBuilder.environment();
        // // env.put("tommy", "true");
        // Process process = processBuilder.start();
        // // process.waitFor();
        // }
        // catch (Exception ex) {
        // ex.printStackTrace();
        // }
    }

    private Misc() {
        super();
    }
}
