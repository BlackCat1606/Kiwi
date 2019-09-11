/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comptes;

import java.io.Serializable;
import java.util.ArrayList;

public class DataAccounts implements Serializable { /// Les comptes
    
    
    private String PassWordAdmin;
    public ArrayList <Eleve> ListeDeComptes = new ArrayList<> ();
    
    
    public String getPassWordAdmin() {
        return PassWordAdmin;
    }

    public void setPassWordAdmin(String PassWordAdmin) {
        this.PassWordAdmin = PassWordAdmin;
    }
     
}
