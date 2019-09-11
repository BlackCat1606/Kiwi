package app;

import AlerteBox.AlerteBox;
import TreeView.SimpleFileTreeItem;
import Polygone.Polygone;
import Trajectoire.Trajet;
import Transformations.Scaling;
import Transformations.Rotating;
import Transformations.Translating;
import Gestion_Comptes.NewAccount;
import Gestion_Comptes.DelAccount;
import Grille.Grille;
import Grille.ZoomController;
import Animation.Animation;

import Gestion_Comptes.Seances;
import Timeline.timeline;
import Groupe.Groupe;
import Structure.Forme;
import Structure.Animation_liste;
import Structure.File_Operations;
import Structure.Cle;
import Structure.Forme_G;
import static app.App.ListeDeComptes;
import static app.App.ele;
import static app.App.motDePasseStandard;
import static app.App.student;
import static app.App.teacher;
import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXTabPane;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import static comptes.Donnees.decrypt;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TimelineBuilder;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

public class InterfaceController implements Initializable { /// Controleur de l'interface principale 
/////////////////////// Chemin pour enregistrer les fichiers /////////////////////
    public static java.nio.file.Path chemin;
    ///////////////////////////////////////////////////////////////////
    
    /////////// Controlleur de zoom pour la grille /////////////////////
    public ZoomController zoomController;
    
    
    ///////////////////////////////////////////////////////////////
    
    
    ///////////////// Variables golabales pour la Sélection ////////////
    public static Polygone poly_select = new Polygone(0, 0);///////////// Polygone séléctionné 
    public static Polygone poly_select2 = new Polygone(0, 0);
    public static Node objet_select = new Node() { ///////// Objet séléctionné (soit polygone ou objet complexe ////////
        @Override
        protected NGNode impl_createPeer() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        protected boolean impl_computeContains(double localX, double localY) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    ///////////////// objet complexe séléctionné ////////////
    public static Groupe group_select = new Groupe();
    ///////////////////////////////////////////////////////
    ////////////////// Operations sur les fichiers///////////////////
    File_Operations f = new File_Operations();
    ////////////////////////////////////////////////////////////
    ///////////// La structure d'animation /////////////////////////
    public static Animation_liste anim = new Animation_liste();
    //////////////////////////////////////////////////////
    ///// tableau de polygones //
    public ArrayList<Polygone> pole = new ArrayList<>();
    ////////////////////////////////////////////////////////
    //////////////// Liste des objets complexes crées /////////////////
    public static ArrayList<Groupe> groupes = new ArrayList<>();
    //////////////////////////////
////////////////// Getters and Setters//////////////////////////////////
    public static Groupe getGroup_select() {
        return group_select;
    }

    public static void setGroup_select(Groupe group_select) {
        InterfaceController.group_select = group_select;
    }

    public static Animation_liste getAnim() {
        return anim;
    }

    public static void setAnim(Animation_liste anim) {
        InterfaceController.anim = anim;
    }

    public static ArrayList<Groupe> getGroupes() {
        return groupes;
    }

    public static void setGroupes(ArrayList<Groupe> groupes) {
        InterfaceController.groupes = groupes;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //// Mouvements///////
    Rotating rot = new Rotating();
    Translating trans = new Translating();
    Scaling sca = new Scaling();
    /////////////////////////////////////////////////////////////////////////////////////////////


   
    
    
    
    
    /////////////////// Text Fields///////////////////////
   @FXML
    Spinner X;
    @FXML
    Spinner Y;
    @FXML
    private TextField rayon; ///////////// Le rayon
    @FXML
    private TextField nb_cote;////////// Le nombre de cotés
    @FXML
    private Spinner rayon1;
    @FXML
    private Spinner nb_cote1;
    @FXML
    private Label coord;
    
    
    ////////////////////////////////////////////////////
    
    ////////////////// Variables////////////////////////////
    
    //// Selected polygones///
    ObservableList<Polygone> selectedRects;
    Timeline timeline = new Timeline();
    List<Polygone> allRects;
    ///////////////////////
    String id_preced_grp = "none";
    String id_grp = "none";
    int size = 0;
    boolean record = false;
    int frame = 0;
    int cpt = 0;
    static int n = 0;
    public int fois = 0;
    Polygone p;
     /////temps précedent////
    int temp_prece = 0;
    /////////////////////////
    double actualx = 0;
    int number = 0;
    double actualy = 0;
    Point2D pivot;

    //// Les id des polygones //// 
    String id = "none";
    String id_prec = "none";
    String id_poly = "none";
    String id_preced_poly = "none";
    int temp_prece_grp = 0;
    static int a = 0;
    public boolean ouvert = false;

    public String name;
    private double initX;

    private double initY;
    private Point2D draganchor;
    String name1 = "poly";

    /////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////
    ////////////// La Grille ///////////////
    public Grille notreGrille;
    ///////////////////////////////////////
    
    //////////////// Le TreeView//////////////////

    TreeView<java.nio.file.Path> treeView;
    @FXML
    private TreeView<File> filesTree;
    
    ///////////////////////////////////////
    
    @FXML
    ImageView translation;
    

   ///////////////////////// Layouts//////////////////////////////
    @FXML
    VBox vbox;
    @FXML
    VBox vbox1;
    @FXML
    StackPane pane;
    @FXML
    AnchorPane principale;
    @FXML
    AnchorPane pane1;
    @FXML
    private Pane horloge;
    @FXML
    HBox hbox;
    @FXML
    BorderPane bor;

    @FXML
    JFXTabPane tabPane;
    
    @FXML
    ScrollPane scrollPane;
    @FXML
    public BorderPane borderPane = new BorderPane();
    
////////////////////////////////// Labeles/////////////////////////////////////
    @FXML
    Label userLabel;
    @FXML
    Label nom_poly;
    
    
    //////////////////////////////////////////////////////////
    /////////////////// MenuBar/////////////////////////////
    @FXML
    Menu file;
    @FXML
    MenuItem close;
    @FXML
    public MenuBar menuBar;
    @FXML
    Menu Comptes;
    @FXML
    MenuItem Save;
    @FXML
    MenuItem Open;
      @FXML
    MenuItem SaveAs;
    ///////////////////////////////////////////////////////////

   ////////////// Les couleurs ///////////

    @FXML
    private JFXColorPicker colorpicker;/////// le coulor picker

   //////////////////////////////////////////// 

   /////////////////// Barre d'outils ///////////////////

    @FXML
    ToolBar tools;
    
    ////////////////////////////////////////////////

    
    
    //////////////////////// Tabulations  pour ouverture de fichier //////////////////
    @FXML
    JFXTabPane ouv = new JFXTabPane() ;
//////////////////////////////////////////////////////////////////////////////////
    /**
     * ******************
     */
    
   
/////////// Sliders ///////////////////////////
    @FXML
    Slider slider_rot;///////////// pour la rotation 

    /**
     * ******************
     */
    
    
    /////////// Fenétre précedente //////////////////////
    Stage prevStage = new Stage();

    public void setPrevStage(Stage stage) {
        this.prevStage = stage;
    }
    ////////////////////////////////////////////////////////

    
////////// Dialog///////////////////////
    @FXML
    JFXDialog dialogSave;
//////////////////////////////////////////////////////////
   
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        colorpicker.setValue(Color.BLACK);
        cling.setVisible(false);
        SpinnerValueFactory svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,8);
        SpinnerValueFactory svf1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(3,100);
        
        rayon1.setValueFactory(svf);
        slider_rot.setValue(0);
        nb_cote1.setValueFactory(svf1);
        SpinnerValueFactory svf2 = new SpinnerValueFactory.DoubleSpinnerValueFactory(-22.5,22.5,0.0,0.5);
        SpinnerValueFactory svf3 = new SpinnerValueFactory.DoubleSpinnerValueFactory(-12.5,12.5,0.0,0.5);
        X.setValueFactory(svf2);
        Y.setValueFactory(svf3);
        
       
        scrollPane.setMinSize(900, 520);
        scrollPane.setContent(new Group(Afficher_grille()));
        notreGrille.zoneDessin.setStyle("caspien.css");

        chemin = Paths.get(System.getProperty("user.home") + "\\Documents\\Seances");
    
        TreeItem<java.nio.file.Path> root = new SimpleFileTreeItem(
                Paths.get(System.getProperty("user.home") + "\\Documents\\Seances"));
        root.setExpanded(true);
        Btn_creer_poly.setDisable(true);
        
        
        //  root.setGraphic(new ImageView(new Image(ClassLoader.getSystemResourceAsStream("app/computer.png"))));

        treeView = new TreeView<>(root);
        treeView.setCellFactory(treeView -> new TreeCell<java.nio.file.Path>() {
            @Override
            public void updateItem(java.nio.file.Path path, boolean empty) {

                super.updateItem(path, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(path.getFileName().toString());
                     
                     if(path.toFile().isFile())
                {
                     setGraphic( new ImageView(new Image(ClassLoader.getSystemResourceAsStream("Icons/text-x-generic.png"))));
                }
                     else
                {
                    if(path.getFileSystem().isOpen())
                      {
                      setGraphic(new ImageView(new Image(ClassLoader.getSystemResourceAsStream("Icons/folder-open.png"))));
                      }
                    else
                    {
                         setGraphic(new ImageView(new Image(ClassLoader.getSystemResourceAsStream("Icons/folder.png"))));
                    }
                }
               
                    
                }
            }
        });
        treeView.setOnMouseClicked(value -> {

            if (value.getButton().equals(MouseButton.PRIMARY)) {
                if (value.getClickCount() == 2) 
                {     java.nio.file.Path miaw = new java.nio.file.Path() {
                    @Override
                    public FileSystem getFileSystem() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public boolean isAbsolute() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public Path getRoot() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public Path getFileName() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public Path getParent() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public int getNameCount() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public Path getName(int index) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public Path subpath(int beginIndex, int endIndex) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public boolean startsWith(Path other) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public boolean startsWith(String other) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public boolean endsWith(Path other) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public boolean endsWith(String other) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public Path normalize() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public Path resolve(Path other) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public Path resolve(String other) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public Path resolveSibling(Path other) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public Path resolveSibling(String other) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public Path relativize(Path other) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public URI toUri() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public Path toAbsolutePath() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public Path toRealPath(LinkOption... options) throws IOException {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public File toFile() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public WatchKey register(WatchService watcher, WatchEvent.Kind<?>[] events, WatchEvent.Modifier... modifiers) throws IOException {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public WatchKey register(WatchService watcher, WatchEvent.Kind<?>... events) throws IOException {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public Iterator<Path> iterator() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public int compareTo(Path other) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                };
                    if(treeView.getSelectionModel().getSelectedItem().getValue().toFile()!=null)
                {
                         miaw = treeView.getSelectionModel().getSelectedItem().getValue();
                   
                    
                    if (miaw.getFileName() != null) 
                    {
                        System.out.println(miaw);
                        if (miaw.toString().contains(".poly")||miaw.toString().contains(".POLY")) 
                        
                        {

        if (student && ele != null) 
            {
                                Animation_liste m = new Animation_liste();
                                comptes.Eleve eleve = ele;
                                boolean opened = false;
                                 m = f.Ouvrir_eleve_tree(eleve, m, miaw);
              if (m.Anim.isEmpty() == false) 
            {
                
           
                Iterator<Forme> fr = m.Anim.iterator();
                Iterator<Forme_G> gr = m.AnimG.iterator();
                FXMLLoader myLoader = new FXMLLoader(getClass().getResource("tabs.fxml"));
                                    try {
                                        Parent myPane = (Parent) myLoader.load();
                                        myPane.setTranslateX(20);
                                    } catch (IOException ex) {
                                        Logger.getLogger(InterfaceController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                InterfaceController controller = (InterfaceController) myLoader.getController();
                controller.userLabel.setText(ele.getNom()+".".concat(ele.getPrenom()));
               // controller.poly_select = poly_select;
                //controller.group_select = group_select;
                //controller.objet_select = objet_select;
                while (fr.hasNext()) {
                    Forme f = fr.next();
                    if (f.getGroup_id().equals("none")) {
                        Polygone poly = AjouterR(f.getId(), (int) f.getRayon(), f.getNbCot());  
                        poly.setFill(Paint.valueOf(f.getCouleur()));
                        if(!f.ll.isEmpty())
                        {
                         poly.setTranslateX(f.ll.getLast().getX());
                         poly.setTranslateY(f.ll.getLast().getY());
                         poly.setRotate(f.ll.getLast().getAngle());
                         poly.setScaleX(f.ll.getLast().getEchelle());
                         poly.setScaleY(f.ll.getLast().getEchelle());
                        }
                        controller.notreGrille.zoneDessin.getChildren().add(poly);
                        controller.poly_select = poly;
                        controller.objet_select = controller.poly_select;
                        drag_poly(controller.notreGrille,controller.poly_select, true);
                    }
                    controller.Frames_poly(controller.poly_select.getTimeline());
                }
  //ArrayList<Groupe> grou = new ArrayList<>();
                while (gr.hasNext()) 
                {

                    ArrayList<Node> groupage = new ArrayList<>();
                  
                    Forme_G r = gr.next();
                    System.out.println(r.getIdentif());
                    fr = m.Anim.iterator();
                    while (fr.hasNext()) {
                        Forme f = fr.next();
                        System.out.println("   azul  " + f.getGroup_id());
                        Polygone poly = AjouterR(f.getId(), (int) f.getRayon(), f.getNbCot());
                        if (r.getIdentif().equals(f.getGroup_id())) {
                            poly.setTranslateX(f.getLastXPosition());
                            poly.setTranslateY(f.getLastYPosition());
                            poly.setRotate(f.getLastangle());
                            poly.setScaleX(f.getLastScale());
                            poly.setScaleY(f.getLastScale());
                            poly.setFill(Paint.valueOf(f.getCouleur()));
                            groupage.add(poly);
                        }
                    }
                    Groupe gp = Groupe.regrouper(controller.notreGrille.zoneDessin, groupage);
                    gp.setId(r.getId());
                    gp.setIdentif(r.getIdentif());
 
                    if(!r.ll.isEmpty())
                    {
                        gp.setTranslateX(r.ll.getLast().getX());
                        gp.setTranslateY(r.ll.getLast().getY());
                        gp.setRotate(r.ll.getLast().getAngle());
                        gp.setScaleX(r.ll.getLast().getEchelle());
                        gp.setScaleY(r.ll.getLast().getEchelle());
                    }
                    
      
                    controller.selectionner_gp(gp, true);
                    controller.group_select = gp;
                    controller.objet_select = controller.group_select;
                    drag_groupe(controller.notreGrille,controller.group_select,true);
                }
                controller.Frames_poly(controller.poly_select.getTimeline());
                controller.notreGrille.adaptationGrille();
 
                controller.anim = m;
                 ouvert = true;
               
            addNewTab(ouv, "Ouverture", myLoader.getRoot(), true);
       
            ouv.getSelectionModel().selectFirst();
            
            }

                            } 
        else 
                             if (teacher)
            {

                                    Animation_liste m = new Animation_liste();
                                    comptes.Eleve eleve = ele;
                                    boolean opened = false;
                                     m  = f.Ouvrir_prof_tree(m, miaw, motDePasseStandard);
          if (m.Anim.isEmpty() == false) 
            {
                
                ouvert = true;
                Iterator<Forme> fr = m.Anim.iterator();
                Iterator<Forme_G> gr = m.AnimG.iterator();
                FXMLLoader myLoader = new FXMLLoader(getClass().getResource("tabs.fxml"));
                                        try {
                                            Parent myPane = (Parent) myLoader.load();
                                            myPane.setTranslateX(20);
                                        } catch (IOException ex) {
                                            Logger.getLogger(InterfaceController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                InterfaceController controller = (InterfaceController) myLoader.getController();
               // controller.poly_select = poly_select;
                //controller.group_select = group_select;
                //controller.objet_select = objet_select;
                controller.userLabel.setText("Administrateur");
                while (fr.hasNext()) {
                    Forme f = fr.next();
                    if (f.getGroup_id().equals("none")) {
                        Polygone poly = AjouterR(f.getId(), (int) f.getRayon(), f.getNbCot());  
                        poly.setFill(Paint.valueOf(f.getCouleur()));
                        if(!f.ll.isEmpty())
                        {
                         poly.setTranslateX(f.ll.getLast().getX());
                         poly.setTranslateY(f.ll.getLast().getY());
                         poly.setRotate(f.ll.getLast().getAngle());
                         poly.setScaleX(f.ll.getLast().getEchelle());
                         poly.setScaleY(f.ll.getLast().getEchelle());
                        }
                        controller.notreGrille.zoneDessin.getChildren().add(poly);
                        controller.poly_select = poly;
                        controller.objet_select = controller.poly_select;
                        drag_poly(controller.notreGrille,controller.poly_select, true);
                    }
                    controller.Frames_poly(controller.poly_select.getTimeline());
                }
  //ArrayList<Groupe> grou = new ArrayList<>();
                while (gr.hasNext()) 
                {

                    ArrayList<Node> groupage = new ArrayList<>();
                  
                    Forme_G r = gr.next();
                    System.out.println(r.getIdentif());
                    fr = m.Anim.iterator();
                    while (fr.hasNext()) {
                        Forme f = fr.next();
                        System.out.println("   azul  " + f.getGroup_id());
                        Polygone poly = AjouterR(f.getId(), (int) f.getRayon(), f.getNbCot());
                        if (r.getIdentif().equals(f.getGroup_id())) {
                            poly.setTranslateX(f.getLastXPosition());
                            poly.setTranslateY(f.getLastYPosition());
                            poly.setRotate(f.getLastangle());
                            poly.setScaleX(f.getLastScale());
                            poly.setScaleY(f.getLastScale());
                            poly.setFill(Paint.valueOf(f.getCouleur()));
                            groupage.add(poly);
                        }
                    }
                    Groupe gp = Groupe.regrouper(controller.notreGrille.zoneDessin, groupage);
                    gp.setId(r.getId());
                    gp.setIdentif(r.getIdentif());
 
                    if(!r.ll.isEmpty())
                    {
                        gp.setTranslateX(r.ll.getLast().getX());
                        gp.setTranslateY(r.ll.getLast().getY());
                        gp.setRotate(r.ll.getLast().getAngle());
                        gp.setScaleX(r.ll.getLast().getEchelle());
                        gp.setScaleY(r.ll.getLast().getEchelle());
                    }
                    
      
                    controller.selectionner_gp(gp, true);
                    controller.group_select = gp;
                    controller.objet_select = controller.group_select;
                    drag_groupe(controller.notreGrille,controller.group_select,true);
                }
                controller.Frames_poly(controller.poly_select.getTimeline());
                controller.notreGrille.adaptationGrille();
 
                controller.anim = m;
               
            addNewTab(ouv, "Ouverture", myLoader.getRoot(), true);
            
            ouv.getSelectionModel().selectFirst();
            
            }

                                  
            }
                            System.out.println("Double clicked");
                        }
                        }
                    
                
            }
                
        }
                
        
        
                }});
        

        vbox1 = new VBox();
        vbox1.setPadding(new Insets(20));
        vbox1.getChildren().addAll(treeView);
        vbox1.setSpacing(10);
        vbox.getChildren().add(vbox1);
        //// 
       ChangeListener<String> textListener = new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                    Btn_creer_poly.setDisable(
                            rayon.getText() == null || rayon.getText().isEmpty()|| Double.parseDouble(rayon.getText())>7 || Double.parseDouble(rayon.getText())<=0
                            || nb_cote.getText() == null || nb_cote.getText().isEmpty()|| Double.parseDouble(nb_cote.getText())<3 || Double.parseDouble(nb_cote.getText())>500);
                }
            };
        rayon.textProperty().addListener(textListener);
        nb_cote.textProperty().addListener(textListener);

        

         
    }
         
    
   
/////////////////////////////////////////////////////////////////////////////////
    /////////////////// Les bouttons ////////////////
    @FXML
    JFXButton Sauv;////// Le boutton Sauvegarder
    @FXML
    JFXButton path;
    @FXML
    ToggleButton RECORD;/////// Le boutton Enreg
    @FXML
    Button logout;/////// Le boutton Déconnecter
    @FXML
    JFXButton b;
    @FXML
    JFXButton Path_grp;
     @FXML
    JFXButton regrouper;

    @FXML
    JFXButton degrouper;

    @FXML
    JFXButton play;
    @FXML
    JFXButton play_grp;
    @FXML
    private Button Btn_creer_poly;
    @FXML
    private JFXButton supp;
    @FXML
    private Button Btn_creer_poly1;
    @FXML
    JFXButton Scale1;
    @FXML
    JFXButton Scale2;
    @FXML 
    JFXButton DeleteAll;
    @FXML
    Slider slider;

    @FXML
    JFXButton Transition;

    @FXML
    JFXButton Rotate;

    @FXML
    JFXButton Scale;
    @FXML
    Slider slider_grp;

    @FXML
    Spinner playing;

    @FXML
    JFXButton Transition_grp;

    @FXML
    JFXButton Rotate_grp;

    @FXML
    JFXButton Scale_grp;
    @FXML
    TitledPane tran;
    @FXML
    TitledPane rota;
    @FXML
    TitledPane eche;
    
    
////////////////////////////////////////////////////////////


    @FXML
    ////////// Fonction de création du polygone selon les paramétres voulus ///////
    public void creerPolygone(ActionEvent event) {

        Polygone poly = AjouterR(name1 + a, (int) notreGrille.xToPixel(Integer.parseInt(rayon.getText())), (Integer.parseInt(nb_cote.getText())));
        a++;
        notreGrille.zoneDessin.getChildren().add(poly);
        poly_select = poly;
        // objet_select = poly_select;
        aaa(poly.get_nbr_cotes(), poly_select.getFill());
        //// Travail sur la structure ///
        pole.add(poly);
        size++;
        n++;

        ///////////////////
        drag_poly(notreGrille, poly, true);

    }

    /**
     * **********************
     */
    @FXML
    public void Clignoter(ActionEvent event) {

    }

    ///******************************************///
    @FXML
    public void creerPolygone1(ActionEvent event) {
        Polygone poly = AjouterR(poly_select.getId(), (int) notreGrille.xToPixel((double) rayon1.getValue()),(int)nb_cote1.getValue());
        a++;
        poly.setTimeline(poly_select.getTimeline());
        String id = poly_select.getId();
        Iterator<Forme> it = anim.Anim.iterator();
        while(it.hasNext())
        { Forme f = it.next();
           if(id.equals(f.getId()))
           {
               f.setNbCot(poly.get_nbr_cotes());
               f.setRayon(poly.get_rayon());              
           }
        }
        notreGrille.zoneDessin.getChildren().add(poly);
        Double x = poly_select.getTranslateX();
        Double y = poly_select.getTranslateY();
        poly.setTranslateX(x);
        poly.setTranslateY(y);
        poly.setRotate(poly_select.getRotate());
        poly.setScaleX(poly_select.getScaleX());
        poly.setScaleY(poly_select.getScaleY());
        
      //  delete();
        notreGrille.zoneDessin.getChildren().remove(poly_select);
        poly_select = poly;
        aaa(poly.get_nbr_cotes(), poly_select.getFill());
        drag_poly(notreGrille, poly, true);

    }

    /**
     * ***********************
     */
   ///////////////////// ///// La fonction de supression///////////////////////////

    @FXML

    public void delete() {
        if (objet_select instanceof Polygone)////////////// Pour Supprimer un polygone 
        {
            if (poly_select != null) {
                poly_select.setVisible(false);
                notreGrille.zoneDessin.getChildren().remove(poly_select);
                pane.getChildren().clear();
                ///// Recherche et supression dans la structure
                String id = poly_select.getId();
                Iterator<Polygone> its = pole.iterator();
                while (its.hasNext()) {
                    Polygone j = its.next();
                    if (j.getId().equals(id)) {
                        its.remove();
                    }
                }
                if (anim.Anim.size() != 0) {
                    Comparator<? super Forme> c = new Comparator<Forme>() {
                        @Override
                        public int compare(Forme o1, Forme o2) {
                            return (o1.compare(o1.getId(), o2.getId()));
                        }
                    };
                    Iterator<Forme> it = anim.Anim.iterator();
                    while (it.hasNext()) {
                        Forme m = it.next();
                        if (m.getId().equals(id)) {
                            it.remove();
                        }
                    }
                    ///// Le tri
                    anim.Anim.sort(c);

                    for (Forme f : anim.Anim) {
                        System.out.println("The sorted id " + f.getId());
                    }
                    System.out.println("U have done a delete operation");

                    for (Forme f : anim.Anim) {
                        System.out.println("The sorted id after " + f.getId());
                    }
                }
            }
        } else {//// Pour supprimer un objet complexe 

            group_select.setVisible(false);
            notreGrille.zoneDessin.getChildren().remove(group_select);
            pane.getChildren().clear();

            ////// Recherche et supression
            String id = group_select.getIdentif();

            for (Node p : group_select.getChildren())
            {

                Polygone d = (Polygone) p;
                String ide = d.getId();

                //// Supprimer les polygones de l'objet complexe de la structure 
                Iterator<Polygone> its = pole.iterator();
                while (its.hasNext())
                {
                    Polygone k = its.next();
                    if (ide.equals(k.getId())) {
                        its.remove();
                    }

                }
                if (anim.Anim.size() != 0) 
                {
                    Comparator<? super Forme> c = new Comparator<Forme>() {
                        @Override
                        public int compare(Forme o1, Forme o2) {
                            return (o1.compare(o1.getId(), o2.getId()));
                        }
                    };
                    int il = 0;
                    anim.Anim.sort(c);
                    Iterator<Forme> it = anim.Anim.iterator();
                    while (it.hasNext()) {
                        Forme m = it.next();
                        if (m.getId().equals(ide)) {
                            it.remove();
                        }
                    }

                }

                /// Recherche et Supression de l'objet complexe de la structure groupes 
                //// Tri
                if (groupes.size() != 0) {
                    Iterator<Groupe> it = groupes.iterator();
                    while (it.hasNext()) {
                        Groupe g = it.next();
                        if (g.getIdentif().equals(ide)) {
                            it.remove();
                        }
                    }
                    Comparator<? super Groupe> c = new Comparator<Groupe>() {
                        @Override
                        public int compare(Groupe o1, Groupe o2) {
                            return (o1.compare(o1.getIdentif(), o2.getIdentif()));
                        }
                    };
                    groupes.sort(c);

                    for (Groupe f : groupes) 
                    {
                        System.out.println("The sorted id " + f.getIdentif());
                    }
                    
                }
                if(anim.AnimG.size()!=0)
                {
                    Iterator<Forme_G> it = anim.AnimG.iterator();
                    while (it.hasNext()) {
                        Forme_G g = it.next();
                        if (g.getIdentif().equals(ide)) 
                        {
                            it.remove();
                        }
                    }
                    Comparator<? super Forme_G> c = new Comparator<Forme_G>() {
                        @Override
                        public int compare(Forme_G o1, Forme_G o2) {
                            return (o1.compare(o1.getIdentif(), o2.getIdentif()));
                        }
                    };
                    
                    
                    
                }
            }
        }
    }
    
   ////////////////////////////// //-----Fonction d'echelle////////////////////
    @FXML

    public void scale() {
      
            if (poly_select != null) {
                sca.Scaling(poly_select, slider.getValue());
       
    }
    }
    //////////////////////////////////////////////////////////////////////////////
    
    //----Fonction de rotation /////////////////////////////////////////////////////

    @FXML
    public void rotate() 
    {
       
      
            if (poly_select != null) {
                poly_select.setAngle((int) slider_rot.getValue());
                rot.Rotating(poly_select, (int) slider_rot.getValue());
            }

    }
//////////////////////// Translation //////////////////////////////////////
    @FXML

    public void Translation(ActionEvent t) {
        if (objet_select instanceof Polygone) {
         
            translate();
        } else if (objet_select instanceof Groupe) {
           
            translate_grp();
        }
    }
    ///////////////////////////////////////////////////////////
/////////////////// Rotation ///////////////////////////////////////
    @FXML
    public void Rotation(MouseEvent e) 
    {
        if (objet_select instanceof Polygone) {
            
            rotate();
        } else if (objet_select instanceof Groupe) {
       
            rotate_grp();
        }

    }

    @FXML
    //////////////////// Echelle////////////////////////////////////////
    public void Echelle(MouseEvent e) 
    {
        if (objet_select instanceof Polygone) {
     
            scale();
        } else if (objet_select instanceof Groupe) {
            
            scale_grp();
        }
    }
//////////////////////////////////////////////////////////////////////
    public void cacher_rot(MouseEvent e) {
        slider_rot.setVisible(false);
    }
    //----Fonction de translate //

    @FXML
    ////////////// Translation poly //////////////////////////////
    public void translate() {
        double x,y;
         x = notreGrille.xToPixel((double) X.getValue());
         y = notreGrille.yToPixel((double) Y.getValue());
        if (poly_select != null) {
            trans.Translating(poly_select, x, y);
        }
    }

    @FXML
    ////////////////////////// Echelle Groupe //////////////////////////////////////

    public void scale_grp() {
       
            if (group_select != null) {
                sca.Scaling_grp(group_select, slider.getValue());
            }
       

    }
    //----Fonction de rotation  grp ///////////////////////////////////////////////////

    @FXML
    public void rotate_grp() {
        //slider_rot.setVisible(true);
       // slider_rot.setVisible(true);
     
            if (group_select != null) 
            {
                group_select.setAngle((int) slider_rot.getValue());
                rot.Rotating_grp(group_select, (int) slider_rot.getValue());
            }

    }
    //----Fonction de translate grp ////////////////////////////////////////////////////////// //

    @FXML
    public void translate_grp() {

       double x,y;
         x = notreGrille.xToPixel((double) X.getValue());
         y = notreGrille.yToPixel((double) Y.getValue());
        if (group_select != null) {
            trans.Translating_grp(group_select, x, y);
        }

    }
    ////////////////////////// Ajouter un polygone a la grille////////// ////////////////

    public Polygone AjouterR(String nom, int r, int nb) {
        this.name = nom;
        Polygone name = new Polygone(r, nb);
        name.setId(nom);
        name.getTimeline().setDisable(true);
        drag_poly(notreGrille, name, true);

        name.setOnMouseClicked(p -> {
            name.selectionner(true);
            if (record) {
                name.getTimeline().setDisable(false);
            } else {
                name.getTimeline().setDisable(true);
            }
            poly_select = name;
            objet_select = poly_select;
            aaa(poly_select.get_nbr_cotes(), poly_select.getFill());
            borderPane.getChildren().removeAll(borderPane.getBottom());

            Frames_poly(poly_select.getTimeline());
            for (int a = 0; a < pole.size(); a++) {

                Forme forme = new Forme();
                forme.setId(pole.get(a).getId());
                forme.setNbCot(pole.get(a).get_nbr_cotes());
                forme.setRayon(pole.get(a).get_rayon());
                Paint couleur = pole.get(a).getFill();
                forme.setCouleur(couleur.toString());
                forme.setScale(pole.get(a).getScaleX());
                int m = 0;
                boolean exist = false;
                while (m < anim.Anim.size()) {
                    if (anim.Anim.get(m).getId().equals(forme.getId()) == true) {
                        exist = true;

                    }
                    m++;
                }
                if (exist == false) {
                    //System.out.println("existe pas ");
                    anim.Anim.add(forme);
                }
            }

        });

        return name;
    }

    /////////////////////////////////// /////////////////////////////// Afficher message///////////////////////////////
    public void aaa(int n_c, Paint c) {
        //if(poly_select.getSelectionne()==true)

        p = new Polygone(60, n_c);
        p.setFill(c);
        p.setScaleX(0.5);
        p.setScaleY(0.5);
        pane.getChildren().clear();
        pane.getChildren().add(p);
        animateMessage();
        //}
        /* else
          {
              System.out.println("no");
          }*/
    }
    ///////////////////////////////////

    public Pane clock_display() {
        Pane G = new Pane();

        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("DigitalClock-background.png")));
        background.setFitHeight(40);
        background.setFitWidth(120);
        background.setTranslateY(25);
        G.getChildren().addAll(background);
        G.setPrefSize(121, 41);
        //G.setTranslateY(-30);G.setTranslateX(40);
        G.getTransforms().add(new Translate(40f, -30f));
        return G;
    }
    //////////////////// Afficher la Grille //////////////////////////

    public Pane Afficher_grille() {
        
        NumberAxis xAxis = new NumberAxis(-20, 20, 1);
        NumberAxis yAxis = new NumberAxis(-10, 10, 1);
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
        notreGrille = new Grille(1000, 600, xAxis, yAxis);
        notreGrille.toFront();
        borderPane.setTop(tools);
        notreGrille.adaptationGrille();
        zoomController = new ZoomController(notreGrille);
        borderPane.setBottom(poly_select.getTimeline());
        borderPane.getBottom().setDisable(true);
        notreGrille.setMinSize(900, 520);
        
        return notreGrille;

    }
////////////////////////// Zoom + //////////////////
    public void scale1(ActionEvent e) {

        zoomController.zoomIn();

    }
    ///////////////// Zoom - //////////////////////////////

    public void scale2(ActionEvent e) {

        zoomController.zoomOut();

    }
////////////////////////////// Enregistrer les keyFrames de la barre de temps du polygone ///////////////
    public void Frames_poly(timeline ti) {

        id = objet_select.getId();
     
        boolean active;
        if (objet_select instanceof Polygone) {
            active = true;
            if (poly_select.getTimeline().isRemplie() == false) {
                poly_select.getTimeline().getSlid().setValue(0);
                poly_select.getTimeline().setTemps_preced(0);
            }

        } else {
            active = false;
        }
        if (active) {
            id_poly = poly_select.getId();
            borderPane.setBottom(poly_select.getTimeline());
          
            ti.setId("hi");
            ti.getSlid().setOnMouseClicked((MouseEvent ev) -> {
               
                frame++; ////// pour le Radio boutton (Pour supprimer un keyframe
                int k = 0;
                ti.setTime(ti.getTemps_preced());
                ti.setTemps_preced((int) Math.round(ti.getSlid().getValue()));
                //ti.setSave_temps(temps);
                if (poly_select != null) {

                    id = poly_select.getId();
                    id_poly = id;
                    id_preced_poly = id_poly;
                    k = 0;
                    id_prec = id;
                    double r = poly_select.get_rayon();
                    double scal = poly_select.getScaleX();
                    double angle = poly_select.getAngle();
                    double x = poly_select.getTranslateX();
                    double y = poly_select.getTranslateY();
                    System.out.println("Id " + id);
                    System.out.println("rayon : " + r);
                    System.out.println("temps : " + ti.getTime());
                    System.out.println("Scale " + scal);
                    System.out.println("x : " + x);
                    System.out.println("y : " + y);
                    System.out.println("angle : " + angle);
                    Iterator<Forme> it = anim.Anim.iterator();
                    int pos = 0;
                    k = 0;
                    while (it.hasNext()) {
                        Forme f = it.next();
                        if (id != null && id != "none" && id.equals(f.getId())) {
                            k = pos;
                        }
                        pos++;
                    }
                    pos = 0;

                    System.out.println("Pos " + k);
                    if (record == true) {
                        Cle e = new Cle();
                        e.setDuree(ti.getTime());
                        e.setAngle(poly_select.getAngle());
                        e.setEchelle((float) poly_select.getScaleX());
                        e.setX(poly_select.getTranslateX());
                        e.setY(poly_select.getTranslateY());
                        int s = 0;
                        int place = 0;
                        boolean trouv = false;
                        while (s < anim.Anim.get(k).ll.size()) {
                            int duree = anim.Anim.get(k).ll.get(s).getDuree();
                            if (e.getDuree() == duree) {
                                trouv = true;
                                place = s;
                            }
                            s++;
                        }
                        if (trouv == true) {
                            System.out.println("La place : " + place);
                            anim.Anim.get(k).ll.set(place, e);
                        } else {
                            System.out.println("I'm here niggas");
                            anim.Anim.get(k).ll.add(e);
                        }
                        anim.Anim.get(k).setAnimated(true);
                    } else {
                        k = 0;
                    }
                }
                if (anim.Anim.size() != 0) {
                    int l = 1, tmp;
                    Comparator<? super Cle> c = new Comparator<Cle>() {
                        @Override
                        public int compare(Cle o1, Cle o2) {
                            return (o1.compare(o1.getDuree(), o2.getDuree()));
                        }
                    };
                    anim.Anim.get(k).ll.sort(c);
                    l = 0;

                    /// Afficher les key frames;
                    if (anim.Anim != null) {
                        while (l < anim.Anim.get(k).ll.size()) {
                            System.out.println("J'ordonne : " + anim.Anim.get(k).ll.get(l).getDuree());
                            l++;
                        }
                    }

                } else {
                    System.out.println("error");
                }

            });
        } else {

            System.out.println("change timeline");
            // poly_select.getTimeline().setDisable(true);   

            Frames_Grp(group_select.getTimeline(), true);

        }

    }
////////////////////////////// Enregistrer les keyFrames de la barre de temps de l'objet complexe  ///////////////
    public void Frames_Grp(timeline tim, boolean active) {

        id = objet_select.getId();
        if (active) {
            id_grp = group_select.getId();
            /*     //System.out.println(" je suis l'id du groupe : "+id);
        
       if(id.equals(id_prec)==false)
       {
         //group_select.getTimeline().getSlid().setValue(0);
         temp_prece_grp = tim.getSave_temps(); 
       }*/

            tim.setId("miaw");
            tim.getSlid().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent ev) {
                    System.out.println("The tiemline  " + tim.getId());
                    frame++; ////// pour le Radio boutton (Pour supprimer un keyframe
                    int k = 0;
                    tim.setTime(tim.getTemps_preced());
                    tim.setTemps_preced((int) Math.round(tim.getSlid().getValue()));
                    // tim.setSave_temps(temps);
                    System.out.println("LE temps precedent " + tim.getTemps_preced());

                    if (group_select != null) {

                        id = objet_select.getId();

                        id_grp = group_select.getIdentif();
                        id_preced_grp = id_grp;

                        k = 0;
                        id_prec = id;

                        double scal = group_select.getScaleX();
                        double angle = group_select.getAngle();
                        double x = group_select.getTranslateX();
                        double y = group_select.getTranslateY();
                        System.out.println("Id " + id_grp);
                        System.out.println("temps : " + tim.getTime());
                        System.out.println("Scale " + scal);
                        System.out.println("x : " + x);
                        System.out.println("y : " + y);
                        System.out.println("angle : " + angle);
                        Iterator<Forme_G> it = anim.AnimG.iterator();
                        int pos = 0;
                        k = 0;
                        while (it.hasNext()) 
                        {
                            Forme_G f = it.next();
                            if (id_grp != null && id_grp != "none" && id_grp.equals(f.getIdentif())) {
                                k = pos;
                            }
                            pos++;
                        }
                        pos = 0;

                        System.out.println("Pos " + k);
                        if (record == true) {
                            Cle e = new Cle();
                            e.setDuree(tim.getTime());
                            e.setAngle(group_select.getAngle());
                            e.setEchelle((float) group_select.getScaleX());
                            e.setX(group_select.getTranslateX());
                            e.setY(group_select.getTranslateY());
                            int s = 0;
                            int place = 0;
                            boolean trouv = false;
                            while (s < anim.AnimG.get(k).ll.size()) {
                                int duree = anim.AnimG.get(k).ll.get(s).getDuree();
                                if (e.getDuree() == duree) 
                                {
                                    trouv = true;
                                    place = s;
                                }
                                s++;
                            }
                            if (trouv == true) {
                                System.out.println("La place : " + place);
                                anim.AnimG.get(k).ll.set(place, e);
                            } else {
                                System.out.println("I'm here niggas");
                                anim.AnimG.get(k).ll.add(e);
                            }
                            anim.AnimG.get(k).setAnimated(true);
                        } else {
                            k = 0;
                        }
                    }
                    if (anim.AnimG.size() != 0) 
                    {
                        int l = 1, tmp;
                        Comparator<? super Cle> c = new Comparator<Cle>() {
                            @Override
                            public int compare(Cle o1, Cle o2) {
                                return (o1.compare(o1.getDuree(), o2.getDuree()));
                            }
                        };
                        anim.AnimG.get(k).ll.sort(c);
                        l = 0;
                        /// Afficher les key frames;
                        if (anim.AnimG != null) {
                            while (l < anim.AnimG.get(k).ll.size()) {
                                System.out.println("J'ordonne : " + anim.AnimG.get(k).ll.get(l).getDuree());
                                l++;
                            }
                        }

                    } else {
                        System.out.println("error");
                    }
                }
            });
        }

    }
////////////////// Afficher le nom du polygone ////////////////////////////////////
    @FXML
    public void animateMessage() 
    {
        if(poly_select.get_nbr_cotes()==3) nom_poly.setText("Triangle");
        if(poly_select.get_nbr_cotes()==4) nom_poly.setText("Carré");
        if(poly_select.get_nbr_cotes()==5) nom_poly.setText("Pentagone");
        if(poly_select.get_nbr_cotes()==6) nom_poly.setText("Hexagone");
        if(poly_select.get_nbr_cotes()==7) nom_poly.setText("Heptagone");
        if(poly_select.get_nbr_cotes()==8) nom_poly.setText("Octagone");
        if(poly_select.get_nbr_cotes()==9) nom_poly.setText("Ennéagone");
        if(poly_select.get_nbr_cotes()==10)nom_poly.setText("Décagone");
        if(poly_select.get_nbr_cotes()>=20)nom_poly.setText("Cercle"); 
        FadeTransition ft = new FadeTransition(Duration.millis(1000), nom_poly);
        ft.setFromValue(0.0);
        ft.setToValue(3);
        ft.setCycleCount(1);
        ft.play();
    }

////////////////////////////////////////// Changer la couleur ////////////////////////////
    @FXML
    public void setcouleur() {
        if (objet_select instanceof Groupe) {
            Couleur_Grp();
        } else {
            Couleur_poly();
        }
    }
//////////// Couleur du poy-////////////////////////////////////////////
    @FXML
    public void Couleur_poly() {
        Color couleur = colorpicker.getValue();
        poly_select.setFill(couleur);
        String poly_id = poly_select.getId();
        if (anim.Anim.size() != 0) {
            int i = 0;
            while (i < anim.Anim.size() && (!poly_id.equals(anim.Anim.get(i).getId()))) {
                i++;
            }
            anim.Anim.get(i).setCouleur(couleur.toString());
        }
        if (p != null) {
            p.setFill(couleur);
        }
    }

    @FXML
    //////////////// Couleur de l'objet complexe //////////////////////
    public void Couleur_Grp() {
        Paint couleur = colorpicker.getValue();
        group_select.setFill((Color) couleur);
        group_select.setCouleur(couleur.toString());
        String gp_id = group_select.getId();
        if (anim.AnimG.size() != 0) {
            int i = 0;
            while (i < anim.AnimG.size()) {
                if (gp_id.equals(anim.AnimG.get(i).getId())) 
                {
                    anim.AnimG.get(i).setCouleur(couleur.toString());
                }
                i++;
            }
        }
    }

 @FXML   
JFXButton cling;
///////////////// Animation///////////////////////////////////////
    Animation animation = new Animation();
///////////////// ************Record ******************/////////////
public int compteur = 0;
    public void record(MouseEvent d) {
        
        ///////////// Une animation pour le Boutton record 
        compteur++;
        FadeTransition f = new FadeTransition(Duration.millis(250), cling);
        f.setFromValue(1.0);
        f.setToValue(0.5);
        f.setRate(0.7);
        f.setAutoReverse(true);
        f.setCycleCount(Timeline.INDEFINITE);
        f.setInterpolator(Interpolator.EASE_BOTH);
        
        record = true;
        borderPane.getBottom().setDisable(false);
     
        
        System.out.println(compteur);
       
        if (cpt == 1) {
            
            record = false;
            borderPane.getBottom().setDisable(true);
            cpt = 0;
            String rech = poly_select.getId();
            int j = 0;
            int pos = anim.Anim.size() - 1;
            while (j < anim.Anim.size()) {
                if (anim.Anim.get(j).getId().equals(rech) == true) {
                    pos = j;
                }
                j++;
            }
            //////// Enregistrer la derniére position TODO
            /*  Cle e = new Cle();
                e.setAngle(poly_select.getRotate());
                e.setEchelle((float) poly_select.getScaleX());
                e.setX(poly_select.getTranslateX());
                e.setY(poly_select.getTranslateY());
                e.setDuree((int) poly_select.getTimeline().getSlid().getValue()); 
             
                anim.Anim.get(pos).ll.add(e);*/
        } else {
            cpt++;
        }
         f.play();

    if ( compteur %2 == 0) cling.setVisible(false); 
    else cling.setVisible(true);
        
    }
    
//// Le drag du polygone ///////////////////////////////////////////////////////////
    public void drag_poly(Grille grille, Polygone poly, boolean active) {
        if (active) {

            poly.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    poly.setStroke(Color.TRANSPARENT);
                }
            });
            poly.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    poly.setCursor(Cursor.CLOSED_HAND);
                }
            });
            /**
             * ***********************************************
             */
            poly.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    double dragX = t.getSceneX() - draganchor.getX();
                    double dragY = t.getSceneY() - draganchor.getY();
                    poly.setCursor(Cursor.OPEN_HAND);
                    double newXPosition = initX + dragX;
                    double newYPosition = initY + dragY;
                    // po.setTranslateX(0);
                    //po.setTranslateY(0);
                    if (((newXPosition - poly.get_rayon() / 2) >= -grille.zoneDessin.getWidth() / 2) && ((newXPosition + poly.get_rayon() / 2) <= grille.zoneDessin.getWidth() / 2)) {
                        poly.setTranslateX(newXPosition);
                    }
                    if ((newYPosition - poly.get_rayon() / 2 >= -grille.zoneDessin.getHeight() / 2) && (newYPosition + poly.get_rayon() / 2 <= grille.zoneDessin.getHeight() / 2)) {
                        poly.setTranslateY(newYPosition);
                    }
                    ////////
                    Parent courant = poly.getParent();
                    while (courant instanceof Group) {
                        courant = courant.getParent();
                    }
                    StackPane sauve = (StackPane) courant;
                    List<Node> liste = new ArrayList<Node>();

                    liste = sauve.getChildren();
                    for (Node test : liste) {
                        if (test instanceof Shape && (test != poly)) {

                            Shape intersect = Shape.intersect(poly, (Shape) test);
                            if (intersect.getBoundsInLocal().getWidth() != -1) {
                                //((Shape) test).setStroke(Color.CHOCOLATE);
                               // (poly).setStroke(Color.CHOCOLATE);
                            } else {
                                //((Shape) test).setStroke(Color.BLACK);
                            }
                        }
                    }
                
                
                 coord.setText(" X = " +Math.round(notreGrille.pixelToX(poly_select.getTranslateX())) + " Y =" + Math.round(notreGrille.pixelToY(poly_select.getTranslateY())));
                }

            });
            /**
             * ***********************************************
             */

            if (poly.getGroup_id().equals("none")) {
                poly.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        //poly.setStroke(Color.TRANSPARENT);
                        initX = poly.getTranslateX();
                        initY = poly.getTranslateY();
                        draganchor = new Point2D(t.getSceneX(), t.getSceneY());
                        poly_select = poly;
                        if (poly.getGroup_id().equals("none")) {
                            objet_select = poly_select;
                        }
                        id = objet_select.getId();
                        System.out.println(" je suis l'id de l'objet : " + id);

                        //  objet_select=poly;
                    }
                });
            }
        } else {
            poly.setOnMouseDragged(e -> {
                //grille.zoneDessin.getChildren().remove(poly);   
            });
        }
        //return poly;
    }
    
    
    
///// le drag de l'objet complexe //////////////////////////////////////////////////////////////
    
    public void drag_groupe(Grille grille, Groupe poly, boolean on) {  //drager le groupe poly si On est true sinon ne pas offrir la possibilité de le drager
        if (on) {

            for (Node po : poly.getChildren()) {
                ((Polygone) po).drag(notreGrille, (Polygone) po, false);
            }

            poly.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    poly.setCursor(Cursor.CLOSED_HAND);
                }
            });
            /**
             * ***********************************************
             */
            poly.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    double dragX = t.getSceneX() - draganchor.getX();
                    double dragY = t.getSceneY() - draganchor.getY();
                    poly.setCursor(Cursor.OPEN_HAND);
                    double newXPosition = initX + dragX;
                    double newYPosition = initY + dragY;
                    // po.setTranslateX(0);
                    //po.setTranslateY(0);
                    //      poly.translateXProperty().bind(grille.zoneDessin.widthProperty());
                    //     poly.translateYProperty().bind(grille.zoneDessin.heightProperty());
                    if (((newXPosition - poly.getLayoutX()) >= -grille.zoneDessin.getWidth()) && ((newXPosition + poly.getLayoutX()) <= grille.zoneDessin.getWidth())) {
                        poly.setTranslateX(newXPosition);
                    }
                    if ((newYPosition - poly.getLayoutY() >= -grille.zoneDessin.getHeight()) && (newYPosition + poly.getLayoutY() <= grille.zoneDessin.getHeight())) {
                        poly.setTranslateY(newYPosition);
                    }
                    ////////

                }

            });
            /**
             * ***********************************************
             */
            poly.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    initX = poly.getTranslateX();
                    initY = poly.getTranslateY();
                    draganchor = new Point2D(t.getSceneX(), t.getSceneY());
                    group_select = poly;
                    objet_select = group_select;
                    id = objet_select.getId();
                    System.out.println("Le id de l'objet est : " + id);
                    borderPane.setBottom(group_select.getTimeline());
                    if (record) {
                        group_select.getTimeline().setDisable(false);
                    } else {
                        group_select.getTimeline().setDisable(true);
                    }
                    // poly.selectionner_gp(true);

                }
            });
            /*poly.setOnMouseClicked(v->{
              
              
                    group_select = poly;
                    objet_select = poly;
                    id = objet_select.getId();
              
              System.out.println("Le id de l'objet est : "+id);
          });*/

        } else {
            poly.setOnMouseDragged(e -> {
                for (Node po : poly.getChildren()) {
                    ((Polygone) po).drag(notreGrille, (Polygone) po, true);
                }
            });
        }// desactiver le drag

    }

    
    //// La selection de l'objet complexe ////////////////////////////////////////////////
    public void selectionner_gp(Groupe gp, boolean activate) {
        objet_select = gp;
        if (activate) {
            gp.setOnMouseClicked(value -> {

                objet_select = gp;
                if (gp.isSelectionner()) {

                    for (Node m : gp.getChildren()) {
                        //((Polygone)m).setSelectionne(false);
                        ((Polygone) m).setStroke(((Polygone) m).getFill());
                    }
                    gp.setSelectionner(false);
                } else {

                    for (Node m : gp.getChildren()) {
                        // ((Polygone)m).setSelectionne(true);
                        ((Polygone) m).setStrokeWidth(3.0);
                        ((Polygone) m).setStroke(Color.AQUA.saturate());
                    }
                    gp.setSelectionner(true);
                }

            });

            //System.out.println("Ne pas Selectionner");
        } else {
            gp.setOnMouseClicked(e -> {
            });
        }

    }

   
    ////////////////******************Play*************************//////////

    ///// La fontion Play /////////////////////////////
    public void play_grp(ActionEvent e) throws IOException {

        final Stage newStage = new Stage();
        timeline = new Timeline();
        NumberAxis xAxis = new NumberAxis(-20, 20, 1);
        xAxis.setAutoRanging(true);
        NumberAxis yAxis = new NumberAxis(-10, 10, 1);
        yAxis.setAutoRanging(true);
        Grille root2 = new Grille(1000, 600, xAxis, yAxis);
        root2.setMinSize(900, 500);
     
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Lecteur.fxml"));
        Parent myPane = (Parent)myLoader.load();
        LecteurController controller = (LecteurController) myLoader.getController();
        controller.btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
              

                    newStage.close();
                    animation.stop(timeline);
                    root2.zoneDessin.getChildren().removeAll(groupes);
            }
        });
        newStage.setOnCloseRequest(value
                -> {

            animation.stop(timeline);
            root2.zoneDessin.getChildren().removeAll(groupes);   
        });


        
       
        

        controller.pause.setOnMouseClicked(value -> {

            animation.pause(timeline);
        });
        controller.stop.setOnMouseClicked(value -> {

            animation.stop(timeline);
        });

        controller.Resume.setOnMouseClicked(v -> {

            animation.play(timeline);
        });
        controller.Replay.setOnMouseClicked(v -> {

            timeline.playFromStart();
        });
       
        root2.getStylesheets().add(App.class.getResource("caspian.css").toExternalForm());
        Polygone poly = null;
        //// animer les polygones
        animation.record(timeline, poly, anim, root2);
        //// animer les groupes
        animation.record_grp(timeline,anim, root2);
           ////
    
        controller.bor.setCenter(root2);       
        animation.play(timeline);
        root2.adaptationGrille();
       
 
        Scene scene2 = new Scene(myPane);
        newStage.setScene(scene2);
        newStage.setAlwaysOnTop(true);
        newStage.setResizable(false);
        newStage.initStyle(StageStyle.TRANSPARENT);
        newStage.show();

    }
    /////////////**********Stop**********/////////

    public void Stop(ActionEvent r) {
        timeline.stop();
    }
    ///////////***************Pause******//////

    public void Pause(ActionEvent r) {

        timeline.pause();
    }

    /**
     * ************ Restart *********************
     */
    public void Restart(ActionEvent r) {
        timeline.playFromStart();
    }

    /**
     * *******  animation du trajet polygone ******************
     */
Trajet paths = new Trajet();
    public void Path(ActionEvent t) throws IOException {
        final Stage newStage = new Stage();
        animation.pathTransition = new PathTransition();
        NumberAxis xAxis = new NumberAxis(-20, 20, 1);
        xAxis.setAutoRanging(true);
        NumberAxis yAxis = new NumberAxis(-10, 10, 1);
        yAxis.setAutoRanging(true);
        Grille root2 = new Grille(1000, 600, xAxis, yAxis);
        Pane root3 = new Pane();
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Lecteur.fxml"));
        Parent myPane = (Parent)myLoader.load();
        LecteurController controller = (LecteurController) myLoader.getController();
        root2.setMinSize(900, 500);
        root3.setPrefSize(900, 500);
       
        controller.btn.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent t) {
              

                    newStage.close();
            }
        });
    
        root2.getStylesheets().add(App.class.getResource("caspian.css").toExternalForm());
        controller.pause.setOnMouseClicked(value -> {

            animation.pathTransition.pause();
        });
        controller.stop.setOnMouseClicked(value -> {

            animation.pathTransition.stop();
        });

        controller.Resume.setOnMouseClicked(v -> {
            
            animation.pathTransition.play();
        });
        controller.Replay.setOnMouseClicked(v -> {

            animation.pathTransition.playFromStart();
        });

        

       
   
        root2.getChildren().add(root3);
        //controller.scrollPane.getChildrenUnmodifiable().add(root2);
        controller.bor.setCenter(root2);
        Scene scene2 = new Scene(myPane);
        boolean trouv = false;
            String id = poly_select.getId();
            Iterator<Forme> it = anim.Anim.iterator();
            while(it.hasNext())
            {  Forme f= it.next();
                if(id.equals(f.getId()))
                {
                    if(f.getTrajectoire()!=null)
                    {
                        paths = f.getTrajectoire();
                       trouv = true;
                    }
              
                }
                
            }
            if(trouv && ouvert)
            {
                
               animation.path_animtaion_open(scene2, root3, paths, poly_select);
            }
            else
            {
               animation.path_animtaion(scene2, root3, paths, poly_select);
            }
       
        
       

        String l = poly_select.getId();
        int d = 0;
        while (d < anim.Anim.size()) {
            if (anim.Anim.get(d).getId().equals(l)) {
                anim.Anim.get(d).setTrajectoire(paths);
                d = anim.Anim.size();
            }
            d++;
        }
        root2.adaptationGrille();
        newStage.setScene(scene2);
        newStage.setResizable(false);
        newStage.initStyle(StageStyle.TRANSPARENT);
        newStage.show();

    }

    //////////  Operations sur les fichiers ****************/////////
    ////////////****Sauvegarde ***/////
    public void Save(ActionEvent s) {

        if (ele != null && student) 
        {
            File_Operations f = new File_Operations();
            comptes.Eleve e = ele;
            Animation_liste m = new Animation_liste();
            m.Anim = anim.Anim;
            m.AnimG = anim.AnimG;
            boolean success = f.Sauvegarder_eleve(m, chemin, e);
            if (success)
            {
                AlerteBox save = new AlerteBox();
                save.infoBox("Operation réussite", "Sauvegarde");

            }
        } else if (teacher) {
            File_Operations f = new File_Operations();
            boolean success = f.Sauvegarder_prof(anim, chemin, motDePasseStandard);
            if (success) {
                AlerteBox save = new AlerteBox();
                save.infoBox("Operation réussite", "Sauvegarde");

            }
        }
    }
    //// Ouvrir********************************************///////////

  

    public void Open(ActionEvent mm) throws IOException 
    {
       
        if (student && ele != null) 
        {
            Animation_liste m = null;
            comptes.Eleve eleve = ele;
            boolean opened = false;
            
            m = f.Ouvrir_eleve(eleve, opened, chemin);
            if (m.Anim.isEmpty() == false) 
            {
                 ouvert = true;
           
                Iterator<Forme> fr = m.Anim.iterator();
                Iterator<Forme_G> gr = m.AnimG.iterator();
                FXMLLoader myLoader = new FXMLLoader(getClass().getResource("tabs.fxml"));
                Parent myPane = (Parent) myLoader.load();
                InterfaceController controller = (InterfaceController) myLoader.getController();
                controller.userLabel.setText(ele.getNom()+".".concat(ele.getPrenom()));
                while (fr.hasNext()) {
                    Forme f = fr.next();
                    if (f.getGroup_id().equals("none")) {
                        Polygone poly = AjouterR(f.getId(), (int) f.getRayon(), f.getNbCot());  
                        poly.setFill(Paint.valueOf(f.getCouleur()));
                        if(!f.ll.isEmpty())
                        {
                         poly.setTranslateX(f.ll.getLast().getX());
                         poly.setTranslateY(f.ll.getLast().getY());
                         poly.setRotate(f.ll.getLast().getAngle());
                         poly.setScaleX(f.ll.getLast().getEchelle());
                         poly.setScaleY(f.ll.getLast().getEchelle());
                        }
                        controller.notreGrille.zoneDessin.getChildren().add(poly);
                        controller.poly_select = poly;
                        controller.objet_select = controller.poly_select;
                        drag_poly(controller.notreGrille,controller.poly_select, true);
                    }
                    controller.Frames_poly(controller.poly_select.getTimeline());
                }
  //ArrayList<Groupe> grou = new ArrayList<>();
                while (gr.hasNext()) 
                {

                    ArrayList<Node> groupage = new ArrayList<>();
                  
                    Forme_G r = gr.next();
                    System.out.println(r.getIdentif());
                    fr = m.Anim.iterator();
                    while (fr.hasNext()) {
                        Forme f = fr.next();
                        System.out.println("   azul  " + f.getGroup_id());
                        Polygone poly = AjouterR(f.getId(), (int) f.getRayon(), f.getNbCot());
                        if (r.getIdentif().equals(f.getGroup_id())) {
                            poly.setTranslateX(f.getLastXPosition());
                            poly.setTranslateY(f.getLastYPosition());
                            poly.setRotate(f.getLastangle());
                            poly.setScaleX(f.getLastScale());
                            poly.setScaleY(f.getLastScale());
                            poly.setFill(Paint.valueOf(f.getCouleur()));
                            groupage.add(poly);
                        }
                    }
                    Groupe gp = Groupe.regrouper(controller.notreGrille.zoneDessin, groupage);
                    gp.setId(r.getId());
                    gp.setIdentif(r.getIdentif());
 
                    if(!r.ll.isEmpty())
                    {
                        gp.setTranslateX(r.ll.getLast().getX());
                        gp.setTranslateY(r.ll.getLast().getY());
                        gp.setRotate(r.ll.getLast().getAngle());
                        gp.setScaleX(r.ll.getLast().getEchelle());
                        gp.setScaleY(r.ll.getLast().getEchelle());
                    }
                    
      
                    controller.selectionner_gp(gp, true);
                    controller.group_select = gp;
                    controller.objet_select = controller.group_select;
                    drag_groupe(controller.notreGrille,controller.group_select,true);
                }
                controller.Frames_poly(controller.poly_select.getTimeline());
                controller.notreGrille.adaptationGrille();
 
                controller.anim = m;
               
            addNewTab(ouv, "Ouverture", myLoader.getRoot(), true);
            myPane.setTranslateX(20);
            ouv.getSelectionModel().selectFirst();
            
            }
        } 
        else
        {
            ///////////// A continuer
         if (teacher) 
         {
                Animation_liste m = null;
                boolean opened = false;
                m = f.Ouvrir_prof(opened, chemin);
               if (m.Anim.isEmpty() == false) 
            {
                
                ouvert = true;
                Iterator<Forme> fr = m.Anim.iterator();
                Iterator<Forme_G> gr = m.AnimG.iterator();
                FXMLLoader myLoader = new FXMLLoader(getClass().getResource("tabs.fxml"));
                Parent myPane = (Parent) myLoader.load();
                InterfaceController controller = (InterfaceController) myLoader.getController();
                controller.userLabel.setText("Administrateur");
               // controller.poly_select = poly_select;
                //controller.group_select = group_select;
                //controller.objet_select = objet_select;
                while (fr.hasNext()) {
                    Forme f = fr.next();
                    if (f.getGroup_id().equals("none")) {
                        Polygone poly = AjouterR(f.getId(), (int) f.getRayon(), f.getNbCot());  
                        poly.setFill(Paint.valueOf(f.getCouleur()));
                        if(!f.ll.isEmpty())
                        {
                         poly.setTranslateX(f.ll.getLast().getX());
                         poly.setTranslateY(f.ll.getLast().getY());
                         poly.setRotate(f.ll.getLast().getAngle());
                         poly.setScaleX(f.ll.getLast().getEchelle());
                         poly.setScaleY(f.ll.getLast().getEchelle());
                        }
                        controller.notreGrille.zoneDessin.getChildren().add(poly);
                        controller.poly_select = poly;
                        controller.objet_select = controller.poly_select;
                        drag_poly(controller.notreGrille,controller.poly_select, true);
                    }
                    controller.Frames_poly(controller.poly_select.getTimeline());
                }
  //ArrayList<Groupe> grou = new ArrayList<>();
                while (gr.hasNext()) 
                {

                    ArrayList<Node> groupage = new ArrayList<>();
                  
                    Forme_G r = gr.next();
                    System.out.println(r.getIdentif());
                    fr = m.Anim.iterator();
                    while (fr.hasNext()) {
                        Forme f = fr.next();
                        System.out.println("   azul  " + f.getGroup_id());
                        Polygone poly = AjouterR(f.getId(), (int) f.getRayon(), f.getNbCot());
                        if (r.getIdentif().equals(f.getGroup_id())) {
                            poly.setTranslateX(f.getLastXPosition());
                            poly.setTranslateY(f.getLastYPosition());
                            poly.setRotate(f.getLastangle());
                            poly.setScaleX(f.getLastScale());
                            poly.setScaleY(f.getLastScale());
                            poly.setFill(Paint.valueOf(f.getCouleur()));
                            groupage.add(poly);
                        }
                    }
                    Groupe gp = Groupe.regrouper(controller.notreGrille.zoneDessin, groupage);
                    gp.setId(r.getId());
                    gp.setIdentif(r.getIdentif());
 
                    if(!r.ll.isEmpty())
                    {
                        gp.setTranslateX(r.ll.getLast().getX());
                        gp.setTranslateY(r.ll.getLast().getY());
                        gp.setRotate(r.ll.getLast().getAngle());
                        gp.setScaleX(r.ll.getLast().getEchelle());
                        gp.setScaleY(r.ll.getLast().getEchelle());
                    }
                    
      
                    controller.selectionner_gp(gp, true);
                    controller.group_select = gp;
                    controller.objet_select = controller.group_select;
                    drag_groupe(controller.notreGrille,controller.group_select,true);
                }
                controller.Frames_poly(controller.poly_select.getTimeline());
                controller.notreGrille.adaptationGrille();
 
                controller.anim = m;
               
            addNewTab(ouv, "Ouverture", myLoader.getRoot(), true);
            
            myPane.setTranslateX(20);
            ouv.getSelectionModel().selectFirst();
            
            }
         }

        }
    }

   
    Groupe gp2 = new Groupe();

    //// Créer un objet complexe 
    @FXML
    public void Grouper(ActionEvent e) {

        List<Node> listgroup = new ArrayList<Node>();
        //listgroup.add(polySelect);
        if (Polygone.pol_select > 1) {

            for (Node d : notreGrille.zoneDessin.getChildren()) {
                if ((d instanceof Polygone) && ((Polygone) d).getSelectionne() && ((Polygone) d).getGroup_id() == "none") {
                    ((Polygone) d).setGroup_id("groupe" + number);
                    ((Polygone) d).setSelectionne(false);
                    ((Polygone) d).selectionner(false);
                    String m = ((Polygone) d).getId();
                    String l = ((Polygone) d).getGroup_id();
                    int i = 0;
                    while (i < anim.Anim.size()) {

                        if (anim.Anim.get(i).getId().equals(m)) {
                            anim.Anim.get(i).setGroup_id("groupe" + number);
                            anim.Anim.get(i).setLastXPosition(((Polygone) d).getTranslateX());
                            anim.Anim.get(i).setLastYPosition(((Polygone) d).getTranslateY());
                            anim.Anim.get(i).setLastScale(((Polygone) d).getScaleX());
                            anim.Anim.get(i).setLastangle(((Polygone) d).getRotate());
                            anim.Anim.get(i).setCouleur(((Polygone) d).getFill().toString());
                            // i=anim.Anim.size();
                        }
                        i++;
                    }

                    listgroup.add(d);// System.out.println("séléctionnée"+(d.getClass()));
                }

            }

            selectionner_gp(gp2, true);
            gp2 = Groupe.regrouper(notreGrille.zoneDessin, listgroup);
            drag_groupe(notreGrille, gp2, true);
            gp2.setId("groupe" + number);
            gp2.setIdentif("groupe" + number);
            number++;
            group_select = gp2;
            if (record) {
                group_select.getTimeline().setDisable(false);
            } else {
                group_select.getTimeline().setDisable(true);
            }
            selectionner_gp(group_select, true);
            drag_groupe(notreGrille, group_select, true);
            selectionner_gp(group_select, true);
            Frames_poly(poly_select.getTimeline());
            groupes.add(gp2);
            Forme_G g = new Forme_G();
            g.setId(gp2.getId());
            g.setIdentif(gp2.getIdentif());
            g.setTimeline(gp2.getTimeline());
            g.setScale(gp2.getScaleX());
            anim.AnimG.add(g);
        } else {

            AlerteBox Select = new AlerteBox();
            Select.infoBox("Veuillez Selectionner plus d'un polygone Svp", "Création d'objets Complexes");

        }
    }

    @FXML
    public void Degrouper(ActionEvent e) {
   
        Iterator<Groupe> it = groupes.iterator();
        List<Node> m = new ArrayList<>();
        ArrayList<Groupe> f = new  ArrayList<>();
        while (it.hasNext()) {
            Groupe g = it.next();
            f.add(g);
            if (g != null && g.isSelectionner()) {

                m = g.degrouper(notreGrille.zoneDessin);
                notreGrille.zoneDessin.getChildren().remove(g);
                //drag_groupe(notreGrille,g, false);
                it.remove();
                group_select.setIdentif("none");
            }
            else
            {
                System.out.println("Erreur");
            }
        }
        
        Iterator<Forme_G> ig = anim.AnimG.iterator();
        
       // List<Node> m = new ArrayList<>();
        Iterator<Groupe> kl = f.iterator();
        while (ig.hasNext()) 
        {
            Forme_G g = ig.next();
            while(kl.hasNext())
            {
                Groupe v = kl.next();
            if (v!=null && v.getIdentif().equals(g.getIdentif())) 
            {
                ig.remove();
            }
            }
        }
        
        Iterator<Node> k = m.iterator();
        while (k.hasNext()) {
            Node c = k.next();

            ((Polygone) c).selectionner(true);
            Polygone poly = AjouterR(((Polygone) c).getId(), (int) ((Polygone) c).get_rayon(), ((Polygone) c).get_nbr_cotes());
            notreGrille.zoneDessin.getChildren().add(poly);
            poly.setTranslateX(((Polygone) c).getTranslateX());
            poly.setGroup_id("none");
            poly.setTranslateY(((Polygone) c).getTranslateY());
            poly.setRotate(((Polygone) c).getRotate());
            poly.setRotate(((Polygone) c).getAngle());
            poly.setAngle(((Polygone) c).getRotate());
            poly.setAngle(((Polygone) c).getAngle());
            poly.setScaleX(((Polygone) c).getScaleX());
            poly.setScaleY(((Polygone) c).getScaleY());
            poly.setFill(((Polygone) c).getFill());
            poly.setTimeline(((Polygone) c).getTimeline());
            String id = ((Polygone) c).getId();
            Iterator<Forme> i = anim.Anim.iterator();
            while (i.hasNext())
            {
                Forme forme = i.next();

                if (forme.getId().equals(id)) {
                    forme.setGroup_id("none");
                   
                }
            }
            poly_select = poly;
            aaa(poly.get_nbr_cotes(), poly_select.getFill());
            drag_poly(notreGrille, poly, true);
        }
        // borderPane.setBottom(poly_select.getTimeline());
        if (record == true) {
            poly_select.getTimeline().setDisable(false);
        } else {
            poly_select.getTimeline().setDisable(true);
        }

        Frames_poly(poly_select.getTimeline());
        aaa(poly_select.get_nbr_cotes(), poly_select.getFill());

        System.out.println("le id aprés dégoupage  " + poly_select.getId());

    }

    public void selection() {
        notreGrille.zoneDessin.setOnMouseClicked(ev -> {
            System.out.println("Salam je séléctionne " + ev.getTarget().getClass().getName());
        });

    }

    public void Ouvrir_tree(ActionEvent event) {
        java.nio.file.Path selectedPath = treeView.getSelectionModel().getSelectedItem().getValue();
        // do something with selectedPath...
        System.out.println(selectedPath);
    }

    public Trajet trajectoire;
    public Trajet trajectoire_grp;

    public void Path_GRP(ActionEvent t) throws IOException {
        animation.pathTransition = new PathTransition();
             if (!group_select.getIdentif().equals("none"))
           {
            final Stage newStage = new Stage();
            //Trajet path = new Trajet();
            NumberAxis xAxis = new NumberAxis(-20, 20, 1);
            xAxis.setAutoRanging(true);
            NumberAxis yAxis = new NumberAxis(-10, 10, 1);
            yAxis.setAutoRanging(true);
            Grille root2 = new Grille(1000, 600, xAxis, yAxis);
            root2.adaptationGrille();
            Pane root3 = new Pane();
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Lecteur.fxml"));
            Parent myPane = (Parent)myLoader.load();
            LecteurController controller = (LecteurController) myLoader.getController();
            
            root2.setMinSize(900, 500);
            root3.setPrefSize(900, 500);
            
            double x = group_select.getTranslateX();
            double y = group_select.getTranslateY();
            controller.btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    try {
                        newStage.close();
                        animation.pathTransition.stop();
                        root3.getChildren().remove(group_select);
                        notreGrille.zoneDessin.getChildren().add(group_select);
                        group_select.setTranslateX(x);
                        group_select.setTranslateY(y);
                    } catch (IllegalArgumentException e) {
                        notreGrille.zoneDessin.getChildren().remove(group_select);
                    }
                }
            });
            newStage.setOnCloseRequest(value -> {

                animation.pathTransition.stop();
                root3.getChildren().remove(group_select);
                notreGrille.zoneDessin.getChildren().add(group_select);
                group_select.setTranslateX(x);
                group_select.setTranslateY(y);

            });
           
            controller.pause.setOnMouseClicked(value -> {

                animation.pathTransition.pause();
            });
            controller.stop.setOnMouseClicked(value -> {

                animation.pathTransition.stop();
            });

            controller.Resume.setOnMouseClicked(v -> {

                animation.pathTransition.play();
            });
            controller.Replay.setOnMouseClicked(v -> {

                animation.pathTransition.playFromStart();
            });

           
              

            notreGrille.zoneDessin.getChildren().remove(group_select);
   

            
            root2.getChildren().add(root3);
            // root2.getChildren().add(group);
            Scene scene2 = new Scene(myPane);
                  boolean trouv = false;
            String id = group_select.getIdentif();
            Iterator<Forme_G> itss = anim.AnimG.iterator();
            while(itss.hasNext())
            {  Forme_G f= itss.next();
                if(id.equals(f.getIdentif()))
                {
                    if(f.getTrajectoire()!=null)
                    {
                        paths = f.getTrajectoire();
                       trouv = true;
                    }
              
                }   
            }
            if(trouv && ouvert)
            {
                animation.path_animtaion_Grp_open(scene2, root3, paths, group_select);
            }
            else
            {
                animation.path_animtaion_Grp(scene2, root3, paths, group_select);
            }
            String m = group_select.getId();
            int i = 0;
            Iterator<Groupe> it = groupes.iterator();
            while (it.hasNext()) {
                Groupe grp = it.next();
                if (grp.getId().equals(m)) {

                    grp.setTrajectoire(paths);
                    i = groupes.size();
                }
                i++;
            }
            
            
            
            
            
            Iterator<Forme_G> its = anim.AnimG.iterator();
            while (it.hasNext()) {
                Forme_G grp = its.next();
                if (grp.getIdentif().equals(m)) {

                    grp.setTrajectoire(paths);
                }
            }
            controller.bor.setCenter(root2);
            newStage.setScene(scene2);
            newStage.setResizable(false);
            newStage.initStyle(StageStyle.TRANSPARENT);
            newStage.show();
        }

    }

    public Tab addNewTab(TabPane tabPane, String newTabName, Node newTabContent, boolean isCloseable)
    {
        Tab newTab = new Tab(newTabName);
        newTab.setContent(newTabContent);
        newTab.setClosable(isCloseable);

        newTab.setOnClosed(new EventHandler<Event>()
        {
            @Override
            public void handle(Event event) {
                if (tabPane.getTabs()
                        .size() == 2) {
                    event.consume();
                }
            }
        });

        tabPane.getTabs().add(fois, newTab);
        fois++;
        return newTab;

    }

    public void setBehaviourForPlusTabClick(final TabPane tabPane) {
        
    }
    @FXML
    StackPane ModalDimmer;

    @FXML
    public void floudis() {
        hideModalMessage();
    }

    private Node getEmptyTabContent() throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("interface.fxml"));
        Parent myPane = (Parent) myLoader.getRoot();
        InterfaceController controller = (InterfaceController) myLoader.getController();
        return controller.borderPane;
    }

    @FXML
    public void exit() {
        System.exit(1);
    }

    public void Afficher() throws ClassNotFoundException {
        Group root = new Group();
        TableView affichage = new TableView();
        TableColumn Nom = new TableColumn("Nom");
        Nom.setCellValueFactory(new PropertyValueFactory("Nom"));
        TableColumn Prenom = new TableColumn("Prenom");
        Prenom.setCellValueFactory(new PropertyValueFactory("Prenom"));
        TableColumn Groupe = new TableColumn("Groupe");
        Groupe.setCellValueFactory(new PropertyValueFactory("Groupe"));
        TableColumn Pass = new TableColumn("Mot de passe");
        Pass.setCellValueFactory(new PropertyValueFactory("Pass"));

        //DataAccounts dataaccounts = new DataAccounts();
        //dataaccounts = lireDonnees();
        ObservableList<Person> data = FXCollections.observableArrayList();

        for (int i = 0; i < ListeDeComptes.size(); i++) {
            data.add(new Person(ListeDeComptes.get(i).getNom(),
                    ListeDeComptes.get(i).getPrenom(),
                    String.valueOf(ListeDeComptes.get(i).getAnneeClasse()),
                    decrypt(ListeDeComptes.get(i).getMotDePasse())));
        }

        affichage.setItems(data);
        affichage.getColumns().addAll(Nom, Prenom, Groupe, Pass);

        root.getChildren().add(affichage);
        //Stage affiche = new Stage();
        //Scene sc = new Scene(root);
        //affiche.setScene(sc);
        //affiche.show();
        showModalMessage(root);
    }

    public static class Person {

        private StringProperty Nom;
        private StringProperty Prenom;
        private StringProperty Groupe;
        private StringProperty Pass;

        public Person(String Nom, String Prenom, String Groupe, String pass) {
            this.Nom = new SimpleStringProperty(Nom);
            this.Prenom = new SimpleStringProperty(Prenom);
            this.Groupe = new SimpleStringProperty(Groupe);
            this.Pass = new SimpleStringProperty(pass);
        }

        public StringProperty NomProperty() {
            return Nom;
        }

        public StringProperty PrenomProperty() {
            return Prenom;
        }

        public StringProperty GroupeProperty() {
            return Groupe;
        }

        public StringProperty PassProperty() {
            return Pass;
        }

    }

    @FXML
    MenuItem nvcmpte;

    @FXML
    public void Creer() throws IOException {

        NewAccount newAccount = new NewAccount();
        newAccount.setModalDimmer(ModalDimmer);
        showModalMessage(newAccount);
    }
    @FXML
    MenuItem supcmpte;

    @FXML
    public void deleteAccount() throws IOException {

        DelAccount delAccount = new DelAccount();
        delAccount.setModalDimmer(ModalDimmer);
        showModalMessage(delAccount);
    }

    public void showModalMessage(Node message) {
        ModalDimmer.getChildren().add(message);
        ModalDimmer.setOpacity(0);
        ModalDimmer.setVisible(true);
        ModalDimmer.setCache(true);
        TimelineBuilder.create().keyFrames(
                new KeyFrame(Duration.seconds(1), (ActionEvent t) -> {
                    ModalDimmer.setCache(false);
                },
                        new KeyValue(ModalDimmer.opacityProperty(), 1, Interpolator.EASE_BOTH)
                )).build().play();
    }

    public void hideModalMessage() {
        ModalDimmer.setCache(true);
        TimelineBuilder.create().keyFrames(
                new KeyFrame(Duration.seconds(1), (ActionEvent t) -> {
                    ModalDimmer.setCache(false);
                    ModalDimmer.setVisible(false);
                    ModalDimmer.getChildren().clear();
                },
                        new KeyValue(ModalDimmer.opacityProperty(), 0, Interpolator.EASE_BOTH)
                )).build().play();
    }

    public void logout() throws IOException {
        
        
        anim =new Animation_liste();
        borderPane.setBottom(new timeline(60));
        Stage stage = new Stage();
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent myPane = (Parent) myLoader.load();

        loginController controller = (loginController) myLoader.getController();
        controller.setPrevStage(stage);

        Scene scene = new Scene(myPane);
        prevStage.close();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.show();
    }

    public void aider() {
        {
            String fileName = "KiWiHelp.chm";
            String[] commands = {"cmd", "/c", fileName};
            try {
                Runtime.getRuntime().exec(commands);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    

    

     @FXML
    public void creerCarre() {

        Polygone poly = AjouterR(name1 + a, (int) notreGrille.xToPixel(4), 4);
        a++;
        notreGrille.zoneDessin.getChildren().add(poly);
        poly_select = poly;
        // objet_select = poly_select;
        aaa(poly.get_nbr_cotes(), poly_select.getFill());
        pole.add(poly);
        size++;
        n++;
        ///////////////////
        drag_poly(notreGrille, poly, true);

    }

    @FXML
    public void creerHexagone() {
        Polygone poly = AjouterR(name1 + a, (int) notreGrille.xToPixel(3), 6);
        a++;
        notreGrille.zoneDessin.getChildren().add(poly);
        poly_select = poly;

        aaa(poly.get_nbr_cotes(), poly_select.getFill());
        pole.add(poly);

        size++;
        n++;
        drag_poly(notreGrille, poly, true);

    }

    @FXML
    public void creerTriangle() {
        Polygone poly = AjouterR(name1 + a, (int) notreGrille.xToPixel(4), 3);
        a++;
        notreGrille.zoneDessin.getChildren().add(poly);
        poly_select = poly;

        aaa(poly.get_nbr_cotes(), poly_select.getFill());
        pole.add(poly);

        size++;
        n++;
        drag_poly(notreGrille, poly, true);
    }

    @FXML
    public void creerCercle() {
        Polygone poly = AjouterR(name1 + a, (int) notreGrille.xToPixel(4), 1500);
        a++;
        notreGrille.zoneDessin.getChildren().add(poly);
        poly_select = poly;

        aaa(poly.get_nbr_cotes(), poly_select.getFill());
        pole.add(poly);

        size++;
        n++;
        drag_poly(notreGrille, poly, true);
    }

//// Supprimer tout les elements de la grille 
    @FXML
    public void supprimertt() {
        notreGrille.zoneDessin.getChildren().clear();
        anim.Anim.removeAll(anim.Anim);
        anim.AnimG.removeAll(anim.AnimG);
        groupes.removeAll(groupes);
        pole.removeAll(pole);
    }

    public void SaveAS(ActionEvent e) {
        if (student && ele != null) {
            Animation_liste m = new Animation_liste();
            m.Anim = anim.Anim;
            m.AnimG = anim.AnimG;
            boolean success = f.SaveAs_eleve(m, chemin, ele);

        } else if (teacher) {
            Animation_liste m = new Animation_liste();
            m.Anim = anim.Anim;
            m.AnimG = anim.AnimG;
            boolean success = f.SaveAs_prof(m, chemin, motDePasseStandard);
        }

    }
 


// 
 // This method will be used to create a new Folder  containing the animations
                      // of the new session...
public int sea = 0;
    @FXML
    MenuItem new_s;
    // Créer un sénance 
 
 public  void creer_seances(ActionEvent val)
  {
        if(student) new_s.setDisable(true);
        Seances newSeance = new Seances();
        newSeance.setModalDimmer(ModalDimmer);
        showModalMessage(newSeance);
  }
 public  void About(ActionEvent val) throws IOException
  {
       /// A propos
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("About.fxml"));
        Parent myPane = (Parent) myLoader.load();
        showModalMessage(myPane);
  }
 
 
 
 @FXML
 
 public void nouvelle() throws IOException
 {
     
                FXMLLoader myLoader = new FXMLLoader(getClass().getResource("tabs.fxml"));
                Parent myPane = (Parent) myLoader.load();
                myPane.setTranslateX(20);
                InterfaceController controller = (InterfaceController) myLoader.getController();
                if(student)
                {
                    controller.userLabel.setText(ele.getNom()+".".concat(ele.getPrenom()));
                }
                else
                {
                    controller.userLabel.setText("Administrateur");
                }
                addNewTab(ouv, "Nouvelle séance", myLoader.getRoot(), true);
               
                ouv.getSelectionModel().selectFirst();    
 }
 
}
