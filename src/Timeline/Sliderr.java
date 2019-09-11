/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Timeline;

import java.io.Serializable;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author aziz
 */
public class Sliderr extends Slider implements Serializable{/// Slider du timeline

    
    private int TailleSlider , curValue=0;

    public int getCurValue() {
        return curValue;
    }
    public int valeur;
    
    public Sliderr(int TailleSlider) {
        this.TailleSlider = TailleSlider;
        super.setMin(0);
        super.setBlockIncrement(1);
        super.setMax(TailleSlider);
        super.setValue(0);
        super.setMinWidth(TailleSlider * 60);
        super.setMajorTickUnit(1);
        super.setMinorTickCount(0);
        super.setShowTickLabels(true);
        super.setShowTickMarks(true);
        
    }

    
    public void DiscretSlider(Sliderr slider) // Methode pour rendre le slider discret //
    { 
       slider.setOnMouseMoved(k->{
           
           if ((int)Math.round(slider.getValue()) != curValue)
           {curValue = (int)Math.round(slider.getValue());
           }
       });
       
       
       slider.setOnMouseReleased(new EventHandler<MouseEvent>(){
           
          @Override
          public void handle(MouseEvent t) {
              slider.setValue(Math.round(slider.getValue()));
              
           /*   for(Node n : GP.getChildren())
              {
                  if((n.getId().compareTo("radio"+2)) == 0)
                  n.setVisible(false);
  
              }*/
              
          }
       });
       
    }
    int Getsize()
    {
        return this.TailleSlider;
    } 
}
