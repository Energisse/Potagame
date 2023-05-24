package Modele.Legume;

public class Tomate extends Legume {

    /**
     * Image du légume
     */
    public static final String image = "./src/images/Tomate.png";
    
    /**
     * Nom du légume
     */
    public static final String nom = "Tomate";

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
}
