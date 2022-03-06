package de.freese.jripper.core;

import java.util.Arrays;

/*************************************************************************
 * Note: Pearl Jam's album Vs. has N = 12 tracks.<br>
 * The first track starts at frames[0] = 150, the second at frames[1] = 14672, the twelfth at frames[11] = 185792, and the disc ends at frames[N] = 208500.<br>
 * Its disc id is 970ADA0C. The disc id is a 32-bit integer, which we represent using 8 hex digits XXYYYYZZ.<br>
 * - XX is the checksum. The checksum is computed as follows: for each starting frame[i], we convert it to seconds by dividing by the frame rate 75; then we sum
 * up the decimal digits. E.g., if frame[i] = 7500600, this corresponds to 100008 seconds whose digit sum is 1 + 8 = 9. XX is the total sum of all of these
 * digit sums mod 255.<br>
 * - YYYY is the length of the album tracks in seconds. It is computed as (frames[N] - frames[0]) / 75 and output in hex.<br>
 * - ZZ is the number of tracks N expressed in hex.
 *************************************************************************/

public class DiskIDAlgorythm
{
    /**
     * b111140e 14 150 24545 41797 60822 80152 117002 142550 169755 192057 211360 239297 256325 279075 306220 4374
     *
     * @param args String[]
     */
    public static void main(final String[] args)
    {
        int FRAMES_PER_SECOND = Settings.getInstance().getFramesPerSecond();
        int[] frames =
                {
                        150, 24545, 41797, 60822, 80152, 117002, 142550, 169755, 192057, 211360, 239297, 256325, 279075, 306220
                };

        int N = frames.length - 1;
        int totalLength = (frames[N] - frames[0]) / FRAMES_PER_SECOND;
        totalLength = (327900 - frames[0]) / FRAMES_PER_SECOND;
        int checkSum = 0;

        for (int i = 0; i < N; i++)
        {
            checkSum += sumOfDigits(frames[i] / FRAMES_PER_SECOND);
        }

        int XX = checkSum % 255;
        int YYYY = totalLength;
        int ZZ = N;

        // XXYYYYZZ
        int discID = ((XX << 24) | (YYYY << 8) | ZZ);
        System.out.print(Integer.toHexString(discID));
        System.out.println(Arrays.toString(frames));
    }

    /**
     * return sum of decimal digits in n
     *
     * @param n int
     *
     * @return int
     */
    static int sumOfDigits(int n)
    {
        int sum = 0;

        while (n > 0)
        {
            sum += (n % 10);
            n /= 10;
        }

        return sum;
    }
}
