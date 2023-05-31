package Modele.Objet;

import Modele.Modele;
import Modele.Parcelle;

public class Epouvantail extends  Objet{

    /**
     * Nom de l'épouvantail
     */
    private static final String nom = "epouvantail";

    /**
     * Rayon d'action de l'épouvantail
     */
    public static  final  int RAYON = 2;

    /**
     * Constructeur de la classe Epouvantail
     */
    public Epouvantail(Parcelle parcelle) {
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

        //Parcours du carré et incrémentation du nombre d'épouvantail
        for (int x = debutX; x <= finX; x++){
            for (int y = debutY; y <= finY; y++){
                //Calcul de la distance entre la parcelle et l'épouvantail
                double distance = Math.sqrt((parcelle.getX() - x) * (parcelle.getX() - x) + (parcelle.getY() - y) * (parcelle.getY() - y));
                //si dans le rayon
                if(distance <= RAYON) {
                    //incrémente le nombre d'épouvantail
                    Modele.getInstance().getParcelle(x, y).setNbEpouvantail(Modele.getInstance().getParcelle(x, y).getNbEpouvantail() + 1);
                }
            }
        }
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

        //Parcours du carré et incrémentation du nombre d'épouvantail
        for (int x = debutX; x <= finX; x++){
            for (int y = debutY; y <= finY; y++){
                //Calcul de la distance entre la parcelle et l'épouvantail
                double distance = Math.sqrt((getParcelle().getX() - x) * (getParcelle().getX() - x) + (getParcelle().getY() - y) * (getParcelle().getY() - y));
                //si dans le rayon
                if(distance <= RAYON) {
                    //incrémente le nombre d'épouvantail
                    Modele.getInstance().getParcelle(x, y).setNbEpouvantail(Modele.getInstance().getParcelle(x, y).getNbEpouvantail() - 1);
                }
            }
        }
    }

    @Override
    public String getNom() {
        return nom;
    }
}
