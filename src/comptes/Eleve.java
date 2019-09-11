/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comptes;

import java.io.Serializable;
import java.util.Comparator;


public class Eleve implements Serializable,Comparable,Comparator<String> { /// Information ur l'éléve
    private String Nom;
    private String Prenom;
    private String MotDePasse;
    private String    AnneeClasse; 

    public Eleve(String Nom, String Prenom, String MotDePasse, String AnneeClasse) {
        this.Nom = Nom;
        this.Prenom = Prenom;
        this.MotDePasse = MotDePasse;
        this.AnneeClasse = AnneeClasse;
    }
     
    public String getNom() {
        return Nom;
    }

   
    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String Prenom) {
        this.Prenom = Prenom;
    }

    public String getMotDePasse() {
        return MotDePasse;
    }

    public void setMotDePasse(String MotDePasse) {
        this.MotDePasse = MotDePasse;
    }

    public String getAnneeClasse() {
        return AnneeClasse;
    }

    public void setAnneeClasse(String AnneeClasse) {
        this.AnneeClasse = AnneeClasse;
    }

    
    
    @Override
    public int compare(String o1,String o2){
        o1=this.Nom;
        return (o1.compareTo(o2));
    }
    @Override
    public boolean equals(Object object){
        Eleve eleve =(Eleve) object;
        return (eleve.getNom().equals(this.Nom));
    }

    @Override
    public int compareTo(Object o) {
        String nom = ((Eleve) o).getNom();
        return (this.Nom.compareTo(nom));
    }
    
}
