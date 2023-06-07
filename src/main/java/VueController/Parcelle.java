package VueController;

import Modele.Modele;

import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

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
     * Label contenant l'effet du lac
     */
    private final JLabel labelEffetLac;


    /**
     * Constructeur de la parcelle
     * @param indiceX Indice X de la parcelle
     * @param indiceY Indice Y de la parcelle
     */
    Parcelle(int indiceX,int indiceY) throws IOException{
        super(indiceX,indiceY);

        labelBordureSelection = new JLabel();

        this.add(labelBordureSelection, JLayeredPane.DRAG_LAYER);
        labelBordureSelection.setBounds(3, 3,TAILLE-6, TAILLE-6);

        labelEffetLac = new JLabel();
        this.add(labelEffetLac, JLayeredPane.POPUP_LAYER);
        labelEffetLac.setBounds(3, 3,TAILLE-6, TAILLE-6);

        labelBordureEpouvantail = new JLabel();
        labelBordureEpouvantail.setBounds(3, 3,TAILLE-6, TAILLE-6);

        this.add(labelBordureEpouvantail, JLayeredPane.POPUP_LAYER);

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
                Modele.getInstance().setParcelleSelectionnee(indiceX, indiceY);
            }

        });
    }

    /**
     * Met a jour l'image de la parcelle
     */
    public void update(Observable o, Object arg){
        super.update(o, arg);

        //si il y a un lac
        if(Modele.getInstance().getEffetLac(indiceX,indiceY) > 0){
            labelEffetLac.setBorder(BorderFactory.createDashedBorder(Color.getHSBColor(0.61F,Math.min(1,Modele.getInstance().getEffetLac(indiceX,indiceY)/100),1),  3, 2, 1, true));
        }
        else{
            labelEffetLac.setBorder(null);
        }

        //si il y a un epouvantail
        if(Modele.getInstance().getNbEpouvantail(indiceX,indiceY) > 0){
            labelBordureEpouvantail.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3, true));
        }
        else{
            labelBordureEpouvantail.setBorder(null);
        }

        if(Modele.getInstance().getParcelleSelectionnee()[0] == indiceX && Modele.getInstance().getParcelleSelectionnee()[1] == indiceY){
            labelBordureSelection.setBorder(BorderFactory.createLineBorder(Color.RED, 3, true));
        }
        else{
            labelBordureSelection.setBorder(null);
        }
    }
}