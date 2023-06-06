package VueController.BarMenu.Fichier.Sauvegarde;

import Modele.Sauvegarde;
import VueController.BarMenu.DialogErreur;

import javax.swing.*;

public class MenuItemSauvegarder extends JMenuItem {

        /**
        * Constructeur du menu sauvegarder
        */
        public MenuItemSauvegarder() {
            super("Sauvegarder");
            addActionListener(e-> {
                try {
                    Sauvegarde.sauvegarder();
                } catch (Exception ex) {
                    new DialogErreur();
                }
            });
        }
}
