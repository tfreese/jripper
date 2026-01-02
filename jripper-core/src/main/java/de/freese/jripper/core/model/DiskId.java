// Created: 09.03.2013
package de.freese.jripper.core.model;

import java.util.Objects;

import de.freese.jripper.core.Settings;

/**
 * DiskId Information of a CD.<br>
 *
 * @author Thomas Freese
 */
public class DiskId {
    private String id;
    private int offset;
    private int seconds;
    private int trackCount;
    private int[] trackOffsets;

    public DiskId(final String diskID) {
        super();

        parseID(diskID);
    }

    public String getID() {
        return id;
    }

    public int getOffset() {
        return offset;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getTrackCount() {
        return trackCount;
    }

    public int[] getTrackOffsets() {
        return trackOffsets;
    }

    /**
     * Returns the Track duration in Seconds.
     */
    public int getTrackSeconds(final int track) {
        final int framesPerSecond = Settings.getInstance().getFramesPerSecond();
        final int trackSeconds;

        if (track == 0) {
            // First Title.
            final int frames = trackOffsets[0] - offset;
            trackSeconds = frames / framesPerSecond;
        }
        else if (track < trackOffsets.length) {
            final int frames = trackOffsets[track] - trackOffsets[track - 1] - offset;
            trackSeconds = frames / framesPerSecond;
        }
        else {
            // Last Track get its Length by "hole duration - Running time of previous title".
            final int vorherigeFrames = trackOffsets[trackOffsets.length - 1] - offset;
            final int vorherigeSeconds = vorherigeFrames / framesPerSecond;
            trackSeconds = seconds - vorherigeSeconds;
        }

        return trackSeconds;
    }

    public void setID(final String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getID());
        sb.append(" ").append(getTrackCount());
        sb.append(" ").append(getOffset());

        for (int trackOffset : getTrackOffsets()) {
            sb.append(" ").append(trackOffset);
        }

        sb.append(" ").append(getSeconds());

        return sb.toString();
    }

    private void parseID(final String diskID) {
        Objects.requireNonNull(diskID, "diskID required");

        final String[] splits = diskID.split(" ");
        id = splits[0];
        trackCount = Integer.parseInt(splits[1]);
        offset = Integer.parseInt(splits[2]);

        trackOffsets = new int[splits.length - 4];

        for (int i = 0; i < trackOffsets.length; i++) {
            trackOffsets[i] = Integer.parseInt(splits[i + 3]);
        }

        seconds = Integer.parseInt(splits[splits.length - 1]);
    }
}
