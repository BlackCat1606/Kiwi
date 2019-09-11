/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transformations;

//import static debut.Debut.poly_select;
import Groupe.Groupe;
import Polygone.Polygone;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;

/**
 *
 * @author aziz
 */
public class Translating extends Translate {

    private Object p1;
    private double initX;
    private double initY;
    private Point2D draganchor;
    Translate trans;

    public void Translating(Polygone poly, double value1, double value2)// Translation pour un polygone 
    {
        value1 = value1;
        value2 = value2;
        trans = new Translate(value1, value2);
        poly.setTranslateX(value1);
        poly.setTranslateY(value2);
        //poly.getTransforms().add(trans);
    }

    public void Translating_grp(Groupe groupe, double value, double value0) {// Translation pour un objet complexe 
        value = value;
        value0 = value0;
        trans = new Translate(value, value0);
        groupe.setTranslateX(value);
        groupe.setTranslateY(value);
    }

}
