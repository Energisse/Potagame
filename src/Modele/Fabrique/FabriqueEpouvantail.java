package Modele.Fabrique;

import Config.ConfigEpouvantail;
import Modele.Objet.Epouvantail;
import Modele.Objet.Objet;
import Modele.Parcelle;

public class FabriqueEpouvantail extends  FabriqueObjet{

    public static void load(ConfigEpouvantail epouvantail){
        //Constructeur de Fabrique retient toutes les instances de Fabrique
        new FabriqueEpouvantail(
                epouvantail.nom(),
                epouvantail.image(),
                epouvantail.raccourci(),
                epouvantail.prixAchat()
        );
    }

    /**
    * Prix de l'épouvantail
    */
    public final int prix;

    /**
     * Constructeur
     */
    public FabriqueEpouvantail(String nom, String image, String raccourci, int prix) {
        super(nom, image, raccourci);
        this.prix = prix;
    }

    @Override
    public boolean peutEtrePose(Parcelle parcelle) {
        return parcelle.getLegume() == null && parcelle.getObjet() == null && !parcelle.aUnRocher() ;
    }

    @Override
    public Objet creer(Parcelle parcelle) {
        return new Epouvantail(parcelle);
    }

    @Override
        public int getPrix() {
            return prix;
        }
}
