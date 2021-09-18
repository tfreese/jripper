/**
 * Created: 02.03.2013
 */

package de.freese.jripper.core.cddb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;

import de.freese.jripper.core.JRipper;
import de.freese.jripper.core.JRipperUtils;
import de.freese.jripper.core.model.AlbumImpl;
import de.freese.jripper.core.model.DiskID;

/**
 * CDDB Provider für FreeDB.
 *
 * @author Thomas Freese
 */
public class CddbProviderFreeDb implements CddbProvider
{
    /**
     *
     */
    private static final String HOST = "localhost";

    // /**
    // *
    // */
    // private static final Pattern RECORD_DGENRE = Pattern.compile("DGENRE=(.*)");
    //
    // /**
    // *
    // */
    // private static final Pattern RECORD_DTITLE = Pattern.compile("DTITLE=(.*)");
    //
    // /**
    // *
    // */
    // private static final Pattern RECORD_DYEAR = Pattern.compile("DYEAR=(.*)");
    //
    // /**
    // *
    // */
    // private static final Pattern RECORD_EXTD = Pattern.compile("EXTD=(.*)");
    //
    // /**
    // *
    // */
    // private static final Pattern RECORD_TTITLE = Pattern.compile("TTITLE(\\d+)=(.*)");

    /**
     *
     */
    private static final int PORT = 80;

    /**
     *
     */
    private static final String SERVER = "freedb.freedb.org";

    /**
     *
     */
    private static final String USER = "anonymous";

    /**
     *
     */
    public final Logger logger = JRipper.getInstance().getLogger();// LoggerFactory.getLogger(getClass());

    /**
     *
     */
    private final String requestPostfix;

    /**
     * Erstellt ein neues {@link CddbProviderFreeDb} Object.
     */
    public CddbProviderFreeDb()
    {
        super();

        StringBuilder sb = new StringBuilder();
        sb.append("&hello=").append(USER);
        sb.append("+").append(HOST);
        sb.append("+jRipper+1.0.0&proto=6");
        this.requestPostfix = sb.toString();
    }

    /**
     * @return {@link Logger}
     */
    private Logger getLogger()
    {
        return this.logger;
    }

    /**
     * Normalisiert die Texte.<br>
     * <ul>
     * <li>trimToEmpty
     * <li>toLowerCase
     * <li>capitalize
     * <li>' cd ' durch ' CD ' ersetzen
     * <li>'(cd ' durch '(CD ' ersetzen
     * <li>dj durch DJ ersetzen
     * <li>' feat ' durch ' Feat. ' ersetzen
     * <li>':' durch ' - ' ersetzen
     * <li>'<' durch '-' ersetzen
     * <li>'>' durch '-' ersetzen
     * <li>'[' durch '(' ersetzen
     * <li>']' durch ')' ersetzen
     * <li>'´' durch ''' ersetzen
     * <li>'`' durch ''' ersetzen
     * <li>Mehrfache Spaces durch einen ersetzen
     * <li>nicht erlaubte Zeichen: < > ? " : | \ / *
     * </ul>
     *
     * @param text String
     *
     * @return String, oder null wenn leer
     */
    private String normalize(final String text)
    {
        if ((text == null) || text.isBlank())
        {
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
        if (value.contains("(") || value.contains("-") || value.contains("."))
        {
            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < value.length(); j++)
            {
                char sign = value.charAt(j);

                if ((j > 0) && ((value.charAt(j - 1) == '(') || (value.charAt(j - 1) == '-') || (value.charAt(j - 1) == '.')))
                {
                    sign = Character.toUpperCase(sign);
                }

                sb.append(sign);
            }

            value = sb.toString();
        }

        value = Optional.ofNullable(JRipperUtils.trim(value)).orElse("");

        return value;
    }

    /**
     * @see de.freese.jripper.core.cddb.CddbProvider#queryAlbum(de.freese.jripper.core.model.DiskID, java.lang.String)
     */
    @Override
    public CddbResponse queryAlbum(final DiskID diskID, final String genre) throws Exception
    {
        CddbResponse cddbResponse = new CddbResponse();

        StringBuilder sb = new StringBuilder();
        sb.append("/~cddb/cddb.cgi?cmd=cddb+read");
        sb.append("+").append(genre);

        String[] splits = diskID.toString().split("[ ]");
        sb.append("+").append(splits[0]);

        sb.append(this.requestPostfix);

        URL url = new URL("http", SERVER, PORT, sb.toString());

        getLogger().debug("Query {}", url);

        URLConnection connection = url.openConnection();

        Map<String, String> responseMap = new TreeMap<>(new TrackTitleComparator());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)))
        {
            String line = null;

            while ((line = reader.readLine()) != null)
            {
                getLogger().debug(line);

                if (line.startsWith("#") || !line.contains("="))
                {
                    // Kein KeyValue
                    continue;
                }

                splits = line.split("[=]", 2);

                if (splits.length == 1)
                {
                    // Kein Value
                    continue;
                }

                String key = splits[0];
                String value = splits[1];

                // Value mit möglichem Vorgänger verknüpfen.
                value = Objects.toString(responseMap.get(key), "") + value;

                responseMap.put(key, value);
            }
        }

        AlbumImpl album = new AlbumImpl(diskID);

        for (Entry<String, String> entry : responseMap.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();

            if ("DTITLE".equals(key))
            {
                // Format: ARTIST / ALBUM
                String artistTitle = value.substring(0, value.indexOf(" / "));
                String albumTitle = value.substring(value.indexOf(" / ") + 1);

                artistTitle = normalize(artistTitle);
                albumTitle = normalize(albumTitle);

                if ("Various".equals(artistTitle))
                {
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

                if ((artistTitle != null) && (artistTitle.length() <= 3))
                {
                    artistTitle = artistTitle.toUpperCase();
                }

                album.setArtist(artistTitle);
                album.setTitle(albumTitle);
            }
            else if ("DYEAR".equals(key))
            {
                String year = normalize(value);

                if ((year != null) && !year.isBlank())
                {
                    album.setYear(Integer.parseInt(year));
                }
            }
            else if ("DGENRE".equals(key))
            {
                // "Richtiges" Genre auslesen.
                String genre2 = normalize(value);

                if ((genre2 != null) && !genre2.isBlank())
                {
                    album.setGenre(genre2);
                }
            }
            else if ("EXTD".equals(key))
            {
                if ((value == null) || value.isBlank())
                {
                    // Kein Kommentar.
                    continue;
                }

                splits = value.lines().toArray(String[]::new);
                StringBuilder comment = new StringBuilder();

                for (int i = 0; i < splits.length; i++)
                {
                    comment.append(splits[i]);

                    if (i < (splits.length - 1))
                    {
                        comment.append("\n");
                    }
                }

                album.setComment(comment.toString().trim());
            }
            else if (key.startsWith("TTITLE"))
            {
                String trackArtist = null;
                String trackTitle = null;

                if (value.contains("/"))
                {
                    // Annahme Compilation.
                    splits = value.split("[/]");

                    trackArtist = normalize(splits[0]);
                    trackTitle = normalize(splits[1]);
                }
                else
                {
                    // Annahme Album.
                    splits = value.split("[ ]");

                    for (int i = 0; i < splits.length; i++)
                    {
                        splits[i] = normalize(splits[i]);
                    }

                    if (JRipperUtils.isNumeric(splits[0]))
                    {
                        splits[0] = "";
                    }

                    // splits[1].replaceAll("[\\d+]", "");
                    trackTitle = Stream.of(splits).collect(Collectors.joining(" "));
                    trackTitle = JRipperUtils.trim(trackTitle);
                }

                // Wenn TrackArtist=null wird AlbumArtist genommen.
                album.addTrack(trackArtist, trackTitle);
            }
        }

        responseMap.clear();
        responseMap = null;

        cddbResponse.setAlbum(album);

        return cddbResponse;
    }

    /**
     * Beispiel Query:<br>
     * http://freedb.freedb.org/~cddb/cddb.cgi?cmd=cddb+query+7c0b8b0b+11+150+23115+
     * 42165+60015+79512+101560+118757+136605+159492+176067+198875+2957&hello=user+ hostname+program+version&proto=3(6)
     *
     * @see de.freese.jripper.core.cddb.CddbProvider#queryGenres(de.freese.jripper.core.model.DiskID)
     */
    @Override
    public CddbResponse queryGenres(final DiskID diskID) throws Exception
    {
        CddbResponse cddbResponse = new CddbResponse();

        StringBuilder sb = new StringBuilder();
        sb.append("/~cddb/cddb.cgi?cmd=cddb+query");

        String[] splits = diskID.toString().split("[ ]");

        for (String split : splits)
        {
            sb.append("+").append(split);
        }

        sb.append(this.requestPostfix);

        URL url = new URL("http", SERVER, PORT, sb.toString());

        getLogger().debug("Query {}", url);

        URLConnection connection = url.openConnection();
        Set<String> genres = new TreeSet<>();
        int responseCode = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)))
        {
            String line = null;

            while ((line = reader.readLine()) != null)
            {
                getLogger().debug(line);

                if (line.startsWith(Integer.toString(CddbResponse.NO_MATCH)))
                {
                    // Nichts gefunden -> Abbruch
                    cddbResponse.setErrorMessage(line);
                    break;
                }

                if (line.startsWith("."))
                {
                    continue;
                }

                splits = line.split("[ ]");

                if (splits.length < 2)
                {
                    continue;
                }

                if (responseCode == 0)
                {
                    if (splits[0].equals(Integer.toString(CddbResponse.MATCH)))
                    {
                        // Nur ein Genre gefunden.
                        responseCode = CddbResponse.MATCH;
                    }
                    else if (splits[0].equals(Integer.toString(CddbResponse.EXACT_MATCHES)))
                    {
                        // Mehrere Genres gefunden
                        responseCode = CddbResponse.EXACT_MATCHES;
                        continue;
                    }
                    else if (splits[0].equals(Integer.toString(CddbResponse.INEXACT_MATCHES)))
                    {
                        // Nicht exakte Treffer -> ungleiche DiskID.
                        responseCode = CddbResponse.INEXACT_MATCHES;
                        continue;
                    }
                }

                String genre = null;

                switch (responseCode)
                {
                    case CddbResponse.EXACT_MATCHES:
                    case CddbResponse.INEXACT_MATCHES:
                        genre = JRipperUtils.trim(splits[0]);
                        break;
                    case CddbResponse.MATCH:
                        genre = JRipperUtils.trim(splits[1]);
                        break;
                    default:
                        break;
                }

                genres.add(genre);

                if (responseCode == CddbResponse.INEXACT_MATCHES)
                {
                    // Erstes Genre nehmen, DiskID aktualisieren und Abbruch.
                    genre = JRipperUtils.trim(splits[1]);
                    diskID.setID(genre);
                    break;
                }
            }
        }

        if (!genres.isEmpty())
        {
            cddbResponse.setGenres(new ArrayList<>(genres));
        }

        return cddbResponse;
    }
}
