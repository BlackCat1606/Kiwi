/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Structure;

/**
 *
 * @author BLACKCAT
 */
import Structure.Cle;
import Trajectoire.Trajet;
import Timeline.timeline;
import java.io.Serializable;
import java.util.*;
import java.io.*;
import javafx.animation.Animation;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Path;

public class Forme_G implements Serializable, Comparable, Comparator<String> {
    /// pour enregistrer les carractéristiques d'un objet compléxe 

    private int nbCot;
    private double lastXPosition = 0;
    private double lastYPosition = 0;
    private double LastScale = 1;
    private double Lastangle = 0;

    public double getLastXPosition() {
        return lastXPosition;
    }

    public void setLastXPosition(double lastXPosition) {
        this.lastXPosition = lastXPosition;
    }

    public double getLastYPosition() {
        return lastYPosition;
    }

    public void setLastYPosition(double lastYPosition) {
        this.lastYPosition = lastYPosition;
    }

    public double getLastScale() {
        return LastScale;
    }

    public void setLastScale(double LastScale) {
        this.LastScale = LastScale;
    }

    public double getLastangle() {
        return Lastangle;
    }

    public void setLastangle(double Lastangle) {
        this.Lastangle = Lastangle;
    }
    private timeline timeline;

    public timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(timeline timeline) {
        this.timeline = timeline;
    }

    private double rayon;
    private String couleur;

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
/////////////////////////////////////////////

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
    ////////////////////////////////////////////////////////////////////
    private String Id;
    private double scale;

    public String getId() {
        return Id;
    }
    private boolean animated;
    private String GroupId;

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String GroupId) {
        this.GroupId = GroupId;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public LinkedList<Cle> ll = new LinkedList<Cle>();

    public int getNbCot() {
        return nbCot;
    }

    public void setNbCot(int nbCot) {
        this.nbCot = nbCot;
    }

    public double getRayon() {
        return rayon;
    }

    public void setRayon(double rayon) {
        this.rayon = rayon;
    }

    @Override
    public int compareTo(Object o) {
        String id = ((Forme_G) o).getId();
        return this.Id.compareTo(id);

    }

    private Trajet trajectoire;

    public Trajet getTrajectoire() {
        return trajectoire;
    }

    public void setTrajectoire(Trajet trajectoire) {
        this.trajectoire = trajectoire;
    }

    private String Group_id = "none";

    public String getGroup_id() {
        return Group_id;
    }

    public void setGroup_id(String Group_id) {
        this.Group_id = Group_id;
    }

    private String identif = "none";

    public String getIdentif() {
        return identif;
    }

    public void setIdentif(String identif) {
        this.identif = identif;
    }

    @Override
    public int compare(String o1, String o2) {
        o1 = this.Id;
        return o1.compareTo(o2);

    }

    @Override
    public boolean equals(Object f) {
        Forme_G m = (Forme_G) f;
        return (m.getId().equals(this.Id));
    }

}
