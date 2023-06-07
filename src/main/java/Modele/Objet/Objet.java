package Modele.Objet;

import Modele.Parcelle;

import java.io.Serializable;

public abstract class Objet implements Serializable {

    /**
     * Nom de l'objet
     */
    public abstract String getNom();

    /**
     * Parcelle de l'objet
     */
    private final Parcelle parcelle;

    /**
     * Constructeur de l'objet
     */
    public Objet(Parcelle parcelle) {
        this.parcelle = parcelle;
    }

    /**
     * Retourne la parcelle de l'objet
     * @return Parcelle
     */
    public Parcelle getParcelle() {
        return parcelle;
    }

    /**
     * Enleve l'objet
     */
    public abstract void enlever();

}
