package Structure;

import Groupe.Groupe;
import java.io.Serializable;
import java.util.ArrayList;

public class Animation_liste implements Serializable { /// Structure pour enregistrer les animations

    public ArrayList<Forme> Anim = new ArrayList<>();  /// Tableau dynamique pour enregistrer les animations de polygones
    public ArrayList<Forme_G> AnimG = new ArrayList<>();/// Tableau dynamique pour enregistrer les animations des objets complexes
}
