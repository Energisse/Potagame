package VueController;

import javax.swing.JMenuItem;

import Modele.Modele;
import Modele.Fabrique.Fabrique;

public class ContextMenuLegume extends JMenuItem{

    public ContextMenuLegume(String nom,Fabrique fabrique,ContextMenu contextMenu){
        super(nom);


        addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                //recuperer la parcelle
                Parcelle p = (Parcelle) contextMenu.getInvoker();
                Modele.getInstance().planter(p.getIndiceX(),p.getIndiceY(),fabrique);
            }
        });
    }


    
}
