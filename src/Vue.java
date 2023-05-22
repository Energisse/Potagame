import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class Vue extends JFrame implements Observer { //JFrame=la fenetre; Jpanel=conteneur s'inserant dans la fenetre; Jlabel=contient texte/image
    private string urlIconelegume = "D:/Cours Java/ProjetInfoEncadre/Potager/img/data.png"; //chemin de stockage des legumes
    private string urlIconeterre = "D:/Cours Java/ProjetInfoEncadre/Potager/img/Terre.png"; //chemin de stockage de la terre
    private JPanel[][] tab ;
    private Modele m;
    public Vue(Modele m){
        super();
        this.m= m;
        tab = new JPanel[m.size_x][m.size_y];
        build();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
    }

    private void build (){
        JPanel jpn = new JPanel (new GridLayout(m.size_x,m.size_y));
        for (int i=0; i<m.size_x ;i++){
            for(int j=0; j<m.size_y ; j++){
                tab[i][j]= new JPanel();
                tab[i][j].setPreferredSize(new Dimension( 50, 50));
                //tab[i][j].setBackground( m.getCaseContent(i,j) ? Color.BLACK : Color.WHITE);
                BufferedImage image = ImageIO.read(new File(urlIconeterre)); //lecture du fichier d'image
                BufferedImage img_place = image.getSubimage(10,10, m.size_x, m.size_y); // image du légume (x, y : coin supérieur gauche, w, h : largeur et hauteur)
                ImageIcon icone = img_place.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)); // icône redimentionnée
                //tab[i][j].setIcon(icone); // partie rafraichissement
            }
                tab[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW));
                jpn.add(tab[i][j]);

                final int ii = i;
                final int jj = j;
                tab[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        m.maj(ii,jj);
                    }
                });
            }
        }
        setContentPane(jpn);
        pack();
    }



    @Override
    public void update(Observable o, Object arg) {
        for (int i=0; i< m.size_x; i++) {
            for (int j = 0; j < m.size_y; j++) {
                tab[i][j].setBackground(m.getCaseContent(i, j) ? Color.BLACK : Color.WHITE);
            }
        }
    }
    //urlIcone = "D:\Cours Java\ProjetInfoEncadre\Potager\img\data.png"
    //BufferedImage image = ImageIO.read(new File(urlIcone)); // chargement de
    //l'image globale
    //
    //new ImageIcon()
    //BufferedImage salade = image.getSubimage(x, y, w, h); // image du légume
    //le légume (x, y : coin supérieur gauche, w, h : largeur et hauteur)
    //
    //ImageIcon iconeSalade = salade.getScaledInstance(20, 20,
    //java.awt.Image.SCALE_SMOOTH)); // icône redimentionnée
    //
    //JLabel case = ... // partie initialisation
    //
    //case.setIcon(iconeSalade); // partie rafraichissement
}

