// Created:25.02.2013
package de.freese.jripper.core;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import de.freese.jripper.core.diskid.DiskIDProvider;
import de.freese.jripper.core.diskid.DiskIDProviderLinux;
import de.freese.jripper.core.model.DiskId;
import de.freese.jripper.core.ripper.Ripper;
import de.freese.jripper.core.ripper.RipperFactory;

/**
 * @author Thomas Freese
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
class TestRipper {
    @Test
    void testGetService() throws Exception {
        final Ripper ripper = RipperFactory.getInstance();
        assertNotNull(ripper);
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    @EnabledIfEnvironmentVariable(named = "SESSION_MANAGER", matches = ".*mainah.*") // Only on Desktop-PC with CD-Drive.
    @Disabled("No CD/DVD/BluRay Drive")
    void testLinux() throws Exception {
        final DiskIDProvider service = new DiskIDProviderLinux();

        try {
            final DiskId diskID = service.getDiskID(JRipperUtils.detectCdDevice());
            assertNotNull(diskID);
        }
        catch (IllegalStateException ex) {
            // No CD/DVD/BluRay Drive.
        }
    }
}
