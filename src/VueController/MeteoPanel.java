package VueController;
import Modele.Meteo.Meteo;
import Modele.Modele;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

public class MeteoPanel extends JPanel implements Observer {
    private static MeteoPanel instance = null;
    private JLabel meteoJLabel;
    static int evolutionIcone=1;
    static ArrayList<String> typeMeteo= new ArrayList<String>(Arrays.asList("soleil","nuageux","pluie"));
    private ImageIcon iconeMeteo= new ImageIcon(ImageIO.read(new File("./src/images/"+typeMeteo.get(evolutionIcone)+".png")));

    /**
     * Constructeur de MeteoPanel permettant de faire passer l'affichage de la météo
     * @throws IOException
     */
    public MeteoPanel() throws IOException {
        super();
        float temperatureTi= Meteo.getInstance().getMeteodata(Modele.getInstance().getTemps()).getTemperatureDuJour();
        float humiditeTi=Meteo.getInstance().getMeteodata(Modele.getInstance().getTemps()).getHumiditeDuJour();
        if (humiditeTi <=60 ){
            evolutionIcone=0;//0=soleil
        }
        else if(humiditeTi<=75){
            evolutionIcone=1;//1=nuageux
        }
        else{
            evolutionIcone=2;//2=pluie
        }
        add(new JLabel(iconeMeteo));
        meteoJLabel = new JLabel("<html> Votre météo du jour : "+
                    "<br> - Température : " + temperatureTi +"°C" +
                    "<br> - Humidité : " + humiditeTi + "% </html>");
        meteoJLabel.setHorizontalAlignment(meteoJLabel.CENTER);
        add(meteoJLabel);
        setOpaque(false);
    }

    /**
     * Retourne une instance de MeteoPanel
     * @return
     * @throws IOException
     */
    public static MeteoPanel getInstance() throws IOException {
        if (instance == null) {
            instance = new MeteoPanel();
        }
        return instance;
    }


    /**
     * Mise à jour de l'affichage de la météo avec les valeurs de température et d'humidité ainsi que l'icone de météo
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        float temperatureTi= Meteo.getInstance().getMeteodata(Modele.getInstance().getTemps()).getTemperatureDuJour();
        float humiditeTi=Meteo.getInstance().getMeteodata(Modele.getInstance().getTemps()).getHumiditeDuJour();
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
            if(Modele.getInstance().getTemps()==Modele.getInstance().getVitesse()){
                //iconeMeteo = new ImageIcon(ImageIO.read(new File("./src/images/"+typeMeteo.get(evolutionIcone)+".png")));
                //new JLabel().setIcon(iconeMeteo);
                add(new JLabel(iconeMeteo));
            }
            System.out.println("ajout de l'iconemeteo depuis le update de MeteoPanel");
            System.out.println("evolutionIcone="+evolutionIcone);
            meteoJLabel.setText("<html> La météo du jour : "+
                    "<br> - Température : "+temperatureTi+"°C" +
                    "<br> - Humidité : "+humiditeTi+"% </html>");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

