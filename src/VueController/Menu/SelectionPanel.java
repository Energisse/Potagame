package VueController.Menu;

import Modele.Legume.Legume;
import Modele.Modele;
import VueController.ParcelleBase;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class SelectionPanel extends JPanel implements Observer {
    /**
     * Parcelle selectionnée
     */
    private final ParcelleBase parcelleSelectionnee;

    /**
     * Label parcelle selectionnée
     */
    private final JTextArea  labelParcelleSelectionnee;

    /**
     * Constructeur du SelectionPanel
     */
     public SelectionPanel(){
        super(new GridBagLayout());
         labelParcelleSelectionnee = new JTextArea();
         labelParcelleSelectionnee.setOpaque(false);

         parcelleSelectionnee = new ParcelleBase(0,0);

         setOpaque(false);
         add(parcelleSelectionnee);
         add(labelParcelleSelectionnee);
     }

    @Override
    public void update(Observable o, Object arg) {
        int[] position = Modele.getInstance().getParcelleSelectionnee();
        parcelleSelectionnee.setPosition(position[0], position[1]);

        parcelleSelectionnee.update(o, arg);

        float humidite = Modele.getInstance().getHumidite(position[0], position[1]);
        Legume legume = Modele.getInstance().getLegume(position[0], position[1]);


        String type;

        if(Modele.getInstance().aDeLHerbe(position[0], position[1])){
            if(Modele.getInstance().aUnRocher(position[0], position[1])){
                type = "Rocher";
            }
            else if(Modele.getInstance().getFleure(position[0], position[1]) != -1){
                type = "Fleure";
            }
            else{
                type = "Herbe";
            }
        }
        else{
            type = "Terre";
        }

        String info = "Parcelle : " +type + "\nHumidité : " + Math.floor(humidite);
        if(legume != null){
            info += "\nLegume : " + legume.getNom() + "\nCroissance : " + legume.getCroissance() + "\nPourriture :" + Math.floor(legume.getTauxPourriture() * 100) + "%" +"\nCramée :" + Math.floor(legume.getTauxBrulure() * 100) + "%";
        }
        else {
            info += "\nLegume : Aucun";
        }

        labelParcelleSelectionnee.setText(info);
    }


}
