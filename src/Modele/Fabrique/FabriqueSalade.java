package Modele.Fabrique;

import Modele.Legume.Legume;
import Modele.Legume.Salade;
import Modele.Parcelle;

public class FabriqueSalade extends FabriqueLegume {

    /**
     * Prix de la salade
     */
    public static final int PRIX = 1;

    @Override
    public Legume creer(Parcelle parcelle) {
        return new Salade();
    }

    @Override
    public int getPrix() {
       return PRIX;
    }
}
