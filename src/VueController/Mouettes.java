package VueController;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Modele.Modele;

public class Mouettes extends JPanel implements Observer{

    /**
     * Liste des JLabel mouette
     */
    private ArrayList<JLabel> mouettes = new ArrayList<>();

    /**
     * Taille d'une mouette
     */
    private static final int TAILLE = 32;

    /**
     * ImageIcon de la mouette
     */
    private static ImageIcon mouetteIcon;

    static {
        try {
            mouetteIcon = new ImageIcon(ImageIO.read(new File("./src/images/Mouette.png")).getScaledInstance(TAILLE, TAILLE, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructeur de la mouette
     */
    public Mouettes(){
        super(null);
        //print size
        setOpaque(false);
    }

    /**
     * Update la mouette
     */
    @Override
    public void update(Observable o, Object arg) {

        //Ajout de JLabel mouette si il y a plus de mouettes que de JLabel mouette
        while(mouettes.size() < Modele.getInstance().getMouettes().size()){
            JLabel nouvelleMouette = new JLabel();
            mouettes.add(nouvelleMouette);
            nouvelleMouette.setIcon(mouetteIcon);
            add(nouvelleMouette);
        }
        //Suppression de JLabel mouette si il y a moins de mouettes que de JLabel mouette
        while(mouettes.size() > Modele.getInstance().getMouettes().size()){
            JLabel ancienneMouette = mouettes.remove(mouettes.size()-1);
            ancienneMouette.setIcon(null);
            remove(ancienneMouette);
        }

        //update every mouette
        for(int i = 0; i < mouettes.size(); i++){
            mouettes.get(i).setBounds(
                    (int)(Parcelle.TAILLE*Modele.getInstance().getMouettes().get(i).getX()),
                    (int)(Parcelle.TAILLE*Modele.getInstance().getMouettes().get(i).getY())
                    ,TAILLE,TAILLE);
        }

        //Pour eviter les bugs de trace de mouette on repaint tout
        revalidate();
        repaint();
    }

}
