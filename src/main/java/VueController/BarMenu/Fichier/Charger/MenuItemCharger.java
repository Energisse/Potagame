package VueController.BarMenu.Fichier.Charger;

import Modele.Sauvegarde;
import VueController.BarMenu.DialogErreur;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MenuItemCharger extends JMenu {

        /**
        * Constructeur du menu sauvegarder
        */
        public MenuItemCharger() {
            super("Charger");

            addMenuListener(new MenuListener() {
                @Override
                public void menuSelected(MenuEvent e) {
                    removeAll();
                    for( String sauvegarde : Sauvegarde.getSauvegardes()){
                        add(new JMenuItem(sauvegarde){
                            {
                                addActionListener(e-> {
                                    try {
                                        Sauvegarde.charger(getText());
                                    } catch (Exception ex) {
                                        new DialogErreur();
                                    }
                                });
                            }
                        });
                    }
                    if(getItemCount() == 0)
                        add(new JMenuItem("Aucune sauvegarde"));
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
