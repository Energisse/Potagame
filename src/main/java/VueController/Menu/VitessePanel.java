package VueController.Menu;

import Modele.Modele;
import javax.swing.*;
import java.awt.*;

public class VitessePanel extends JPanel{

    /**
     * Constructeur du VitessePanel
     */
    public VitessePanel(){
        super(new GridLayout(2,1));

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 12, 1);
        slider.addChangeListener(e -> Modele.getInstance().setVitesse(slider.getValue()));
        slider.setValue(Modele.getInstance().getVitesse());
        slider.setPaintTicks(true);
        slider.setPaintTrack(true);
        slider.setPaintLabels(true);//Le tout permet l'affichage des traits et valeurs du slider
        slider.setMajorTickSpacing(1);
        slider.setOpaque(false);
        //Non focusable pour ne pas provoquer d'interférence avec le clavier
        slider.setFocusable(false);

        add(new JLabel("Vitesse de pousse :"));
        add(slider);
        setOpaque(false);
    }
}
