/**
 * Created: 09.03.2013
 */

package de.freese.jripper.core.model;

import java.util.Objects;
import de.freese.jripper.core.Settings;

/**
 * DiskID Informationen einer CD.<br>
 * Beispiel:<br>
 * b111140e 14 150 24545 41797 60822 80152 117002 142550 169755 192057 211360 239297 256325 279075 306220 4374<br>
 * ae0ff80e 14 150 10972 37962 56825 81450 103550 127900 153025 179675 200425 225187 247687 270712 295700 4090
 *
 * @author Thomas Freese
 */
public class DiskID
{
    /**
     *
     */
    private String id;

    /**
     *
     */
    private int offset;

    /**
     *
     */
    private int seconds;

    /**
     *
     */
    private int trackCount;

    /**
     *
     */
    private int[] trackOffsets;

    /**
     * Erstellt ein neues {@link DiskID} Object.
     *
     * @param diskID String
     */
    public DiskID(final String diskID)
    {
        super();

        setDiskID(diskID);
    }

    /**
     * @return String
     */
    public String getID()
    {
        return this.id;
    }

    /**
     * @return int
     */
    public int getOffset()
    {
        return this.offset;
    }

    /**
     * @return int
     */
    public int getSeconds()
    {
        return this.seconds;
    }

    /**
     * @return int
     */
    public int getTrackCount()
    {
        return this.trackCount;
    }

    /**
     * @return int[]
     */
    public int[] getTrackOffsets()
    {
        return this.trackOffsets;
    }

    /**
     * Liefert die Trackdauer in Sekunden.
     *
     * @param track int
     * @return int
     */
    public int getTrackSeconds(final int track)
    {
        int framesPerSecond = Settings.getInstance().getFramesPerSecond();
        int seconds = 0;

        if (track == 0)
        {
            // Erster Titel
            int frames = this.trackOffsets[0] - this.offset;
            seconds = frames / framesPerSecond;
        }
        else if (track < this.trackOffsets.length)
        {
            int frames = this.trackOffsets[track] - this.trackOffsets[track - 1] - this.offset;
            seconds = frames / framesPerSecond;
        }
        else
        {
            // Letzter Track erhält seine Länge durch Gesamtzeit - Laufzeit vorheriger Titel.
            int vorherigeFrames = this.trackOffsets[this.trackOffsets.length - 1] - -this.offset;
            int vorherigeSeconds = vorherigeFrames / framesPerSecond;
            seconds = this.seconds - vorherigeSeconds;
        }

        return seconds;
    }

    /**
     * @param diskID String
     */
    public void setDiskID(final String diskID)
    {
        Objects.requireNonNull(diskID, "diskID required");

        String[] splits = diskID.split("[ ]");
        this.id = splits[0];
        this.trackCount = Integer.parseInt(splits[1]);
        this.offset = Integer.parseInt(splits[2]);

        this.trackOffsets = new int[splits.length - 4];

        for (int i = 0; i < this.trackOffsets.length; i++)
        {
            this.trackOffsets[i] = Integer.parseInt(splits[i + 3]);
        }

        this.seconds = Integer.parseInt(splits[splits.length - 1]);
    }

    /**
     * @param id String
     */
    public void setID(final String id)
    {
        this.id = id;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(getID());
        sb.append(" ").append(getTrackCount());
        sb.append(" ").append(getOffset());

        for (int trackOffset : getTrackOffsets())
        {
            sb.append(" ").append(trackOffset);
        }

        sb.append(" ").append(getSeconds());

        return sb.toString();
    }
}
