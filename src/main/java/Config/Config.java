package Config;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Config {

    /**
     * Instance de la config
     */
    private static Config instance;
    static {
        String json= "";
        try {
            InputStream is = Config.class.getClassLoader().getResourceAsStream("Config/config.json");
            if (is == null) {
                System.err.println("Null.........................");
            }
            InputStreamReader isReader = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isReader);
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            reader.close();
            isReader.close();
            is.close();
            json= sb.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Gson gson = new Gson();
        instance = gson.fromJson(json, Config.class);
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

    /**
     * Config lac
     */
    private ConfigLac lac;

    /**
     * Constructeur de Config
     */
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

    /**
     * Retourne la config du lac
     * @return lac
     */
    public ConfigLac getConfigLac() {
        return lac;
    }
}
