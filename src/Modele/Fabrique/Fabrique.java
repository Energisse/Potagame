package Modele.Fabrique;

import Modele.Parcelle;
import Modele.Legume.Legume;

public abstract class Fabrique {

    public abstract boolean peutEtrePlante(Parcelle parcelle);

    public abstract Legume creerLegume();

    public abstract int getPrix();
}
