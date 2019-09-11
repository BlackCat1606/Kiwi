package Timeline;

import Groupe.Groupe;
import Structure.Forme;
import Timeline.AnchorPanes;
import Polygone.Polygone;
import static app.InterfaceController.anim;
import static app.InterfaceController.group_select;
import static app.InterfaceController.groupes;
import static app.InterfaceController.objet_select;
import static app.InterfaceController.poly_select;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author aziz
 */
public class timeline extends ScrollPane implements Serializable {
    //// Barre de tems visualiser sur l'interface
    
    private Sliderr slid;
    public int cpt;
    int temps ;
    private ScrollBar sc ;
    public AnchorPanes field;
    private boolean remplie;
    public int remp;
    public boolean isRemplie() {
      if(remp==0) remplie = false;
      else remplie = true;
        return remplie;
    }

    public void setRemplie(boolean remplie) {
        this.remplie = remplie;
    }
    
    public ArrayList<Integer> tab_supp = new ArrayList<Integer>();
    public GridPanes GP;
    final int taille;
    
    protected String [] radios;
   // private Timeline timeline;

   /* public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }*/

    public String[] getRadios() {
        return radios;
    }
    
    public timeline(int TailleSlider) {
        
        taille = TailleSlider+1;
        //timeline = new Timeline();
        this.slid = new Sliderr(TailleSlider);
        this.slid.setTranslateY(5);
        remp=0;
        this.slid.DiscretSlider(this.slid);
        radios = new String[taille];
        super.setPrefHeight(75);
        GP = new GridPanes();
        GP.setHgap(46.59);
        Filler(GP,TailleSlider);
        GP.setTranslateX(-1);
        GP.setTranslateY(32);
        field = new AnchorPanes();
        //field.setPrefWidth(100);
        //field.setPrefHeight(35);
        field.getChildren().addAll(slid,GP);
        super.setContent(field);
        
        
        slid.setOnMouseReleased(new EventHandler<MouseEvent>(){
           
         
          public void handle(MouseEvent t) {
              slid.setValue(Math.round(slid.getValue()));
              
              for(Node n : GP.getChildren())
              {
                  if(((n.getId().compareTo("radio"+slid.getCurValue())) == 0) && slid.getCurValue() != slid.getValue())
                  {n.setVisible(true);
                  }
  
              }
              
          }
        });
        
    }; 
    
    private void Filler(GridPane GP , int Taille)
    {
        int i =0;
        tab_supp = new ArrayList<Integer>();
        for (i=0 ; i<=Taille ; i++)
        {
            RadioButton r = new RadioButton();
            r.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent m) {
                    if (r.isSelected()==false)  {
                        String temps = r.getId().substring(5,r.getId().length()) ;
                        int tem = Integer.valueOf(temps);
                        tab_supp.add(tem);
                        
                        cpt++;
                        r.setSelected(true);
                        r.setVisible(false);
                        System.out.println("deleted");
                        System.out.println("temps  : "+tem);
                   if(objet_select instanceof Polygone)
                   {
                        if(anim.Anim.size()!=0)
                        {
                            int pos = 0;
                            String id = poly_select.getId();
                            int j=0;
                            int k = 0;
                            Iterator <Forme> it = anim.Anim.iterator();
                            while (it.hasNext()) 
                            {
                                 Forme f = it.next();
                                 if(id!= null && id.equals(f.getId()))  k = pos;
                                 pos++;
                            }
                            if(anim.Anim.get(k).ll.size()!=0)
                            {
                            while(j<anim.Anim.get(k).ll.size())
                            {
                                if(anim.Anim.get(k).ll.get(j).getDuree() == tem ) pos = j;
                                j++;
                            }
                            System.out.println("u removed here " + tem);
                            anim.Anim.get(k).ll.remove(pos);
                            remp = remp-1;
                            int e=0;
                            while (e < anim.Anim.get(k).ll.size())
                            {
                                System.out.println("temps medifié "+ anim.Anim.get(k).ll.get(e).getDuree()); 
                                
                                e++;
                            }
                           }
                   }
                        
                   }
                   else
                   {
                       if(groupes.size()!=0)
                        {
                            int pos = 0;
                            String id = group_select.getIdentif();
                            int j=0;
                            int k = 0;
                            int i=0;
                            Iterator <Groupe> it = groupes.iterator();
                            while (i<groupes.size()) 
                            {
                                 
                                 if(id.equals(groupes.get(i).getIdentif()))  k = i;
                                 i++;
                            }
                           
                            if(groupes.get(k).ll.size()!=0)
                                
                            {
                            while(j<groupes.get(k).ll.size())
                            {
                                if(groupes.get(k).ll.get(j).getDuree() == tem ) pos = j;
                                j++;
                            }
                            
                            
                            System.out.println("u removed here " + tem);
                            groupes.get(k).ll.remove(pos);
                            remp = remp-1;
                            int e=0;
                            while (e < groupes.get(k).ll.size())
                            {
                                System.out.println("temps medifié "+ groupes.get(k).ll.get(e).getDuree()); 
                                System.out.println("x medifié "+ groupes.get(k).ll.get(e).getX()); 
                                System.out.println("y medifié "+ groupes.get(k).ll.get(e).getY()); 
                                System.out.println("angle medifié "+ groupes.get(k).ll.get(e).getAngle()); 
                                System.out.println("echelle medifié "+ groupes.get(k).ll.get(e).getEchelle()); 
                                e++;
                            }
                        }
                        }
                   
                   }
                    }
                }
            });
            r.setId("radio"+i);
            r.setSelected(true);
            r.setTranslateX(i*-8.255);
            r.setVisible(false);
            r.setTranslateY(10);
            GP.add(r, i , 0);
            radios[i] = ("radio"+i);
            remp++;
        }
        
    }
    
    private int save_temps=0;
    private int time=0;
    private int temps_preced=0;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTemps_preced() {
        return temps_preced;
    }

    public void setTemps_preced(int temps_preced) {
        this.temps_preced = temps_preced;
    }
    

    public int getSave_temps() {
        return save_temps;
    }

    public void setSave_temps(int save_temps) {
        this.save_temps = save_temps;
    }
    
  
    public Sliderr getSlid() {
        return slid;
    }
    
    }
    
