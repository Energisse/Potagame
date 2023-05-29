package VueController;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

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
     * ImageIcon de l'herbe
     */
    private static ImageIcon herbeImage;

    /**
     * ImageIcon du Rocher
     */
    private static ImageIcon rocherImage;
    
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
            terreHumideImage = ImageIO.read(new File("./src/images/Terre_humide.png"));
            terreImage = ImageIO.read(new File("./src/images/Terre.png"));

            herbeImage = new ImageIcon(ImageIO.read(new File("./src/images/Herbe.png")).getScaledInstance(TAILLE, TAILLE, java.awt.Image.SCALE_SMOOTH));
            rocherImage = new ImageIcon(ImageIO.read(new File("./src/images/Rocher.png")).getScaledInstance(TAILLE, TAILLE, java.awt.Image.SCALE_SMOOTH));

            imageMap.put("crame",ImageIO.read(new File("./src/images/Crame.png")));
            imageMap.put("pourriture",ImageIO.read(new File("./src/images/Pourriture.png")));
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
        labelTerre = new JLabel();

        //Création du label du legume
        labelLegume = new JLabel();

        labelTerre.setBounds(0, 0,TAILLE, TAILLE);
        //change opacity of labelTerreHumide

        this.add(labelTerre, JLayeredPane.DEFAULT_LAYER);
        this.add(labelLegume, JLayeredPane.PALETTE_LAYER);
        updateImage();
        //Taille de la parcelle
        this.setPreferredSize(new Dimension( 50, 50));
        setComponentPopupMenu(ContextMenu.getInstance());

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if(evt.getButton() == java.awt.event.MouseEvent.BUTTON1){
                    Modele.getInstance().recolter(indiceX, indiceY);
                }
            }
        });
    }

    /**
     * Met a jour l'image de la parcelle
     */
    private void updateImage(){
        if(Modele.getInstance().getParcelle(indiceX, indiceY).aDeLHerbe()){
            this.labelTerre.setIcon(herbeImage);
            if(Modele.getInstance().getParcelle(indiceX, indiceY).aUnRocher()){
                this.labelLegume.setIcon(rocherImage);
                labelLegume.setBounds(0,0,TAILLE, TAILLE);
            }
            else{
                this.labelLegume.setIcon(null);
            }
            return;
        }

        //Si la parcelle est modifié on met a jour l'image du legume
        Legume legume = Modele.getInstance().getLegume(indiceX, indiceY);

        if(legume != null){
            //Calcul de la taille de l'image en fonction de la croissance du legume
            int taille = (int)(TAILLE*legume.getCroissance()/100);
            if(taille < 5)taille = 5;

            //On met a jour l'image du legume
            //Si le legume est malade on met a jour l'image en fonction de la maladie
            if(Modele.getInstance().getTauxMaladie(indiceX, indiceY) > 0){
                labelLegume.setIcon(new ImageIcon(intersectionImage(
                        imageMap.get(legume.getNom()),
                        imageMap.get("pourriture"),
                        Modele.getInstance().getTauxMaladie(indiceX,indiceY)
                ).getScaledInstance(taille,taille, java.awt.Image.SCALE_SMOOTH)));
            }
            //Si le legume est brulé on met a jour l'image en fonction du taux de brulure
            else if(Modele.getInstance().getTauxBrulure(indiceX, indiceY) > 0){
                labelLegume.setIcon(new ImageIcon(intersectionImage(
                        imageMap.get(legume.getNom()),
                        imageMap.get("crame"),
                        Modele.getInstance().getTauxBrulure(indiceX,indiceY)
                ).getScaledInstance(taille,taille, java.awt.Image.SCALE_SMOOTH)));
            }
            //Sinon on met a jour l'image du legume
            else{
                labelLegume.setIcon(new ImageIcon(imageMap.get(legume.getNom()).getScaledInstance(taille,taille, java.awt.Image.SCALE_SMOOTH)));
            }

            //Positionnement de l'image du legume
            labelLegume.setBounds((TAILLE/2)-(taille/2),0,TAILLE, TAILLE);
        }
        else{
            this.labelLegume.setIcon(null);
        }

        labelTerre.setIcon(new ImageIcon(superpossitionImage(terreImage,terreHumideImage,Modele.getInstance().getHumidite(indiceX,indiceY) / 100f).getScaledInstance(TAILLE,TAILLE, java.awt.Image.SCALE_SMOOTH)));
    }

    @Override
    public void update(Observable o, Object arg) {
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


    /**
     * Superpose deux images
     * @param source Image de fond
     * @param superposition Image a superposer
     * @param alpha Opacité de l'image a superposer
     * @return
     */
    private BufferedImage superpossitionImage(BufferedImage source, BufferedImage superposition,float alpha ){
        // Créer une nouvelle BufferedImage avec un type d'image compatible avec la transparence (TYPE_INT_ARGB)
        BufferedImage sortie = new BufferedImage(
                source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // Obtenir le contexte graphique de l'image transparente
        Graphics2D g2d = sortie.createGraphics();

        // Dessiner l'image source sur l'image transparente
        g2d.drawImage(source, 0, 0, null);

        // Activer la transparence
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        // Dessiner l'image source sur l'image transparente
        g2d.drawImage(superposition, 0, 0, null);

        // Libérer les ressources graphiques
        g2d.dispose();

        return  sortie;
    }

    /**
     * Intersection de deux images
     * @param source Image de fond
     * @param intersection Image a superposer
     * @param alpha Opacité de l'image a superposer
     * @return
     */
    private BufferedImage intersectionImage(BufferedImage source, BufferedImage intersection,float alpha){

        //Etape 1 : Detoure l'image intersection avec la source en effecant un SRV_IN
        //Etape 2 : Superpose l'image source avec l'image intersection

        // Créer une nouvelle BufferedImage avec un type d'image compatible avec la transparence (TYPE_INT_ARGB)
        BufferedImage sortie = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // Obtenir le contexte graphique de l'image transparente
        Graphics2D g2d = sortie.createGraphics();

        // Dessiner l'image source sur l'image transparente
        g2d.drawImage(source, 0, 0, null);

        //active la transparence en multiplication
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN)    );

        g2d.drawImage(intersection, 0, 0, null);

        // Libérer les ressources graphiques
        g2d.dispose();

        return superpossitionImage(sortie,source,1-alpha);
    }
}