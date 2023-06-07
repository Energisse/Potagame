package VueController.BarMenu.Fichier;

import VueController.BarMenu.Fichier.Charger.MenuItemCharger;
import VueController.BarMenu.Fichier.NouvellePartie.MenuItemNouvellePartie;
import VueController.BarMenu.Fichier.Sauvegarde.MenuItemSauvegarder;
import VueController.BarMenu.Fichier.SauvegardeAs.MenuItemSauvegarderAs;

import javax.swing.*;

public class MenuFichier extends JMenu {

    /**
     * Constructeur du menu fichier
     */
    public MenuFichier() {
        super("Fichier");
        add(new MenuItemNouvellePartie());
        add(new MenuItemSauvegarder());
        add(new MenuItemSauvegarderAs());
        add(new MenuItemCharger());
        add(new VueController.BarMenu.Fichier.Effacer.MenuItemEffacer());
    }
}
