package Modele.Fabrique;

import Modele.Parcelle;

public abstract class Fabrique<T> {

    public abstract boolean peutEtrePose(Parcelle parcelle);

    public abstract T creer();

    public abstract int getPrix();
}
