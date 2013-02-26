/**
 * Created: 26.02.2013
 */

package de.freese.jripper.console;


/**
 * Startklasse.
 * 
 * @author Thomas Freese
 */
public final class Main
{
	// /**
	// * Liefert die möglichen Optionen der Kommandozeile.<br>
	// * Dies sind die JRE Programm Argumente.
	// *
	// * @return {@link Options}
	// */
	// private static Options getCommandOptions()
	// {
	// Options options = new Options();
	//
	// // GUI.
	// Option option = new Option("gui", "[console,swing]");
	// option.setRequired(true);
	// option.setArgs(1);
	// options.addOption(option);
	//
	// // // Springkonfiguration.
	// // Option option = new Option("springConfig", "Spring-Konfiguration: springClient*.xml");
	// // option.setRequired(true);
	// // option.setArgs(1);
	// // options.addOption(option);
	//
	// // // LoginMethoden.
	// // option =
	// // new Option("loginMethods",
	// // "Loginmethoden: [AUTH_ID_PWD] [AUTH_ID_PKI] [AUTH_ID_SECURID]");
	// // option.setRequired(true);
	// // option.setArgs(3);
	// // options.addOption(option);
	//
	// return options;
	// }

	/**
	 * @param args String[]
	 */
	public static void main(final String[] args)
	{
		JRipperConsole console = new JRipperConsole();
		console.showMainMenu();
		// // StartParameter auslesen.
		// Options options = Main.getCommandOptions();
		// CommandLine line = null;
		//
		// try
		// {
		// CommandLineParser parser = new GnuParser();
		// line = parser.parse(options, args);
		// }
		// catch (Exception ex)
		// {
		// LoggerFactory.getLogger(Main.class).error(ex.getMessage());
		//
		// HelpFormatter formatter = new HelpFormatter();
		// formatter.printHelp("Main", options, true);
		//
		// System.exit(-1);
		// return;
		// }
	}

	/**
	 * Erstellt ein neues {@link Main} Object.
	 */
	private Main()
	{
		super();
	}
}
