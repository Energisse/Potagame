import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

public class Vue extends JFrame implements Observer { //JFrame=la fenetre; Jpanel=conteneur s'inserant dans la fenetre; Jlabel=contient texte/image

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
                tab[i][j].setBackground( m.getCaseContent(i,j) ? Color.BLACK : Color.WHITE);
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
    //urlIcone = "D:\Downloads\data.png"
    //BufferedImage image = ImageIO.read(new File(urlIcone)); // chargement de
    //l'image globale
    //
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

