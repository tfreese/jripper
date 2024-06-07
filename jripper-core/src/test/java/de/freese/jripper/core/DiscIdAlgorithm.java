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

public final class DiscIdAlgorithm {
    /**
     * b111140e 14 150 24545 41797 60822 80152 117002 142550 169755 192057 211360 239297 256325 279075 306220 4374
     */
    public static void main(final String[] args) {
        final int framesPerSecond = Settings.getInstance().getFramesPerSecond();
        final int[] frames = {150, 24545, 41797, 60822, 80152, 117002, 142550, 169755, 192057, 211360, 239297, 256325, 279075, 306220};

        final int n = frames.length - 1;
        // int totalLength = (frames[n] - frames[0]) / framesPerSecond;
        final int totalLength = (327900 - frames[0]) / framesPerSecond;
        int checkSum = 0;

        for (int i = 0; i < n; i++) {
            checkSum += sumOfDigits(frames[i] / framesPerSecond);
        }

        final int xx = checkSum % 255;
        final int yyyy = totalLength;
        final int zz = n;

        // XXYYYYZZ
        final int discID = (xx << 24) | (yyyy << 8) | zz;
        System.out.print(Integer.toHexString(discID));
        System.out.println(Arrays.toString(frames));
    }

    /**
     * return sum of decimal digits in n
     */
    static int sumOfDigits(final int n) {
        int digit = n;
        int sum = 0;

        while (digit > 0) {
            sum += digit % 10;
            digit /= 10;
        }

        return sum;
    }

    private DiscIdAlgorithm() {
        super();
    }
}
