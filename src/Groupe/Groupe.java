/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package Groupe;

import Grille.Grille;
import Structure.Cle;
import Timeline.Point2Ds;
import Polygone.Polygone;
import Trajectoire.Trajet;
import Timeline.timeline;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import static app.InterfaceController.poly_select;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import static app.InterfaceController.group_select;
import static app.InterfaceController.objet_select;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

/**
 *
 * @author Fodil
 */
public class Groupe extends Group implements Serializable, Comparable, Comparator<String> {
    //// La classe qui gére les objets compléxes

    private double initTranslateX = 0;
    private double initTranslateY = 0;/*La translation du groupe lors le groupage*/
    private double initX;
    private double initY;
    private Point2Ds draganchor;
    private timeline timeline;
    private String couleur;
    private Trajet trajectoire;

    public Trajet getTrajectoire() {
        return trajectoire;
    }

    public void setTrajectoire(Trajet trajectoire) {
        this.trajectoire = trajectoire;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public LinkedList<Cle> ll = new LinkedList<Cle>();

    private boolean animated;

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(timeline timeline) {
        this.timeline = timeline;
    }
    private boolean selectionner = false;
    private int cpt = 0;

    public boolean isSelectionner() {
        return selectionner;
    }

    public void setSelectionner(boolean selectionner) {
        this.selectionner = selectionner;
    }

    public void selectionner_gp(boolean activate) // Selection d'un objet complexe
    {

        if (activate) {
            this.setOnMouseClicked(value -> {

                // objet_select = this;
                if (this.selectionner) {

                    for (Node m : this.getChildren()) {
                        ///((Polygone)m).setSelectionne(false);
                        ((Polygone) m).setStroke(((Polygone) m).getFill());
                    }
                    setSelectionner(false);
                } else {

                    for (Node m : this.getChildren()) {
                        // ((Polygone)m).setSelectionne(true);
                        ((Polygone) m).setStroke(Color.BLUE);
                    }
                    setSelectionner(true);
                }

            });

            //System.out.println("Ne pas Selectionner");
        } else {
            this.setOnMouseClicked(e -> {
            });
        }

    }

    public static Groupe regrouper(Pane parent, List<Node> liste) {// grouper les éléments de la liste le mettre dans un groupe dans la pane pane
        Groupe gp = new Groupe();
        for (Node sh : liste) {
            //sh.setDisable(true);
            sh.setOnMousePressed(value -> {

            });
            gp.getChildren().add((Shape) sh);
            //sh.setDisable(true);

        }
        parent.getChildren().add(gp);
        gp.adaptation(parent);

        // gp.getTimeline().setDisable(true);
        return gp;

    }

    public static Groupe regrouper(List<Node> liste) //grouper les element de liste et les ajouter à un groupe qui sera retourné
    {
        Groupe gp = new Groupe();
        for (Node sh : liste) {

            gp.getChildren().add((Shape) sh);

        }
        return gp;
    }
    private String identif = "none";

    public String getIdentif() {
        return identif;
    }

    public void setIdentif(String identif) {
        this.identif = identif;
    }

    public void drag(Grille grille, Groupe poly, boolean on) {  //drager le groupe poly si On est true sinon ne pas offrir la possibilité de le drager
        if (on) {

            for (Node po : this.getChildren()) {
                ((Polygone) po).drag(grille, (Polygone) po, false);
            }

            poly.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    poly.setCursor(Cursor.CLOSED_HAND);
                }
            });
            /**
             * ***********************************************
             */
            poly.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    double dragX = t.getSceneX() - draganchor.getX();
                    double dragY = t.getSceneY() - draganchor.getY();
                    poly.setCursor(Cursor.OPEN_HAND);
                    double newXPosition = initX + dragX;
                    double newYPosition = initY + dragY;
                    // po.setTranslateX(0);
                    //po.setTranslateY(0);
                    poly.setTranslateX(newXPosition);
                    poly.setTranslateY(newYPosition);
                    ////////

                }

            });
            /**
             * ***********************************************
             */
            poly.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    initX = poly.getTranslateX();
                    initY = poly.getTranslateY();
                    draganchor = new Point2Ds(t.getSceneX(), t.getSceneY());
                    //              group_select = poly;
                    //            objet_select = poly;

                    // poly.selectionner_gp(true);
                }
            });
        } else {
            poly.setOnMouseDragged(e -> {
                for (Node po : this.getChildren()) {
                    ((Polygone) po).drag(grille, (Polygone) po, true);
                }
            });
        }// desactiver le drag

    }

    public void adaptation(Pane scene) {
        //cette méthode corrige la translation implicite de groupe lors l'ajout des noeuds dans ce groupe là
        double txx = this.getLayoutBounds().getWidth() / 2;

        double tr = scene.getWidth() / 2 - txx - this.getLayoutBounds().getMinX();
        //System.out.println("gp 1="+tr+"gp 2 ="+txx);
        double tyy = this.getLayoutBounds().getHeight() / 2;
        double tryy = scene.getHeight() / 2 - tyy - this.getLayoutBounds().getMinY();
        // System.out.println("gp 1="+tr+"gp 2 ="+txx);
        this.setTranslateX(-tr);
        this.setTranslateY(-tryy);
        this.setInitTranslateX(this.getTranslateX());
        this.setInitTranslateY(this.getTranslateY());
    }

    public List<Node> degrouper(Pane pane) { // retourner les éléments d'un groupe et le supprimer de Pane

        ObservableList<Node> ol = this.getChildren();
        List<Node> list = new ArrayList<Node>();

        for (Node nd : ol) {
            list.add(nd);

            double a = -this.getInitTranslateX() + this.getTranslateX();
            double b = -this.getInitTranslateY() + this.getTranslateY();

            //   ((Polygone)nd).drag((Polygone)nd, true);
            //    ((Polygone)nd).setSelectionne(false);
            ((Polygone) nd).setGroup_id("none");
            /* ((Polygone)nd).setOnMouseClicked(value->
            {*/
            ((Polygone) nd).selectionner(true);
            // poly_select = (Polygone)nd;
            //});

            // System.out.println("le translate de polygone dans le groupe"+gp2.getTranslateX()+" &&& "+gp2.getTranslateY() +"sceneloc "+ gp2.sceneToLocal(600, 350));
            double s = nd.getTranslateX();
            double q = nd.getTranslateY();
            nd.setTranslateX(s + a);
            nd.setTranslateY(q + b);
        }
        pane.getChildren().remove(this);
        //   pane.getChildren().addAll(list);         
        return list;
    }

    /**
     * @return the initTranslateX
     */
    public double getInitTranslateX() {
        return initTranslateX;
    }

    /**
     * @param initTranslateX the initTranslateX to set
     */
    public void setInitTranslateX(double initTranslateX) {
        this.initTranslateX = initTranslateX;
    }

    /**
     * @return the initTranslateY
     */
    public double getInitTranslateY() {
        return initTranslateY;
    }

    /**
     * @param initTranslateY the initTranslateY to set
     */
    public void setInitTranslateY(double initTranslateY) {
        this.initTranslateY = initTranslateY;
    }

    public Groupe() {
        timeline = new timeline(60);
    }
    private double angle;

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setFill(Paint couleur) { //// Changer la couleur d'un objet complexe
        for (Node m : this.getChildren()) {
            ((Polygone) m).setFill(couleur);
        }
    }

    @Override
    public int compare(String o1, String o2) {
        o1 = this.identif;
        return o1.compareTo(o2);

    }

    /* @Override
    public boolean equals(Object f)
    {
         Groupe m = (Groupe)f;
        return( m.getId().equals( this.identif));
    }*/
    @Override
    public int compareTo(Object o) {
        String id = ((Groupe) o).getIdentif();
        return this.identif.compareTo(id);

    }

}
