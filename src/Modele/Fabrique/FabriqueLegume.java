package Modele.Fabrique;

import Config.ConfigFabriqueLegume;
import Modele.Legume.Legume;
import Modele.Parcelle;

public class FabriqueLegume extends  Fabrique<Legume> {

    public static void load(ConfigFabriqueLegume[] fabriqueLegume){
        for (ConfigFabriqueLegume configFabriqueLegume : fabriqueLegume) {
            //Constructeur de Fabrique retient toutes les instances de Fabrique
            new FabriqueLegume(
                    configFabriqueLegume.nom(),
                    configFabriqueLegume.image(),
                    configFabriqueLegume.raccourci(),
                    configFabriqueLegume.description(),
                    configFabriqueLegume.prixAchat(),
                    configFabriqueLegume.prixVente()
            );
        }
    }

    /**
     * Description du legume
     */
    private final String description;


    /**
     * Prix d'achat du legume
     */
    private final int prixAchat;

    /**
     * Prix de revente du legume
     */
    private final int prixVente;

    /**
     * Constructeur
     */
    private FabriqueLegume(String nom, String image, String raccourci, String description, int prixAchat, int prixVente) {
        super(nom, image, raccourci);
        this.description = description;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
    }

    /**
     * Retourne vrai si le legume peut être posé sur la parcelle
     * @param parcelle Parcelle sur laquelle le legume est posé
     * @return boolean
     */
    public boolean peutEtrePose(Parcelle parcelle){
        return parcelle.getLegume() == null && parcelle.getObjet() == null && !parcelle.aUnRocher() && !parcelle.aDeLHerbe();
    }

    /**
     * Fabrique une instance de legume
     * @param parcelle Parcelle sur laquelle le legume doit etre posé
     * @return boolean
     */
    @Override
    public Legume creer(Parcelle parcelle) {
        return new Legume(getNom(),prixVente);
    }

    /**
     * Retourne le d'achat du legume
     * @return prix
     */
    @Override
    public int getPrix() {
        return this.prixAchat;
    }
}
