
package app;
import static app.InterfaceController.chemin;
import comptes.DataAccounts;
import static comptes.Donnees.ecrireDonnees;
import static comptes.Donnees.lireDonnees;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application { /// La classe main
    public static String motDePasseAdmin =null;
    public static ArrayList <comptes.Eleve> ListeDeComptes ;
    public static boolean student=false;
    public static boolean teacher=false;
    public static comptes.Eleve ele=null;
    public static String motDePasseStandard ="admin";
  
    @Override
    public void start(Stage stage) throws Exception {
        chemin = Paths.get(System.getProperty("user.home") + "\\Documents\\Seances");
        try /// Verifier si le dossier séance existe déja ou pas et le créer dans ce cas
        {
        File f = new File(chemin.toString());
        if (!(f.exists() && f.isDirectory())) 
        {
             
        boolean    success = ( new File(chemin.toString())).mkdir();
        }
        }
       catch (Exception e)
        {//Catch exception if any
       System.err.println("Error: " + e.getMessage());
        }
        
     
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent myPane = (Parent)myLoader.load();
        loginController controller = (loginController) myLoader.getController();
        controller.setPrevStage(stage);
        
        Scene scene = new Scene(myPane);
  
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
     
    }
    
    public static void main(String[] args) throws ClassNotFoundException {
        /// Charger la structure des comptes
        DataAccounts dataAccounts = new DataAccounts();
        //ecrireDonnees();
        dataAccounts = lireDonnees();
        motDePasseAdmin = dataAccounts.getPassWordAdmin();
        ListeDeComptes = dataAccounts.ListeDeComptes; 
        launch(args);
       
        dataAccounts.setPassWordAdmin(motDePasseAdmin);
        dataAccounts.ListeDeComptes = ListeDeComptes;
        
        ecrireDonnees(ListeDeComptes,motDePasseAdmin);
      
    }   
   
    
}

