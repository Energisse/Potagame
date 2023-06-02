package VueController;

import Modele.Fabrique.FabriqueEpouvantail;
import Modele.Fabrique.FabriqueSalade;
import Modele.Fabrique.FabriqueTomate;
import Modele.Modele;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ClavierListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            //Deplacement avec les fleches pour selectionner une parcelle
            case KeyEvent.VK_UP -> {
                int[] position = Vue.getInstance().getPositionSelectionnee();
                Vue.getInstance().setPositionSelectionnee(position[0],position[1] - 1);
            } case KeyEvent.VK_DOWN -> {
                int[] position = Vue.getInstance().getPositionSelectionnee();
                Vue.getInstance().setPositionSelectionnee(position[0],position[1] + 1);
            }
            case KeyEvent.VK_LEFT -> {
                int[] position = Vue.getInstance().getPositionSelectionnee();
                Vue.getInstance().setPositionSelectionnee(position[0] - 1,position[1]);
            }
            case KeyEvent.VK_RIGHT -> {
                int[] position = Vue.getInstance().getPositionSelectionnee();
                Vue.getInstance().setPositionSelectionnee(position[0] + 1,position[1]);
            }

            //Planter un legume
            case KeyEvent.VK_T -> {
                int[] position = Vue.getInstance().getPositionSelectionnee();
                Modele.getInstance().poser(position[0],position[1],new FabriqueTomate());
            }
            case KeyEvent.VK_S -> {
                int[] position = Vue.getInstance().getPositionSelectionnee();
                Modele.getInstance().poser(position[0],position[1],new FabriqueSalade());
            }

            //Poser un objet
            case KeyEvent.VK_E -> {
                int[] position = Vue.getInstance().getPositionSelectionnee();
                Modele.getInstance().poser(position[0],position[1],new FabriqueEpouvantail());
            }

            //Enlever un objet/legume
            case KeyEvent.VK_BACK_SPACE -> {
                int[] position = Vue.getInstance().getPositionSelectionnee();
                Modele.getInstance().enlever(position[0],position[1]);
            }

            //recolter un legume
            case KeyEvent.VK_R -> {
                int[] position = Vue.getInstance().getPositionSelectionnee();
                Modele.getInstance().recolter(position[0],position[1]);
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
