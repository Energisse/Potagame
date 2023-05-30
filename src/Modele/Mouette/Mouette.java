package Modele.Mouette;

import Modele.Modele;

import java.io.Serializable;

public class Mouette  implements Serializable {

    /**
     * Position de la mouette en pourcentage de case (0.5 = 50% d'une case) (2,5 = 2 cases + 0.5% d'une case)
     */
    private float x;

    /**
     * Position de la mouette en pourcentage de case (0.5 = 50% d'une case) (2,5 = 2 cases + 0.5% d'une case)
     */
    private float y;

    /**
     * Si la mouette a finit son trajet
     */
    private boolean finit = false;

    /*
     * Constructeur de la mouette
     */
    public Mouette(float x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * Déplace la mouette
     */
    public void deplacer(){
        if(finit)return;
        this.x+=0.1;
        if(this.x > Modele.getInstance().getLargeur()+0.5f)
            finit = true;
    }

    /**
     * Retourne la position X de la mouette en pourcentage de case (0.5 = 50% d'une case) (2,5 = 2 cases + 0.5% d'une case)
     * @return
     */
    public float getX(){
        return this.x;
    }

    /**
     * Retourne la position Y de la mouette en pourcentage de case (0.5 = 50% d'une case) (2,5 = 2 cases + 0.5% d'une case)
     * @return
     */
    public float getY(){
        return this.y;
    }

    /**
     * Retourne si la mouette a finit son trajet
     * @return
     */
    public boolean aFinit(){
        return this.finit;
    }
}
