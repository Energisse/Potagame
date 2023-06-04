package VueController.Mouette;

import Modele.Modele;
import VueController.Parcelle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class Mouette extends JLabel implements Observer {

    /**
     * Taille d'une mouette
     */
    private static final int TAILLE = 32;

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
        try {
            mouetteIcon = new ImageIcon[4];
            mouetteIcon[0] = new ImageIcon(ImageIO.read(new File("./src/images/Mouette.png")).getScaledInstance(TAILLE, TAILLE, Image.SCALE_SMOOTH));
            mouetteIcon[1] = new ImageIcon(ImageIO.read(new File("./src/images/Mouette2.png")).getScaledInstance(TAILLE, TAILLE, Image.SCALE_SMOOTH));
            mouetteIcon[2] = new ImageIcon(ImageIO.read(new File("./src/images/Mouette3.png")).getScaledInstance(TAILLE, TAILLE, Image.SCALE_SMOOTH));
            mouetteIcon[3] = mouetteIcon[1];
        } catch (IOException e) {
            throw new RuntimeException(e);
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
