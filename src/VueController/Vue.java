package VueController;

import javax.swing.*;
import Modele.Modele;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
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
     * @throws IOException
     */
    private void build () throws IOException{
        //setLayout left to right
        setLayout(new FlowLayout());
        setTitle("Potagame");

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

        //Affichage météo
        menu.add(MeteoPanel.getInstance());


      /*  //Création son
        try {
            Clip audio = AudioSystem.getClip();
            audio.open(AudioSystem.getAudioInputStream(new FileInputStream("./src/son/Cybersdf-Dolling.wav")));
            audio.start();
            audio.loop(Clip.LOOP_CONTINUOUSLY);
            //audio.stop();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }*/


        //Création slider de vitesse de pousse
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 10, 1);
        slider.addChangeListener(e -> Modele.getInstance().setVitesse(slider.getValue()));
        slider.setPaintTicks(true);
        slider.setPaintTrack(true);
        slider.setPaintLabels(true);//Le tout permet l'affichage des traits et valeurs du slider
        slider.setMajorTickSpacing(1);
        slider.setOpaque(false);
        //Affichage slider de vitesse de pousse
        menu.add(new JLabel("Vitesse de pousse :"));
        menu.add(slider);
        try{
            menu.add(ArgentPanel.getInstance());
        }
        catch(Exception e){
            e.printStackTrace();
        }

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

        //Ajout du panel principal et du panneau latéral de menu à la fenêtre
        add(jpn);
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
        try{
            MeteoPanel.getInstance().update(o,arg);
            //update every parcelle
            for (int x=0; x< Modele.getInstance().getLargeur(); x++) {
                for (int y = 0; y < Modele.getInstance().getHauteur(); y++) {
                    tabParcelles[x][y].update(o,arg);
                }
            }
            ArgentPanel.getInstance().update(o,arg);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

