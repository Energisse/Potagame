package Modele;
import java.util.Observable;
import java.lang.Runnable;

import Modele.Fabrique.Fabrique;
import Modele.Legume.Legume;

public class Modele extends Observable implements Runnable {

    /**
     * Largeur du potager
     */
    public int largeur =10;
    
    /**
     * Hauteur du potager
     */
    public int hauteur =10;

    /**
     * Liste des parcelles
     */
    private Parcelle [][] tabParcelles = new Parcelle[largeur][hauteur];

    /**
     * Argent du joueur
     */
    private int argent = 10;

    /**
     * Instance du modèle
     */
    private static Modele instance = null;

    /**
     * Constructeur du modèle
     */
    private Modele() {
        super();

        // Initialisation des parcelles
        for (int x = 0; x < largeur; x++){
            for (int y = 0; y < hauteur; y++){
                tabParcelles[x][y] = new Parcelle(x,y);
            }
        }
    }

    /**
     * Retourne l'instance du modèle
     * @return instance
     */
    public static Modele getInstance() {
        if (instance == null) {
            instance = new Modele();
        }
        return instance;
    }

    @Override
    public void run() {
        for (int x = 0; x < largeur; x++){
            for (int y = 0; y < hauteur; y++){
                tabParcelles[x][y].run();
            }
        }
        setChanged();
        notifyObservers();
    }
    
    /**
     * Retourne la parcelle aux coordonnées x et y
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @return Parcelle
     */
    public Parcelle getParcelle(int x, int y){
        return tabParcelles[x][y];
    }

    /**
     * Arache le légume aux coordonnées x et y
     * @param x Coordonnée x
     * @param y Coordonnée y
     */
    public void aracher(int x, int y) {
        tabParcelles[x][y].setLegume(null);
        setChanged();
        notifyObservers();
    }


    /**
     * Plante un légume aux coordonnées x et y
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @param Fabrique permettant de planter le légume
     */
    public void planter(int x, int y, Fabrique fabrique) {
        if(!fabrique.peutEtrePlante(getParcelle(x, y))){
            return;
        }

        if(fabrique.getPrix() > argent){
            return;
        }

        argent -= fabrique.getPrix();

        tabParcelles[x][y].setLegume(fabrique.creerLegume());
        setChanged();
        notifyObservers();
    }

    /**
     * Retourne le légume aux coordonnées x et y
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @return Legume
     */
    public Legume getLegume(int x, int y) {
        return tabParcelles[x][y].getLegume();
    }

    /**
     * Retourne la largeur du potager
     * @return largeur
     */
    public int getLargeur() {
        return largeur;
    }

    /**
     * Retourne la hauteur du potager
     * @return hauteur
     */
    public int getHauteur() {
        return hauteur;
    }

    /**
     * Retourne si la parcelle aux coordonnées x et y est prete à être récoltée
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @return boolean
     */
    public boolean estRecoltable(int x, int y) {
        return tabParcelles[x][y].estRecoltable();
    }

    /**
     * Recolte le légume aux coordonnées x et y
     * @param x Coordonnée x
     * @param y Coordonnée y
     */
    public void recolter(int x, int y) {
        if(!tabParcelles[x][y].estRecoltable())return;

        argent += tabParcelles[x][y].getLegume().getPrixRevente();

        tabParcelles[x][y].setLegume(null);

        setChanged();
        notifyObservers();
    }
    
    /**
     * Retourne l'argent du joueur
     * @return argent
     */
    public int getArgent() {
        return argent;
    }
}




