/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.DepthTest;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author saiyen
 */
public class loginController implements Initializable { /// Controleur de connexion à l'application
    
    
    
    @FXML
    JFXButton eleve;
    @FXML
    JFXButton enseignant;
    
    @FXML
     JFXButton exit; 
    
    Tooltip eleveT;
    Tooltip ensegT;
    Tooltip exitT;
   /* @FXML
    ImageView eleveLabel;
    @FXML
    ImageView ensegLabel;*/
    
    Stage prevStage;
   
    public void setPrevStage(Stage stage){
         this.prevStage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eleveT = new  Tooltip();
        ensegT = new  Tooltip();
        exitT = new  Tooltip();
        
        eleveT.setText("eleve");
        ensegT.setText("enseignant");
        exitT.setText("Sortir");
        
        eleve.setTooltip(eleveT);
        enseignant.setTooltip(ensegT);
        exit.setTooltip(exitT);
    }

    
    public void eleve() throws IOException {
       Stage stage = new Stage();
//       Parent myPane = null;
//       myPane = FXMLLoader.load(getClass().getResource("eleve.fxml"));
       
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("eleve.fxml"));
        Parent myPane = (Parent)myLoader.load();

       eleveController controller = (eleveController) myLoader.getController();
       controller.setPrevStage(stage);
       
       Scene scene = new Scene(myPane);
       stage.setScene(scene);

       prevStage.close();
       stage.initStyle(StageStyle.TRANSPARENT);
       stage.show();
       
       
    }
    public void enseignant() throws IOException {
       Stage stage = new Stage();
//     
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("enseignant.fxml"));
        Parent myPane = (Parent)myLoader.load();
       
        enseignantController controller = (enseignantController) myLoader.getController();
        controller.setPrevStage(stage);
        
        
       
       Scene escene = new Scene(myPane);
       stage.setScene(escene);
       prevStage.close();
       stage.initStyle(StageStyle.TRANSPARENT);
       stage.show();
      
       
    }
    
    public void exit()
    {
        System.exit(1);
    }

}
