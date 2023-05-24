package VueController;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import Modele.Modele;
import Modele.Legume.Legume;
import Modele.Legume.Tomate;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.awt.image.*;


public class Parcelle  extends JLayeredPane  implements Observer{

    /**
     * Chemin vers l'image de la terre
     */
    private static final String terreImageUrl = "./src/images/Terre.png";
    
    /**
     * Indice X de la parcelle
     */
    private int indiceX;

    /**
     * Indice Y de la parcelle
     */
    private int indiceY;

    /**
     * Label contenant l'image de la terre
     */
    private JLabel labelTerre;
    
    /**
     * Label contenant l'image du legume
     */
    private JLabel labelLegume;
    
    /**
     * Taille de la parcelle
     */
    private static final int TAILLE = 50; 

    /**
     * Map de toutes les images possible contenue dans la parcelle
     */
    private static HashMap<String, BufferedImage> imageMap;

    /**
     * Initialisation de la map d'image
     */
    static {
        imageMap = new HashMap<String, BufferedImage>();
        try {
            imageMap.put(Tomate.nom,ImageIO.read(new File(Tomate.image)));   
            imageMap.put("Terre",ImageIO.read(new File(terreImageUrl)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructeur de la parcelle
     * @param indiceX Indice X de la parcelle
     * @param indiceY Indice Y de la parcelle
     * @throws IOException
     */
    Parcelle(int indiceX,int indiceY) throws IOException{
        super();
        this.indiceX = indiceX;
        this.indiceY = indiceY;

        //Création du label du fond correspondant à la terre
        labelTerre = new JLabel(new ImageIcon(imageMap.get("Terre").getScaledInstance(TAILLE, TAILLE, java.awt.Image.SCALE_SMOOTH)));
        //Création du label du legume
        labelLegume = new JLabel();

        labelTerre.setBounds(0, 0,TAILLE, TAILLE);

        this.add(labelTerre, JLayeredPane.DEFAULT_LAYER);
        this.add(labelLegume, JLayeredPane.PALETTE_LAYER);

        //Taille de la parcelle
        this.setPreferredSize(new Dimension( 50, 50));

        //Ajout de l'écouteur de clic droit
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //on verifie si click droit
                if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                    //Demande a la vue d'afficher le menu contextuel
                    Vue.getInstance().showContextMenu(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        //Si la parcelle est modifié on met a jour l'image du legume
        Legume legume = Modele.getInstance().getLegume(indiceX, indiceY);
        
        if(legume != null){
            //Calcul de la taille de l'image en fonction de la croissance du legume
            int taille = (int)(TAILLE*legume.getCroissance()/100);
            if(taille < 5)taille = 5;

            //Mise a jour de l'image du legume
            this.labelLegume.setIcon(new ImageIcon(imageMap.get(legume.getNom()).getScaledInstance(
                taille, 
                taille, 
                java.awt.Image.SCALE_SMOOTH)));
            //Positionnement de l'image du legume
            labelLegume.setBounds((TAILLE/2)-(taille/2),0,TAILLE, TAILLE);
        }
        else{
            this.labelLegume.setIcon(null);
        }
    }

    /**
     * Retourne l'indice X de la parcelle
     * 
     * @return indiceX 
     */
    public int getIndiceX(){
        return this.indiceX;
    }
    
    /**
     * Retourne l'indice Y de la parcelle
     * 
     * @return indiceY 
     */
    public int getIndiceY(){
        return this.indiceY;
    }

}
