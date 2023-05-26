package Modele.Fabrique;

import Modele.Parcelle;
import Modele.Legume.Legume;
import Modele.Legume.Salade;

public class FabriqueSalade extends Fabrique {

    /**
     * Prix de la salade
     */
    public static final int PRIX = 1;

    @Override
    public boolean peutEtrePlante(Parcelle parcelle) {
        if(parcelle.isFree()){
            return true;
        }
        return false;
    }

    @Override
    public Legume creerLegume() {
        return new Salade();
    }

    @Override
    public int getPrix() {
       return PRIX;
    }
}
