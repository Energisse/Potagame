package VueController;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Modele.Modele;

public class ArgentPanel extends JPanel implements Observer {

    /**
     * Instance de l'ArgentPanel
     */
    private static ArgentPanel instance = null;

    /**
     * Label contenant l'argent
     */
    private JLabel argentLabel;

    /**
     * Constructeur de l'ArgentPanel
     * @throws IOException
     */
    private ArgentPanel() throws IOException{
        super();
        add(new JLabel(new ImageIcon(ImageIO.read(new File("./src/images/piece.png")).getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH))));
        argentLabel = new JLabel("" + Modele.getInstance().getArgent());
        add(argentLabel);
        setOpaque(false);
    }

    /**
     * Retourne l'instance de l'ArgentPanel
     * @return instance
     * @throws IOException
     */
    public static ArgentPanel getInstance() throws IOException{
        if (instance == null){
            instance = new ArgentPanel();
        }
        return instance ;
    }

    @Override
    public void update(Observable o, Object arg) {
        try{
            argentLabel.setText("" + Modele.getInstance().getArgent());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
