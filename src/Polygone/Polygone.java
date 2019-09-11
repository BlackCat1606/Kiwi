/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Polygone;

import Trajectoire.Trajet;
import Grille.Grille;
import Timeline.timeline;
import Structure.Cle;
//import static app.InterfaceController.notreGrille;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 *
 * @author user
 */
public class Polygone extends Polygon implements Serializable {
/// La classe polygone 

    /**
     *
     * @param r
     * @param n
     */
    Double tab[];

    private double rayon;
    private String group_id = "none";
    public static int pol_select = 0;

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    private timeline timeline;
//LinkedList<Cle> ll=new LinkedList<>();

    public timeline getTimeline() {
        return timeline;
    }

    private String identif = "none";

    public String getIdentif() {
        return identif;
    }

    public void setIdentif(String identif) {
        this.identif = identif;
    }

    public void setTimeline(timeline timeline) {
        this.timeline = timeline;
    }
    private int nbr_cotes;
    private Point2D pivot;
    private Object p1;
    private double initX;
    private double initY;
    private Point2D draganchor;
    private String Couleur;
    private double trueScaleX = 1;
    private double trueScaleY = 1;
    private boolean selectionne = false;
    private Trajet trajectoire;

    public Trajet getTrajectoire() {
        return trajectoire;
    }

    public void setTrajectoire(Trajet trajectoire) {
        this.trajectoire = trajectoire;
    }

    public Polygone() {

    }
    public LinkedList<Cle> ll;

    public String getCouleur() {
        return Couleur;
    }

    public void setCouleur(String Couleur) {
        this.Couleur = Couleur;
    }
    private double angle;

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
    private StackPane root = new StackPane();

    public Polygone(double r, int n) // Constructeur avec les paramétres (rayon , nombre de cotés ) 
    {
        tab = new Double[2 * n];
        timeline = new timeline(60);
        ll = new LinkedList<>();
        // timeline.setDisable(true);
        double a;
        a = (2 * Math.PI / n);
        int j = 0;
        this.pivot = new Point2D(0, 0);
        for (int i = 0; i < n; i++) {
            tab[j] = Math.cos(i * a) * r;
            tab[j + 1] = Math.sin(i * a) * r;
            j += 2;
        }
        this.getPoints().addAll(tab);
        this.rayon = r;
        this.nbr_cotes = n;
        this.setFill( //on remplie notre rectangle avec un dégradé
                new LinearGradient(0f, 0f, 0f, 1f, true, CycleMethod.NO_CYCLE,
                        new Stop[]{
                            new Stop(0, Color.web("#333333")),
                            new Stop(1, Color.web("#000000"))
                        }
                )
        );
        // this.selectionner(true);  
        //  System.out.println(this.getStrokeDashOffset());

    }

    public void set_rayon(int r) {
        this.rayon = r;
    }

    public void set_nbr_cotes(int nbr) {
        this.nbr_cotes = nbr;
    }

    public double get_rayon() {
        return this.rayon;
    }

    public int get_nbr_cotes() {
        return this.nbr_cotes;
    }

    public Point2D get_pivot() {
        return this.pivot;
    }

    public void set_pivot(Point2D p) {
        this.pivot = p;
    }

    public void setSelectionne(boolean state) {//le polygone sera selectionnée logiquement
        this.selectionne = state;
    }

    public void selectionner(boolean activate) {
        //activer la selection si activate est vrai lors le clic 
        if (activate) {

            if (getSelectionne()) {
                pol_select--;
                this.setStroke(getFill());
                setSelectionne(false);
            } else {
                pol_select++;
                this.setStrokeWidth(3.0);
                this.setStroke(Color.AQUA.saturate());
                setSelectionne(true);
                //  poly_select = this;

            }
        } else {
            this.setOnMouseClicked(e -> {
            });
        }

    }

    public boolean getSelectionne() {//récupérer l'état de selection de polygone

        return this.selectionne;
    }

    public void drag(Grille notreGrille, Polygone poly, boolean active) { // Drag du polygone
        if (active) {

            poly.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    poly.setStroke(Color.TRANSPARENT);
                }
            });
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
                    if (((newXPosition - poly.get_rayon() / 2) >= -notreGrille.zoneDessin.getWidth() / 2) && ((newXPosition + poly.get_rayon() / 2) <= notreGrille.zoneDessin.getWidth() / 2)) {
                        poly.setTranslateX(newXPosition);
                    }
                    if ((newYPosition - poly.get_rayon() / 2 >= -notreGrille.zoneDessin.getHeight() / 2) && (newYPosition + poly.get_rayon() / 2 <= notreGrille.zoneDessin.getHeight() / 2)) {
                        poly.setTranslateY(newYPosition);
                    }
                    ////////
                    Parent courant = poly.getParent();
                    while (courant instanceof Group) {
                        courant = courant.getParent();
                    }
                    StackPane sauve = (StackPane) courant;
                    List<Node> liste = new ArrayList<Node>();

                    liste = sauve.getChildren();
                    for (Node test : liste) {
                        if (test instanceof Shape && (test != poly)) {

                            Shape intersect = Shape.intersect(poly, (Shape) test);
                            if (intersect.getBoundsInLocal().getWidth() != -1) {
                                ((Shape) test).setStroke(Color.CHOCOLATE);
                                (poly).setStroke(Color.CHOCOLATE);
                            } else {
                                ((Shape) test).setStroke(Color.BLACK);
                            }
                        }
                    }
                }

            });
            /**
             * ***********************************************
             */
            poly.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    //poly.setStroke(Color.TRANSPARENT);
                    initX = poly.getTranslateX();
                    initY = poly.getTranslateY();
                    draganchor = new Point2D(t.getSceneX(), t.getSceneY());

                }

            });
        } else {
            poly.setOnMouseDragged(e -> {
            });
        }
        //return poly;
    }

    /**
     *
     * @param i
     */
    public void setTrueScaleX(double i) {
        trueScaleX = i;
        super.setScaleX(super.getScaleX() * trueScaleX);

    }

    public double getTrueScaleX() {

        return trueScaleX;
    }

    public void setTrueScaleY(double i) {
        trueScaleY = i;
        super.setScaleY(super.getScaleY() * trueScaleY);

    }

    public double getTrueScaleY() {

        return trueScaleY;
    }

    public void adopteGrille(Grille grille) {
        grille.pixelToX(rayon);

    }

    public List<Node> getIntersection(Pane pane) /* retourne  les Shapes qui sont en intersection avec ce polygone la dans le Pane pane  */ {
        List<Node> listeIntersection = new ArrayList<Node>();
        List<Node> liste = new ArrayList<Node>();

        liste = pane.getChildren();
        for (Node test : liste) {
            if ((test != this)) {

                if (test instanceof Shape) {
                    Shape intersect = Shape.intersect(this, (Shape) test);
                    if (intersect.getBoundsInLocal().getWidth() != -1) {
                        listeIntersection.add(test);

                    }
                }
            }
        }

        return listeIntersection;
    }

}
