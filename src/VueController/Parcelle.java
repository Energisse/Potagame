package VueController;
import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import Modele.Modele;


public class Parcelle  extends ParcelleBase  implements Observer{

    /**
     * Label contenant la bordure d'action de l'epouvantail
     */
    private final JLabel labelBordureEpouvantail;

    /**
     * Label contenant la bordure de selection
     */
    private final JLabel labelBordureSelection;

    /**
     * Constructeur de la parcelle
     * @param indiceX Indice X de la parcelle
     * @param indiceY Indice Y de la parcelle
     * @throws IOException Exception si l'image n'est pas trouvée
     */
    Parcelle(int indiceX,int indiceY) throws IOException{
        super(indiceX,indiceY);

        labelBordureEpouvantail = new JLabel();
        labelBordureEpouvantail.setBounds(0, 0,TAILLE, TAILLE);

        this.add(labelBordureEpouvantail, JLayeredPane.POPUP_LAYER);

        labelBordureSelection = new JLabel();

        this.add(labelBordureSelection, JLayeredPane.POPUP_LAYER);
        labelBordureSelection.setBounds(0, 0,TAILLE, TAILLE);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if(evt.getButton() == java.awt.event.MouseEvent.BUTTON1){
                    Modele.getInstance().recolter(indiceX, indiceY);
                }
            }

            /**
             * Change la parcelle selectionnée
             */
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Vue.getInstance().setPositionSelectionnee(indiceX, indiceY);
            }

        });
    }

    /**
     * Met a jour l'image de la parcelle
     */
    public void update(Observable o, Object arg){
        super.update(o, arg);
        //si il y a un epouvantail
        if(Modele.getInstance().getNbEpouvantail(indiceX,indiceY) > 0){
            labelBordureEpouvantail.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));
        }
        else{
            labelBordureEpouvantail.setBorder(null);
        }

        if(Vue.getInstance().getPositionSelectionnee()[0] == indiceX && Vue.getInstance().getPositionSelectionnee()[1] == indiceY){
            labelBordureSelection.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        }
        else{
            labelBordureSelection.setBorder(null);
        }
    }
}