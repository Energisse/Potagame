package Modele.Meteo;

public class MeteoData {
    private float temperature;
    private float humidite;

    /**
     * Constructeur de MeteoData
     * @param temperature
     * @param humidite
     */
    public MeteoData(float temperature, float humidite){
        this.temperature=temperature;
        this.humidite=humidite;

    }

    /**
     * Renvoie une valeur de température contenu dans un meteoData
     * @return
     */
    public float getTemperatureDuJour() {
        return temperature;
    }

    /**
     * Renvoie une valeur d'humidité contenu dans un meteoData
     * @return
     */
    public float getHumiditeDuJour() {
        return humidite;
    }

}
