package Grille;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;

public class Repere extends ScatterChart { // Repére de la grille

    private double mouseX = 0;//cordonnée de la sourie dans le repère
    private double mouseY = 0;

    /*-----------------------------------------------*/
    /**
     * Declaration Constructeur
     */
    public Repere(NumberAxis xAxis, NumberAxis yAxis) {
        super(xAxis, yAxis);

    }

    /*-----------------------------------------------*/

    /**
     * Declaration des Méthodes
     */
    /**
     * @return the mouseX
     */
    public double getMouseX() {
        return mouseX;
    }

    /**
     * @param mouseX the mouseX to set
     */
    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    /**
     * @return the mouseY
     */
    public double getMouseY() {
        return mouseY;
    }

    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }

}
