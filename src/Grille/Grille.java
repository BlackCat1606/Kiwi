package Grille;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class Grille extends StackPane { // LA grille 

    public StackPane zoneDessin = new StackPane(); //zone de dessin , là ou les formes seront inséré
    public Repere rep;//notre chart

    /*          Le Constructeur         */
    public Grille(double longueur, double largeur, NumberAxis xAxis, NumberAxis yAxis, Node... elements) {
        this.setHeight(largeur);
        this.setWidth(longueur);
        rep = new Repere(xAxis, yAxis);
        rep.setMaxSize(longueur, largeur);
        zoneDessin.getChildren().addAll(elements);
        zoneDessin.resize(rep.getXAxis().getWidth(), rep.getYAxis().getHeight());//donner à la zone de dessin la long et la larg de repere
        zoneDessin.setTranslateX(13);
        zoneDessin.setTranslateY(-12);
        this.getChildren().addAll(rep, zoneDessin);
        this.adaptationGrille();
    }

    public void mouseCordinates()/*genération des coordonnées souris relatifs au repere */ {
        this.setOnMouseMoved((MouseEvent event) -> {

            Point2D mouseSceneCoords = new Point2D(event.getSceneX(), event.getSceneY());
            double xcord = rep.getXAxis().sceneToLocal(mouseSceneCoords).getX();
            double ycord = rep.getYAxis().sceneToLocal(mouseSceneCoords).getY();
            rep.setMouseX((double) rep.getXAxis().getValueForDisplay(xcord));
            rep.setMouseY((double) rep.getYAxis().getValueForDisplay(ycord));
        });
    }

    public void adaptationGrille() {

        /*       cordonnées de centre de repère    */
        double xCenterChart = rep.getXAxis().localToScene(rep.getXAxis().getWidth() / 2, 0).getX();//avoir le x de centre du repère
        double yCenterChart = rep.getYAxis().localToScene(0, rep.getYAxis().getHeight() / 2).getY();//avoir le y de centre du repère

        /*cordonée de centre de la zone de dessin */
        double xCenterGrille = zoneDessin.localToScene(zoneDessin.getWidth() / 2, zoneDessin.getHeight() / 2).getX();
        double yCenterGrille = zoneDessin.localToScene(zoneDessin.getWidth() / 2, zoneDessin.getHeight() / 2).getY();

        zoneDessin.setTranslateX(xCenterChart - xCenterGrille);
        zoneDessin.setTranslateY(yCenterChart - yCenterGrille);
    }

    public double xToPixel(double valeur) //convertir des valeurs d'abscisse à pixel
    {
        double zero = rep.getXAxis().getDisplayPosition(0);
        double un = rep.getXAxis().getDisplayPosition(1);
        return (un - zero) * valeur;
    }

    public double yToPixel(double valeur) //convertir des valeurs d'ordonnée à pixel
    {
        double zero = rep.getYAxis().getDisplayPosition(0);
        double un = rep.getYAxis().getDisplayPosition(1);
        return (un - zero) * valeur;
    }

    public double pixelToY(double valeur) //convertir des pixel en valeurs d'ordonnées
    {
        double zero = rep.getYAxis().getDisplayPosition(0);
        double un = rep.getYAxis().getDisplayPosition(1);
        return valeur / (un - zero);
    }

    public double pixelToX(double valeur) //convertir des pixxels en valeurs d'abscisses
    {
        double zero = rep.getXAxis().getDisplayPosition(0);
        double un = rep.getXAxis().getDisplayPosition(1);
        return valeur / (un - zero);
    }

    public void showXY(Node A) // Pas encore terminé son rôle est d'afficher les positions xy lors le pointeur pointe sur lui
    {
        Label lab = new Label();
        A.setOnMouseEntered((MouseEvent ev) -> {
            lab.setDisable(false);
            String str = new String();
            Double a = pixelToX(A.getTranslateX());
            str = "position de x" + a.toString();

            lab.setText(str);

            zoneDessin.getChildren().add(lab);

        });
        A.setOnMouseExited((MouseEvent ev) -> {
            lab.setDisable(true);
        });
    }
}
