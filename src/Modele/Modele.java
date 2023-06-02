package Modele;
import java.util.ArrayList;
import java.util.Observable;
import java.io.Serializable;
import java.lang.Runnable;
import Modele.Fabrique.Fabrique;
import Modele.Fabrique.FabriqueLegume;
import Modele.Fabrique.FabriqueObjet;
import Modele.Legume.Legume;
import Modele.Mouette.GestionaireMouette;
import Modele.Mouette.Mouette;
import Modele.Objet.Objet;

public class Modele extends Observable implements Runnable, Serializable {

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
    private final Parcelle [][] tabParcelles = new Parcelle[largeur][hauteur];

    /**
     * Argent du joueur
     */
    private int argent = 10;

    /**
     * Vitesse du jeu
     */
    private int vitesse = 1;

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


    /**
     * Modifie l'instance du modèle
     * @param newInstance Nouvelle instance
     */
    public static void setInstance(Modele newInstance) {
        instance = newInstance;
    }

    @Override
    public void run() {
        for(int i = 0; i < vitesse; i++){
            GestionaireMouette.getInstance().run();

            for (int x = 0; x < largeur; x++){
                for (int y = 0; y < hauteur; y++){
                    tabParcelles[x][y].run();
                }
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
     * Place un legume ou un objet a une parcelle
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @param fabrique permettant de poser l'ojet ou le legume
     */
    public void poser(int x, int y, Fabrique fabrique) {
        if(!fabrique.peutEtrePose(getParcelle(x, y))){
            return;
        }

        if(fabrique.getPrix() > argent){
            return;
        }

        argent -= fabrique.getPrix();


        if(fabrique instanceof  FabriqueLegume fabriqueLegume) {
            tabParcelles[x][y].setLegume(fabriqueLegume.creer(getParcelle(x, y)));

        }
        else if (fabrique instanceof  FabriqueObjet fabriqueObjet){
            tabParcelles[x][y].setObjet(fabriqueObjet.creer(getParcelle(x, y)));
        }

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
        // Si les coordonnées sont hors du potager
        if(x >= largeur || y >= hauteur || x < 0 || y < 0)
            return null;
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

    /**
     * Retourne l'humidité de la parcelle aux coordonnées x et y
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @return humidité
     */
    public int getHumidite(int x, int y) {
        return tabParcelles[x][y].getHumidite();
    }

    /**
     * Set la vitesse du jeu
     * @param vitesse Vitesse du jeu
     */
    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    /**
     * Retourne la vitesse du jeu
     * @return vitesse
     */
    public int getVitesse() {
        return vitesse;
    }

    /**
     * Retourne si la parcelle aux coordonnées x et y a de l'herbe
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @return boolean
     */
    public boolean aDeLHerbe(int x, int y) {
        return tabParcelles[x][y].aDeLHerbe();
    }

    /**
     * Retourne le type d'herbe de la parcelle aux coordonnées x et y
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @return type d'herbe
     */
    public Parcelle.TypeHerbe getHerbe(int x, int y) {
        return tabParcelles[x][y].getHerbe();
    }

    /**
     * Retourne si la parcelle aux coordonnées x et y a un rocher
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @return boolean
     */
    public boolean aUnRocher(int x, int y) {
        return tabParcelles[x][y].aUnRocher();
    }

    /**
     * Mine le rocher aux coordonnées x et y si possible et si le joueur a assez d'argent
     * @param indiceX Coordonnée x
     * @param indiceY Coordonnée y
     */
    public void miner(int indiceX, int indiceY) {
        if(!tabParcelles[indiceX][indiceY].aUnRocher())return;

        if(5 > argent){
            return;
        }

        argent -= 5;
        tabParcelles[indiceX][indiceY].setRocher(false);

        setChanged();
        notifyObservers();
    }

    /**
     * Labourer la parcelle aux coordonnées x et y si possible et si le joueur a assez d'argent
     * @param indiceX Coordonnée x
     * @param indiceY Coordonnée y
     */
    public void labourer(int indiceX, int indiceY) {
        if(!tabParcelles[indiceX][indiceY].aDeLHerbe())return;

        if(5 > argent){
            return;
        }

        argent -= 5;

        tabParcelles[indiceX][indiceY].setHerbe(Parcelle.TypeHerbe.NON_HERBE);
        setChanged();
        notifyObservers();
    }

    /**
     * Retourne le taux de brullure du legume aux coordonnées x et y
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @return taux de brulure
     */
    public float getTauxBrulure(int x, int y) {
        return tabParcelles[x][y].getLegume().getTauxBrulure();
    }

    /**
     * Retourne le taux de maladie du legume aux coordonnées x et y
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @return taux de maladie
     */
    public float getTauxMaladie(int x, int y) {
        return tabParcelles[x][y].getLegume().getTauxPourriture();
    }

    /**
     * Retourne les mouettes
     * @return mouettes
     */
    public ArrayList<Mouette> getMouettes() {
        return GestionaireMouette.getInstance().getMouettes();
    }

    /**
     * Retourne l'objet aux coordonnées x et y
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @return objet
     */
    public Objet getObjet(int x, int y) {
        return tabParcelles[x][y].getObjet();
    }

    /**
     * Enleve l'objet aux coordonnées x et y
     * @param indiceX Coordonnée x
     * @param indiceY Coordonnée y
     */
    public void enlever(int indiceX, int indiceY) {
        tabParcelles[indiceX][indiceY].setObjet(null);
        tabParcelles[indiceX][indiceY].setLegume(null);
    }

    /**
     * Retourne si la parcelle aux coordonnées x et y a un objet
     * @param indiceX Coordonnée x
     * @param indiceY Coordonnée y
     * @return boolean
     */
    public int getNbEpouvantail(int indiceX, int indiceY) {
        return tabParcelles[indiceX][indiceY].getNbEpouvantail();
    }
}




