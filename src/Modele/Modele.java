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
import Config.Config;
import VueController.Vue;

public class Modele extends Observable implements Runnable, Serializable {

    /**
     * Largeur du potager
     */
    public int largeur;

    /**
     * Hauteur du potager
     */
    public int hauteur;

    /**
     * Liste des parcelles
     */
    private final Parcelle [][] tabParcelles;

    /**
     * Argent du joueur
     */
    private int argent;

    /**
     * Vitesse du jeu
     */
    private int vitesse = 1;

    /**
     * Mouettes
     */
    private final  GestionaireMouette gestionaireMouette = new GestionaireMouette();

    /**
     * Variable décomptant le temps passé à réactualiser les parcelles lié à la valeur du slider de vitesse de pousse
     * Elle est ensuite utilisée pour permettre la sélection d'un meteoData à un time précis
     */
    public int temps = 0;

    /**
     * Instance du modèle
     */
    private static Modele instance = null;

    /**
     * Constructeur du modèle
     */
    private Modele() {
        super();

        // Initialisation des variables
        Config config = Config.getInstance();
        largeur = config.getLargeur();
        hauteur = config.getHauteur();
        argent = config.getArgent();

        tabParcelles = new Parcelle[largeur][hauteur];

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
            instance.addObserver(Vue.getInstance());
        }
        return instance;
    }
    /**
     * Recreer un modele
     */
    public static void newGame() {
        if(instance != null){
            instance.deleteObservers();
        }
        instance = new Modele();
        instance.addObserver(Vue.getInstance());
    }


    /**
     * Modifie l'instance du modèle
     * @param newInstance Nouvelle instance
     */
    public static void setInstance(Modele newInstance) {
        if(instance != null){
            instance.deleteObservers();
        }
        instance = newInstance;
        instance.addObserver(Vue.getInstance());
    }

    /**
     * Mise à jour des parcelles pour un temps de simulation = vitesse modifiée par le slider
     **/
    @Override
    public void run() {
        for(int i = 0; i < vitesse; i++){
            gestionaireMouette.run();

            for (int x = 0; x < largeur; x++){
                for (int y = 0; y < hauteur; y++){
                    tabParcelles[x][y].run();
                }
            }
            temps++;
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
     * Modifie l'argent du joueur
     * @param argent Nouvelle valeur de l'argent
     */
    public void setArgent(int argent) {
        this.argent = argent;
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
     * Retourne le type de fleure de la parcelle aux coordonnées x et y
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @return type de fleur
     */
    public int getFleure(int x, int y) {
        return tabParcelles[x][y].getFleure();
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

        tabParcelles[indiceX][indiceY].setHerbe(false);
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
        return gestionaireMouette.getMouettes();
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

    /**
     * Renvoie la valeur de temps s'écoulant pour le passage de la météo
     */
    public int getTemps() {
        return temps;
    }

}




