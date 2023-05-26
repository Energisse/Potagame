package Modele.Fabrique;

import Modele.Parcelle;
import Modele.Legume.Legume;
import Modele.Legume.Tomate;

public class FabriqueTomate extends Fabrique{

    /**
     * Prix de la tomate
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
        return new Tomate();
    }
    
    @Override
    public int getPrix() {
       return PRIX;
    }
}
