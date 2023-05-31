package VueController;
import javax.swing.*;

import Modele.Modele;
import Modele.Sauvegarde;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class Vue extends JFrame implements Observer {

    /**
     * Tableau de parcelle
     */
    private Parcelle[][] tabParcelles;

    /**
     * Instance de la vue
     */
    private static Vue instance = null; 

    private Mouettes mouettes = new Mouettes();

    /**
     * Constructeur de la vue
     */
    private Vue(){
        super();
        tabParcelles = new Parcelle[Modele.getInstance().getLargeur()][Modele.getInstance().getLargeur()];
        try {
            build();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //super.windowClosing(e);
                Sauvegarde.sauvegarder();
                System.exit(0);
            }
        });
    }

    /**
     * Retourne l'instance de la vue
     * @return instance
     */
    public static Vue getInstance(){
        if (instance == null){
            instance = new Vue();
        }
        return instance ;
    }

    /**
     * Construit la vue
     * @throws IOException
     */
    private void build() throws IOException {
        // setLayout left to right
        setLayout(new FlowLayout());

        setTitle("Potagame");
        //Contient les parcelles + les mouettes
        JLayeredPane parcellesContainer = new JLayeredPane();
        //contient les parcelles
        JPanel parcelles = new JPanel(new GridLayout(Modele.getInstance().getLargeur(), Modele.getInstance().getHauteur()));
        parcellesContainer.add(parcelles);

        JPanel menu = new JPanel(new GridLayout(2, 1));
        menu.setPreferredSize(new Dimension(200, 100));

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 10, 1);
        slider.addChangeListener(e -> Modele.getInstance().setVitesse(slider.getValue()));

        menu.add(slider);

        try {
            menu.add(ArgentPanel.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialisation des parcelles
        for (int y = 0; y < Modele.getInstance().getLargeur(); y++) {
            for (int x = 0; x < Modele.getInstance().getHauteur(); x++) {
                try {
                    tabParcelles[x][y] = new Parcelle(x, y);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                parcelles.add(tabParcelles[x][y]);
            }
        }

        parcelles.setBounds(0, 0, Parcelle.TAILLE * Modele.getInstance().getLargeur(), Parcelle.TAILLE * Modele.getInstance().getHauteur());
        mouettes.setBounds(0, 0, Parcelle.TAILLE * Modele.getInstance().getLargeur(), Parcelle.TAILLE * Modele.getInstance().getHauteur());
        parcellesContainer.setPreferredSize( new Dimension(Parcelle.TAILLE * Modele.getInstance().getLargeur(), Parcelle.TAILLE * Modele.getInstance().getHauteur()));

        // Ajout de la mouette au LayeredPane
        parcellesContainer.add(mouettes, Integer.valueOf(2));

        // Ajout des composants à la fenêtre
        add(parcellesContainer);
        add(menu);
        pack();
    }

    @Override
    public void update(Observable o, Object arg) {
        mouettes.update(o, arg);

        //update every parcelle
        for (int x=0; x< Modele.getInstance().getLargeur(); x++) {
            for (int y = 0; y < Modele.getInstance().getHauteur(); y++) {
                tabParcelles[x][y].update(o,arg);
            }
        }
        try{
            ArgentPanel.getInstance().update(o,arg);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

