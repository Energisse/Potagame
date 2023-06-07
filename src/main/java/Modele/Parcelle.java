package Modele;

import java.io.Serializable;
import Config.Config;
import Modele.Legume.Legume;
import Modele.Objet.Objet;

public class Parcelle implements Runnable, Serializable {

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
    private float humidite;


    /**
     * A de l'herbe
     */
    private boolean herbe;

    /**
     * id du type de fleure (-1 si aucune fleure)
     */
    private int fleure;

    /**
     * A un rocher
     */
    private boolean rocher;

    /**
     * Objet contenu dans la parcelle
     */
    private Objet objet;

    /**
     * Nombre d'epouvantails
     */
    private int nbEpouvantail;

    /**
     * Effet des lac sur la parcelle
     */
    private int effetLac;

    /**
     * Constructeur de la parcelle
     * @param x Coordonnées X
     * @param y Coordonnées Y
     */
    public Parcelle(int x, int y) {
        this.x = x;
        this.y = y;
        this.legume = null;

        // Initialisation de l'humidité de 0 a 100 de manière aléatoire
        this.humidite = (int) (Math.random() * 100);

        genererHerbe();
        // chance sur 2 d'avoir un rocher si de l'herbe
        if (this.herbe && this.fleure == -1)
            this.rocher = Math.random() < Config.getInstance().getConfigParcelle().chanceRocher();
    }

    /**
     * Generer l'herbe de la parcelle
     */
    private void genererHerbe(){
       if(Math.random() < Config.getInstance().getConfigParcelle().chanceHerbe()) {
            this.herbe = true;
            this.fleure = -1;
            if(Math.random() < Config.getInstance().getConfigParcelle().chanceFleure()) {
                this.fleure = (int) (Math.random() * Config.getInstance().getConfigParcelle().imagesFleures().length);
            }
       }
    }

    /**
     * Plante un légume dans la parcelle
     * @param legume Legume à planter
     */
    public void setLegume(Legume legume) {
        this.legume = legume;
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
        humidite+=((float)effetLac)/100;
        if(75 < Modele.getInstance().getMeteo().humidite() && this.humidite < Modele.getInstance().getMeteo().humidite()){
            humidite+=Math.random()*2;
        }
        else {
            humidite-=Math.random()*1;
        }
        if(humidite < 0){
            humidite = 0;
        }
        if(humidite > 100){
            humidite = 100;
        }

        //Si la parcelle contient un légume, on le fait pousser
        if (this.legume != null) {
            this.legume.pousser(this);
        } else {
            //une chance sur 100_000 de faire d'avoir de l'herbe
            if (Math.random() < 0.00001) {
                genererHerbe();
            }
        }
    }

    /**
     * Retourne vrai si le légume contenu dans la parcelle est récoltable
     * @return boolean
     */
    public boolean estRecoltable() {
        return this.legume != null && this.legume.estRecoltable();
    }

    /**
     * Retourne le taux d'humidité de la parcelle
     * @return int
     */
    public float getHumidite() {
        return this.humidite;
    }

    /**
     * Modifie le taux d'humidité de la parcelle
     * @param i Nouveau taux d'humidité
     */
    public void setHumidite(float i) {
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
     * Retourne la fleure
     * @return int
     */
    public int getFleure() {
        return this.fleure;
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
     * @param b etat du rocher
     */
    public void setRocher(boolean b) {
        this.rocher = b;
    }

    /**
     * Modifie l'herbe de la parcelle
     * @param b etat de l'herbe
     */
    public void setHerbe(boolean b) {
        this.herbe = b;
    }

    /**
     * Retourne l'ojet contenu dans la parcelle
     * @return Objet contenu dans la parcelle
     */
    public Objet getObjet() {
        return this.objet;
    }

    /**
     * Modifie l'objet contenu dans la parcelle
     * @param objet Objet a mettre dans la parcelle
     */
    public void setObjet(Objet objet) {
        if(this.objet != null){
            this.objet.enlever();
        }
        this.objet = objet;
    }

    /**
     * Retourne les coordonnées X de la parcelle
     * @return int
     */
    public int getX() {
        return this.x;
    }

    /**
     * Retourne les coordonnées Y de la parcelle
     * @return int
     */
    public int getY() {
        return this.y;
    }

    /**
     * Retourne le nombre d'epouvantails
     * @return int
     */
    public int getNbEpouvantail() {
        	return this.nbEpouvantail;
    }

    /**
     * Modifie le nombre d'epouvantails
     * @param nbEpouvantails Nombre d'epouvantails
     */
    public void setNbEpouvantail(int nbEpouvantails) {
        this.nbEpouvantail = nbEpouvantails;
    }

    /**
     * Retourne l'effet du lac
     * @return float
     */
    public int getEffetLac() {
        return this.effetLac;
    }

    /**
     * Modifie l'effet du lac
     * @param effetLac Effet du lac
     */
    public void setEffetLac(int effetLac) {
        this.effetLac = effetLac;
    }

    /**
     * Enleve les fleures
     */
    public void enlverFleures() {
        this.fleure = -1;
    }
}
