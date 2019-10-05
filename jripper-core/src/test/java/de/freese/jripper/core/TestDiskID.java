/**
 * Created: 25.02.2013
 */
package de.freese.jripper.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import de.freese.jripper.core.diskid.DiskIDProvider;
import de.freese.jripper.core.diskid.DiskIDProviderFactory;
import de.freese.jripper.core.diskid.DiskIDProviderLinux;
import de.freese.jripper.core.model.DiskID;

/**
 * Testklasse für die {@link DiskIDProvider}.
 *
 * @author Thomas Freese
 */
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class TestDiskID
{
    /**
     * Erstellt ein neues {@link TestDiskID} Object.
     */
    public TestDiskID()
    {
        super();
    }

    /**
     * Liefert je nach Betriebssystem die passende Implementierung.
     */
    @Test
    public void testGetService()
    {
        try
        {
            DiskIDProvider diskID = DiskIDProviderFactory.getInstance();
            assertNotNull(diskID);
        }
        catch (Exception ex)
        {
            fail();
        }
    }

    /**
     *
     */
    @Test
    public void testID1()
    {
        String id = "b111140e 14 150 24545 41797 60822 80152 117002 142550 169755 192057 211360 239297 256325 279075 306220 4374";
        DiskID diskID = new DiskID(id);
        assertEquals("b111140e", diskID.getID());
        assertEquals(14, diskID.getTrackCount());
        assertEquals(150, diskID.getOffset());
        assertEquals(4374, diskID.getSeconds());
        assertEquals(id, diskID.toString());
    }

    /**
     *
     */
    @Test
    public void testID2()
    {
        String id = "ae0ff80e 14 150 10972 37962 56825 81450 103550 127900 153025 179675 200425 225187 247687 270712 295700 4090";
        DiskID diskID = new DiskID(id);
        assertEquals("ae0ff80e", diskID.getID());
        assertEquals(14, diskID.getTrackCount());
        assertEquals(150, diskID.getOffset());
        assertEquals(4090, diskID.getSeconds());
        assertEquals(id, diskID.toString());
    }

    /**
     * Linux.
     */
    @Test
    @EnabledOnOs(
    {
            OS.LINUX, OS.WINDOWS
    })
    public void testLinux()
    {
        String device = JRipperUtils.detectCdDevice();

        if (StringUtils.isBlank(device))
        {
            // Buildserver haben nicht zwangsläufig ein DVD Laufwerk.
            return;
        }

        DiskIDProvider diskIDProvider = new DiskIDProviderLinux();

        try
        {
            DiskID diskID = diskIDProvider.getDiskID(device);
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
