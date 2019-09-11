/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author BLACKCAT
 */

public class LecteurController implements Initializable {/// Controleur du lecteur d'animation
    
@FXML 
BorderPane bor = new BorderPane() ;
@FXML
ScrollPane scrollPane = new ScrollPane();
@FXML 
AnchorPane gr = new AnchorPane();


@FXML 
JFXButton pause;

@FXML 
JFXButton Replay;

@FXML 
JFXButton Resume;

@FXML 
JFXButton stop;

@FXML
AnchorPane lect;

@FXML
ToolBar tool;
@FXML
JFXButton btn = new JFXButton();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }
    
}
