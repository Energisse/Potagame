package Modele.Meteo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Meteo {

    /**
     * Instance de Meteo
     */
    private static Meteo instance = null;

    /**
     * Tableau stockant des meteoData possédant chacun une température et une humidité
     */
    private MeteoData[] meteoData;

    /**
     * Constructeur de Meteo
     */
    private Meteo() {
        extractDataMeteo();
    }

    /**
     * Récupération de l'instance de Meteo
     * @return instance de Meteo
     */
    public static Meteo getInstance(){
        if(instance == null){
            instance = new Meteo();
        }
        return instance;
    }

    /**
     * Récupération du fichier de données météo depuis :
     * @see: https://www.data.gouv.fr/fr/datasets/donnees-quotidiennes-de-119-stations-en-france-metropolitaine-pour-les-etudes-de-liens-entre-meteorologie-et-covid-19-du-01-01-2020-au-21-04-2021/#/resources
     * Lecture du fichier de données récupérant 366 valeurs de température et d'humidité moyenne
     */
    public void extractDataMeteo(){
        meteoData = new MeteoData[366];
        int k=0;
        try {
            FileReader fr = new FileReader("./src/donneesmeteo/tu_meteofrance_119_stations.csv");///src/donneesmeteo/Juin2023.csv
            BufferedReader bfr = new BufferedReader(fr);
            String line = null;
            //On échappe la première ligne correspondant au nom des colonnes et on lit la 2nd qui est le début des valeurs du fichier
            for (int i = 0; i < 2; i++) {
                line = bfr.readLine();
            }
            while (line != null) {
                float temperature,humidite;
                String[] dataline = line.split(";");
                if (dataline[0].contains("v")) {
                    temperature = 10;//valeur par défaut
                } else {
                    temperature= Float.parseFloat(dataline[0]);
                }
                if (dataline[1].contains("v")) {
                    humidite = 11;
                } else {
                    humidite= Float.parseFloat(dataline[1]);
                }
                meteoData[k]=new MeteoData(temperature,humidite);
                k++;
                line = bfr.readLine();
            }
            bfr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Getter d'un meteoData pour un temps(horaire) donné
     * Retourne une temperature et une humidite pour 1h
     * @param time temps
     * @return MeteoData
     */
    public MeteoData getMeteodata(int time) {
        return meteoData[(time/24)%365];
    }

}

