package Modele;

import java.io.Serializable;

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
     * Objet contenu dans la parcelle
     */
    private Objet objet;

    /**
     * Nombre d'epouvantails
     */
    private int nbEpouvantail;

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

        // chance sur 2 d'avoir de l'herbe
        this.herbe = Math.random() < 0.5;

        // chance sur 2 d'avoir un rocher si de l'herbe
        if (this.herbe)
            this.rocher = Math.random() < 0.5;
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

    @Override
    public void run() {
        //mise a jour de l'humidité
        this.humidite++;
        if (this.humidite > 100) this.humidite = 100;

        //Si la parcelle contient un légume, on le fait pousser
        if (this.legume != null) {
            this.legume.pousser(this);
        } else {
            //une chance sur 100_000 de faire d'avoir de l'herbe
            if (Math.random() < 0.00001) {
                this.herbe = true;
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
    public int getHumidite() {
        return this.humidite;
    }

    /**
     * Modifie le taux d'humidité de la parcelle
     * @param i Nouveau taux d'humidité
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

}
