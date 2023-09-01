// * Created: 11.07.2014
package de.freese.jripper.core.cddb;

import java.util.Comparator;

/**
 * {@link Comparator} für die richtige Reihenfolge der Tracks.
 *
 * @author Thomas Freese
 */
public class TrackTitleComparator implements Comparator<String> {
    @Override
    public int compare(final String arg0, final String arg1) {
        if (arg0.startsWith("TTITLE") && arg1.startsWith("TTITLE")) {
            int track1 = Integer.parseInt(arg0.substring(6));
            int track2 = Integer.parseInt(arg1.substring(6));

            return track1 - track2;
        }

        return arg0.compareTo(arg1);
    }
}
