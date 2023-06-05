package VueController.BarMenu.Fichier.SauvegardeAs;

import Modele.Sauvegarde;
import VueController.BarMenu.DialogErreur;
import VueController.Vue;

import javax.swing.*;
import java.awt.*;
import java.nio.file.FileAlreadyExistsException;

public class DialogSauvegarderAs extends JDialog {

    /**
     * Constructeur du menu sauvegarder
     */
    public DialogSauvegarderAs() {
        super();
        setTitle("Sauvegarder");
        setLayout(new FlowLayout());
        add(new JLabel("Nom de la sauvegarde : "));
        JTextField textField = new JTextField(10);
        add(textField);
        setLocationRelativeTo(Vue.getInstance());

        add(new JButton("Sauvegarder") {
            {
                addActionListener(e -> {
                    try {
                        Sauvegarde.sauvegarder(textField.getText());
                    } catch (FileAlreadyExistsException ex) {
                        new JDialog() {
                            {
                                setTitle("Erreur");
                                setLocationRelativeTo(Vue.getInstance());
                                setLayout(new FlowLayout());
                                add(new JLabel("Le fichier existe déjà"));
                                add(new JButton("Remplacer") {
                                    {
                                        addActionListener(e ->{
                                            try {
                                                Sauvegarde.sauvegarder(textField.getText(),true);
                                            } catch (Exception exception) {
                                                new DialogErreur();
                                            }
                                            dispose();
                                        });
                                    }
                                });
                                add(new JButton("Annuler") {
                                    {
                                        addActionListener(e -> dispose());
                                    }
                                });
                                pack();
                                setVisible(true);
                            }
                        };
                    } catch (Exception ex) {
                        new DialogErreur();
                    }
                    dispose();
                });

            }
        });
        pack();
        setVisible(true);
    }
}
