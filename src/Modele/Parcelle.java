package Modele;

import Modele.Legume.Legume;
import Modele.Meteo.Meteo;

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
     * Taux d'humidité de la parcelle
     */
    private int humidite;


    /**
     * Est de l'herbe
     */
    private boolean herbe;

    /**
     * A un rocher
     */
    private boolean rocher;

    /**
     * Constructeur de la parcelle
     * @param x Coordonnées X
     * @param y Coordonnées Y
     */
    public Parcelle(int x, int y){
        this.x = x;
        this.y = y;
        this.legume = null;
    
        // Initialisation de l'humidité
        this.humidite = 0;

        // chance sur 2 d'avoir de l'herbe
        this.herbe = Math.random() < 0.5;

        // chance sur 2 d'avoir un rocher si de l'herbe
        if (this.herbe)
            this.rocher = Math.random() < 0.5;
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

    /**
     * Fonction permettant l'execution des différent process sur une parcelle
     * Le choix de l'humidité d'une parcelle est effectuer de manière aléatoire en rapport à celle du jour
     */
    @Override
    public void run() {
        humidite+=(int) (Math.random() * Meteo.getInstance().getMeteodata(Modele.getInstance().getTemps()).getHumiditeDuJour());
        if (this.humidite > 100) this.humidite = 100;

        //Si la parcelle contient un légume, on le fait pousser
        if (this.legume != null){
            this.legume.pousser(this);
        }
        else{
            //une chance sur 100_000 de faire d'avoir de l'herbe
            if (Math.random() < 0.00001){
                this.herbe = true;
            }
        }
    }

    /**
     * Retourne vrai si le légume contenu dans la parcelle est récoltable
     * @return
     */
    public boolean estRecoltable() {
        return this.legume != null && this.legume.estRecoltable();
    }

    /**
     * Retourne le taux d'humidité de la parcelle
     * @return int
     */
    public int getHumidite() {
        return this.humidite;
    }

    /**
     * Modifie le taux d'humidité de la parcelle
     * @param i
     */
    public void setHumidite(int i) {
        this.humidite = i;
    }

    /**
     * Retourne vrai si la parcelle est de l'herbe
     * @return boolean
     */
    public boolean aDeLHerbe() {
        return this.herbe;
    }

    /**
     * Retourne vrai si la parcelle est un rocher
     * @return boolean
     */
    public boolean aUnRocher() {
        return this.rocher;
    }

    /**
     * Modifie le rocher de la parcelle
     * @param b
     */
    public void setRocher(boolean b) {
        this.rocher = b;
    }

    /**
     * Modifie l'herbe de la parcelle
     * @param b
     */
    public void setHerbe(boolean b) {
        this.herbe = b;
    }
}
