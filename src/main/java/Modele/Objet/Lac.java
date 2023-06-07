package Modele.Objet;

import Config.Config;
import Modele.Modele;
import Modele.Parcelle;

public class Lac extends  Objet{

    /**
     * Nom de lac
     */
    private static final String nom = Config.getInstance().getConfigLac().nom();

    /**
     * Humidité du lac (multiplie par 100 pour avoir un entier et eviter les erreurs de calcul (PTN DE RESIDUE DE MERDE))
     */
    public static  final int HUMIDITE = (int) (Config.getInstance().getConfigLac().humidite()*100);

    /**
     * disipatiob de laction du lac (multiplie par 100 pour avoir un entier et eviter les erreurs de calcul (PTN DE RESIDUE DE MERDE))
     */
    public static  final int DISIPATION = (int) (Config.getInstance().getConfigLac().disipation()*100);

    /**
     * Rayon d'action du lac
     */
    public static  final int RAYON = (int) Math.ceil(HUMIDITE/DISIPATION);

    /**
     * Prix de revente de l'épouvantail
     */
    public static final int PRIX_REVENTE = Config.getInstance().getConfigEpouvantail().prixVente();

    /**
     * Constructeur de la classe Lac
     */
    public Lac(Parcelle parcelle) {
        super(parcelle);

        //Calcul du carré contenant le cercle d'action
        int debutX = parcelle.getX() - RAYON;
        if(debutX < 0) debutX = 0;

        int finX = parcelle.getX() + RAYON;
        if(finX >= Modele.getInstance().largeur) finX = Modele.getInstance().largeur - 1;

        int debutY = parcelle.getY() - RAYON;
        if(debutY < 0) debutY = 0;

        int finY = parcelle.getY() + RAYON;
        if(finY >= Modele.getInstance().hauteur) finY = Modele.getInstance().hauteur - 1;

        for (int x = debutX; x <= finX; x++){
            for (int y = debutY; y <= finY; y++){
                double distance = Math.sqrt((parcelle.getX() - x) * (parcelle.getX() - x) + (parcelle.getY() - y) * (parcelle.getY() - y));
                //si dans le rayon
                if(distance <= RAYON) {
                    Modele.getInstance().getParcelle(x, y).setEffetLac((int) (Modele.getInstance().getParcelle(x, y).getEffetLac()+HUMIDITE - (distance-1)* DISIPATION));
                }
            }
        }

        parcelle.enlverFleures();
    }


    @Override
    public void enlever() {
        //Calcul du carré contenant le cercle d'action
        int debutX = getParcelle().getX() - RAYON;
        if(debutX < 0) debutX = 0;

        int finX = getParcelle().getX() + RAYON;
        if(finX >= Modele.getInstance().largeur) finX = Modele.getInstance().largeur - 1;

        int debutY = getParcelle().getY() - RAYON;
        if(debutY < 0) debutY = 0;

        int finY = getParcelle().getY() + RAYON;
        if(finY >= Modele.getInstance().hauteur) finY = Modele.getInstance().hauteur - 1;

        for (int x = debutX; x <= finX; x++){
            for (int y = debutY; y <= finY; y++){
                double distance = Math.sqrt((getParcelle().getX() - x) * (getParcelle().getX() - x) + (getParcelle().getY() - y) * (getParcelle().getY() - y));
                //si dans le rayon
                if(distance <= RAYON) {
                    Modele.getInstance().getParcelle(x, y).setEffetLac((int) (Modele.getInstance().getParcelle(x, y).getEffetLac()-HUMIDITE + (distance-1)* DISIPATION));
                }
            }
        }
        Modele.getInstance().setArgent(Modele.getInstance().getArgent()+PRIX_REVENTE);
    }

    @Override
    public String getNom() {
        return nom;
    }
}
