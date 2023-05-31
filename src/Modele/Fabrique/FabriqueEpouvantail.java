package Modele.Fabrique;

import Modele.Objet.Epouvantail;
import Modele.Objet.Objet;
import Modele.Parcelle;

public class FabriqueEpouvantail extends  FabriqueObjet{

    /**
    * Prix de l'épouvantail
    */
    public static final int PRIX = 10;

    @Override
    public boolean peutEtrePose(Parcelle parcelle) {
        return parcelle.getLegume() == null && parcelle.getObjet() == null && !parcelle.aUnRocher() ;
    }

    @Override
    public Objet creer() {
        return new Epouvantail();
    }

    @Override
        public int getPrix() {
            return PRIX;
        }
}
