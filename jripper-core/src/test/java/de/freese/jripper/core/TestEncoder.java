// Created: 25.02.2013
package de.freese.jripper.core;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import de.freese.jripper.core.encoder.Encoder;
import de.freese.jripper.core.encoder.EncoderFactory;
import de.freese.jripper.core.encoder.EncoderFormat;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

/**
 * Testklasse für die {@link Encoder}.
 *
 * @author Thomas Freese
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
class TestEncoder
{
    @Test
    @EnabledOnOs(OS.LINUX)
    void testLinuxFlac()
    {
        try
        {
            Encoder encoder = EncoderFactory.getInstance(EncoderFormat.FLAC);
            assertNotNull(encoder);
        }
        catch (Exception ex)
        {
            fail();
        }
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    void testLinuxMp3()
    {
        try
        {
            Encoder encoder = EncoderFactory.getInstance(EncoderFormat.MP3);
            assertNotNull(encoder);
        }
        catch (Exception ex)
        {
            fail();
        }
    }
}
