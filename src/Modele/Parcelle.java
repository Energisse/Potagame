package Modele;

import Modele.Legume.Legume;

public class Parcelle implements Runnable {

    /**
     * Coordonnées X de la parcelle
     */
    private int x;
    /**
     * Coordonnées Y de la parcelle
     */
    private int y;

    /**
     * Legume contenu dans la parcelle
     */
    private Legume legume;

    /**
     * Constructeur de la parcelle
     * @param x Coordonnées X
     * @param y Coordonnées Y
     */
    public Parcelle(int x, int y){
        this.x = x;
        this.y = y;
        this.legume = null;
    }

    /**
     * Plante un légume dans la parcelle
     * @param legume
     */
    public void setLegume(Legume legume){
        this.legume = legume;
    }

    /**
     * Retourne vrai si la parcelle est libre
     * @return boolean
     */
    public boolean isFree(){
        return this.legume == null;
    }

    /**
     * Retourne le légume contenu dans la parcelle
     * @return Legume
     */
    public Legume getLegume() {
        return this.legume;
    }

    @Override
    public void run() {
        //Si la parcelle contient un légume, on le fait pousser
        if (this.legume != null){
            this.legume.pousser();
        }
    }

    /**
     * Retourne vrai si le légume contenu dans la parcelle est récoltable
     * @return
     */
    public boolean estRecoltable() {
        return this.legume != null && this.legume.estRecoltable();
    }

}
