package VueController;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.awt.AlphaComposite;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import Modele.Modele;
import Modele.Legume.Legume;
import Modele.Legume.Salade;
import Modele.Legume.Tomate;
import java.awt.image.*;


public class Parcelle  extends JLayeredPane  implements Observer{

    /**
     * ImageIcon de la terre humide
     */
    private static BufferedImage terreHumideImage;
    
    /**
     * ImageIcon de la terre 
     */
    private static BufferedImage terreImage;
    
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
     * Label contenant l'image de la terre humide
     */
    private JLabel labelTerreHumide;
    
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
            terreHumideImage = ImageIO.read(new File("./src/images/Terre_humide.png"));
            terreImage = ImageIO.read(new File("./src/images/Terre.png"));
            
            imageMap.put(Tomate.nom,ImageIO.read(new File(Tomate.image)));   
            imageMap.put(Salade.nom,ImageIO.read(new File(Salade.image)));
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
        labelTerre = new JLabel(new ImageIcon(terreImage.getScaledInstance(TAILLE,TAILLE, java.awt.Image.SCALE_SMOOTH)));

        //Création du label du fond correspondant à la terre humide
        labelTerreHumide = new JLabel();
        
        //Création du label du legume
        labelLegume = new JLabel();

        labelTerre.setBounds(0, 0,TAILLE, TAILLE);
        labelTerreHumide.setBounds(0, 0,TAILLE, TAILLE);
        //change opacity of labelTerreHumide

        this.add(labelTerreHumide, JLayeredPane.DEFAULT_LAYER);
        this.add(labelTerre, JLayeredPane.DEFAULT_LAYER);
        this.add(labelLegume, JLayeredPane.PALETTE_LAYER);
        updateImage();
        //Taille de la parcelle
        this.setPreferredSize(new Dimension( 50, 50));
        setComponentPopupMenu(ContextMenu.getInstance());
    }

    /**
     * Met a jour l'image de la parcelle
     */
    private void updateImage(){
         // Créer une nouvelle BufferedImage avec un type d'image compatible avec la transparence (TYPE_INT_ARGB)
         BufferedImage transparentImage = new BufferedImage(
            terreHumideImage.getWidth(), terreHumideImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // Obtenir le contexte graphique de l'image transparente
        Graphics2D g2d = transparentImage.createGraphics();

        // Activer la transparence
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Modele.getInstance().getHumidite(indiceX,indiceY) / 100f));

        // Dessiner l'image source sur l'image transparente
        g2d.drawImage(terreHumideImage, 0, 0, null);

        // Libérer les ressources graphiques
        g2d.dispose();

        labelTerreHumide.setIcon(new ImageIcon(transparentImage.getScaledInstance(TAILLE,TAILLE, java.awt.Image.SCALE_SMOOTH)));
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
        updateImage();
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