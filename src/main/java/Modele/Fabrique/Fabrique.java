package Modele.Fabrique;

import Modele.Parcelle;

import java.util.ArrayList;

public abstract class Fabrique<T> {

    /**
     * Liste des instances de fabrique
     */
    static final ArrayList<Fabrique> instances = new ArrayList<>();

    /**
     * Raccourci du legume
     */
    private final String  raccourci;

    /**
     * Image du legume
     */
    private final String image;


    /**
     * Nom du legume
     */
    private final String nom;

    /**
     * Retourne la liste des instances de fabrique de legumes
     * @return instances
     */
    public static ArrayList<Fabrique> getInstances(){
        return instances;
    }

    /**
     * Constructeur
     */
    public Fabrique(String nom, String image, String raccourci){
        instances.add(this);
        this.nom = nom;
        this.image = image;
        this.raccourci = raccourci;
    }

    /**
     * Retourne vrai si l'objet peut être posé sur la parcelle
     * @param parcelle Parcelle sur laquelle l'objet est posé
     * @return boolean
     */
    public abstract boolean peutEtrePose(Parcelle parcelle);

    /**
     * Crée l'objet sur la parcelle
     * @param parcelle Parcelle sur laquelle l'objet est créé
     * @return T
     */
    public abstract T creer(Parcelle parcelle);

    /**
     * Retourne le prix de l'objet
     * @return int
     */
    public abstract int getPrix();

    /**
     * Retourne le nom du legume
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne le racourci clavier du legume
     * @return raccourci
     */
    public String getRaccourci() {
        return raccourci;
    }

    /**
     * Retourne l'image du legume
     * @return image
     */
    public String getImage() {
        return image;
    }
}
