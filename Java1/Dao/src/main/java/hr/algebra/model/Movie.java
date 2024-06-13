/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author ana
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Movie {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    @XmlAttribute
    private int id;
    
    private String title;
    
    @XmlJavaTypeAdapter(PublishedDateAdapter.class)
    @XmlElement(name = "published")
    private LocalDateTime publishedDate;
    
    private String description;
    
    @XmlElement(name = "originaltitle")
    private String originalTitle;
    
    @XmlElementWrapper
    @XmlElement(name = "director")
    private List<Director> directors;
    
    @XmlElementWrapper
    @XmlElement(name = "actor")
    private List<Actor> actors;

    private String duration;
    
    @XmlElementWrapper
    @XmlElement(name = "genre")
    private List<Genre> genres;

    @XmlElement(name = "posterpath")
    private String posterPath;
    
    private String link;
    
    @XmlJavaTypeAdapter(PublishedDateAdapterShort.class)
    private LocalDate expected;

    public Movie() {
    }

    public Movie(String title, LocalDateTime pubDate, String description, String originalTitle, String duration, String posterPath, String link, LocalDate expected) {
        this.title = title;
        this.publishedDate = pubDate;
        this.description = description;
        this.originalTitle = originalTitle;
        this.duration = duration;
        this.posterPath = posterPath;
        this.link = link;
        this.expected = expected;
    }

    public Movie(int id, String title, LocalDateTime pubDate, String description, String originalTitle, String duration, String posterPath, String link, LocalDate expected) {
        this.id = id;
        this.title = title;
        this.publishedDate = pubDate;
        this.description = description;
        this.originalTitle = originalTitle;
        this.duration = duration;
        this.posterPath = posterPath;
        this.link = link;
        this.expected = expected;
    }

    public Movie(int id, String title, LocalDateTime pubDate, String description, String originalTitle, List<Director> directors, List<Actor> actors, String duration, List<Genre> genres, String posterPath, String link, LocalDate expected) {
        this.id = id;
        this.title = title;
        this.publishedDate = pubDate;
        this.description = description;
        this.originalTitle = originalTitle;
        this.directors = directors;
        this.actors = actors;
        this.duration = duration;
        this.genres = genres;
        this.posterPath = posterPath;
        this.link = link;
        this.expected = expected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime pubDate) {
        this.publishedDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDate getExpectedDate() {
        return expected;
    }

    public void setExpectedDate(LocalDate expected) {
        this.expected = expected;
    }

    @Override
    public String toString() {
        return id + " - " + title;
    }

}
