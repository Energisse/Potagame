package VueController;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;

import Modele.Modele;
import Modele.Legume.Legume;
import Modele.Legume.Salade;
import Modele.Legume.Tomate;
import java.awt.image.*;


public class Parcelle  extends JLayeredPane  implements Observer{

    /**
     * Indice X de la parcelle
     */
    private final int indiceX;

    /**
     * Indice Y de la parcelle
     */
    private final int indiceY;

    /**
     * Label contenant l'image de la terre
     */
    private final JLabel labelTerre;

    /**
     * Label contenant l'image du legume
     */
    private final JLabel labelLegume;

    /**
     * Label contenant l'objet
     */
    private final JLabel labelObjet;

    /**
     * Label contenant la bordure d'action de l'epouvantail
     */
    private final JLabel labelBordureEpouvantail;
    
    /**
     * Taille de la parcelle
     */
    public static final int TAILLE = 50;

    /**
     * Map de toutes les images possible contenue dans la parcelle
     */
    private static final HashMap<String, ImageIcon> imageMap;

    /**
     * Etat de l'herbe
     */
    enum EtatHerbe {
        NON_HERBE,
        HERBE,
        HERBE_FLEURE1,
        HERBE_FLEURE2,
        HERBE_FLEURE3,
    }

    /**
     * Etat de l'herbe
     */
    private EtatHerbe lastEtatHerbe = EtatHerbe.NON_HERBE;

    static {
        imageMap = new HashMap<>();
        try {
            imageMap.put("terreHumide",new ImageIcon(ImageIO.read(new File("./src/images/Terre_humide.png")).getScaledInstance(TAILLE, TAILLE, Image.SCALE_SMOOTH)));
            imageMap.put("terre",new ImageIcon(ImageIO.read(new File("./src/images/Terre.png")).getScaledInstance(TAILLE, TAILLE, Image.SCALE_SMOOTH)));

            imageMap.put("herbe",new ImageIcon(ImageIO.read(new File("./src/images/Herbe.png")).getScaledInstance(TAILLE, TAILLE, Image.SCALE_SMOOTH)));
            imageMap.put("rocher",new ImageIcon(ImageIO.read(new File("./src/images/Rocher.png")).getScaledInstance(TAILLE, TAILLE, Image.SCALE_SMOOTH)));

            imageMap.put("crame",new ImageIcon(ImageIO.read(new File("./src/images/Crame.png")).getScaledInstance(TAILLE, TAILLE, Image.SCALE_SMOOTH)));
            imageMap.put("pourriture",new ImageIcon(ImageIO.read(new File("./src/images/Pourriture.png")).getScaledInstance(TAILLE, TAILLE, Image.SCALE_SMOOTH)));

            imageMap.put("epouvantail",new ImageIcon(ImageIO.read(new File("./src/images/Epouvantail.png")).getScaledInstance(TAILLE, TAILLE, Image.SCALE_SMOOTH)));

            imageMap.put("herbeFleure1",new ImageIcon(ImageIO.read(new File("./src/images/Herbe_fleure1.png")).getScaledInstance(TAILLE, TAILLE, Image.SCALE_SMOOTH)));
            imageMap.put("herbeFleure2",new ImageIcon(ImageIO.read(new File("./src/images/Herbe_fleure2.png")).getScaledInstance(TAILLE, TAILLE, Image.SCALE_SMOOTH)));
            imageMap.put("herbeFleure3",new ImageIcon(ImageIO.read(new File("./src/images/Herbe_fleure3.png")).getScaledInstance(TAILLE, TAILLE, Image.SCALE_SMOOTH)));
            imageMap.put(Tomate.nom,new ImageIcon(ImageIO.read(new File(Tomate.image)).getScaledInstance(TAILLE, TAILLE, Image.SCALE_SMOOTH)));
            imageMap.put(Salade.nom,new ImageIcon(ImageIO.read(new File(Salade.image)).getScaledInstance(TAILLE, TAILLE, Image.SCALE_SMOOTH)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructeur de la parcelle
     * @param indiceX Indice X de la parcelle
     * @param indiceY Indice Y de la parcelle
     * @throws IOException Exception si l'image n'est pas trouvée
     */
    Parcelle(int indiceX,int indiceY) throws IOException{
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

        labelBordureEpouvantail = new JLabel();
        labelBordureEpouvantail.setBounds(0, 0,TAILLE, TAILLE);

        this.add(labelBordureEpouvantail, JLayeredPane.POPUP_LAYER);

        JLabel labelBordure = new JLabel();

        this.add(labelBordure, JLayeredPane.POPUP_LAYER);
        labelBordure.setBounds(0, 0,TAILLE, TAILLE);

        //Taille de la parcelle
        this.setPreferredSize(new Dimension( 50, 50));
        setComponentPopupMenu(ContextMenu.getInstance());

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if(evt.getButton() == java.awt.event.MouseEvent.BUTTON1){
                    Modele.getInstance().recolter(indiceX, indiceY);
                }
            }

            /**
             * Change la bordure de la parcelle quand la souris est dessus
             */
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelBordure.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            }

            /**
             * Change la bordure de la parcelle quand la souris n'est plus dessus
             */
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelBordure.setBorder(null);
            }
        });
    }

    /**
     * Met a jour l'image de la parcelle
     */
    private void updateImage(){
        //Ajoute l'image de l'herbe
        if(Modele.getInstance().getParcelle(indiceX, indiceY).aDeLHerbe()){
            switch (this.lastEtatHerbe) {
                case HERBE -> this.labelTerre.setIcon(imageMap.get("herbe"));
                case HERBE_FLEURE1 -> this.labelTerre.setIcon(imageMap.get("herbeFleure1"));
                case HERBE_FLEURE2 -> this.labelTerre.setIcon(imageMap.get("herbeFleure2"));
                case HERBE_FLEURE3 -> this.labelTerre.setIcon(imageMap.get("herbeFleure3"));
            }
            if(Modele.getInstance().getParcelle(indiceX, indiceY).aUnRocher()){
                this.labelLegume.setIcon(imageMap.get("rocher"));
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
                labelLegume.setIcon(new ImageIcon(imageMap.get(legume.getNom()).getImage().getScaledInstance(taille,taille, java.awt.Image.SCALE_SMOOTH)));
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
        ).getScaledInstance(TAILLE,TAILLE, java.awt.Image.SCALE_SMOOTH)));

        if(Modele.getInstance().getObjet(indiceX,indiceY) != null){
            labelObjet.setIcon(imageMap.get(Modele.getInstance().getObjet(indiceX,indiceY).getNom()));
            labelObjet.setBounds(0,0,TAILLE, TAILLE);
        }
        else{
            labelObjet.setIcon(null);
        }

        //si il y a un epouvantail
        if(Modele.getInstance().getNbEpouvantail(indiceX,indiceY) > 0){
            labelBordureEpouvantail.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));
        }
        else{
            labelBordureEpouvantail.setBorder(null);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        //detecte un changement d'etat de l'herbe
        if(Modele.getInstance().aDeLHerbe(indiceX,indiceY)){
            if(lastEtatHerbe == EtatHerbe.NON_HERBE){
                lastEtatHerbe = EtatHerbe.HERBE;
                if(!Modele.getInstance().aUnRocher(indiceX,indiceY)) {
                    int rand = (int) Math.floor(Math.random() * 10);
                    switch (rand) {
                        case 0 -> lastEtatHerbe = EtatHerbe.HERBE_FLEURE1;
                        case 1 -> lastEtatHerbe = EtatHerbe.HERBE_FLEURE2;
                        case 2 -> lastEtatHerbe = EtatHerbe.HERBE_FLEURE3;
                    }
                }
            }
        }
        else{
            lastEtatHerbe = EtatHerbe.NON_HERBE;
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
}