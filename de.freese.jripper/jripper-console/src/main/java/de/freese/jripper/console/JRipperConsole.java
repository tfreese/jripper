/**
 * Created: 26.02.2013
 */

package de.freese.jripper.console;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;

/**
 * Console-View für den JRipper.
 * 
 * @author Thomas Freese
 */
public class JRipperConsole implements IAnsiCodes
{
	/**
	 * 
	 */
	private final boolean isDevelopment;

	/**
	 * 
	 */
	private final PrintStream printStream;

	/**
	 * 
	 */
	private final BufferedReader reader;

	/**
	 * Erstellt ein neues {@link JRipperConsole} Object.
	 */
	public JRipperConsole()
	{
		super();

		this.printStream = System.out;

		Console console = System.console();
		Reader reader = null;

		if (console != null)
		{
			reader = console.reader();
		}
		else
		{
			// In Eclipse kann Console null sein.
			reader = new InputStreamReader(System.in);
		}

		if (!(reader instanceof BufferedReader))
		{
			reader = new BufferedReader(reader);
		}

		this.reader = (BufferedReader) reader;

		this.isDevelopment = System.getProperty("dev.env") == null ? false : true;
	}

	/**
	 * Liefert dein eingegebenen Wert von der Konsole.
	 * 
	 * @return String
	 */
	private String getInput()
	{
		String line = "";

		try
		{
			line = this.reader.readLine();
		}
		catch (Exception ex)
		{
			print("%s%s%s", ANSI_RED, ex.getMessage(), ANSI_RESET);
		}

		return line;
	}

	/**
	 * @param format String
	 * @param params Object[]
	 * @see String#format(String, Object...)
	 */
	private void print(final String format, final Object...params)
	{
		if (this.isDevelopment)
		{
			// In der Entwicklungsumgebung die ANSI-Codes entfernen.
			for (int i = 0; i < params.length; i++)
			{
				switch (params[i].toString())
				{
					case ANSI_CYAN:
					case ANSI_RED:
					case ANSI_RESET:
						params[i] = "";
						break;

					default:
						break;
				}
			}
		}

		this.printStream.format(format, params);
	}

	/**
	 * 
	 */
	public void showMainMenu()
	{

		print("%s\n", "*****************");
		print("%s\n", "JRipper Hauptmenü");
		print("%s\n", "*****************");

		print("%s%s%s \t%s\n", ANSI_CYAN, "1", ANSI_RESET, "FreeDB auslesen");
		print("%s\n", "");
		print("%s%s%s \t%s\n", ANSI_CYAN, "q", ANSI_RESET, "Beenden");

		String input = getInput();

		switch (input)
		{
			case "1":
				// TODO
				break;

			case "q":
				return;

			default:
				print("%s%s \t%s%s\n", ANSI_RED, input, "Unbekannte Eingabe", ANSI_RESET);
				break;
		}

		showMainMenu();
	}
}
