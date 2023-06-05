package Modele.Legume;
import Modele.Meteo.Meteo;
import Modele.Modele;
import Modele.Parcelle;

public abstract class Legume {
    /**
     * Croissance du légume
     */
    protected float croissance = 0;

    /**
     * Taux de croissance du légume dépendant de la température du jour et de l'humidité de la parcelle
     * Si les conditions sont mauvaises, le taux de croissance reduit jusqu'a 0
     * Si les conditions sont bonnes, le taux de croissance augmente jusqu'a 2
     * Attention : Il faut que le taux soit compris entre ces 2 valeurs car sinon la condition AFinitCroissance ne serait
     * pas vérifier: on aurait alors l'image qui augmente de plus en plus et qui ne ressemblerait à rien !
     */
    protected int tauxCroissance = 1;
    /**
     * Vie du légume
     * @return
     */
    protected int vie = 100;

    /**
     * Retourne le prix du légume
     * @return int
     */
    public abstract int getPrixRevente();

    /**
     * Retourne l'image du légume
     * @return String
     */
    public abstract String getImage();

    /**
     * Retourne le nom du légume
     * @return String
     */
    public abstract String getNom();

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
    private float tauxMaladie = 0;

    /**
     * Fait pousser le légume
     * Le taux de croissance est donné par une formule (complètement "aléatoire") qui permet de prendre en compte la température globale régnant
     * sur le potager ainsi que la valeur d'humidité propre à chaque parcelle: la vitesse de pousse devrait donc être légèrement différente selon les parcelles
     * @return void
     */
    public void pousser(Parcelle parcelle){
        tauxCroissance=(int)((Meteo.getInstance().getMeteodata(Modele.getInstance().getTemps()).getTemperatureDuJour()/parcelle.getHumidite())*10+0.5);
        if(tauxCroissance == 0 ){ //Les valeurs étant souvent en 0.xxx, le cast en int peut retourner 0 ce qui ne permettrait pas de faire pousser la plante
            tauxCroissance=1;
        }
        if(tauxCroissance>2){ //De même certaines valeurs peuvent être plus grande que 2 ce que l'on ne veut pas comme mentionner plus haut
            tauxCroissance=2;
        }
        if(!aFinitCroissance()){
            if(parcelle.getHumidite() > 2){
                croissance += tauxCroissance;
                parcelle.setHumidite(parcelle.getHumidite() - 2); //L'humidite de la parcelle diminue au fur et à mesure de la croissance de la plante
            }
        }
        else{
            if(parcelle.getHumidite() > 1){
                parcelle.setHumidite(parcelle.getHumidite() - 1);
            }
            //La maladie sur les plantes évolue en fonction de la météo
            //Si très sec il n'y a quasi aucune augmentation de la maladie mais si très humide le risque est plus élevé
            if (parcelle.getHumidite() < 50){
                tauxMaladie += 0.001;
            }
            else{
                tauxMaladie += 0.008;
            }
            if(tauxMaladie > 1) tauxMaladie = 1;

        }
    }

    /**
     * Retourne si le légume a finit de pousser
     * @return boolean
     */
    private boolean aFinitCroissance(){
        return croissance == 100;
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
        return aFinitCroissance() && vie > 0;
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
     * @return
     */
    public float getTauxMaladie() {
        return tauxMaladie;
    }
}
