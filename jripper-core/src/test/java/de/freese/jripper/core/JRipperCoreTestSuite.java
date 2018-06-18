/**
 * Created: 26.02.2013
 */

package de.freese.jripper.core;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Thomas Freese
 */
public class JRipperCoreTestSuite
{
	/**
	 * @return {@link Test}
	 */
	public static Test suite()
	{
		TestSuite suite = new TestSuite("de.freese.jripper.core");
		suite.addTest(new JUnit4TestAdapter(TestDiskID.class));
		suite.addTest(new JUnit4TestAdapter(TestRipper.class));

		return suite;
	}

	/**
	 * Erstellt ein neues {@link JRipperCoreTestSuite} Object.
	 */
	private JRipperCoreTestSuite()
	{
		super();
	}
}
