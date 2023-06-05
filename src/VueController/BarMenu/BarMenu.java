package VueController.BarMenu;

import VueController.BarMenu.Aide.MenuAide;
import VueController.BarMenu.Fichier.MenuFichier;

import javax.swing.*;

public class BarMenu extends JMenuBar {

    /**
     * Constructeur du menu
     */
    public BarMenu() {
        super();

        this.add(new MenuFichier());
        this.add(new MenuAide());
    }
}
