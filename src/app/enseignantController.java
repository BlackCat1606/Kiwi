
package app;

import static app.App.ListeDeComptes;
import static app.App.motDePasseAdmin;
import static app.App.student;
import static app.App.teacher;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import comptes.DataAccounts;
import static comptes.Donnees.decrypt;
import static comptes.Donnees.lireDonnees;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.DepthTest;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author saiyen
 */
public class enseignantController implements Initializable{ // Controleur de l'interface de connexion de l'enseignat
    
   Stage prevStage;

    public void setPrevStage(Stage stage){
         this.prevStage = stage;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }
    
    @FXML
    Label infoFalse;
    
    @FXML
    JFXButton connect;
    
    @FXML
    JFXPasswordField pass;
    
    
    
     public void con() throws IOException, ClassNotFoundException
    {
        
      
        String pass=this.pass.getText();
        
        if (pass.equals(decrypt(motDePasseAdmin))){
            
           
            Stage stage = new Stage();
     

            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Inter.fxml"));
            Parent myPane = (Parent)myLoader.load();
            InterController controller = (InterController) myLoader.getController();
            controller.setPrevStage(stage);
            controller.nom.setText("Administrateur");
            controller.prenom.setVisible(false);
            
            myPane.setDepthTest(DepthTest.DISABLE);
             myPane.getStyleClass().add("application");
             student = false;
             teacher = true;
            
            Scene scene = new Scene(myPane);
            scene.getStylesheets().add(App.class.getResource("css.css").toExternalForm());
            scene.getStylesheets().add(App.class.getResource("caspian.css").toExternalForm());
            stage.setScene(scene);
            stage.setFullScreen(true);
         //   stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
            prevStage.close();  
        }
        else{
            infoFalse.setVisible(true);
        }
            
       
       
        
    }

    
    public void back() throws IOException
    {
        
       Stage stage = new Stage();
     
       
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent myPane = (Parent)myLoader.load();
       
            
            loginController controller = (loginController) myLoader.getController();
            controller.setPrevStage(stage);
            
            Scene scene = new Scene(myPane);
            stage.setScene(scene);
    
            prevStage.close();
            
            
            stage.show(); 
            
    }

}
