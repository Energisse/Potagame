package Modele.Mouette;

import java.io.Serializable;
import Config.Config;
import Modele.Modele;

public class Mouette  implements Serializable {

    /**
     * Position de la mouette en pourcentage de case (0.5 = 50% d'une case) (2,5 = 2 cases + 0.5% d'une case)
     */
    private float x;

    /**
     * Position de la mouette en pourcentage de case (0.5 = 50% d'une case) (2,5 = 2 cases + 0.5% d'une case)
     */
    private final float y;

    /**
     * Si la mouette a finit son trajet
     */
    private boolean finit = false;

    /**
     * Pourcentage de mangé le legume
     */
    private float pourcentageLegumeMange = -1;

    /**
     * Vitesse de deplacement min
     */
    private static final float VITESSE_DEPLACEMENT_MIN = Config.getInstance().getConfigMouette().vitesseDeDeplacementMin();

    /**
     * Vitesse de deplacement max
     */
    private static final float VITESSE_DEPLACEMENT_MAX = Config.getInstance().getConfigMouette().vitesseDeDeplacementMax();

    /**
     * Vitesse de mange de la mouette
     */
    private static final float VITESSE_MANGE = Config.getInstance().getConfigMouette().vitesseDeMange();

    /**
     * Chance de mangé un legume quand la mouette passe au dessus une chance sur 10
     */
    private static final float CHANCE_MANGER_LEGUME = Config.getInstance().getConfigMouette().chanceDeManger();

    /**
     * Vitesse de deplacement de la mouette
     */
    private final float vitesse;

    /*
     * Constructeur de la mouette
     */
    public Mouette(float x, float y){
        this.x = x;
        this.y = y;

        this.vitesse = (float) (Math.random() * (VITESSE_DEPLACEMENT_MAX - VITESSE_DEPLACEMENT_MIN) + VITESSE_DEPLACEMENT_MIN);
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
                Modele.getInstance().enlever(x,y);
            }
            return;
        }

        int dernierX = (int) Math.floor(this.x);
        this.x+=vitesse;
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
