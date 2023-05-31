package Modele.Fabrique;

import Modele.Parcelle;

public abstract class Fabrique<T> {

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
}
