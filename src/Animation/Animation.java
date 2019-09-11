/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Animation;

import AlerteBox.AlerteBox;
import Groupe.Groupe;
import Structure.Animation_liste;
import Grille.Grille;
import Polygone.Polygone;
import Structure.Forme;
import Structure.Forme_G;
import static app.InterfaceController.poly_select;
import java.util.ArrayList;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.PathTransitionBuilder;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import static app.InterfaceController.group_select;
import java.util.Iterator;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

/**
 *
 * @author BLACKCAT
 */
public class Animation {

    public Animation() { /// Constructeur par défaut 
    }

    ;
public void record(Timeline timeline, Polygone poly, Animation_liste anim, Grille root) { //// animer un polygone
        int i = 0;

        while (i < anim.Anim.size()) {
            double rayon = anim.Anim.get(i).getRayon();
            int nbr_c = anim.Anim.get(i).getNbCot();
            String couleur = anim.Anim.get(i).getCouleur();
            double eche = anim.Anim.get(i).getScale();

            if (anim.Anim.get(i).isAnimated() && anim.Anim.get(i).getGroup_id().equals("none")) {

                //double ang = anim.Anim.get(i).ll.getFirst().getAngle();
                poly = new Polygone(rayon, nbr_c);
                poly.ll = anim.Anim.get(i).ll;
                //poly.drag(poly, true);
                double ang = anim.Anim.get(i).ll.getFirst().getAngle();
                poly.setFill(Paint.valueOf(couleur));
                poly.setScaleX(eche);
                poly.setScaleY(eche);

                poly.setRotate(ang);
                poly.drag(root, poly, true);
                //   root.zoneDessin.getChildren().remove(poly_select); 
                root.zoneDessin.getChildren().add(poly);
                root.adaptationGrille();
                //     poly_select = poly;

                boolean transition = false;
                boolean rotation = false;
                boolean echelle = false;
                int j = 0;
                int pos = 0;
                int previous_temps = 0;
                double previous_x = 0.0;

                double previous_y = 0.0;
                double previous_scale = 1.0;
                double previous_angle = 0.0;

                while (j < anim.Anim.get(i).ll.size()) {
                    double x = anim.Anim.get(i).ll.get(j).getX();
                    double y = anim.Anim.get(i).ll.get(j).getY();;

                    double angle = anim.Anim.get(i).ll.get(j).getAngle();
                    double scale = anim.Anim.get(i).ll.get(j).getEchelle();
                    //KeyValue[] keys = new KeyValue[10];
                    // int siz = 0;
                    int temps = anim.Anim.get(i).ll.get(j).getDuree();

                    if (x != previous_x && y != previous_y) {

                        KeyValue key1 = new KeyValue(poly.translateXProperty(), x, Interpolator.EASE_BOTH);//TANGENT(Duration.seconds(temps-previous_temps), x));   
                        KeyValue key2 = new KeyValue(poly.translateYProperty(), y, Interpolator.EASE_BOTH);//TANGENT(Duration.seconds(temps-previous_temps),y ));

                        KeyFrame frame = new KeyFrame(Duration.seconds(temps), key1, key2/*,keyangle,keyscx,keyscy*/);
                        timeline.getKeyFrames().add(frame);
                        //timeline.playFrom(Duration.seconds(temps));
                        pos++;
                    }

                    if (angle != previous_angle) {

                        KeyValue keyscx = new KeyValue(poly.scaleXProperty(), previous_scale, Interpolator.EASE_BOTH);//TANGENT(Duration.seconds(previous_temps), previous_scale, Duration.seconds(temps), scale));
                        KeyValue keyscy = new KeyValue(poly.scaleYProperty(), previous_scale, Interpolator.EASE_BOTH);
                        KeyValue key1 = new KeyValue(poly.rotateProperty(), previous_angle, Interpolator.EASE_BOTH
                        );
                        KeyValue key = new KeyValue(poly.rotateProperty(), angle, Interpolator.EASE_BOTH);//TANGENT( Duration.seconds(temps-previous_temps),previous_angle,Duration.seconds(temps),angle));

                        KeyFrame frame0 = new KeyFrame(Duration.seconds(previous_temps), key1, keyscx, keyscy);
                        KeyFrame frame1 = new KeyFrame(Duration.seconds(temps), key);
                        timeline.getKeyFrames().addAll(frame0, frame1);
                        pos++;
                    }
                    if (scale != previous_scale) {

                        KeyValue keyangle = new KeyValue(poly.rotateProperty(), previous_angle, Interpolator.EASE_BOTH);
                        KeyValue key = new KeyValue(poly.scaleXProperty(), previous_scale, Interpolator.EASE_BOTH);
                        KeyValue key0 = new KeyValue(poly.scaleYProperty(), previous_scale, Interpolator.EASE_BOTH);

                        KeyValue key1 = new KeyValue(poly.scaleXProperty(), scale, Interpolator.EASE_BOTH
                        );
                        KeyValue key2 = new KeyValue(poly.scaleYProperty(), scale, Interpolator.EASE_BOTH);

                        KeyFrame frame0 = new KeyFrame(Duration.seconds(previous_temps), keyangle, key, key0);
                        KeyFrame frame = new KeyFrame(Duration.seconds(temps), key1, key2);
                        timeline.getKeyFrames().addAll(frame0, frame);

                        pos++;
                    }
                    previous_temps = temps;
                    previous_x = x;
                    previous_angle = angle;
                    previous_y = y;
                    previous_scale = scale;
                    //timeline.
                    j++;
                }
            }

            i++;
        }

        timeline.setAutoReverse(true);
        timeline.setCycleCount(1);

    }

    public void record_grp(Timeline timeline, Animation_liste anim, Grille root) //// Animer un Objet complexe
    {
        int i = 0;
        while (i < anim.AnimG.size()) {

            String couleur = anim.AnimG.get(i).getCouleur();
            double eche = anim.AnimG.get(i).getScale();

            if (anim.AnimG.get(i).getIdentif() != "none" && anim.AnimG.get(i).isAnimated()) {
                double ang = anim.AnimG.get(i).ll.getFirst().getAngle();

                Forme_G r = anim.AnimG.get(i);
                Groupe grp = new Groupe();
                Iterator<Forme> fr = anim.Anim.iterator();
                ArrayList<Node> groupage = new ArrayList<>();
                System.out.println(r.getIdentif());
                while (fr.hasNext()) {
                    Forme f = fr.next();
                    System.out.println("   azul  " + f.getGroup_id());
                    Polygone poly = new Polygone((int) f.getRayon(), f.getNbCot());
                    if (r.getIdentif().equals(f.getGroup_id())) {
                        poly.setTranslateX(f.getLastXPosition());
                        poly.setTranslateY(f.getLastYPosition());
                        poly.setRotate(f.getLastangle());
                        poly.setScaleX(f.getLastScale());
                        poly.setScaleY(f.getLastScale());
                        poly.setFill(Paint.valueOf(f.getCouleur()));
                        System.out.println("hello it's me");
                        System.out.println("this is it yaker " + poly.getId());
                        groupage.add(poly);
                    }
                }
                grp = Groupe.regrouper(root.zoneDessin, groupage);

                grp.setScaleX(eche);
                grp.setScaleY(eche);
                grp.setRotate(ang);
                grp.setEffect(new Lighting());
                // root.zoneDessin.getChildren().remove(group_select);
                //try
                //{
                // root.zoneDessin.getChildren().
                // root.zoneDessin.getChildren().add(grp); 
                root.adaptationGrille();
                // }
                /*  catch(Exception e)
                  {
                      grp.setId("problem");
                  }*/
//           grp.setFill(Paint.valueOf(couleur));

                // group_select = grp;   
                //  root.zoneDessin.getChildren().remove(grp);
                int j = 0;
                int pos = 0;
                int previous_temps = 0;
                double previous_x = 0.0;

                double previous_y = 0.0;
                double previous_scale = 1.0;
                double previous_angle = 0.0;

                while (j < anim.AnimG.get(i).ll.size()) {
                    double x = anim.AnimG.get(i).ll.get(j).getX();
                    double y = anim.AnimG.get(i).ll.get(j).getY();;

                    double angle = anim.AnimG.get(i).ll.get(j).getAngle();
                    double scale = anim.AnimG.get(i).ll.get(j).getEchelle();

                    int temps = anim.AnimG.get(i).ll.get(j).getDuree();

                    if (x != previous_x && y != previous_y) {

                        KeyValue key1 = new KeyValue(grp.translateXProperty(), x, Interpolator.EASE_BOTH);

                        KeyValue key2 = new KeyValue(grp.translateYProperty(), y, Interpolator.EASE_BOTH);

                        KeyFrame frame = new KeyFrame(Duration.seconds(temps), key1, key2);

                        timeline.getKeyFrames().add(frame);

                    }

                    if (angle != previous_angle) {

                        KeyValue keyscx = new KeyValue(grp.scaleXProperty(), previous_scale, Interpolator.EASE_BOTH);
                        KeyValue keyscy = new KeyValue(grp.scaleYProperty(), previous_scale, Interpolator.EASE_BOTH);

                        KeyValue keyx = new KeyValue(grp.translateXProperty(), previous_x, Interpolator.EASE_BOTH);
                        KeyValue keyy = new KeyValue(grp.translateYProperty(), previous_y, Interpolator.EASE_BOTH);

                        KeyValue key1 = new KeyValue(grp.rotateProperty(), previous_angle, Interpolator.EASE_BOTH);

                        KeyValue key = new KeyValue(grp.rotateProperty(), angle, Interpolator.EASE_BOTH);

                        KeyFrame frame0 = new KeyFrame(Duration.seconds(previous_temps), key1, keyscx, keyscy, keyx, keyy);
                        KeyFrame frame1 = new KeyFrame(Duration.seconds(temps), key);
                        timeline.getKeyFrames().addAll(frame0, frame1);
                        pos++;
                    }
                    if (scale != previous_scale) {

                        KeyValue keyangle = new KeyValue(grp.rotateProperty(), previous_angle, Interpolator.EASE_BOTH);
                        KeyValue key = new KeyValue(grp.scaleXProperty(), previous_scale, Interpolator.EASE_BOTH);
                        KeyValue key0 = new KeyValue(grp.scaleYProperty(), previous_scale, Interpolator.EASE_BOTH);

                        KeyValue key1 = new KeyValue(grp.scaleXProperty(), scale, Interpolator.EASE_BOTH
                        );
                        KeyValue key2 = new KeyValue(grp.scaleYProperty(), scale, Interpolator.EASE_BOTH);
                        KeyFrame frame0 = new KeyFrame(Duration.seconds(previous_temps), keyangle, key, key0);
                        KeyFrame frame = new KeyFrame(Duration.seconds(temps), key1, key2);
                        timeline.getKeyFrames().addAll(frame0, frame);
                        pos++;
                    }
                    previous_temps = temps;
                    previous_x = x;
                    previous_angle = angle;
                    previous_y = y;
                    previous_y = y;
                    previous_scale = scale;

                    j++;
                }
            }
            i++;
        }
        timeline.setCycleCount(1);

    }

    public void play(Timeline timeline) {
        timeline.play();
    }

    public void pause(Timeline timeline) {
        timeline.pause();
    }

    public void stop(Timeline timeline) {
        timeline.stop();
    }
    public PathTransition pathTransition;

    public void path_animtaion(Scene scene3, Pane root4, Path path, Polygone poly1) {/// Animer un polygone sur un trajet
        Polygone poly;
        if (poly1.getGroup_id() == "none") {
            double rayon = poly1.get_rayon();
            int nbr = poly1.get_nbr_cotes();
            path.setStrokeWidth(1);
            path.setStroke(Color.BLACK);
            poly = new Polygone(rayon, nbr);
            poly.setFill(poly1.getFill());
            poly.setTranslateX(450);
            poly.setTranslateY(300);

            //Mouse button pressed - clear path and start from the current X, Y.
            scene3.onMousePressedProperty().set(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    path.getElements().clear();
                    path.getElements().add(new MoveTo(event.getX(), event.getY()));
                }
            });

            //Mouse dragged - add current point.
            scene3.onMouseDraggedProperty().set(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    path.getElements().add(new LineTo(event.getX(), event.getY()));
                }
            });

            //Mouse button released,  finish path.
            scene3.onMouseReleasedProperty().set(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    pathTransition = PathTransitionBuilder.create()
                            .node(poly)
                            .path(path)
                            .duration(Duration.millis(5000))
                            .orientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT)
                            .cycleCount(1)
                            .interpolator(Interpolator.EASE_BOTH)
                            .rate(0.5)
                            .build();
                    AlerteBox a = new AlerteBox();
                    AlerteBox.infoBox("Cliquez sur Jouer pour Commencer L'annimation", "Animation Trajet Polygone");
                }
            });
            root4.getChildren().addAll(poly, path);

        }

    }

    public void path_animtaion_Grp(Scene scene3, Pane root4, Path path, Group groupes) {/// Animer un objet complexe sur un trajet
        Group groupe = new Group();

        groupe.getChildren().add(groupes);

        //Mouse button pressed - clear path and start from the current X, Y.
        scene3.onMousePressedProperty().set(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                path.getElements().clear();
                path.getElements().add(new MoveTo(event.getX(), event.getY()));
            }
        });

        //Mouse dragged - add current point.
        scene3.onMouseDraggedProperty().set(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                path.getElements().add(new LineTo(event.getX(), event.getY()));
            }
        });
        pathTransition = new PathTransition();
        //Mouse button released,  finish path.
        scene3.onMouseReleasedProperty().set(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                pathTransition = PathTransitionBuilder.create()
                        .node(groupe)
                        .path(path)
                        .duration(Duration.millis(5000))
                        .orientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT)
                        .cycleCount(1)
                        .interpolator(Interpolator.EASE_BOTH)
                        .rate(0.5)
                        .build();
                AlerteBox a = new AlerteBox();
                AlerteBox.infoBox("Cliquez sur Jouer pour Commencer L'annimation", "Animation Trajet Objet complexe");
            }
        });
        root4.getChildren().addAll(groupe, path);

    }

    public void path_animtaion_open(Scene scene3, Pane root4, Path path, Polygone poly1) {
        Polygone poly;
        if (poly1.getGroup_id() == "none") {
            double rayon = poly1.get_rayon();
            int nbr = poly1.get_nbr_cotes();
            path.setStrokeWidth(1);
            path.setStroke(Color.BLACK);
            poly = new Polygone(rayon, nbr);
            poly.setFill(poly1.getFill());
            poly.setTranslateX(450);
            poly.setTranslateY(300);

            //Mouse button pressed - clear path and start from the current X, Y.   
            //Mouse button released,  finish path.
            pathTransition = PathTransitionBuilder.create()
                    .node(poly)
                    .path(path)
                    .duration(Duration.millis(5000))
                    .orientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT)
                    .cycleCount(1)
                    .interpolator(Interpolator.EASE_BOTH)
                    .rate(0.5)
                    .build();
            AlerteBox a = new AlerteBox();
            AlerteBox.infoBox("Cliquez sur Jouer pour Commencer L'annimation", "Animation Trajet Polygone");
            root4.getChildren().addAll(poly, path);

            // poly.drag(root,poly,true);
        }

    }

    public void path_animtaion_Grp_open(Scene scene3, Pane root4, Path path, Group groupes) {
        Group groupe = new Group();

        groupe.getChildren().add(groupes);

        //int rayon = poly1.get_rayon();
        //int nbr = poly1.get_nbr_cotes();
        //path.setStrokeWidth(1);
        //path.setStroke(Color.BLACK);
        //poly = new Polygone(rayon,nbr);
        //poly.setFill(poly1.getFill());
        //groupe.setTranslateX(400);
        //groupe.setTranslateY(400);
        pathTransition = new PathTransition();

        pathTransition = PathTransitionBuilder.create()
                .node(groupe)
                .path(path)
                .duration(Duration.millis(5000))
                .orientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT)
                .cycleCount(1)
                .interpolator(Interpolator.EASE_BOTH)
                .rate(0.5)
                .build();
        AlerteBox a = new AlerteBox();
        AlerteBox.infoBox("Cliquez sur Jouer pour Commencer L'annimation", "Animation Trajet Objet complexe");

        root4.getChildren().addAll(groupe, path);

        //poly.drag(poly);     
    }
}
