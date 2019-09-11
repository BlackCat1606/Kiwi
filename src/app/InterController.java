package app;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author saiyen
 */
public class InterController implements Initializable { ////Controleur de l'interface intermédiaire 

    Stage prevStage;

    public void setPrevStage(Stage stage) {
        this.prevStage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    JFXButton principale;

    @FXML
    JFXButton exemples;

    @FXML
    Label TextPrincipe;

    @FXML
    Label debuter;

    @FXML
    TextField nom;

    @FXML
    TextField prenom;

    @FXML
    Label exp;

    @FXML
    JFXButton exit;

    @FXML
    JFXButton aide;

    @FXML
    JFXButton home;

    @FXML
    public void dist() {
        exemples.setTranslateX(principale.getTranslateX() + 20);
        aide.setTranslateX(principale.getTranslateX() + 20);
    }

    public void AgrandirAide() {

        ScaleTransition f = new ScaleTransition(Duration.millis(10), aide);
        f.setFromX(1);
        f.setFromY(1);
        f.setToX(1.1);
        f.setToY(1.1);
        f.setAutoReverse(true);
        f.play();

    }

    public void ReduireAide() {

        ScaleTransition f = new ScaleTransition(Duration.millis(10), aide);
        f.setFromX(1.1);
        f.setFromY(1.1);
        f.setToX(1);
        f.setToY(1);
        f.setAutoReverse(true);
        f.play();

    }

    public void AgrandirExemple() {

        ScaleTransition f = new ScaleTransition(Duration.millis(10), exemples);
        f.setFromX(1);
        f.setFromY(1);
        f.setToX(1.1);
        f.setToY(1.1);
        f.setAutoReverse(true);
        f.play();

    }

    public void ReduireExemple() {

        ScaleTransition f = new ScaleTransition(Duration.millis(10), exemples);
        f.setFromX(1.1);
        f.setFromY(1.1);
        f.setToX(1);
        f.setToY(1);
        f.setAutoReverse(true);
        f.play();

    }

    public void AgrandirHome() {

        ScaleTransition f = new ScaleTransition(Duration.millis(10), home);
        f.setFromX(1);
        f.setFromY(1);
        f.setToX(1.1);
        f.setToY(1.1);
        f.setAutoReverse(true);
        f.play();

    }

    public void ReduireHome() {

        ScaleTransition f = new ScaleTransition(Duration.millis(10), home);
        f.setFromX(1.1);
        f.setFromY(1.1);
        f.setToX(1);
        f.setToY(1);
        f.setAutoReverse(true);
        f.play();

    }

    public void LaunchPlayer() throws MalformedURLException {

        Stage stage = new Stage();
        // create the scene
        stage.setTitle("vidéos tutorielles");
        Browser bros = new Browser();
        bros.setPrefSize(1200, 750);

        Scene scene = new Scene(bros, 1366, 690, Color.web("#666970"));

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(h
                -> {
            bros.webEngine.load(null);
        }
        );

    }

    public void LaunchExemples() throws MalformedURLException {

        Stage stage = new Stage();
        // create the scene
        stage.setTitle("Exemples d'animation");
        Browser1 bros = new Browser1();
        bros.setPrefSize(1200, 750);

        Scene scene = new Scene(bros, 1366, 690, Color.web("#666970"));

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(h
                -> {
            bros.webEngine.load(null);
        }
        );

    }

    public void exit() {
        System.exit(1);
    }

    public void goHome() throws IOException {
        Stage stage = new Stage();

        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("interface.fxml"));
        Parent myPane = (Parent) myLoader.load();

        InterfaceController controller = (InterfaceController) myLoader.getController();
        controller.setPrevStage(stage);

        if ((this.nom.getText()).compareTo("Administrateur") != 0) {
            controller.Comptes.setDisable(true);
        }

        controller.userLabel.setText(nom.getText() + "." + prenom.getText());
        Scene scene = new Scene(myPane);
        stage.setScene(scene);
        prevStage.close();
        stage.setFullScreen(true);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public void logout() throws IOException {
        Stage stage = new Stage();
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent myPane = (Parent) myLoader.load();

        loginController controller = (loginController) myLoader.getController();
        controller.setPrevStage(stage);

        Scene scene = new Scene(myPane);
        prevStage.close();

        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.show();
    }
}
