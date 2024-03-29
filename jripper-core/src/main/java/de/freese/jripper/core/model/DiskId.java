// Created: 09.03.2013
package de.freese.jripper.core.model;

import java.util.Objects;

import de.freese.jripper.core.Settings;

/**
 * DiskId Informationen einer CD.<br>
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
        return this.id;
    }

    public int getOffset() {
        return this.offset;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public int getTrackCount() {
        return this.trackCount;
    }

    public int[] getTrackOffsets() {
        return this.trackOffsets;
    }

    /**
     * Liefert die Trackdauer in Sekunden.
     */
    public int getTrackSeconds(final int track) {
        final int framesPerSecond = Settings.getInstance().getFramesPerSecond();
        final int trackSeconds;

        if (track == 0) {
            // Erster Titel
            final int frames = this.trackOffsets[0] - this.offset;
            trackSeconds = frames / framesPerSecond;
        }
        else if (track < this.trackOffsets.length) {
            final int frames = this.trackOffsets[track] - this.trackOffsets[track - 1] - this.offset;
            trackSeconds = frames / framesPerSecond;
        }
        else {
            // Letzter Track erhält seine Länge durch Gesamtzeit - Laufzeit vorheriger Titel.
            final int vorherigeFrames = this.trackOffsets[this.trackOffsets.length - 1] - this.offset;
            final int vorherigeSeconds = vorherigeFrames / framesPerSecond;
            trackSeconds = this.seconds - vorherigeSeconds;
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
        this.id = splits[0];
        this.trackCount = Integer.parseInt(splits[1]);
        this.offset = Integer.parseInt(splits[2]);

        this.trackOffsets = new int[splits.length - 4];

        for (int i = 0; i < this.trackOffsets.length; i++) {
            this.trackOffsets[i] = Integer.parseInt(splits[i + 3]);
        }

        this.seconds = Integer.parseInt(splits[splits.length - 1]);
    }
}
