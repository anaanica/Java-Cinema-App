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
public class Director implements Comparable<Director>{

    @XmlAttribute
    private int id;
    @XmlElement(name = "directorname")
    private String directorName;

    public Director(String directorName) {
        this.directorName = directorName;
    }

    public Director(int id, String directorName) {
        this.id = id;
        this.directorName = directorName;
    }

    public int getIdDirector() {
        return id;
    }

    public void setIdDirector(int id) {
        this.id = id;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    @Override
    public String toString() {
        return directorName;
    }

    @Override
    public int compareTo(Director o) {
        return directorName.compareTo(o.directorName);
    }

}
