/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author ana
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Genre {

    @XmlAttribute
    private int id;
    @XmlElement(name = "genretype")
    private String genreName;

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public Genre(int id, String genreName) {
        this.id = id;
        this.genreName = genreName;
    }

    public int getIdGenre() {
        return id;
    }

    public void setIdGenre(int id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public String toString() {
        return genreName;
    }

}
