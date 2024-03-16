// Created: 02.03.2013
package de.freese.jripper.core.cddb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.slf4j.Logger;

import de.freese.jripper.core.JRipper;
import de.freese.jripper.core.JRipperUtils;
import de.freese.jripper.core.model.AlbumImpl;
import de.freese.jripper.core.model.DiskId;

/**
 * CDDB Provider für FreeDB.
 *
 * @author Thomas Freese
 */
public class CddbProviderGnuDb implements CddbProvider {
    private static final String HOST = "localhost";

    // private static final Pattern RECORD_DGENRE = Pattern.compile("DGENRE=(.*)");

    // private static final Pattern RECORD_DTITLE = Pattern.compile("DTITLE=(.*)");

    // private static final Pattern RECORD_DYEAR = Pattern.compile("DYEAR=(.*)");

    // private static final Pattern RECORD_EXTD = Pattern.compile("EXTD=(.*)");

    // private static final Pattern RECORD_TTITLE = Pattern.compile("TTITLE(\\d+)=(.*)");

    private static final int PORT = 80;
    /**
     * freedb.freedb.org gibs nicht mehr.
     */
    private static final String SERVER = "gnudb.gnudb.org";
    private static final String USER = "anonymous";

    private final Logger logger = JRipper.getInstance().getLogger();// LoggerFactory.getLogger(getClass());
    private final String requestPostfix;

    public CddbProviderGnuDb() {
        super();

        final StringBuilder sb = new StringBuilder();
        sb.append("&hello=").append(USER);
        sb.append("+").append(HOST);
        sb.append("+jRipper+1.0.0&proto=6");
        this.requestPostfix = sb.toString();
    }

    /**
     * https://gnudb.gnudb.org/~cddb/cddb.cgi?cmd=cddb+read+rock+b111140e&hello=anonymous+localhost+jRipper+1.0.0&proto=6<br>
     * https://gnudb.gnudb.org/~cddb/cddb.cgi?cmd=cddb+read+misc+ae0ff80e&hello=anonymous+localhost+jRipper+1.0.0&proto=6<br>
     */
    @Override
    public CddbResponse queryAlbum(final DiskId diskID, final String genre) throws Exception {
        final StringBuilder sb = new StringBuilder();
        sb.append("/~cddb/cddb.cgi?cmd=cddb+read");
        sb.append("+").append(genre);

        String[] splits = diskID.toString().split(" ");
        sb.append("+").append(splits[0]);

        sb.append(this.requestPostfix);

        // gnudb.gnudb.org verwendet kein HTTPS !
        final URL url = URI.create("http://" + SERVER + ":" + PORT + sb).toURL();

        getLogger().debug("Query {}", url);

        final List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), StandardCharsets.UTF_8))) {
            reader.lines().forEach(lines::add);
        }

        final String firstLine = lines.remove(0);
        final int status = Integer.parseInt(firstLine.split(" ")[0]);

        final CddbResponse cddbResponse = new CddbResponse(status);

        if ((status == CddbResponse.NO_MATCH) || (status == CddbResponse.SYNTAX_ERROR)) {
            // Nichts gefunden -> Abbruch
            cddbResponse.setErrorMessage(firstLine);
        }
        else {
            Map<String, String> responseMap = new TreeMap<>(new TrackTitleComparator());

            for (String line : lines) {
                getLogger().debug(line);

                if (line.startsWith("#") || !line.contains("=")) {
                    // Kein KeyValue
                    continue;
                }

                splits = line.split("=", 2);

                if (splits.length == 1) {
                    // Kein Value
                    continue;
                }

                final String key = splits[0];
                String value = splits[1];

                // Value mit möglichem Vorgänger verknüpfen.
                value = Objects.toString(responseMap.get(key), "") + value;

                responseMap.put(key, value);
            }

            final AlbumImpl album = new AlbumImpl(diskID);

            for (Entry<String, String> entry : responseMap.entrySet()) {
                final String key = entry.getKey();
                final String value = entry.getValue();

                if ("DTITLE".equals(key)) {
                    // Format: ARTIST / ALBUM
                    String artistTitle = value.substring(0, value.indexOf(" / "));
                    String albumTitle = value.substring(value.indexOf(" / ") + 2);

                    artistTitle = normalize(artistTitle);
                    albumTitle = normalize(albumTitle);

                    if ("Various".equals(artistTitle)) {
                        artistTitle = null;
                    }

                    // if ((splits.length == 1) && StringUtils.isNoneBlank(album.getTitle()))
                    // {
                    // // 2. Titel
                    // String title = StringUtils.join(album.getTitle(), " ", artistTitle);
                    // album.setTitle(title);
                    //
                    // continue;
                    // }

                    if ((artistTitle != null) && (artistTitle.length() <= 3)) {
                        artistTitle = artistTitle.toUpperCase();
                    }

                    album.setArtist(artistTitle);
                    album.setTitle(albumTitle);
                }
                else if ("DYEAR".equals(key)) {
                    final String year = normalize(value);

                    if ((year != null) && !year.isBlank()) {
                        album.setYear(Integer.parseInt(year));
                    }
                }
                else if ("DGENRE".equals(key)) {
                    // "Richtiges" Genre auslesen.
                    final String genre2 = normalize(value);

                    if ((genre2 != null) && !genre2.isBlank()) {
                        album.setGenre(genre2);
                    }
                }
                else if ("EXTD".equals(key)) {
                    if ((value == null) || value.isBlank()) {
                        // Kein Kommentar.
                        continue;
                    }

                    splits = value.lines().toArray(String[]::new);
                    final StringBuilder comment = new StringBuilder();

                    for (int i = 0; i < splits.length; i++) {
                        comment.append(splits[i]);

                        if (i < (splits.length - 1)) {
                            comment.append("\n");
                        }
                    }

                    album.setComment(comment.toString().strip());
                }
                else if (key.startsWith("TTITLE")) {
                    String trackArtist = null;
                    String trackTitle = null;

                    if (value.contains("/")) {
                        // Annahme Compilation.
                        splits = value.split("/");

                        trackArtist = normalize(splits[0]);
                        trackTitle = normalize(splits[1]);
                    }
                    else {
                        // Annahme Album.
                        splits = value.split(" ");

                        for (int i = 0; i < splits.length; i++) {
                            splits[i] = normalize(splits[i]);
                        }

                        if (JRipperUtils.isNumeric(splits[0])) {
                            splits[0] = "";
                        }

                        // splits[1].replaceAll("[\\d+]", "");
                        trackTitle = String.join(" ", splits);
                        trackTitle = JRipperUtils.trim(trackTitle);
                    }

                    // Wenn TrackArtist=null wird AlbumArtist genommen.
                    album.addTrack(trackArtist, trackTitle);
                }
            }

            responseMap.clear();
            responseMap = null;

            cddbResponse.setAlbum(album);
        }

        return cddbResponse;
    }

    /**
     * https://gnudb.gnudb.org/~cddb/cddb.cgi?cmd=cddb+query+b111140e+14+150+24545+41797+60822+80152+117002+142550+169755+192057+211360+239297+256325+279075+306220+4374
     * &hello=anonymous+localhost+jRipper+1.0.0&proto=6<br>
     * https://gnudb.gnudb.org/~cddb/cddb.cgi?cmd=cddb+query+ae0ff80e+14+150+10972+37962+56825+81450+103550+127900+153025+179675+200425+225187+247687+270712+295700+4090
     * &hello=anonymous+localhost+jRipper+1.0.0&proto=6<br>
     */
    @Override
    public CddbResponse queryGenres(final DiskId diskID) throws Exception {
        final StringBuilder sb = new StringBuilder();
        sb.append("/~cddb/cddb.cgi?cmd=cddb+query");

        sb.append("+").append(diskID.getID());
        sb.append("+").append(diskID.getTrackCount());
        sb.append("+").append(diskID.getOffset());

        for (int offset : diskID.getTrackOffsets()) {
            sb.append("+").append(offset);
        }

        sb.append("+").append(diskID.getSeconds());

        sb.append(this.requestPostfix);

        // gnudb.gnudb.org verwendet kein HTTPS !
        final URL url = URI.create("http://" + SERVER + ":" + PORT + sb).toURL();

        getLogger().debug("Query {}", url);

        final List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), StandardCharsets.UTF_8))) {
            reader.lines().forEach(lines::add);
        }

        final String firstLine = lines.remove(0);
        final int status = Integer.parseInt(firstLine.split(" ")[0]);

        final CddbResponse cddbResponse = new CddbResponse(status);

        if ((status == CddbResponse.NO_MATCH) || (status == CddbResponse.SYNTAX_ERROR)) {
            // Nichts gefunden -> Abbruch
            cddbResponse.setErrorMessage(firstLine);
        }
        else {
            final Set<String> genres = new TreeSet<>();

            for (String line : lines) {
                getLogger().debug(line);

                // Abschluss Kennung
                if (line.startsWith(".")) {
                    break;
                }

                final String[] splits = line.split(" ");

                if (splits.length < 2) {
                    continue;
                }

                final String genre = switch (status) {
                    case CddbResponse.EXACT_MATCHES, CddbResponse.INEXACT_MATCHES -> JRipperUtils.trim(splits[0]);
                    case CddbResponse.MATCH -> JRipperUtils.trim(splits[1]);
                    default -> throw new IllegalStateException("unsupported status: " + status);
                };

                genres.add(genre);

                if (status == CddbResponse.INEXACT_MATCHES) {
                    // Erstes Genre nehmen, DiskId aktualisieren und Abbruch.
                    final String id = JRipperUtils.trim(splits[1]);
                    diskID.setID(id);
                    break;
                }
            }

            if (!genres.isEmpty()) {
                cddbResponse.setGenres(new ArrayList<>(genres));
            }
        }

        return cddbResponse;
    }

    private Logger getLogger() {
        return this.logger;
    }

    /**
     * Normalisiert die Texte.<br>
     * <ul>
     * <li>trimToEmpty</li>
     * <li>toLowerCase</li>
     * <li>capitalize</li>
     * <li>' cd ' durch ' CD ' ersetzen</li>
     * <li>'(cd ' durch '(CD ' ersetzen</li>
     * <li>dj durch DJ ersetzen</li>
     * <li>' feat ' durch ' Feat. ' ersetzen</li>
     * <li>':' durch ' - ' ersetzen</li>
     * <li>'<' durch '-' ersetzen</li>
     * <li>'>' durch '-' ersetzen</li>
     * <li>'[' durch '(' ersetzen</li>
     * <li>']' durch ')' ersetzen</li>
     * <li>'´' durch ''' ersetzen</li>
     * <li>'`' durch ''' ersetzen</li>
     * <li>Mehrfache Spaces durch einen ersetzen</li>
     * <li>nicht erlaubte Zeichen: < > ? " : | \ / *</li>
     * </ul>
     */
    private String normalize(final String text) {
        if ((text == null) || text.isBlank()) {
            return null;
        }

        String value = text;
        value = Optional.ofNullable(JRipperUtils.trim(value)).orElse("");
        value = value.toLowerCase();
        value = JRipperUtils.capitalize(value);
        value = value.replace(" cd ", " CD ");
        value = value.replace("(cd ", "(CD ");
        value = value.replace("Dj ", "DJ ");
        value = value.replace(" feat ", " Feat. ");
        value = value.replace(":", " - ");
        value = value.replace("<", "-");
        value = value.replace(">", "-");
        value = value.replace("[", "(");
        value = value.replace("]", ")");
        value = value.replace("´", "'");
        value = value.replace("`", "'");
        value = JRipperUtils.normalizeSpace(value);

        // Nach '(', '-', '.' auch Grossbuchstaben.
        if (value.contains("(") || value.contains("-") || value.contains(".")) {
            final StringBuilder sb = new StringBuilder();

            for (int j = 0; j < value.length(); j++) {
                char sign = value.charAt(j);

                if ((j > 0) && ((value.charAt(j - 1) == '(') || (value.charAt(j - 1) == '-') || (value.charAt(j - 1) == '.'))) {
                    sign = Character.toUpperCase(sign);
                }

                sb.append(sign);
            }

            value = sb.toString();
        }

        value = Optional.ofNullable(JRipperUtils.trim(value)).orElse("");

        return value;
    }
}
