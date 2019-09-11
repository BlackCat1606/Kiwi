package Structure;

import java.io.Serializable;
import java.util.Comparator;

public class Cle implements Serializable, Comparator<Integer>, Comparable {// Structure pour enrigstrer les valeurs clés pour les objets animés sur la grille

    private double x;
    private double y;
    private double angle;
    private int duree;
    private float echelle;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public float getEchelle() {
        return echelle;
    }

    public void setEchelle(float echelle) {
        this.echelle = echelle;
    }

    @Override
    public int compareTo(Object o) {
        int m = ((Cle) o).getDuree();
        int l = 4;
        if (this.getDuree() > m) {
            l = 1;
        }
        if (this.getDuree() < m) {
            l = -1;
        }
        if (this.getDuree() == m) {
            l = 0;
        }
        return l;
    }

    @Override
    public boolean equals(Object f) {
        Cle m = (Cle) f;
        return (this.getDuree() == m.getDuree());
    }

    @Override
    public int compare(Integer o1, Integer o2) {
        return o1.compareTo(o2);
    }

}
