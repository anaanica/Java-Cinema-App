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
public class Actor implements Comparable<Actor>{

    @XmlAttribute
    private int id;
    @XmlElement(name = "actorname")
    private String actorName;

    public Actor(String actorName) {
        this.actorName = actorName;
    }

    public Actor(int id, String actorName) {
        this.id = id;
        this.actorName = actorName;
    }

    public int getIdActor() {
        return id;
    }

    public void setIdActor(int id) {
        this.id = id;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    @Override
    public String toString() {
        return actorName;
    }

    @Override
    public int compareTo(Actor o) {
        return actorName.compareTo(o.actorName);
    }

}
