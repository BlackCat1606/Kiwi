package Structure;

import java.io.Serializable;

public class Entete implements Serializable {/// Entete du fichier pour reconnétre l'utilisateur (gestion de sécurité)

    private String nom;
    private String motDePasse;
    private String motDePasseStandard;

    public String getNom() {
        return nom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getMotDePasseStandard() {
        return motDePasseStandard;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setMotDePasseStandard(String motDePasseStandard) {
        this.motDePasseStandard = motDePasseStandard;
    }
}
