/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grille;

/**
 *
 * @author Fodil
 */
import javafx.scene.shape.Rectangle;

public class Rectangle1 extends Rectangle {

    public Rectangle1(float longueur, float largeur) {
        this.setX(-longueur / 2);
        this.setY(largeur / 2);
        this.setHeight(longueur);
        this.setWidth(largeur);

    }

}
