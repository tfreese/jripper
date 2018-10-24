/**
 * Created: 02.03.2013
 */

package de.freese.jripper.core.encoder;

import java.util.ServiceLoader;
import org.apache.commons.lang3.SystemUtils;

/**
 * Zentrale Klasse für die Bereitstellung der Encoder.<br>
 * Je nach Betriebssystem wird die entsprechende Implementierung verwendet.
 *
 * @author Thomas Freese
 */
public final class EncoderFactory
{
    /**
     * 
     */
    private static final ServiceLoader<Encoder> SERVICE_LOADER = ServiceLoader.load(Encoder.class);

    /**
     * Je nach Betriebssystem wird die entsprechende Implementierung geliefert.
     * 
     * @param format {@link EncoderFormat}
     * @return {@link Encoder}
     */
    public static Encoder getInstance(final EncoderFormat format)
    {
        Encoder impl = null;

        for (Encoder encoder : SERVICE_LOADER)
        {
            if (encoder.supportsOS(SystemUtils.OS_NAME) && encoder.getFormat().equals(format))
            {
                impl = encoder;
                break;
            }
        }

        if (impl == null)
        {
            throw new NullPointerException("no encoder found for " + SystemUtils.OS_NAME + "/" + format);
        }

        return impl;
    }

    /**
     * Erstellt ein neues {@link EncoderFactory} Object.
     */
    private EncoderFactory()
    {
        super();
    }
}
