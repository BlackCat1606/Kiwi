/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comptes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class Donnees { // Gestion des comptes sur son fichier
    
    public static void ecrireDonnees() throws FileNotFoundException, IOException, ClassNotFoundException  {
        
        /***************************************************************************************************/
        DataAccounts dataAccounts = new DataAccounts();
        
        
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir le nombre d'etudiants :");
        int n = sc.nextInt();
        String nom ;
        String prenom;
        String AnneeClasse;
        Eleve eleve ;
        for (int i=0 ;i < n ; i++){
            System.out.println("nom d'etudiant "+(i+1)+"= ");
            nom  = sc.next();
            //sc.next();
            System.out.println("prenom d'etudiant "+(i+1)+"= ");
            prenom  = sc.next();
            System.out.println("le numero du groupe d'etudiant "+(i+1)+"= ");
            AnneeClasse = sc.next();
            
            eleve = new Eleve(nom,prenom,encrypt(passwordGenera()),AnneeClasse);
            dataAccounts.ListeDeComptes.add(eleve);
                
           
        }
        
        

        
        
        /****************************************************/
        Comparator <? super Eleve> c = new Comparator<Eleve>()
                {
                    public int compare(Eleve o1, Eleve o2)
                    {
                        return(o1.compare(o1.getNom(), o2.getNom()));
                    }
                };
                dataAccounts.ListeDeComptes.sort(c);
        /****************************************************/
        
        String passwordAdmin =  "admin";
        dataAccounts.setPassWordAdmin(encrypt(passwordAdmin));
        
        /*************************************************************************************************/
        
        try {
            FileOutputStream fos = new FileOutputStream("datafinal.db");           
            ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(dataAccounts);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
         
    }
            
    public static void ecrireDonnees(ArrayList<Eleve> ListeComptes,String PasseAdmin){
        
        
        DataAccounts dataAccounts = new DataAccounts();
        
         /****************************************************/
        Comparator <? super Eleve> c = new Comparator<Eleve>()
                {
                    public int compare(Eleve o1, Eleve o2)
                    {
                        return(o1.compare(o1.getNom(), o2.getNom()));
                    }
                };
               ListeComptes.sort(c);
        /****************************************************/
        
        dataAccounts.ListeDeComptes=ListeComptes;
        dataAccounts.setPassWordAdmin(PasseAdmin);
        
        /*************************************************************************************************/
        
        try {
            FileOutputStream fos = new FileOutputStream("datafinal.db");           
            ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(dataAccounts);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
         
    }  
    
    
    public static DataAccounts lireDonnees() throws ClassNotFoundException{ 

       DataAccounts dataAccounts = null;
       try {
                FileInputStream fis = new FileInputStream("datafinal.db");
                ObjectInputStream ois = new ObjectInputStream(fis) ;
                dataAccounts = (DataAccounts)ois.readObject();
                afficherDataAcounts(dataAccounts);
                ois.close();
           } catch (FileNotFoundException e) {
                   e.printStackTrace();
           } catch (IOException e) {
                   e.printStackTrace();
           }

        return dataAccounts;    
    }
         
    public static String encrypt(String password){
        String crypte="";
        for (int i=0; i < password.length(); i++){
            int c = password.charAt(i)^48;
            crypte = crypte+(char) c;
        }
        return crypte;
    }
     
    public static  String decrypt(String password){
        String aCrypter="";
        for (int i=0 ; i <password.length(); i++){
            int c = password.charAt(i)^48;
            aCrypter = aCrypter + (char) c;
        }
        return aCrypter;
    }
    public static String passwordGenera(){
		  
	      String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_-@#";
	      Random rand = new Random();
	      String motDePasse ="";
	      for (int i=0; i<5; i++)
	        {
	                motDePasse = motDePasse +alphabet.charAt(rand.nextInt(alphabet.length()));
	        }
	       return motDePasse;
		  
	  }
    
    public static void afficherDataAcounts(DataAccounts dataAccounts){
       
        for (int i=0 ;i < dataAccounts.ListeDeComptes.size(); i++ ){
            System.out.println("Nom eleve"+(i+1)+"= "+ dataAccounts.ListeDeComptes.get(i).getNom());
            System.out.println("Prenom eleve"+(i+1)+"= "+ dataAccounts.ListeDeComptes.get(i).getPrenom());
            System.out.println("Mot de passe eleve"+(i+1)+"= "+decrypt( dataAccounts.ListeDeComptes.get(i).getMotDePasse()));           
        }
    }
}
    
    

