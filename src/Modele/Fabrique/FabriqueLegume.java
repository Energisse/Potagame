package Modele.Fabrique;

import Modele.Legume.Legume;
import Modele.Parcelle;

public abstract class FabriqueLegume extends  Fabrique<Legume> {

    public boolean peutEtrePose(Parcelle parcelle){
        return parcelle.getLegume() == null && parcelle.getObjet() == null;
    }

    public abstract int getPrix();
}
