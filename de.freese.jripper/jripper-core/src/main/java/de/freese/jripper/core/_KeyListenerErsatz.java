/**
 * Created: 23.02.2013
 */

package de.freese.jripper.core;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Reagier auf ENTER der Konsole und beendet das Programm.
 * 
 * @author Thomas Freese
 */
public class _KeyListenerErsatz implements Runnable
{
	/**
	 * Erstellt ein neues {@link _KeyListenerErsatz} Object.
	 */
	public _KeyListenerErsatz()
	{
		super();
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		Console console = System.console();
		Reader reader = null;

		if (console != null)
		{
			reader = console.reader();
		}
		else
		{
			reader = new InputStreamReader(System.in);
		}

		if (!(reader instanceof BufferedReader))
		{
			reader = new BufferedReader(reader);
		}

		try
		{
			// Auf Eingabe warten.
			((BufferedReader) reader).readLine();
		}
		catch (Exception ex)
		{
			// Ignore
		}

		System.exit(-1);
	}
}
