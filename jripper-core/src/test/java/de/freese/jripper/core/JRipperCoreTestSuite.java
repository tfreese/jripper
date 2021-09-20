// Created: 26.02.2013
package de.freese.jripper.core;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

/**
 * @author Thomas Freese
 */
@RunWith(JUnitPlatform.class)
@SuiteDisplayName("TestSuite for Binding")
// @SelectPackages("de.freese.jripper.core")
@SelectClasses(
{
        TestCddbProvider.class, TestEncoder.class, TestRipper.class
})

public class JRipperCoreTestSuite
{
    /**
     * Erstellt ein neues {@link JRipperCoreTestSuite} Object.
     */
    private JRipperCoreTestSuite()
    {
        super();
    }
}
