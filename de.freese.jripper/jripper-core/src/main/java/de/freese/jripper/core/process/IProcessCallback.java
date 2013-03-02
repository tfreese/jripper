/**
 * Created: 02.03.2013
 */

package de.freese.jripper.core.process;

import java.io.PrintWriter;

/**
 * Interface für einen {@link Process}.
 * 
 * @author Thomas Freese
 * @param <T> Konkreter Rückgabewert
 */
public interface IProcessCallback<T>
{
	/**
	 * Ausführung von Logic innerhalb einen {@link Process}.
	 * 
	 * @param process {@link Process}
	 * @param printWriter {@link PrintWriter}
	 * @return Object
	 * @throws Exception Falls was schief geht.
	 */
	public T execute(Process process, PrintWriter printWriter) throws Exception;
}
