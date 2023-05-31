package Modele.Fabrique;

import Modele.Legume.Legume;
import Modele.Legume.Tomate;
import Modele.Parcelle;

public class FabriqueTomate extends FabriqueLegume{

    /**
     * Prix de la tomate
     */
    public static final int PRIX = 1;

    @Override
    public Legume creer(Parcelle parcelle) {
        return new Tomate();
    }
    
    @Override
    public int getPrix() {
       return PRIX;
    }
}
