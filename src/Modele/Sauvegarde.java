package Modele;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Sauvegarde {

    /**
     * Sauvegarde le modèle dans un fichier
     */
    public static void sauvegarder() {
        try (FileOutputStream fileOut = new FileOutputStream("sauvegarde.ser");
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {
            Modele.getInstance().deleteObservers();
            objOut.writeObject(Modele.getInstance());
            System.out.println("Sauvegarde effectuée avec succès !");
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de la sauvegarde : " + e.getMessage());
        }
    }

    /**
     * Charge le modèle depuis un fichier
     * @return le modèle chargé si la sauvegarde existe, null sinon 
     */
    public static Modele charger() {
        try (FileInputStream fileIn = new FileInputStream("sauvegarde.ser");
             ObjectInputStream objIn = new ObjectInputStream(fileIn)) {
            Modele obj = (Modele) objIn.readObject();
            Modele.setInstance(obj);
            System.out.println("Chargement effectué avec succès !");
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Une erreur s'est produite lors du chargement : " + e.getMessage());
        }
        return null;
    }
}
