package VueController.Menu;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class Menu extends JPanel implements Observer {

    /**
     * ArgentPanel
     */
    private final ArgentPanel argentPanel;

    /**
     * MetoPanel
     */
    private final MeteoPanel meteoPanel;

    /**
     * SelectionPanel
     */
    private final SelectionPanel selectionPanel;

    /**
     * Constructeur du menu
     */
    public Menu() throws IOException {
        super(new GridLayout(6,1));
        setBackground(new Color(134,151,224));
        argentPanel = new ArgentPanel();
        meteoPanel = new MeteoPanel();

        JLabel clock = new JLabel();
        clock.setFont(new Font("",Font.BOLD, 15));
        clock.setHorizontalAlignment(clock.CENTER);

        Timer timer = new Timer(1000, e -> clock.setText(DateFormat.getDateTimeInstance().format(new Date())));
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();

        selectionPanel = new SelectionPanel();

        add(clock);
        add(meteoPanel);
        add(new VitessePanel());
        add(argentPanel);
        add(selectionPanel);

    }
    @Override
    public void update(Observable o, Object arg) {
        argentPanel.update(o,arg);
        meteoPanel.update(o,arg);
        selectionPanel.update(o, arg);
    }
}
