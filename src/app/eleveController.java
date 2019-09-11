package app;

import static app.App.ListeDeComptes;
import static app.App.ele;
import static app.App.student;
import static app.App.teacher;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import static comptes.Donnees.decrypt;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.DepthTest;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class eleveController implements Initializable{// Controleur de l'interface de connexion de l'éleve
   
    Stage prevStage;
    private StackPane modalDimmer;

    public void setPrevStage(Stage stage){
         this.prevStage = stage;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) { 
    
    
    }
    
    @FXML
    Label infoFalse;
            
    @FXML
    JFXButton connecter;
    
    @FXML
    Menu comptes;
    
    @FXML
    JFXPasswordField pass;
    
    @FXML
    JFXTextField user;
    
    @FXML
    JFXButton arabe;
    
    public void arabe()
    {
        user.setPromptText("اسم المستخدم");
        pass.setPromptText("كلمة السر");
        connecter.setText("دخول");
    }
    
    public void connect() throws IOException, ClassNotFoundException
    {
        String user=this.user.getText();
        String pass=this.pass.getText();
        
        int i=0;
        boolean trouve=false;
        
        while ((i <ListeDeComptes.size())&& trouve==false){
          if ((user.equals(ListeDeComptes.get(i).getNom()+"."+ListeDeComptes.get(i).getPrenom()))&& (pass.equals(decrypt(ListeDeComptes.get(i).getMotDePasse())))){
            trouve=true;
            Stage stage = new Stage();
   
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Inter.fxml"));
            Parent myPane = (Parent)myLoader.load();
            
       
            InterController controller = (InterController) myLoader.getController();
           controller.setPrevStage(stage);
           controller.nom.setText(ListeDeComptes.get(i).getNom());
           controller.prenom.setText(ListeDeComptes.get(i).getPrenom());
     //       controller.Comptes.setDisable(true);
//            controller.userLabel.setText(ListeDeComptes.get(i).getNom()+" "+ListeDeComptes.get(i).getPrenom());
            
            
            myPane.setDepthTest(DepthTest.DISABLE);
            myPane.getStyleClass().add("application");
            teacher = false;
            student = true;
            ele = ListeDeComptes.get(i);
//            modalDimmer.setVisible(false);
             
//            layerPane.getChildren().addAll(myPane,modalDimmer);
            
            
            Scene scene = new Scene(myPane);
            scene.getStylesheets().add(App.class.getResource("css.css").toExternalForm());
            scene.getStylesheets().add(App.class.getResource("caspian.css").toExternalForm());
            stage.setScene(scene);

            prevStage.close();
            
            //hideModalMessage();
            stage.setFullScreen(true);
            //stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
            
            
            
        }  
       i++;
    }
       if(trouve==false)
               {
//                System.out.println("Nom d'utilisateur ou mot de passe incorrect");
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
    
    public void showModalMessage(Node message) {
        modalDimmer.getChildren().add(message);
        modalDimmer.setOpacity(0);
        modalDimmer.setVisible(true);
        modalDimmer.setCache(true);
        TimelineBuilder.create().keyFrames(
            new KeyFrame(Duration.seconds(10), (ActionEvent t) -> {
                modalDimmer.setCache(false);
        },
                new KeyValue(modalDimmer.opacityProperty(),1, Interpolator.EASE_BOTH)
        )).build().play();
    }
    
    public void hideModalMessage() {
        modalDimmer.setCache(true);
        TimelineBuilder.create().keyFrames(
            new KeyFrame(Duration.seconds(5), (ActionEvent t) -> {
                modalDimmer.setCache(false);
                modalDimmer.setVisible(false);
                modalDimmer.getChildren().clear();
        },
                new KeyValue(modalDimmer.opacityProperty(),0, Interpolator.EASE_BOTH)
        )).build().play();
    }
    
}
