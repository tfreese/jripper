/**
 * Created: 07.03.2013
 */

package de.freese.jripper.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Thomas Freese
 */
public class Misc
{
	/**
	 * @param args String[]
	 */
	public static void main(final String[] args)
	{
		String s = "100/12453 ( 1%)| 0:00/ 0:21| 0:00/ 0:22| 15.366x| 0:22";
		// Pattern pattern = Pattern.compile("[(\\s[\\d]{1,2}%)]?");
		// Pattern pattern = Pattern.compile("[\\(][.*][\\)]"); // \[(.*?)\], /[^(<td>)].+[^(</td>)]/;
		// Pattern pattern = Pattern.compile("^[0-9]{1,}+/[0-9]{1,}+.*"); ///\(.*\)/ versucht? Alternativ würde mir grad noch das hier einfallen: /\(.*\)/U
		Pattern pattern = Pattern.compile("[(.*)]");
		Matcher matcher = pattern.matcher(s);

		System.out.println(matcher.matches());

		if (matcher.matches())
		{
			System.out.println(matcher.group());
		}

		int start = s.indexOf(" (");
		int end = s.indexOf("%)");

		if ((start > 0) && (end > 0))
		{

			String prozent = s.substring(start + 2, end).trim();
			System.out.println(prozent);
		}
	}
}
