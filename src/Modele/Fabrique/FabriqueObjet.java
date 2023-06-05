package Modele.Fabrique;

import Modele.Objet.Objet;
import Modele.Parcelle;

public abstract class FabriqueObjet extends  Fabrique<Objet> {

    /**
     * Constructeur
     */
    public FabriqueObjet(String nom, String image, String raccourci) {
        super(nom, image, raccourci);
    }

    /**
     * Retourne vrai si l'objet peut être posé sur la parcelle
     * @param parcelle Parcelle sur laquelle l'objet est posé
     * @return boolean
     */
    public boolean peutEtrePose(Parcelle parcelle){
        return parcelle.getLegume() == null && parcelle.getObjet() == null;
    }

    /**
     * Retourne le prix de l'objet
     * @return int
     */
    public abstract int getPrix();
}
