package VueController.BarMenu.Aide.Aide;

import javax.swing.*;

public class MenuItemAide extends JMenuItem {

    /**
     * Constructeur du menu aide
     */
    public MenuItemAide() {
        super("Aide");

        addActionListener(e -> new DialogAide());
    }
}
