package VueController;

import Config.Config;
import Config.ConfigParcelle;
import Modele.Fabrique.Fabrique;
import Modele.Legume.*;
import Modele.Modele;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;


public class ParcelleBase extends JLayeredPane  implements Observer{

    /**
     * Indice X de la parcelle
     */
    protected int indiceX;

    /**
     * Indice Y de la parcelle
     */
    protected int indiceY;

    /**
     * Label contenant l'image de la terre
     */
    protected final JLabel labelTerre;

    /**
     * Label contenant l'image du legume
     */
    protected final JLabel labelLegume;

    /**
     * Label contenant l'objet
     */
    protected final JLabel labelObjet;

    /**
     * Taille de la parcelle
     */
    public static final int TAILLE = Config.getInstance().getConfigParcelle().taille();

    /**
     * Map de toutes les images possible contenue dans la parcelle
     */
    protected static final HashMap<String, ImageIcon> imageMap;


    static {
        imageMap = new HashMap<>();
        ConfigParcelle config = Config.getInstance().getConfigParcelle();
        imageMap.put("terreHumide", ImageLoader.loadIcon(config.imageTerreHumide(),TAILLE));
        imageMap.put("terre", ImageLoader.loadIcon(config.imageTerre(),TAILLE));

        imageMap.put("herbe", ImageLoader.loadIcon(config.imageHerbe(),TAILLE));
        imageMap.put("rocher", ImageLoader.loadIcon(config.imageRocher(),TAILLE));

        imageMap.put("crame", ImageLoader.loadIcon(config.imageCrame(),TAILLE));
        imageMap.put("pourriture", ImageLoader.loadIcon(config.imagePourriture(),TAILLE));


        for(int i = 0; i < config.imagesFleures().length; i++){
            imageMap.put("herbeFleure"+i, ImageLoader.loadIcon(config.imagesFleures()[i],TAILLE));
        }

        Fabrique.getInstances().forEach(fabrique-> imageMap.put(fabrique.getNom(), ImageLoader.loadIcon(fabrique.getImage(),TAILLE)));
    }

    /**
     * Constructeur de la parcelle
     * @param indiceX Indice X de la parcelle
     * @param indiceY Indice Y de la parcelle
     */
    public ParcelleBase(int indiceX, int indiceY) {
        super();
        this.indiceX = indiceX;
        this.indiceY = indiceY;

        //Création du label du fond correspondant à la terre
        labelTerre = new JLabel();

        //Création du label du legume
        labelLegume = new JLabel();

        //Création du label de l'objet
        labelObjet = new JLabel();

        labelTerre.setBounds(0, 0,TAILLE, TAILLE);
        //change opacity of labelTerreHumide

        this.add(labelTerre, JLayeredPane.DEFAULT_LAYER);
        this.add(labelLegume, JLayeredPane.PALETTE_LAYER);
        this.add(labelObjet, JLayeredPane.POPUP_LAYER);

        //Taille de la parcelle
        this.setPreferredSize(new Dimension( TAILLE, TAILLE));
        setComponentPopupMenu(ContextMenu.getInstance());
    }

    /**
     * Met a jour l'image de la parcelle
     */
    @Override
    public void update(Observable o, Object arg) {

        if(Modele.getInstance().getObjet(indiceX,indiceY) != null){
            labelObjet.setIcon(imageMap.get(Modele.getInstance().getObjet(indiceX,indiceY).getNom()));
            labelObjet.setBounds(0,0,TAILLE, TAILLE);
        }
        else{
            labelObjet.setIcon(null);
        }

        if(Modele.getInstance().aDeLHerbe(indiceX, indiceY)){
            this.labelLegume.setIcon(null);
            //Ajoute l'image de l'herbe
            if(Modele.getInstance().getParcelle(indiceX, indiceY).aDeLHerbe()){
                if(Modele.getInstance().aUnRocher(indiceX,indiceY)){
                    this.labelTerre.setIcon(imageMap.get("rocher"));
                }
                else if(Modele.getInstance().getParcelle(indiceX, indiceY).getFleure() != -1){
                    this.labelTerre.setIcon(imageMap.get("herbeFleure"+(Modele.getInstance().getParcelle(indiceX, indiceY).getFleure())));
                }
                else{
                    this.labelTerre.setIcon(imageMap.get("herbe"));
                }
            }
            else{
                this.labelTerre.setIcon(imageMap.get("terre"));
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
                ).getScaledInstance(taille,taille, Image.SCALE_SMOOTH)));
            }
            //Si le legume est brulé on met a jour l'image en fonction du taux de brulure
            else if(Modele.getInstance().getTauxBrulure(indiceX, indiceY) > 0){
                labelLegume.setIcon(new ImageIcon(intersectionImage(
                        imageMap.get(legume.getNom()),
                        imageMap.get("crame"),
                        Modele.getInstance().getTauxBrulure(indiceX,indiceY)
                ).getScaledInstance(taille,taille, Image.SCALE_SMOOTH)));
            }
            //Sinon on met a jour l'image du legume
            else{
                labelLegume.setIcon(new ImageIcon(imageMap.get(legume.getNom()).getImage().getScaledInstance(taille,taille, Image.SCALE_SMOOTH)));
            }

            //Positionnement de l'image du legume
            labelLegume.setBounds((TAILLE/2)-(taille/2),0,TAILLE, TAILLE);
        }
        else{
            this.labelLegume.setIcon(null);
        }

        labelTerre.setIcon(new ImageIcon(superpossitionImage(
                imageMap.get("terre"),
                imageMap.get("terreHumide"),
                Modele.getInstance().getHumidite(indiceX,indiceY) / 100f
        ).getScaledInstance(TAILLE,TAILLE, Image.SCALE_SMOOTH)));

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
     * @return Image superposé
     */
    private BufferedImage superpossitionImage(ImageIcon source, ImageIcon superposition,float alpha ){
        // Créer une nouvelle BufferedImage avec un type d'image compatible avec la transparence (TYPE_INT_ARGB)
        BufferedImage sortie = new BufferedImage(
                source.getIconWidth(), source.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

        // Obtenir le contexte graphique de l'image transparente
        Graphics2D g2d = sortie.createGraphics();

        // Dessiner l'image source sur l'image transparente
        g2d.drawImage(source.getImage(), 0, 0, null);

        // Activer la transparence
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        // Dessiner l'image source sur l'image transparente
        g2d.drawImage(superposition.getImage(), 0, 0, null);

        // Libérer les ressources graphiques
        g2d.dispose();

        return  sortie;
    }

    /**
     * Intersection de deux images
     * @param source Image de fond
     * @param intersection Image a superposer
     * @param alpha Opacité de l'image a superposer
     * @return Image intersection
     */
    private BufferedImage intersectionImage(ImageIcon source, ImageIcon intersection,float alpha){

        //Etape 1 : Detoure l'image intersection avec la source en effecant un SRV_IN
        //Etape 2 : Superpose l'image source avec l'image intersection

        // Créer une nouvelle BufferedImage avec un type d'image compatible avec la transparence (TYPE_INT_ARGB)
        BufferedImage sortie = new BufferedImage(source.getIconWidth(), source.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

        // Obtenir le contexte graphique de l'image transparente
        Graphics2D g2d = sortie.createGraphics();

        // Dessiner l'image source sur l'image transparente
        g2d.drawImage(source.getImage(), 0, 0, null);

        //active la transparence en multiplication
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN)    );

        g2d.drawImage(intersection.getImage(), 0, 0, null);

        // Libérer les ressources graphiques
        g2d.dispose();

        return superpossitionImage(new ImageIcon(sortie),source,1-alpha);
    }

    /**
     * Modifie la position de la parcelle
     * @param x Nouvelle position en X
     * @param y Nouvelle position en Y
     */
    public void setPosition(int x, int y) {
        this.indiceX = x;
        this.indiceY = y;
    }
}