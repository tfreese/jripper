/**
 * Created: 25.02.2013
 */

package de.freese.jripper.core;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import de.freese.jripper.core.encoder.Encoder;
import de.freese.jripper.core.encoder.EncoderFactory;
import de.freese.jripper.core.encoder.EncoderFormat;

/**
 * Testklasse für die {@link Encoder}.
 *
 * @author Thomas Freese
 */
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class TestEncoder
{
    /**
     * Erstellt ein neues {@link TestEncoder} Object.
     */
    public TestEncoder()
    {
        super();
    }

    /**
     * Linux.
     */
    @Test
    @EnabledOnOs(OS.LINUX)
    public void testLinuxFlac()
    {
        try
        {
            Encoder encoder = EncoderFactory.getInstance(EncoderFormat.flac);
            assertNotNull(encoder);
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
    public void testLinuxMp3()
    {
        try
        {
            Encoder encoder = EncoderFactory.getInstance(EncoderFormat.mp3);
            assertNotNull(encoder);
        }
        catch (Exception ex)
        {
            fail();
        }
    }
}
