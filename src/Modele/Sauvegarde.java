package Modele;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Sauvegarde {

    /**
     * Nom de la sauvegarde actuelle
     */
    private static String sauvegardeActuelle = null;

    /**
     * Chemin du dossier de sauvegarde
     */
    private static final String PATH = System.getenv("APPDATA") + "\\Potagame";

    //Creation du dossier de sauvegarde
    static {
        File file = new File(PATH);
        if(!file.exists()){
            file.mkdir();
        }
    }

    /**
     * Remplace la sauvegarde actuelle ou en crée une nouvelle si aucune n'existe
     * @throws FileAlreadyExistsException La sauvegarde existe déjà
     * @throws Exception Une erreur s'est produite lors de la sauvegarde
     */
    public static void sauvegarder() throws Exception {
        if(sauvegardeActuelle != null){
            sauvegarder(sauvegardeActuelle,true);
            return;
        }

        String date = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        sauvegarder(date,true);
    }
    /**
     * Sauvegarde la partie dans un fichier
     * @param nom nom de la sauvegarde
     * @throws FileAlreadyExistsException La sauvegarde existe déjà
     * @throws Exception Une erreur s'est produite lors de la sauvegarde
     */
    public  static void sauvegarder(String nom) throws Exception {
        sauvegarder(nom,false);
    }

    /**
     * Sauvegarde la partie dans un fichier avec le nom donné et force l'écrasement si force est vrai
     * @param nom nom de la sauvegarde
     * @param force ecrase la sauvegarde si elle existe déjà
     * @throws FileAlreadyExistsException La sauvegarde existe déjà
     * @throws Exception Une erreur s'est produite lors de la sauvegarde
     */
    public static void sauvegarder(String nom,boolean force) throws Exception {
        String path = getFilePath(nom);
        if(!force){
            File file = new File(path);
            if(file.exists())
                throw new FileAlreadyExistsException("La sauvegarde : " + nom + " existe déjà !");
        }

        FileOutputStream fileOut = new FileOutputStream(path);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        sauvegardeActuelle = nom;
        objOut.writeObject(Modele.getInstance());
    }


    /**
     * Charge la sauvegarde la plus récente
     * @throws Exception Une erreur s'est produite lors du chargement
     */
    public static void charger() throws Exception {
        File dossier = new File(PATH);
        File[] fichiers = dossier.listFiles();
        File fichieRecent = null;
        long dateRecent = 0;

        if(fichiers == null){
            return;
        }

        for (File file : fichiers) {
            long date = file.lastModified();
            if (date > dateRecent) {
                fichieRecent = file;
                dateRecent = date;
            }
        }

        if(fichieRecent == null){
            return;
        }

        charger(fichieRecent.getName().split("\\.")[0]);
    }

    /**
     * Charge la sauvegarde avec le nom donné
     * @param nom nom de la sauvegarde
     * @throws Exception Une erreur s'est produite lors du chargement
     */
    public static void charger(String nom) throws Exception {
       FileInputStream fileIn = new FileInputStream(getFilePath(nom));
       ObjectInputStream objIn = new ObjectInputStream(fileIn);
       Modele obj = (Modele) objIn.readObject();
       Modele.setInstance(obj);
       fileIn.close();
       objIn.close();
       sauvegardeActuelle = nom;
    }

    /**
     * Supprime la sauvegarde
     * @param nom nom de la sauvegarde
     */
    public static void effacer(String nom) {
        File file = new File(getFilePath(nom));
        if(file.exists()){
            file.delete();
        }
    }

    /**
     * Retourne la liste des sauvegardes triées par date de modification
     * @return liste des sauvegardes
     */
    public static String[] getSauvegardes(){
        File[] files = new File(PATH).listFiles();

        if(files == null){
            return new String[0];
        }

        String[] names = new String[files.length];

        Arrays.sort(files, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));

        for(int i = 0; i < files.length; i++){
            names[i] = files[i].getName().split("\\.")[0];
        }
        return names;
    }

    /**
     * Retourne le chemin du fichier de sauvegarde
     * @param name nom de la sauvegarde
     * @return chemin du fichier de sauvegarde
     */
    private static String getFilePath(String name){
        return PATH + "\\" + name + ".ser";
    }
}
