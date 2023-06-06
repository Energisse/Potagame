package VueController.BarMenu.Aide.Aide;

import javax.swing.*;
import java.awt.*;

public class DialogAide extends JDialog {
    /**
     * Constructeur du menu aide
     */
    public DialogAide() {
        super();
        setLayout(new FlowLayout());
        JTextArea text = new JTextArea(
                """
                    Tu crois que j'ai que ça à faire de t'aider ? \s
                    Tu te débrouilles ! \s
                    C'est pas compliqué ! \s
                    Tu cliques sur les boutons et tu regardes ce qui se passe !
                  """
        );
        text.setOpaque(false);
        add(text);
        add(new JButton("Ok"){
            {
                addActionListener(e -> dispose());
            }
        });
        pack();
        setVisible(true);
    }
}
