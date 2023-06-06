package VueController.Mouette;

import Config.Config;
import Modele.Modele;
import VueController.ImageLoader;
import VueController.Parcelle;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class Mouette extends JLabel implements Observer {

    /**
     * Taille d'une mouette
     */
    private static final int TAILLE = Config.getInstance().getConfigMouette().taille();

    /**
     * ImageIcon de la mouette
     */
    private static final ImageIcon[] mouetteIcon;

    /**
     * Image index
     */
    private int imageIndex = 0;

    /**
     * Id mouette
     */
    private final int id;

    static {
        mouetteIcon = new ImageIcon[Config.getInstance().getConfigMouette().images().length];
        for (int i = 0; i < mouetteIcon.length; i++) {
            mouetteIcon[i] = ImageLoader.loadIcon(Config.getInstance().getConfigMouette().images()[i],TAILLE);
        }
    }

    /**
     * Constructeur de la mouette
     */
    public Mouette(int id){
        super(mouetteIcon[0]);
        this.id = id;
    }

    /**
     * Update la mouette
     */
    @Override
    public void update(Observable o, Object arg) {
         setIcon(mouetteIcon[imageIndex++%mouetteIcon.length]);
         setBounds(
                 (int)(Parcelle.TAILLE*Modele.getInstance().getMouettes().get(this.id).getX()-TAILLE/2),
                 (int)(Parcelle.TAILLE*Modele.getInstance().getMouettes().get(this.id).getY()-TAILLE/2),
                 TAILLE,
                 TAILLE);
    }
}
