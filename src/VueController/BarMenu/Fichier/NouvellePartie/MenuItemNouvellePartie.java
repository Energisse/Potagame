package VueController.BarMenu.Fichier.NouvellePartie;

import Modele.Modele;
import VueController.BarMenu.DialogErreur;

import javax.swing.*;

public class MenuItemNouvellePartie extends JMenuItem {

        /**
        * Constructeur du menu sauvegarder
        */
        public MenuItemNouvellePartie() {
            super("Nouvelle partie");
            addActionListener(e-> {
                try {
                    Modele.newGame();
                } catch (Exception ex) {
                    new DialogErreur();
                }
            });
        }
}
