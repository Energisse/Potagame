package VueController;
import javax.swing.*;

import Modele.Modele;

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
     * Composant Context menu
     */
    ContextMenu contextMenu = new ContextMenu();
    
    /**
     * Instance de la vue
     */
    private static Vue instance = null; 

    /**
     * Constructeur de la vue
     */
    private Vue(){
        super();
        tabParcelles = new Parcelle[Modele.getInstance().getLargeur()][Modele.getInstance().getLargeur()];
        build();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
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
     */
    private void build (){
        setTitle("Potagame");
        //Panel principal contenant toutes les parcelles du potager
        JPanel jpn = new JPanel (new GridLayout(Modele.getInstance().getLargeur(),Modele.getInstance().getHauteur()));
        
        //Initialisation des parcelles
        for (int x=0; x<Modele.getInstance().getLargeur();x++){
            for(int y=0; y<Modele.getInstance().getHauteur(); y++){
                try {
                    tabParcelles[x][y]= new Parcelle(x,y);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                jpn.add(tabParcelles[x][y]);
            }
        }

        //Ajout du panel principal à la fenêtre
        setContentPane(jpn);
        pack();
    }

    /**
     * Affiche le context menu
     * @param invoker Parcelle qui a invoqué le context menu
     * @param x Position x a laquelle afficher le context menu
     * @param y Position y a laquelle afficher le context menu
     */
    public void showContextMenu(Component invoker, int x, int y){
        contextMenu.show(invoker, x, y);
    }

    @Override
    public void update(Observable o, Object arg) {
        //update every parcelle
        for (int x=0; x< Modele.getInstance().getLargeur(); x++) {
            for (int y = 0; y < Modele.getInstance().getHauteur(); y++) {
                tabParcelles[x][y].update(o,arg);
            }
        }
    }
}

