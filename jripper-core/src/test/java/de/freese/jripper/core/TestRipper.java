/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import de.freese.jripper.core.diskid.DiskIDProvider;
import de.freese.jripper.core.diskid.DiskIDProviderLinux;
import de.freese.jripper.core.model.DiskID;
import de.freese.jripper.core.ripper.Ripper;
import de.freese.jripper.core.ripper.RipperFactory;

/**
 * Testklasse für die {@link Ripper}.
 *
 * @author Thomas Freese
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
class TestRipper
{

    /**
     * Liefert je nach Betriebssystem die passende Implementierung.
     */
    @Test
    void testGetService()
    {
        try
        {
            Ripper ripper = RipperFactory.getInstance();
            assertNotNull(ripper);
        }
        catch (Exception ex)
        {
            fail();
        }
    }

    /**
     * Linux.
     */
    @Test
    @EnabledOnOs(OS.LINUX)
    @EnabledIfEnvironmentVariable(named = "SESSION_MANAGER", matches = ".*mainah.*") // Nur auf Desktop-PC mit CD-Laufwerk
    void testLinux()
    {
        DiskIDProvider service = new DiskIDProviderLinux();

        try
        {
            DiskID diskID = service.getDiskID(JRipperUtils.detectCdDevice());
            assertNotNull(diskID);
        }
        catch (IllegalStateException ex)
        {
            // Keine CD im Laufwerk.
        }
        catch (Exception ex)
        {
            fail();
        }
    }
}
