/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transformations;

import Groupe.Groupe;
import Polygone.Polygone;
import javafx.scene.transform.Scale;

/**
 *
 * @author BLACKCAT
 */
public class Scaling extends Scale {/// Changement d'echelle pour un polygone

    Scale sc;

    public void Scaling(Polygone poly, double value) {
        value = value;
        sc = new Scale(value, value);
        poly.setScaleX(value);
        poly.setScaleY(value);

        //.getTransforms().add(sc);
    }

    public void Scaling_grp(Groupe groupe, double value) {///Changement d'echelle pour un objet complexe
        value = value;
        sc = new Scale(value, value);
        groupe.setScaleX(value);
        groupe.setScaleY(value);

    }
}
