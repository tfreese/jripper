/**
 * Created: 07.10.2013
 */

package de.freese.jripper.core.encoder;

import de.freese.jripper.core.process.IProcessMonitor;
import de.freese.jripper.core.process.PrintWriterProcessMonitor;
import java.io.PrintWriter;

/**
 * {@link IProcessMonitor} für LAME.
 * 
 * @author Thomas Freese
 */
public class LameProcessMonitor extends PrintWriterProcessMonitor
{
	/**
	 * Erstellt ein neues {@link LameProcessMonitor} Object.
	 * 
	 * @param printWriter {@link PrintWriter}
	 */
	public LameProcessMonitor(final PrintWriter printWriter)
	{
		super(printWriter);
	}

	// Invalid field value: 'DISCID=b111140e'. Ignored
	// Invalid field value: 'DISCNUMBER=1'. Ignored
	// Invalid field value: 'TOTALDISCS=1'. Ignored
	// Invalid field value: 'DISCTOTAL=1'. Ignored
	// LAME 3.99.5 64bits (http://lame.sf.net)
	// Using polyphase lowpass filter, transition band: 20094 Hz - 20627 Hz
	// Encoding ../wav/track01.cdda.wav
	// to Karat (Vierzehn Karat - Ihre Größten Hits) - 01 - Der Blaue Planet.mp3
	// Encoding as 44.1 kHz j-stereo MPEG-1 Layer III (4.4x) 320 kbps qval=0
	// Frame | CPU time/estim | REAL time/estim | play/CPU | ETA
	// 0/ ( 0%)| 0:00/ : | 0:00/ : | x| :
	// 05:25--------------------------------------------------------------------------
	// kbps % %
	// 0.0 [A[A[A
	// 0/12453 ( 0%)| 0:00/ 0:00| 0:00/ 0:00| 0.0000x| 0:00
	// 05:25--------------------------------------------------------------------------
	// kbps % %
	// 0.0 [A[A[A
	// 100/12453 ( 1%)| 0:00/ 0:21| 0:00/ 0:22| 15.366x| 0:22
	// 05:22-------------------------------------------------------------------------
	// kbps LR MS % long switch short %
	// 320.0 32.0 68.0 91.0 5.0 4.0 [A[A[A
	// 200/12453 ( 2%)| 0:00/ 0:26| 0:00/ 0:27| 12.150x| 0:26
	// --05:20------------------------------------------------------------------------
	// kbps LR MS % long switch short %
	// 320.0 65.5 34.5 82.2 10.0 7.8 [A[A[A
	// 300/12453 ( 2%)| 0:00/ 0:28| 0:00/ 0:29| 11.358x| 0:28
	// --05:17------------------------------------------------------------------------
	// kbps LR MS % long switch short %
	// 320.0 74.3 25.7 78.7 11.7 9.7 [A[A[A
	// 400/12453 ( 3%)| 0:00/ 0:29| 0:00/ 0:30| 10.884x| 0:29
	/**
	 * @see de.freese.jripper.core.process.PrintWriterProcessMonitor#monitorProcess(java.lang.String)
	 */
	@Override
	public void monitorProcess(final String line)
	{
		if (line.startsWith("Encoding") || line.contains("to") || line.contains("Frame"))
		{
			getPrintWriter().println(line);
		}
		else if (line.contains("%)"))
		{
			// int start = line.indexOf(" (");
			// int end = line.indexOf("%)");
			//
			// String prozentS = line.substring(start + 2, end).trim();
			// int prozent = Integer.parseInt(prozentS);

			// if ((prozent % 5) == 0)
			{
				getPrintWriter().println(line);
			}
		}

		getPrintWriter().flush();
	}
}
