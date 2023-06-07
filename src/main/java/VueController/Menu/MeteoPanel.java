package VueController.Menu;

import VueController.ImageLoader;
import Modele.Modele;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MeteoPanel extends JPanel implements Observer {

    /**
     * Label contenant l'icone de la météo
     */
    private final JLabel iconeLabel;

    /**
     * Label contenant la météo
     */
    private final JLabel meteoJLabel;

    /**
     * Variable permettant de faire évoluer l'icone de la météo
     */
    int evolutionIcone =0 ;

    /**
     * Tableau contenant les icones de la météo
     */
    private static final ImageIcon[] iconesMeteo;

    static {
        iconesMeteo = new ImageIcon[3];
        iconesMeteo[0] = ImageLoader.loadIcon("Soleil.png",64);
        iconesMeteo[1] = ImageLoader.loadIcon("Nuageux.png",64);
        iconesMeteo[2] = ImageLoader.loadIcon("Pluie.png",64);
    }

    /**
     * Constructeur de MeteoPanel permettant de faire passer l'affichage de la météo
     */
    public MeteoPanel() {
        super();
        setOpaque(false);
        iconeLabel = new JLabel();
        meteoJLabel = new JLabel();
        add(iconeLabel);
        add(meteoJLabel);
        meteoJLabel.setHorizontalAlignment(meteoJLabel.CENTER);
    }

    /**
     * Mise à jour de l'affichage de la météo avec les valeurs de température et d'humidité ainsi que l'icone de météo
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        float temperatureTi= Modele.getInstance().getMeteo().temperature();
        float humiditeTi= Modele.getInstance().getMeteo().humidite();
        if (humiditeTi <=60 ){
            evolutionIcone=0;//0=soleil
        }
        else if(humiditeTi<=75){
            evolutionIcone=1;//1=nuageux
        }
        else{
            evolutionIcone=2;//2=pluie
        }
        try {
            iconeLabel.setIcon(iconesMeteo[evolutionIcone]);
            meteoJLabel.setText("<html> La météo du jour : "+
                    //Année 2023 car non bissextile et date de création du jeu
                    "<br> - " + LocalDate.ofYearDay(2023, Modele.getInstance().getTemps()/24%365  + 1).format(DateTimeFormatter.ofPattern("dd MMMM"))+
                    "<br> - Température : "+temperatureTi+"°C" +
                    "<br> - Humidité : "+humiditeTi+"% </html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

