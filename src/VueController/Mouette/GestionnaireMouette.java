package VueController.Mouette;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import Modele.Modele;

public class GestionnaireMouette extends JPanel implements Observer{

    /**
     * Liste des JLabel mouette
     */
    private final ArrayList<Mouette> mouettes = new ArrayList<>();

    /**
     * Constructeur du gestionnaire de mouette
     */
    public GestionnaireMouette(){
        super(null);
        setOpaque(false);
    }

    /**
     * Update la mouette
     */
    @Override
    public void update(Observable o, Object arg) {

        //Ajout de JLabel mouette si il y a plus de mouettes que de JLabel mouette
        while(mouettes.size() < Modele.getInstance().getMouettes().size()){
            Mouette nouvelleMouette = new Mouette(mouettes.size());
            mouettes.add(nouvelleMouette);
            add(nouvelleMouette);
        }
        //Suppression de JLabel mouette si il y a moins de mouettes que de JLabel mouette
        while(mouettes.size() > Modele.getInstance().getMouettes().size()){
            Mouette ancienneMouette = mouettes.remove(mouettes.size()-1);
            ancienneMouette.setIcon(null);
            remove(ancienneMouette);
        }

        //update every mouette
        for (Mouette mouette : mouettes) {
            mouette.update(o, arg);
        }

        //Pour eviter les bugs de trace de mouette on repaint tout
        revalidate();
        repaint();
    }

}
