/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transformations;

import Groupe.Groupe;
import Polygone.Polygone;
import javafx.geometry.Point3D;
import javafx.scene.transform.Rotate;

/**
 *
 * @author BLACKCAT
 */
public class Rotating extends Rotate {/// Rotation pour un polygone

    Rotate rot;

    public void Rotating(Polygone poly, double value) {
        rot = new Rotate(value);
        rot.setAngle(value);
        // poly.getTransforms().add(rot);
        poly.setRotate(value);
    }

    public void Rotating_grp(Groupe groupe, double value) { // Rotations pour un objet complexe
        rot = new Rotate(value);
        rot.setAngle(value);
        groupe.setRotate(value);

    }
}
