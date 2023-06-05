package Modele.Legume;

import java.io.Serializable;

import Modele.Parcelle;

public class Legume implements Serializable{
    /**
     * Croissance du légume
     */
    protected float croissance = 0;

    /**
     * Taux de croissance du légume
     * Si les conditions sont mauvaises, le taux de legume reduit jusqu'a 0
     * Si les conditions sont bonnes, le taux de legume augmente jusqu'a 2
     */
    protected float tauxCroissance = 1;

    /**
     * Vie du légume
     */
    protected int vie = 100;

    /**
     * Prix du légume
     */
    protected int prix;

    /**
     * Nom
     */
    protected String nom;

    //Constructeur
    public Legume(String nom, int prix){
        this.nom = nom;
        this.prix = prix;
    }

    /**
     * Retourne le prix du légume
     * @return int
     */
    public int getPrixRevente(){
        return prix;
    }

    /**
     * Retourne le nom du légume
     * @return String
     */
    public String getNom(){
        return nom;
    }

    /**
     * Retourne la croissance du légume
     * @return pourcentage de croissance
     */
    public float getCroissance() {
        return croissance;
    }

    /**
     * Taux de brullure du légume de 0 a 1
     */
    private float tauxBrulure = 0;

    /**
     *  tauxMaladie du légume de 0 a 1
     */
    private float tauxPourriture = 0;

    /**
     * Fait pousser le légume
     */
    public void pousser(Parcelle parcelle){
        if(!aFinitCroissance()){
            if(parcelle.getHumidite() > 2){
                croissance += tauxCroissance;
                parcelle.setHumidite(parcelle.getHumidite() - 2);
            }
        }
        else{
            if(parcelle.getHumidite() > 1){
                parcelle.setHumidite(parcelle.getHumidite() - 1);
            }
            //a augmenter en fonction de la météo
            tauxPourriture += 0.001;
            if(tauxPourriture > 1) tauxPourriture = 1;

        }
    }

    /**
     * Retourne si le légume a finit de pousser
     * @return boolean
     */
    private boolean aFinitCroissance(){
        return croissance == 100;
    }

    /**
     * Retourne si le légume est mort
     * @return boolean
     */
    public boolean estMort(){
        return croissance == 0;
    }

    /**
     * Retourne si le légume est récoltable
     * @return boolean
     */
    public boolean estRecoltable(){
        return aFinitCroissance() && vie > 0;
    }

    /**
     * Retourne le taux de brullure du légume
     * @return float
     */
    public float getTauxBrulure() {
        return tauxBrulure;
    }

    /**
     * Retourne le taux de maladie du légume
     * @return float
     */
    public float getTauxPourriture() {
        return tauxPourriture;
    }
}
