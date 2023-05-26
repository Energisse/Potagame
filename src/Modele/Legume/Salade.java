package Modele.Legume;

public class Salade extends Legume {

    /**
     * Image du légume
     */
    public static final String image = "./src/images/Salade.png";
    
    /**
     * Nom du légume
     */
    public static final String nom = "Salade";

    /**
     * Prix du légume
     */
    public static final int PRIX = 5;

    /**
     * Retourne l'image du légume
     * @return String
     */
    public String getImage(){
        return image;
    }

    /**
     * Retourne le nom du légume
     * @return String
     */
    public String getNom(){
        return nom;
    }

    @Override
    public int getPrixRevente() {
       return PRIX * (vie / 100);
    }
}
