package VueController;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

import Modele.Legume.Legume;
import Modele.Modele;
import Modele.Sauvegarde;
import VueController.BarMenu.BarMenu;
import VueController.ClavierListener.ClavierListener;
import VueController.Mouette.GestionnaireMouette;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
     * position de la parcelle selectionnée
     */
    private final int[] positionSelectionnee = {0,0};

    /**
     * Parcelle selectionnée
     */
    private ParcelleBase parcelleSelectionnee = null;

    /**
     * Label parcelle selectionnée
     */
    private final JTextArea  labelParcelleSelectionnee = new JTextArea ();

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

        //Panel principal contenant toutes les parcelles du potager
        JPanel jpn = new JPanel (new GridLayout(Modele.getInstance().getLargeur(),Modele.getInstance().getHauteur()));

        //Panel affichant la météo et les infos d'une parcelle
        JPanel menu = new JPanel (new GridLayout(5,1));
        menu.setPreferredSize(new Dimension(300,500));
        menu.setBackground(new Color(134,151,224));

        //Affichage de l'heure dynamique et de la date actuelle
        JLabel clock = new JLabel();
        clock.setFont(new Font("",Font.BOLD, 20));
        clock.setHorizontalAlignment(clock.CENTER);
        Timer timer = new Timer(1000, e -> clock.setText(DateFormat.getDateTimeInstance().format(new Date())));
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();
        menu.add(clock);
        //Contient les parcelles + les mouettes
        JLayeredPane parcellesContainer = new JLayeredPane();
        //contient les parcelles
        JPanel parcelles = new JPanel(new GridLayout(Modele.getInstance().getLargeur(), Modele.getInstance().getHauteur()));
        parcellesContainer.add(parcelles);

        JPanel menu = new JPanel(new GridLayout(3, 1));

        //Affichage météo
        menu.add(MeteoPanel.getInstance());


//        //Création son
//        try {
//            Clip audio = AudioSystem.getClip();
//            //audio.open(AudioSystem.getAudioInputStream(class.getResource()));
//            audio.start();
//            audio.loop(Clip.LOOP_CONTINUOUSLY);
//            //audio.stop();
//        } catch (LineUnavailableException e) {
//            throw new RuntimeException(e);
//        }


        //Création slider de vitesse de pousse
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 10, 1);
        slider.addChangeListener(e -> Modele.getInstance().setVitesse(slider.getValue()));
        slider.setPaintTicks(true);
        slider.setPaintTrack(true);
        slider.setPaintLabels(true);//Le tout permet l'affichage des traits et valeurs du slider
        slider.setMajorTickSpacing(1);
        slider.setOpaque(false);
        //Non focusable pour ne pas provoquer d'interférence avec le clavier
        slider.setFocusable(false);
        //Affichage slider de vitesse de pousse
        menu.add(new JLabel("Vitesse de pousse :"));

        menu.add(slider);

        try{
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

        parcelleSelectionnee = new ParcelleBase(0,0);
        JPanel info = new JPanel(new GridBagLayout());

        info.add(parcelleSelectionnee);
        info.add(labelParcelleSelectionnee);

        labelParcelleSelectionnee.setOpaque(false);
        menu.add(info);


        parcelles.setBounds(0, 0, Parcelle.TAILLE * Modele.getInstance().getLargeur(), Parcelle.TAILLE * Modele.getInstance().getHauteur());
        gestionnaireMouette.setBounds(0, 0, Parcelle.TAILLE * Modele.getInstance().getLargeur(), Parcelle.TAILLE * Modele.getInstance().getHauteur());
        parcellesContainer.setPreferredSize( new Dimension(Parcelle.TAILLE * Modele.getInstance().getLargeur(), Parcelle.TAILLE * Modele.getInstance().getHauteur()));

        // Ajout de la mouette au LayeredPane
        parcellesContainer.add(gestionnaireMouette, Integer.valueOf(2));

        // Ajout des composants à la fenêtre
        add(parcellesContainer);
        //Ajout du panel principal et du panneau latéral de menu à la fenêtre
        add(menu);
        pack();
    }

    /**
     * Mise à jour de la vue de toutes les parcelles
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        MeteoPanel.getInstance().update(o,arg);
        parcelleSelectionnee.update(o, arg);
        int x = positionSelectionnee[0];
        int y = positionSelectionnee[1];

        float humidite = Modele.getInstance().getParcelle(x,y).getHumidite();
        Legume legume = Modele.getInstance().getParcelle(x,y).getLegume();
        String type;

        if(Modele.getInstance().getParcelle(x, y).aDeLHerbe()){
            if(Modele.getInstance().aUnRocher(x,y)){
                type = "Rocher";
            }
            else if(Modele.getInstance().getParcelle(x, y).getFleure() != -1){
                type = "Fleure";
            }
            else{
                type = "Herbe";
            }
        }
        else{
            type = "Terre";
        }

        String info = "Parcelle : " +type + "\nHumidité : " + humidite;
        if(legume != null){
            info += "\nLegume : " + legume.getNom() + "\nCroissance : " + legume.getCroissance() + "\nPourriture :" + Math.floor(legume.getTauxPourriture() * 100) + "%";
        }
        else {
            info += "\nLegume : Aucun";
        }

        labelParcelleSelectionnee.setText(info);

        gestionnaireMouette.update(o, arg);

        //update every parcelle
        for (x=0; x< Modele.getInstance().getLargeur(); x++) {
            for (y = 0; y < Modele.getInstance().getHauteur(); y++) {
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

    /**
     * Retourne la position de la parcelle selectionnée
     * @return positionSelectionnee
     */
    public int[] getPositionSelectionnee() {
        return positionSelectionnee;
    }

    /**
     * Modifie la position de la parcelle selectionnée
     * @param x position x
     * @param y position y
     */
    public void setPositionSelectionnee(int x, int y) {
        if(x < 0 || x >= Modele.getInstance().getLargeur() || y < 0 || y >= Modele.getInstance().getHauteur())
            return;
        this.positionSelectionnee[0] = x;
        this.positionSelectionnee[1] = y;
        parcelleSelectionnee.setPosition(x,y);
    }
}