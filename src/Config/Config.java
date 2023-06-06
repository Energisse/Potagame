package Config;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;

public class Config {

    /**
     * Instance de la config
     */
    private static Config instance;

    static {
        String nomFichier = "src/Config/config.json";
        try (FileReader reader = new FileReader(nomFichier)) {
            Gson gson = new Gson();
            instance = gson.fromJson(reader, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Tableau de fabrique de legume
     */
    private ConfigFabriqueLegume[] legumes;

    /**
     * nombre de parcelle en largeur
     */
    private int largeur;

    /**
     * nombre de parcelle en hauteur
     */
    private int hauteur;

    /**
     * argent de depart
     */
    private int argent;

    /**
     * Config mouette
     */
    private ConfigMouette mouette;

    /**
     * Config parcelle
     */
    private ConfigParcelle parcelle;

    /**
     * Config epouvantail
     */
    private ConfigEpouvantail epouvantail;

    private Config(){
    }

    /**
     * Force le chargement de la config
     */
    public static void load(){
        if(instance == null){
            new Config();
        }
    }

    /**
     * Retourne l'instance de la config
     * @return instance
     */
    public static Config getInstance(){
        return instance;
    }

    /**
     * Retourne la largeur de la grille
     * @return largeur
     */
    public int getLargeur() {
        return largeur;
    }

    /**
     * Retourne la hauteur de la grille
     * @return hauteur
     */
    public int getHauteur() {
        return hauteur;
    }

    /**
     * Retourne l'argent de depart
     * @return argent
     */
    public int getArgent() {
        return argent;
    }

    /**
     * Retourne la config de la mouette
     * @return mouette
     */
    public ConfigMouette getConfigMouette() {
        return mouette;
    }

    /**
     * Retourne la config de la parcelle
     * @return parcelle
     */
    public ConfigParcelle getConfigParcelle() {
        return parcelle;
    }

    /**
     * Retourne la config de l'epouvantail
     * @return epouvantail
     */
    public ConfigEpouvantail getConfigEpouvantail() {
        return epouvantail;
    }

    /**
     * Retourne la fabrique de legume
     * @return fabriqueLegume
     */
    public ConfigFabriqueLegume[] getFabriqueLegumes() {
        return legumes;
    }
}
