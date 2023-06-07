package VueController.BarMenu;


import VueController.BarMenu.Aide.MenuAide;

import javax.swing.*;

public class BarMenu extends JMenuBar {

    /**
     * Constructeur du menu
     */
    public BarMenu() {
        super();

        this.add(new VueController.BarMenu.Fichier.MenuFichier());
        this.add(new MenuAide());
    }
}
