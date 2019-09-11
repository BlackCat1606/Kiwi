/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestion_Comptes;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author saiyen
 */
public class Autenticator { /// Valider le nom et le mot de passe d'utilisateur

    private static final Map<String, String> USERS = new HashMap<String, String>();

    static {
        USERS.put("seifou", "seifou");
    }

    public static boolean Validate(String user, String password) {
        String ValidateUserPassword = USERS.get(user);
        return ValidateUserPassword != null && ValidateUserPassword.equals(password);
    }

}
