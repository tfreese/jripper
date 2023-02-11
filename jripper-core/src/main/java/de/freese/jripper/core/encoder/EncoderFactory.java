// Created: 02.03.2013
package de.freese.jripper.core.encoder;

import java.util.ServiceLoader;

import de.freese.jripper.core.JRipperUtils;

/**
 * Zentrale Klasse für die Bereitstellung der Encoder.<br>
 * Je nach Betriebssystem wird die entsprechende Implementierung verwendet.
 *
 * @author Thomas Freese
 */
public final class EncoderFactory {
    private static final ServiceLoader<Encoder> SERVICE_LOADER = ServiceLoader.load(Encoder.class);

    /**
     * Je nach Betriebssystem wird die entsprechende Implementierung geliefert.
     */
    public static Encoder getInstance(final EncoderFormat format) {
        Encoder impl = null;

        for (Encoder encoder : SERVICE_LOADER) {
            if (encoder.supportsOS(JRipperUtils.getOsName()) && encoder.getFormat().equals(format)) {
                impl = encoder;
                break;
            }
        }

        if (impl == null) {
            throw new NullPointerException("no encoder found for " + JRipperUtils.getOsName() + "/" + format);
        }

        return impl;
    }

    private EncoderFactory() {
        super();
    }
}
