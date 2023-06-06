package Modele.Legume;
import Modele.Modele;
import java.io.Serializable;
import Modele.Parcelle;

public class Legume implements Serializable{
    /**
     * Croissance du légume
     */
    private float croissance = 0;

    /**
     * Prix du légume
     */
    private final int prix;

    /**
     * Nom
     */
    private final String nom;

    /**
     * Temperature optimale du légume
     */
    private final float temperatureOptimale;

    /**
     * Humidité optimale du légume
     */
    private final float humiditeOptimale;

    /**
     * temps de pousse en jour
     */
    private final float tempsDePousse;

    //Constructeur
    public Legume(String nom, int prix, float temperature, float humidite, float tempsPousse){
        this.nom = nom;
        this.prix = prix;
        this.temperatureOptimale = temperature;
        this.humiditeOptimale = humidite;
        this.tempsDePousse = tempsPousse;
    }

    /**
     * Retourne le prix du légume
     * @return int
     */
    public int getPrixRevente(){
        return (int) (prix*(1-tauxPourriture)*(1-tauxBrulure));
    }

    /**
     * Retourne le nom du légume
     * @return String
     */
    public String getNom(){
        return nom;
    }

    /**
     * Retourne la croissance du légume
     * @return pourcentage de croissance
     */
    public float getCroissance() {
        return croissance;
    }

    /**
     * Taux de brullure du légume de 0 a 1
     */
    private float tauxBrulure = 0;

    /**
     *  tauxMaladie du légume de 0 a 1
     */
    private float tauxPourriture = 0;

    /**
     * Fait pousser le légume
     * Le taux de croissance est donné par une formule (complètement "aléatoire") qui permet de prendre en compte la température globale régnant
     * sur le potager ainsi que la valeur d'humidité propre à chaque parcelle: la vitesse de pousse devrait donc être légèrement différente selon les parcelles
     */
    public void pousser(Parcelle parcelle){
        if(aFinitCroissance())return;
        //50% liée à la température, 50% liée à l'humidité

        float temperature = Modele.getInstance().getMeteo().temperature();

        float diffTemperature = temperature/temperatureOptimale;
        //si la température est trop éloignée de la température optimale, le taux de croissance est null
        if(diffTemperature <= 0.5){
            return;
        }
        //si la température est loin de la température optimale alors le legume brule
        if(diffTemperature >= 2){
            tauxBrulure+=0.01;
            if(tauxBrulure > 1)tauxBrulure = 1;
            return;
        }

        float tauxDeCroissance = 50;

        if(parcelle.getHumidite() > humiditeOptimale){
            tauxDeCroissance += 50;
            parcelle.setHumidite(parcelle.getHumidite()-humiditeOptimale);
        }
        else{
            //si l'humidité est trop faible, le légume pourrit
            tauxDeCroissance -= 25;
            tauxPourriture += 0.01;
            if(tauxPourriture > 1)tauxPourriture = 1;
            parcelle.setHumidite(0);
        }
        if(tauxDeCroissance < 0)return;
        croissance += tauxDeCroissance/(tempsDePousse *24);
        if(croissance > 100)croissance = 100;

    }

    /**
     * Retourne si le légume a finit de pousser
     * @return boolean
     */
    private boolean aFinitCroissance(){
        return croissance == 100 || tauxPourriture == 1 || tauxBrulure == 1;
    }

    /**
     * Retourne si le légume est mort
     * @return boolean
     */
    public boolean estMort(){
        return croissance == 0;
    }

    /**
     * Retourne si le légume est récoltable
     * @return boolean
     */
    public boolean estRecoltable(){
        return aFinitCroissance();
    }

    /**
     * Retourne le taux de brullure du légume
     * @return float
     */
    public float getTauxBrulure() {
        return tauxBrulure;
    }

    /**
     * Renvoie le taux de maladie du légume
     * @return float
     */
    public float getTauxPourriture() {
        return tauxPourriture;
    }
}
