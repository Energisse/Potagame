package VueController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {

    private ImageLoader() {
    }

    /**
     * Charge une image
     * @param path chemin de l'image
     * @return BufferedImage
     */
    public static BufferedImage loadImage(String path) {
        try {
            URL url =ImageLoader.class.getClassLoader().getResource("images/"+path);
            return ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException("Le fichier " + path + " n'a pas pu être chargé",e);
        }
    }

    /**
     * Charge une image et la redimensionne
     * @param path chemin de l'image
     * @param size taille de l'image
     * @return ImageIcon
     */
    public static ImageIcon loadIcon(String path, int size) {
        return loadIcon(path, size, size);
    }

    /**
     * Charge une image et la redimensionne
     * @param path chemin de l'image
     * @param width largeur de l'image
     * @param height hauteur de l'image
     * @return ImageIcon
     */
    public static ImageIcon loadIcon(String path, int width, int height) {
        return new ImageIcon(loadImage(path).getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
    }
}
