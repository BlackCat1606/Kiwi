package Gestion_Comptes;

import static app.App.ListeDeComptes;
import static app.App.motDePasseAdmin;
import com.jfoenix.controls.JFXTextField;
import static comptes.Donnees.ecrireDonnees;
import static comptes.Donnees.encrypt;
import static comptes.Donnees.passwordGenera;
import comptes.Eleve;
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
public class NewAccount extends VBox{ ////////////////////////Afficher une interface pour ajouter un compte
        
    private StackPane ModalDimmer;
    private JFXTextField nom;
    private JFXTextField prenom;
    private Spinner classe;
    private Spinner Annee;
    private Label faux;
    
    public void setModalDimmer(StackPane ModalDimmer) {
        this.ModalDimmer = ModalDimmer;
    }
    
    public NewAccount(){

        ImageView add = new ImageView(new Image("Icons/Add User Male-50.png"));
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
        Label title = new Label("Ajouter un nouveau compte");
        title.setId("title");
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
        Label nomLabel = new Label("Nom     :");
        hbox_nom.getChildren().addAll(nomLabel,nom,add);
        
        HBox hbox_prenom = new HBox(20);
        prenom = new JFXTextField();prenom.setTranslateY(-10);
        Label prenomLabel = new Label("Prenom :");
        hbox_prenom.getChildren().addAll(prenomLabel,prenom);
        
        HBox hbox_groupe = new HBox(20);hbox_groupe.setTranslateY(10);
        classe = new Spinner();
        SpinnerValueFactory svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,20);
        classe.setValueFactory(svf);
        classe.setPrefWidth(91);
        classe.setPrefHeight(25);
       
        
        Annee = new Spinner();
        SpinnerValueFactory svf1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,4);
        Annee.setValueFactory(svf1);
        Annee.setPrefWidth(91);
        Annee.setPrefHeight(25);
        Label groupeLabel = new Label("Classe :");
        Label anneeLabel = new Label("Annee   :");
        hbox_groupe.getChildren().addAll(anneeLabel,Annee,groupeLabel,classe);
        nom.setMaxSize(127,31);
        prenom.setMaxSize(127,31);
        
        vbox.getChildren().addAll(hbox_nom,hbox_prenom,hbox_groupe);
        vbox.setTranslateY(20);
        bottomBar.setTranslateY(10);
        
        getChildren().addAll(vbox,bottomBar);
        
        ChangeListener<String> textListener = new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                    okBtn.setDisable(
                            nom.getText() == null || nom.getText().isEmpty()
                            || prenom.getText() == null || prenom.getText().isEmpty());
                }
            };
        nom.textProperty().addListener(textListener);
        prenom.textProperty().addListener(textListener);
        
    }
    
    
    
    
    
    
    public boolean create() /// Ajouter un compte
    {
       
        String name,prename; //** Nom et prenom //
        int classee,annee;  //** Annee et classe //
        
        
        name=nom.getText();
        prename=prenom.getText();
        
        classee = (Integer)classe.getValue();
        annee = (Integer)Annee.getValue();
        
        String AnneeClasse =annee+"AM"+classee;
        int i=0;
        boolean trouve=false;
         while ((i <ListeDeComptes.size())&& trouve==false){
          if (name.equals(ListeDeComptes.get(i).getNom())&& prename.equals(ListeDeComptes.get(i).getPrenom())){
            trouve=true;}
          i++;
          }
         if (trouve==false){
             Eleve eleve = new Eleve(name,prename,encrypt(passwordGenera()),AnneeClasse);
             ListeDeComptes.add(eleve);
             ecrireDonnees(ListeDeComptes,motDePasseAdmin); 
             
             faux.setText("Nouveau compte ajouté");
             faux.setVisible(true); 
             return true;
         }
         else{
           faux.setText("Le compte existe dèja !");
           faux.setVisible(true); 
           return false;
         }
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
