/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.parsers.rss;

import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Genre;
import hr.algebra.model.Movie;
import hr.algebra.utilities.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author ana
 */
public class MovieParser {
    private static final String WHITE_SPACE = ", ";
    private static final String DEL = ",";
    private static final String DATE_PATTERN = "dd.MM.yyyy";
    private static final String RSS_URL = "https://www.blitz-cinestar-bh.ba/rss.aspx?id=2682";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";

    private MovieParser() {}

    public static List<Movie> parse() throws IOException, XMLStreamException {
        List<Movie> movies = new ArrayList<>();
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(RSS_URL);
        try (InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);
            Optional<TagType> tagType = Optional.empty();
            Movie movie = null;
            StartElement startElement = null;

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);
                        if (tagType.isPresent() && tagType.get().equals(TagType.ITEM)) {
                            movie = new Movie();
                            movies.add(movie);   
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (tagType.isPresent()) {
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();
                            switch (tagType.get()) {
                                case TITLE:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setTitle(data);
                                    }
                                    break;

                                case PUB_DATE:
                                    if (movie != null && !data.isEmpty()) {
                                        LocalDateTime publishedDate = LocalDateTime.parse(data, DateTimeFormatter.RFC_1123_DATE_TIME);
                                        movie.setPublishedDate(publishedDate);
 
                                    }
                                    break;

                                case DESCRIPTION:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setDescription(data);
                                    }
                                    break;

                                case ORIGINAL_TITLE:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setOriginalTitle(data);
                                    }
                                    break;

                                case DIRECTOR:
                                    if (movie != null && !data.isEmpty()) {
                                        List<Director> directors = new ArrayList<>();
                                        String noSpaces = data.replaceAll(WHITE_SPACE, DEL);
                                        String[] elements = noSpaces.split(DEL);
                                        for (String value : elements){
                                            directors.add(new Director(value));
                                        }
                                        movie.setDirectors(directors);
                                    }
                                    break;

                                case ACTOR:
                                    if (movie != null && !data.isEmpty()) {
                                        List<Actor> actors = new ArrayList<>();
                                        String noSpaces = data.replaceAll(WHITE_SPACE, DEL);
                                        String[] elements = noSpaces.split(DEL);
                                        for (String value : elements){
                                            actors.add(new Actor(value));
                                        }
                                        movie.setActors(actors);
                                    }
                                    break;

                                case DURATION:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setDuration(data);
                                    }
                                    break;

                                case GENRES:
                                    
                                    if (movie != null && !data.isEmpty()) {
                                        List<Genre> genres = new ArrayList<>();
                                        String noSpaces = data.replaceAll(WHITE_SPACE, DEL);
                                        String[] elements = noSpaces.split(DEL);
                                        for (String value : elements){
                                            genres.add(new Genre(value));
                                        }
                                        movie.setGenres(genres);
                                    }
                                    break;

                                case POSTER:
                                    if (movie != null && startElement != null && movie.getPosterPath() == null) {
                                        handlePicture(movie, data);
                                    }
                                    break;

                                case LINK:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setLink(data);
                                    }
                                    break;
                                case EXPECTED:
                                    if (movie != null && !data.isEmpty()) {

                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.GERMAN);
                                        LocalDate date = LocalDate.parse(data, formatter);
                                        movie.setExpectedDate(date);
                                    }
                                    break;
                            }
                        }
                        break;
                }
            }
        }
        return movies;
    }
    

    private static void handlePicture(Movie movie, String pictureUrl) {
        try {
            String ext = pictureUrl.substring(pictureUrl.lastIndexOf("."));
            if (ext.length() > 4) {
                ext = EXT;
            }
            String pictureName = UUID.randomUUID() + ext;
            String localPicturePath = DIR + File.separator + pictureName;

            FileUtils.copyFromUrl(pictureUrl, localPicturePath);
            movie.setPosterPath(localPicturePath);
        } catch (IOException ex) {
            Logger.getLogger(MovieParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private enum TagType {

        ITEM("item"),
        TITLE("title"),
        PUB_DATE("pubDate"),
        DESCRIPTION("description"),
        ORIGINAL_TITLE("orignaziv"),
        DIRECTOR("redatelj"),
        ACTOR("glumci"),
        DURATION("trajanje"),
        GENRES("zanr"),
        POSTER("plakat"),
        LINK("link"),
        EXPECTED("datumprikazivanja");

        private final String name;

        private TagType(String name) {
            this.name = name;
        }

        private static Optional<TagType> from(String name) {
            for (TagType value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }
}
