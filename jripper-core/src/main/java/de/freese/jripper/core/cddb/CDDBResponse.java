/**
 * Created: 09.03.2013
 */

package de.freese.jripper.core.cddb;

/**
 * ResponseCodes der CDDB - Server.
 *
 * @author Thomas Freese
 */
enum CDDBResponse
{
    /**
     * 210 - Mehrere Genres gefunden.
     */
    EXACT_MATCHES,

    /**
     * 211 - Nicht exackte DiskID.
     */
    INEXACT_MATCHES,

    /**
     * 200 - Nur ein Genre gefunden.
     */
    MATCH;
}
