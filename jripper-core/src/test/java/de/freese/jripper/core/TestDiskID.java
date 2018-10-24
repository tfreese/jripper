/**
 * Created: 25.02.2013
 */
package de.freese.jripper.core;

import de.freese.jripper.core.diskid.DiskIDProviderFactory;
import de.freese.jripper.core.diskid.DiskIDProvider;
import de.freese.jripper.core.diskid.LinuxDiskIDProvider;
import de.freese.jripper.core.model.DiskID;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Testklasse für die {@link DiskIDProvider}.
 *
 * @author Thomas Freese
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
            Assert.assertNotNull(diskID);
        }
        catch (Exception ex)
        {
            Assert.fail();
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
        Assert.assertEquals("b111140e", diskID.getID());
        Assert.assertEquals(14, diskID.getTrackCount());
        Assert.assertEquals(150, diskID.getOffset());
        Assert.assertEquals(4374, diskID.getSeconds());
        Assert.assertEquals(id, diskID.toString());
    }

    /**
     *
     */
    @Test
    public void testID2()
    {
        String id = "ae0ff80e 14 150 10972 37962 56825 81450 103550 127900 153025 179675 200425 225187 247687 270712 295700 4090";
        DiskID diskID = new DiskID(id);
        Assert.assertEquals("ae0ff80e", diskID.getID());
        Assert.assertEquals(14, diskID.getTrackCount());
        Assert.assertEquals(150, diskID.getOffset());
        Assert.assertEquals(4090, diskID.getSeconds());
        Assert.assertEquals(id, diskID.toString());
    }

    /**
     * Linux.
     */
    @Test
    public void testLinux()
    {
        if (!SystemUtils.IS_OS_LINUX)
        {
            return;
        }

        String device = JRipperUtils.detectCDDVD();

        if (StringUtils.isBlank(device))
        {
            // Buildserver haben nicht zwangsläufig ein DVD Laufwerk.
            return;
        }

        DiskIDProvider diskIDProvider = new LinuxDiskIDProvider();

        try
        {
            DiskID diskID = diskIDProvider.getDiskID(device);
            Assert.assertNotNull(diskID);
        }
        catch (IllegalStateException ex)
        {
            // Keine CD im Laufwerk.
        }
        catch (Exception ex)
        {
            Assert.fail();
        }
    }
}
