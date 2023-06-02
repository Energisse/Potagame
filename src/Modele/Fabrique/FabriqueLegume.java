package Modele.Fabrique;

import Modele.Legume.Legume;
import Modele.Parcelle;

public abstract class FabriqueLegume extends  Fabrique<Legume> {

    /**
     * Retourne vrai si le legume peut être posé sur la parcelle
     * @param parcelle Parcelle sur laquelle le legume est posé
     * @return boolean
     */
    public boolean peutEtrePose(Parcelle parcelle){
        return parcelle.getLegume() == null && parcelle.getObjet() == null && !parcelle.aUnRocher() && !parcelle.aDeLHerbe();
    }

    /**
     * Retourne le prix du legume
     * @return prix
     */
    public abstract int getPrix();
}
