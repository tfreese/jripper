package de.freese.jripper.core.diskid;

import java.util.ServiceLoader;

import de.freese.jripper.core.JRipperUtils;

/**
 * @author Thomas Freese
 * @since 25.02.2013
 */
public final class DiskIDProviderFactory {
    private static final ServiceLoader<DiskIDProvider> SERVICE_LOADER = ServiceLoader.load(DiskIDProvider.class);

    public static DiskIDProvider getInstance() {
        DiskIDProvider impl = null;

        for (final DiskIDProvider diskID : SERVICE_LOADER) {
            if (diskID.supportsOS(JRipperUtils.getOsName())) {
                impl = diskID;
                break;
            }
        }

        if (impl == null) {
            throw new NullPointerException("no diskID found for " + JRipperUtils.getOsName());
        }

        return impl;
    }

    private DiskIDProviderFactory() {
        super();
    }
}
