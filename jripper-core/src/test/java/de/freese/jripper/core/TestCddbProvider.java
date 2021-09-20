// Created: 25.02.2013
package de.freese.jripper.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import de.freese.jripper.core.cddb.CddbProvider;
import de.freese.jripper.core.cddb.CddbProviderFreeDb;
import de.freese.jripper.core.cddb.CddbResponse;
import de.freese.jripper.core.diskid.DiskIDProvider;
import de.freese.jripper.core.diskid.DiskIDProviderFactory;
import de.freese.jripper.core.diskid.DiskIDProviderLinux;
import de.freese.jripper.core.model.DiskID;

/**
 * @author Thomas Freese
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
class TestCddbProvider
{
    /**
     * Liefert je nach Betriebssystem die passende Implementierung.
     */
    @Test
    void testGetService()
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
     * @throws Exception Falls was schief geht.
     */
    @Test
    void testID1() throws Exception
    {
        // Karat / Vierzehn Karat - Ihre größten Hits
        // data, newage, rock
        String id = "b111140e 14 150 24545 41797 60822 80152 117002 142550 169755 192057 211360 239297 256325 279075 306220 4374";
        DiskID diskID = new DiskID(id);
        assertEquals("b111140e", diskID.getID());
        assertEquals(14, diskID.getTrackCount());
        assertEquals(150, diskID.getOffset());
        assertEquals(4374, diskID.getSeconds());
        assertEquals(id, diskID.toString());

        CddbProvider cddbProvider = new CddbProviderFreeDb();

        CddbResponse response = cddbProvider.queryGenres(diskID);
        assertNotNull(response);
        assertNotNull(response.getGenres());
        assertTrue(!response.getGenres().isEmpty());

        response = cddbProvider.queryAlbum(diskID, "rock");
        assertNotNull(response);
        assertNotNull(response.getAlbum());
        assertEquals(14, response.getAlbum().getTrackCount());
    }

    /**
     * @throws Exception Falls was schief geht.
     */
    @Test
    void testID2() throws Exception
    {
        // Culture Beat / Inside Out
        // misc, soundtrack
        String id = "ae0ff80e 14 150 10972 37962 56825 81450 103550 127900 153025 179675 200425 225187 247687 270712 295700 4090";
        DiskID diskID = new DiskID(id);
        assertEquals("ae0ff80e", diskID.getID());
        assertEquals(14, diskID.getTrackCount());
        assertEquals(150, diskID.getOffset());
        assertEquals(4090, diskID.getSeconds());
        assertEquals(id, diskID.toString());

        CddbProvider cddbProvider = new CddbProviderFreeDb();

        CddbResponse response = cddbProvider.queryGenres(diskID);
        assertNotNull(response);
        assertNotNull(response.getGenres());
        assertTrue(!response.getGenres().isEmpty());

        response = cddbProvider.queryAlbum(diskID, "misc");
        assertNotNull(response);
        assertNotNull(response.getAlbum());
        assertEquals(14, response.getAlbum().getTrackCount());
    }

    /**
     * Linux.
     */
    @Test
    @EnabledOnOs(
    {
            OS.LINUX, OS.WINDOWS
    })
    void testLinux()
    {
        String device = JRipperUtils.detectCdDevice();

        if ((device == null) || device.isBlank())
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
