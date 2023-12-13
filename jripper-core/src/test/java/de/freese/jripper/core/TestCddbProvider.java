// Created: 25.02.2013
package de.freese.jripper.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import de.freese.jripper.core.cddb.CddbProvider;
import de.freese.jripper.core.cddb.CddbProviderGnuDb;
import de.freese.jripper.core.cddb.CddbResponse;
import de.freese.jripper.core.diskid.DiskIDProvider;
import de.freese.jripper.core.diskid.DiskIDProviderFactory;
import de.freese.jripper.core.diskid.DiskIDProviderLinux;
import de.freese.jripper.core.model.DiskId;

/**
 * @author Thomas Freese
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
class TestCddbProvider {
    @Test
    void testGetService() throws Exception {
        final DiskIDProvider diskID = DiskIDProviderFactory.getInstance();
        assertNotNull(diskID);
    }

    @Test
    void testId1() throws Exception {
        // Karat / Vierzehn Karat - Ihre größten Hits
        // data, newage, rock
        final String id = "b111140e 14 150 24545 41797 60822 80152 117002 142550 169755 192057 211360 239297 256325 279075 306220 4374";
        final DiskId diskID = new DiskId(id);
        //        DiskId diskID = new DiskIdBeispielKarat();
        assertEquals("b111140e", diskID.getID());
        assertEquals(14, diskID.getTrackCount());
        assertEquals(150, diskID.getOffset());
        assertEquals(4374, diskID.getSeconds());
        assertEquals(id, diskID.toString());

        final CddbProvider cddbProvider = new CddbProviderGnuDb();

        CddbResponse response = cddbProvider.queryGenres(diskID);
        assertNotNull(response);

        response = cddbProvider.queryAlbum(diskID, "rock");
        assertNotNull(response);
        assertNotNull(response.getAlbum());
        assertEquals(14, response.getAlbum().getTrackCount());
    }

    @Test
    void testId2() throws Exception {
        // Culture Beat / Inside Out
        // misc, soundtrack
        final String id = "ae0ff80e 14 150 10972 37962 56825 81450 103550 127900 153025 179675 200425 225187 247687 270712 295700 4090";
        final DiskId diskID = new DiskId(id);
        //        DiskId diskID = new DiskIdBeispielCultureBeat();
        assertEquals("ae0ff80e", diskID.getID());
        assertEquals(14, diskID.getTrackCount());
        assertEquals(150, diskID.getOffset());
        assertEquals(4090, diskID.getSeconds());
        assertEquals(id, diskID.toString());

        final CddbProvider cddbProvider = new CddbProviderGnuDb();

        CddbResponse response = cddbProvider.queryGenres(diskID);
        assertNotNull(response);
        assertNotNull(response.getGenres());
        assertFalse(response.getGenres().isEmpty());

        response = cddbProvider.queryAlbum(diskID, "misc");
        assertNotNull(response);
        assertNotNull(response.getAlbum());
        assertEquals(14, response.getAlbum().getTrackCount());
    }

    @Test
    @EnabledOnOs({OS.LINUX, OS.WINDOWS})
    void testLinux() throws Exception {
        final String device = JRipperUtils.detectCdDevice();

        if ((device == null) || device.isBlank()) {
            // No CD/DVD/BluRay Drive.
            return;
        }

        final DiskIDProvider diskIDProvider = new DiskIDProviderLinux();

        try {
            final DiskId diskID = diskIDProvider.getDiskID(device);
            assertNotNull(diskID);
        }
        catch (IllegalStateException ex) {
            // No CD/DVD/BluRay Drive.
        }
    }
}
