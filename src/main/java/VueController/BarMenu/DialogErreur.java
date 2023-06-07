package VueController.BarMenu;



import VueController.Vue;

import javax.swing.*;

public class DialogErreur extends JDialog {

        /**
        * Constructeur de la fenetre d'erreur
        */
        public DialogErreur() {
            super();
            setTitle("Erreur");
            setSize(300, 100);
            setLocationRelativeTo(Vue.getInstance());
            setResizable(false);
            setModal(true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setVisible(true);
        }
}
