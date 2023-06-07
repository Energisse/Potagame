package VueController.BarMenu.Fichier.SauvegardeAs;

import javax.swing.*;

public class MenuItemSauvegarderAs extends JMenuItem {

        /**
        * Constructeur du menu sauvegarder
        */
        public MenuItemSauvegarderAs() {
            super("Sauvegarder sous");

            addActionListener(e-> new DialogSauvegarderAs());
        }
}
