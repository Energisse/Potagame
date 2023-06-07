package VueController.BarMenu.Aide;

import VueController.BarMenu.Aide.Aide.MenuItemAide;

import javax.swing.*;

public class MenuAide extends JMenu {

    /**
     * Constructeur du menu aide
     */
    public MenuAide() {
        super("Aide");
        add(new MenuItemAide());
    }
}
