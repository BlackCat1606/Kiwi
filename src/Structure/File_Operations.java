package Structure;

import Structure.Entete;
import AlerteBox.AlerteBox;
import Groupe.Groupe;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import comptes.Eleve;
import java.util.Collection;
import java.util.Scanner;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class File_Operations { /// Operations sur les fichiers (ouverture ,sauvegarde ...) 

    String nomFichier;

    public void Sauvegarder(Animation_liste m) {

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        // Read file name
        Scanner sc = new Scanner(System.in);
        System.out.println("Pupil's name= ");
        String nomEleve = sc.nextLine();
        nomFichier = nomEleve.concat("_SéanceXX.poly");
        // Entete			
        Entete entete = new Entete();

        String motDePasse = "user";
        String motDePasseStandard = "admin";

        String a = "nnnnn";
        String b = "adel_tebessa12";

        entete.setMotDePasse(motDePasse);
        entete.setNom(nomEleve);
        //System.out.println("Le diamétre est"+m.Anim.get(0).get_rayon());
        entete.setMotDePasseStandard(motDePasseStandard);

        //Animation_liste kk = new Animation_liste();
        /// ****** Function of Saving File ****** ///
        try {
            fos = new FileOutputStream(nomFichier);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(entete);
            oos.writeObject(m);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("opération réussie");
        }
    }

    public Animation_liste Ouvrir() {
        Animation_liste k = new Animation_liste();
        int taille = 0;
        int i = 0;
        try {

            FileInputStream fis = new FileInputStream(nomFichier);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Entete entete2 = (Entete) ois.readObject();
            Animation_liste kk = (Animation_liste) ois.readObject();
            String a = "user";
            String b = "adel_tebessa12";

            // if (a.equals(entete2.getMotDePasse())||b.equals(entete2.getMotDePasseStandard()))
            System.out.println(entete2.getMotDePasse());
            System.out.println(entete2.getMotDePasseStandard());
            System.out.println(entete2.getNom());
            k = kk;
            System.out.println("savaaaa!");

            //}
            // else { System.out.println("Wrong Password!");}
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return k;
    }

    public boolean Sauvegarder_eleve(Animation_liste m, Path chemin, Eleve eleve) {
        boolean success = false;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        // Read file name
        String nomEleve = eleve.getNom().toLowerCase() + "_".concat(eleve.getPrenom().toLowerCase()).concat("_" + eleve.getAnneeClasse());
        nomFichier = chemin.toString().concat("\\" + nomEleve.concat(".poly"));
        Entete entete = new Entete();
        String name = eleve.getNom().toLowerCase() + ".".concat(eleve.getPrenom().toLowerCase());
        String motDePasse = eleve.getMotDePasse();
        String motDePasseStandard = "admin";

        entete.setMotDePasse(motDePasse);
        entete.setNom(name);

        entete.setMotDePasseStandard(motDePasseStandard);

        /// ****** Function of Saving File ****** ///
        try {

            fos = new FileOutputStream(nomFichier);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(entete);
            oos.writeObject(m);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            success = false;

        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        } finally {
            System.out.println("opération réussie");

            success = true;
        }
        return success;
    }

    public Animation_liste Ouvrir_eleve(Eleve eleve, boolean Ouvert, Path chemin) {

        FileChooser fileChooser = new FileChooser();
        Stage root = new Stage();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Poly-Anim files (*.POLY), (*.poly)", "*.poly");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(new File(chemin.toString()));

        File file = fileChooser.showOpenDialog(root);
        Entete entete2 = new Entete();
        ArrayList<Forme> kl = null;
        ArrayList<Groupe> ks = null;
        Animation_liste kk = new Animation_liste();
        Animation_liste km = new Animation_liste();
        if (file != null) {
            try {
                try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
                    entete2 = (Entete) ois.readObject();
                    kk = (Animation_liste) ois.readObject();
                    ois.close();
                    int v = 0;
                    while (v < kk.AnimG.size()) {

                        System.out.println("the miaaaw: " + kk.AnimG.get(v).getIdentif());
                        v++;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                entete2 = null;
                Ouvert = false;
            } catch (IOException e) {
                e.printStackTrace();
                entete2 = null;
                Ouvert = false;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                entete2 = null;
                Ouvert = false;
            }
        }

        if (entete2 != null) {
            if ((eleve.getNom().toLowerCase() + ".".concat(eleve.getPrenom().toLowerCase())).equals(entete2.getNom()) && eleve.getMotDePasse().equals(entete2.getMotDePasse())) {
                Ouvert = true;

            } else {
                AlerteBox non_Authorized = new AlerteBox();
                non_Authorized.infoBox("Accés non autorisé", "Impossible d'ouvrir le fichier");
                Ouvert = false;
            }

        }

        return kk;
    }

    public Animation_liste Ouvrir_prof(boolean Ouvert, Path chemin) {

        FileChooser fileChooser = new FileChooser();
        Stage root = new Stage();
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Poly-Anim files (*.POLY), (*.poly)", "*.poly");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(new File(chemin.toString()));

        //Show save file dialog
        File file = fileChooser.showOpenDialog(root);
        Entete entete2 = null;
        ArrayList<Forme> kl = null;
        ArrayList<Groupe> ks = null;
        Animation_liste kk = new Animation_liste();
        Animation_liste km = new Animation_liste();
        if (file != null) {
            try {
                try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
                    entete2 = (Entete) ois.readObject();
                    kk = (Animation_liste) ois.readObject();
                    ois.close();
                    Ouvert = true;
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Ouvert = false;
            } catch (IOException e) {
                e.printStackTrace();
                Ouvert = false;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Ouvert = false;
            }
        }

        return kk;
    }

    public boolean Sauvegarder_prof(Animation_liste m, Path chemin, String motPassePrincip) {
        boolean success = false;

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        // Read file name
        nomFichier = chemin.toString().concat("\\" + "Enseignant".concat(".poly"));
        // Entete			
        Entete entete = new Entete();
        String name = "Enseignat";
        String motDePasse = "admin";
        String motDePasseStandard = "admin";

        entete.setMotDePasse(motDePasse);
        entete.setNom(name);
        entete.setMotDePasseStandard(motDePasseStandard);

        /// ****** Function of Saving File ****** ///
        try {
            int v = 0;
            while (v < m.AnimG.size()) {

                System.out.println("the hell: " + m.AnimG.get(v).getId());
                v++;
            }
            fos = new FileOutputStream(nomFichier);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(entete);
            oos.writeObject(m);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            success = false;

        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        } finally {
            System.out.println("opération réussie");

            success = true;
        }
        return success;
    }

    public boolean SaveAs_prof(Animation_liste m, Path chemin, String motPassePrincip) {
        boolean success = false;
        FileChooser fileChooser = new FileChooser();
        Stage root = new Stage();
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Poly-Anim files (*.POLY), (*.poly)", "*.poly");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(new File(chemin.toString()));

        File file = fileChooser.showSaveDialog(root);
        Entete entete2 = new Entete();
        entete2.setNom("Enseignant");
        entete2.setMotDePasse(motPassePrincip);
        entete2.setMotDePasseStandard("admin");

        // Read file name
        if (file != null) {
            try {
                try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
                    oos.writeObject(entete2);
                    oos.writeObject(m);
                    oos.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                success = false;

            } catch (IOException e) {
                e.printStackTrace();
                success = false;
            } finally {
                System.out.println("opération réussie");

                success = true;
            }
        }
        return success;
    }

    public boolean SaveAs_eleve(Animation_liste m, Path chemin, Eleve eleve) {
        boolean success = false;
        FileChooser fileChooser = new FileChooser();
        Stage root = new Stage();
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Poly-Anim files (*.POLY), (*.poly)", "*.poly");
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooser.setInitialDirectory(new File(chemin.toString()));
        fileChooser.setInitialFileName(nomFichier);
        File file = fileChooser.showSaveDialog(root);
        Entete entete2 = new Entete();
        entete2.setNom(eleve.getNom().toLowerCase() + ".".concat(eleve.getPrenom().toLowerCase()));
        entete2.setMotDePasse(eleve.getMotDePasse());
        entete2.setMotDePasseStandard("admin");

        if (file != null) {
            try {
                try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
                    oos.writeObject(entete2);
                    oos.writeObject(m);
                    oos.close();
                    success = true;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                success = false;

            } catch (IOException e) {
                e.printStackTrace();
                success = false;
            } finally {
                System.out.println("opération réussie");

                //success = true;
            }
        }
        return success;
    }

    public Animation_liste Ouvrir_eleve_tree(Eleve eleve, Animation_liste k, Path chemin) {
        boolean opened = false;
        int taille = 0;
        int i = 0;
        Entete entete2 = null;
        Animation_liste kk = new Animation_liste();

        try {

            FileInputStream fis = new FileInputStream(chemin.toString());
            ObjectInputStream ois = new ObjectInputStream(fis);

            entete2 = (Entete) ois.readObject();
            kk = (Animation_liste) ois.readObject();
            k = kk;

            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            opened = false;
        } catch (IOException e) {
            e.printStackTrace();
            opened = false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            opened = false;
        }

        if (entete2 != null && kk != null) {
            if ((eleve.getNom().toLowerCase() + ".".concat(eleve.getPrenom().toLowerCase())).equals(entete2.getNom()) && eleve.getMotDePasse().equals(entete2.getMotDePasse())) {
                opened = true;
                k = kk;

            } else {
                AlerteBox non_Authorized = new AlerteBox();
                non_Authorized.infoBox("Accés non autorisé", "Impossible d'ouvrir le fichier");
                opened = false;
                kk = new Animation_liste();
            }

        }
        return kk;
    }

    public Animation_liste Ouvrir_prof_tree(Animation_liste k, Path chemin, String mot_passe) {
        boolean opened = false;
        int taille = 0;
        int i = 0;
        Entete entete2 = null;
        Animation_liste kk = null;
        try {

            FileInputStream fis = new FileInputStream(chemin.toString());
            ObjectInputStream ois = new ObjectInputStream(fis);

            entete2 = (Entete) ois.readObject();
            kk = (Animation_liste) ois.readObject();
            k = kk;
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            opened = false;
        } catch (IOException e) {
            e.printStackTrace();
            opened = false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            opened = false;
        }

        {
            if (entete2 != null && kk != null) {
                if (mot_passe.equals(entete2.getMotDePasseStandard())) {
                    opened = true;
                }
                k = kk;
            } else {
                AlerteBox non_Authorized = new AlerteBox();
                non_Authorized.infoBox("Accés non autorisé", "Impossible d'ouvrir le fichier");
                opened = false;
            }

        }
        return kk;
    }
}
