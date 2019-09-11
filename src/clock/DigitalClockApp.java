package clock;
 
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
 
/**
 * A digital clock application that demonstrates JavaFX animation, images, and
 * effects.
 */
public class DigitalClockApp extends Application {
 
    private Clock clock;
 
    public Parent createContent() {
        Group root = new Group();
        // background image
        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("DigitalClock-background.png")));
        background.setFitHeight(100);
        background.setFitWidth(120);        
        // digital clock
        clock = new Clock(Color.ORANGERED, Color.rgb(50, 50, 50));
    //  clock.setLayoutX(45);
    //    clock.setLayoutY(186);
    //  clock.getTransforms().add(new Scale(0.83f, 0.83f, 0, 0));
clock.setScaleX(0.25);clock.setScaleY(0.25);
clock.setTranslateX(-180);
// add background and clock to sample
        root.getChildren().addAll(background, clock);
        return root;
    }
 
    public void play() {
        clock.play();
    }
 
    @Override public void stop() {
        clock.stop();
    }
 
    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
        play();
    }
 
    /**
     * Java main for when running without JavaFX launcher
     */
    public static void main(String[] args) {
        launch(args);
    }
}