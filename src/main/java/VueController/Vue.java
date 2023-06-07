package VueController;
import javax.swing.*;

import Modele.Sauvegarde;
import Modele.Modele;
import VueController.BarMenu.BarMenu;
import VueController.ClavierListener.ClavierListener;
import VueController.Mouette.GestionnaireMouette;
import VueController.Menu.Menu;

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
    private final Parcelle[][] tabParcelles;

    /**
     * Instance de la vue
     */
    private static Vue instance = null; 

    /**
     * Mouettes
     */
    private final GestionnaireMouette gestionnaireMouette = new GestionnaireMouette();

    /**
     * Menu
     */
    private Menu menu;

    /**
     * Constructeur de la vue
     */
    private Vue(){
        super();
        tabParcelles = new Parcelle[Modele.getInstance().getLargeur()][Modele.getInstance().getLargeur()];
        try {
            build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Sauvegarde.sauvegarder();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(0);
            }
        });

        setFocusable(true);

        //On key down
        addKeyListener(new ClavierListener());
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
     * @throws IOException Exception
     */
    private void build() throws IOException {
        // setLayout left to right
        setLayout(new FlowLayout());

        setTitle("Potagame");

        setJMenuBar(new BarMenu());

        //Affichage de l'heure dynamique et de la date actuelle

        //Contient les parcelles + les mouettes
        JLayeredPane parcellesContainer = new JLayeredPane();

        //contient les parcelles
        JPanel parcelles = new JPanel(new GridLayout(Modele.getInstance().getLargeur(), Modele.getInstance().getHauteur()));
        parcellesContainer.add(parcelles);

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

        menu = new Menu();

        parcelles.setBounds(0, 0, Parcelle.TAILLE * Modele.getInstance().getLargeur(), Parcelle.TAILLE * Modele.getInstance().getHauteur());
        gestionnaireMouette.setBounds(0, 0, Parcelle.TAILLE * Modele.getInstance().getLargeur(), Parcelle.TAILLE * Modele.getInstance().getHauteur());
        parcellesContainer.setPreferredSize( new Dimension(Parcelle.TAILLE * Modele.getInstance().getLargeur(), Parcelle.TAILLE * Modele.getInstance().getHauteur()));
        menu.setPreferredSize(new Dimension(300,Parcelle.TAILLE * Modele.getInstance().getHauteur()));

        // Ajout de la mouette au LayeredPane
        parcellesContainer.add(gestionnaireMouette, Integer.valueOf(2));

        add(parcellesContainer);
        add(menu);
        pack();
        setResizable(false);
    }

    /**
     * Mise à jour de la vue de toutes les parcelles
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        menu.update(o,arg);

        gestionnaireMouette.update(o, arg);

        //update every parcelle
        for (int x=0; x< Modele.getInstance().getLargeur(); x++) {
            for (int y = 0; y < Modele.getInstance().getHauteur(); y++) {
                tabParcelles[x][y].update(o,arg);
            }
        }
    }
}