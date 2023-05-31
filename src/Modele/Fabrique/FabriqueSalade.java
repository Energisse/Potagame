package Modele.Fabrique;

import Modele.Legume.Legume;
import Modele.Legume.Salade;

public class FabriqueSalade extends FabriqueLegume {

    /**
     * Prix de la salade
     */
    public static final int PRIX = 1;

    @Override
    public Legume creer() {
        return new Salade();
    }

    @Override
    public int getPrix() {
       return PRIX;
    }
}
