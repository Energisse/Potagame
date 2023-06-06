package VueController.Menu;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Modele.Modele;
import VueController.ImageLoader;

public class ArgentPanel extends JPanel implements Observer {

    /**
     * Label contenant l'argent
     */
    private JLabel argentLabel;

    /**
     * Constructeur de l'ArgentPanel
     * @throws IOException
     */
    public ArgentPanel() throws IOException{
        super();
        add(new JLabel(ImageLoader.loadIcon("Piece.png", 30)));
        argentLabel = new JLabel();
        add(argentLabel);
        setOpaque(false);
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
