package Modele.Fabrique;

import Config.ConfigEpouvantail;
import Config.ConfigLac;
import Modele.Objet.Epouvantail;
import Modele.Objet.Lac;
import Modele.Objet.Objet;
import Modele.Parcelle;

public class FabriqueLac extends  FabriqueObjet{

    public static void load(ConfigLac lac){
        //Constructeur de Fabrique retient toutes les instances de Fabrique
        new FabriqueLac(
                lac.nom(),
                lac.image(),
                lac.raccourci(),
                lac.prixAchat()
        );
    }

    /**
    * Prix de l'épouvantail
    */
    public final int prix;

    /**
     * Constructeur
     */
    public FabriqueLac(String nom, String image, String raccourci, int prix) {
        super(nom, image, raccourci);
        this.prix = prix;
    }

    @Override
    public boolean peutEtrePose(Parcelle parcelle) {
        return parcelle.getLegume() == null && parcelle.getObjet() == null && !parcelle.aUnRocher() ;
    }

    @Override
    public Objet creer(Parcelle parcelle) {
        return new Lac(parcelle);
    }

    @Override
        public int getPrix() {
            return prix;
        }
}
