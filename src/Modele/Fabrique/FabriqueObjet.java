package Modele.Fabrique;

import Modele.Objet.Objet;
import Modele.Parcelle;

public abstract class FabriqueObjet extends  Fabrique<Objet> {

    public boolean peutEtrePose(Parcelle parcelle){
        return parcelle.getLegume() == null && parcelle.getObjet() == null;
    }

    public abstract int getPrix();
}
