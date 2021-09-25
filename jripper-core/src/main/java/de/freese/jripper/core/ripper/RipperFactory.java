// Created: 02.03.2013
package de.freese.jripper.core.ripper;

import java.util.ServiceLoader;

import de.freese.jripper.core.JRipperUtils;

/**
 * Zentrale Klasse für die Bereitstellung des Rippers.<br>
 * Je nach Betriebssystem wird die entsprechende Implementierung verwendet.
 *
 * @author Thomas Freese
 */
public final class RipperFactory
{
    /**
     *
     */
    private static final ServiceLoader<Ripper> SERVICE_LOADER = ServiceLoader.load(Ripper.class);

    /**
     * Je nach Betriebssystem wird die entsprechende Implementierung geliefert.
     *
     * @return {@link Ripper}
     */
    public static Ripper getInstance()
    {
        Ripper impl = null;

        for (Ripper ripper : SERVICE_LOADER)
        {
            if (ripper.supportsOS(JRipperUtils.getOsName()))
            {
                impl = ripper;
                break;
            }
        }

        if (impl == null)
        {
            throw new NullPointerException("no ripper found for " + JRipperUtils.getOsName());
        }

        return impl;
    }

    /**
     * Erstellt ein neues {@link RipperFactory} Object.
     */
    private RipperFactory()
    {
        super();
    }
}
