package VueController.BarMenu.Fichier.Effacer;

import Modele.Sauvegarde;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MenuItemEffacer extends JMenu {

        /**
        * Constructeur du menu sauvegarder
        */
        public MenuItemEffacer() {
            super("Effacer");

            addMenuListener(new MenuListener() {
                @Override
                public void menuSelected(MenuEvent e) {
                    removeAll();
                    for( String sauvegarde : Sauvegarde.getSauvegardes()){
                        add(new JMenuItem(sauvegarde){
                            {
                                addActionListener(e-> Sauvegarde.effacer(getText()));
                            }
                        });
                    }
                    if(getItemCount() == 0)
                        add(new JMenuItem("C'est déjà vide par ici !"));
                }

                @Override
                public void menuDeselected(MenuEvent e) {

                }

                @Override
                public void menuCanceled(MenuEvent e) {
                    removeAll();
                }
            });
        }

}
