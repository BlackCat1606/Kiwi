/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestion_Comptes;

import static app.App.ListeDeComptes;
import static app.InterfaceController.chemin;
import com.jfoenix.controls.JFXTextField;
import static comptes.Donnees.encrypt;
import static comptes.Donnees.passwordGenera;
import comptes.Eleve;
import java.io.File;
import java.nio.file.Paths;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TimelineBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @author L SMAIL
 */
public class Seances extends VBox{
        
    private StackPane ModalDimmer;
    private JFXTextField nom;
    private JFXTextField prenom;
    private Spinner classe;
    private Spinner Annee;
    private Label faux;
    
    public void setModalDimmer(StackPane ModalDimmer) {
        this.ModalDimmer = ModalDimmer;
    }
    
    public Seances(){ /// Affiche une petite interface pour ajouter des dossiers de séances
        
        Image i = new Image("Icons/1461656450_add_folder.png");
        ImageView add = new ImageView(i);
       
        add.setFitHeight(64);
        add.setFitWidth(64);
       
        add.setTranslateY(10);add.setTranslateX(90);
        add.setScaleX(1.5);add.setScaleY(1.5);
        faux = new Label();
        faux.setVisible(false);
        faux.setTranslateX(-110);faux.setTranslateY(10);
        faux.setId("creationFausse");
        
        
        setId("NewAccount");
        //setSpacing(10);
        setMaxSize(430, 270);
           // block mouse clicks
        setOnMouseClicked((MouseEvent t) -> {
            t.consume();
        });
        BorderPane explPane = new BorderPane();
        VBox.setMargin(explPane, new Insets(5, 5, 5, 5));
        
        // create title
        Label title = new Label("Ajouter une nouvelle seance");
        title.setId("Seance");
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(Pos.CENTER);
        getChildren().add(title);

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setId("cancelButton");
       
        cancelBtn.setOnAction((ActionEvent actionEvent) -> {
          hideModalMessage();
        });
        cancelBtn.setMinWidth(74);
        cancelBtn.setPrefWidth(74);
        HBox.setMargin(cancelBtn, new Insets(0, 8, 0, 0));
        Button okBtn = new Button("Créer");
        okBtn.setId("saveButton");
        okBtn.setDefaultButton(true);
        okBtn.setDisable(true);
        okBtn.setOnAction((ActionEvent actionEvent) -> {
            if(create())
            hideModalMessage();
            
        });
        
        HBox bottomBar = new HBox(0);
        bottomBar.setAlignment(Pos.BASELINE_RIGHT);cancelBtn.setTranslateY(20);okBtn.setTranslateY(20);
        bottomBar.getChildren().addAll(faux,cancelBtn, okBtn);
        VBox.setMargin(bottomBar, new Insets(20, 5, 5, 5));
        
        
        
        
        VBox vbox = new VBox(10);
        
        HBox hbox_nom = new HBox(20);
        nom = new JFXTextField();nom.setTranslateY(-10);
        Label nomLabel = new Label("Numéro      :");
        hbox_nom.getChildren().addAll(nomLabel,nom,add);
        
        
        nom.setMaxSize(127,31);
      
        
        vbox.getChildren().addAll(hbox_nom);
        vbox.setTranslateY(20);
        bottomBar.setTranslateY(10);
        
        getChildren().addAll(vbox,bottomBar);
        
        ChangeListener<String> textListener = new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                    okBtn.setDisable(
                            nom.getText() == null || nom.getText().isEmpty()
                      );
                }
            };
        nom.textProperty().addListener(textListener);
      
        
    }
    
    
    
    
    
    
    public boolean create() /// Ajouter du dossier séance i  0<=i dans le dossier Seances
    {
        String name; //** Nom et prenom //
        
          
        name=nom.getText();
        
        
        boolean success = true;
 chemin = Paths.get(System.getProperty("user.home") + "\\Documents\\Seances");

  try 
  {  
  String strDirectoy =chemin.toString()+"//séance".concat(name);
  // Create one directory
  success = ( new File(strDirectoy)).mkdir();
  if (success)
  {
  System.out.println("Directory: " 
   + strDirectoy + " created");
  }  
     else
  {
           faux.setText("Cette Séance existe dèja !");
           faux.setVisible(true); 
            success = false;
  }
  
  }
  catch (Exception e)
  {//Catch exception if any
  System.err.println("Error: " + e.getMessage());
  }
        
        
      
       
      return success;
           
         }
  
      public void hideModalMessage() {
        ModalDimmer.setCache(true);
        TimelineBuilder.create().keyFrames(
            new KeyFrame(Duration.seconds(0.6), (ActionEvent t) -> {
                ModalDimmer.setCache(false);
                ModalDimmer.setVisible(false);
                ModalDimmer.getChildren().clear();
        },
                new KeyValue(ModalDimmer.opacityProperty(),0, Interpolator.EASE_BOTH)
        )).build().play();
    }
}

