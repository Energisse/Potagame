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

    /**
     * Pourcentage de mangé le legume
     */
    private float pourcentageLegumeMange = -1;

    /**
     * Vitesse de mange de la mouette
     */
    private static final float VITESSE_MANGE = 0.01F;

    /**
     * Chance de mangé un legume quand la mouette passe au dessus une chance sur 10
     */
    private static final float CHANCE_MANGER_LEGUME = 0.1F;

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

        //si la mouette est en train de mangé un legume
        if(pourcentageLegumeMange >= 0){
            //augmente le pourcentage de mangé le legume
            pourcentageLegumeMange += VITESSE_MANGE;
            //si la mouette a mangé le legume
            if(pourcentageLegumeMange >= 1){
                pourcentageLegumeMange = -1;
                //arache le legume
                int x = (int) this.x;
                int y = (int) this.y;
                Modele.getInstance().aracher(x,y);
            }
            return;
        }

        int dernierX = (int) this.x;
        this.x+=0.1;

        //detecte les changements de case
        if(dernierX != (int) this.x && Math.random() < CHANCE_MANGER_LEGUME){
            int x = (int) this.x;
            int y = (int) this.y;

            if(Modele.getInstance().getLegume(x,y) != null && Modele.getInstance().getNbEpouvantail(x,y) == 0){
                //lance le processus de mangé un legume
                pourcentageLegumeMange = 0;
            }
        }

        if(this.x > Modele.getInstance().getLargeur()+0.5f)
            finit = true;
    }

    /**
     * Retourne la position X de la mouette en pourcentage de case (0.5 = 50% d'une case) (2,5 = 2 cases + 0.5% d'une case)
     * @return float position X
     */
    public float getX(){
        return this.x;
    }

    /**
     * Retourne la position Y de la mouette en pourcentage de case (0.5 = 50% d'une case) (2,5 = 2 cases + 0.5% d'une case)
     * @return float position Y
     */
    public float getY(){
        return this.y;
    }

    /**
     * Retourne si la mouette a finit son trajet
     * @return boolean finit
     */
    public boolean aFinit(){
        return this.finit;
    }
}
