package VueController.ClavierListener;


import Modele.Fabrique.Fabrique;
import Modele.Modele;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class ClavierListener implements KeyListener {

    /**
     * Liste des listeners de raccourcis de legumes
     */
    private final ArrayList<VueController.ClavierListener.ListenerRaccourciFabrique> listenersLegume = new ArrayList<>();

    /**
     * Constructeur
     */
    public ClavierListener(){
        super();

        Fabrique.getInstances().forEach(fabrique -> {
            try {
                listenersLegume.add(new VueController.ClavierListener.ListenerRaccourciFabrique(KeyEvent.class.getDeclaredField("VK_"+fabrique.getRaccourci()),fabrique));
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("Le raccourci " + fabrique.getRaccourci() + " n'existe pas",e);
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            //Deplacement avec les fleches pour selectionner une parcelle
            case KeyEvent.VK_UP -> {
                int[] position = Modele.getInstance().getParcelleSelectionnee();
                Modele.getInstance().setParcelleSelectionnee(position[0],position[1] - 1);
            } case KeyEvent.VK_DOWN -> {
                int[] position = Modele.getInstance().getParcelleSelectionnee();
                Modele.getInstance().setParcelleSelectionnee(position[0],position[1] + 1);
            } case KeyEvent.VK_LEFT -> {
                int[] position = Modele.getInstance().getParcelleSelectionnee();
                Modele.getInstance().setParcelleSelectionnee(position[0] - 1,position[1]);
            } case KeyEvent.VK_RIGHT -> {
                int[] position = Modele.getInstance().getParcelleSelectionnee();
                Modele.getInstance().setParcelleSelectionnee(position[0] + 1,position[1]);
            }

            //Enlever un objet/legume
            case KeyEvent.VK_BACK_SPACE -> {
                int[] position = Modele.getInstance().getParcelleSelectionnee();
                if(Modele.getInstance().getObjet(position[0],position[1]) != null)
                    Modele.getInstance().enlever(position[0],position[1]);
                else if(Modele.getInstance().aUnRocher(position[0],position[1]))
                    Modele.getInstance().miner(position[0],position[1]);
                else if (Modele.getInstance().aDeLHerbe(position[0],position[1])){
                    Modele.getInstance().labourer(position[0],position[1]);
                }
            }

            //recolter un legume
            case KeyEvent.VK_ENTER -> {
                int[] position = Modele.getInstance().getParcelleSelectionnee();
                Modele.getInstance().recolter(position[0],position[1]);
            }

            //Sinon on regarde si c'est un raccourci de legume
            default -> {
                for (VueController.ClavierListener.ListenerRaccourciFabrique listenerLegume : listenersLegume) {
                    try {
                        if (e.getKeyCode() == listenerLegume.keyEvent().getInt(null)) {
                            int[] position = Modele.getInstance().getParcelleSelectionnee();
                            Modele.getInstance().poser(position[0],position[1],listenerLegume.fabrique());
                        }
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
