package VueController;

import Modele.Modele;
import Modele.Legume.Tomate;

public class ContextMenu extends javax.swing.JPopupMenu{
    public ContextMenu(){
        super();

        /* Menu Planter */
        javax.swing.JMenuItem item = new javax.swing.JMenuItem("Planter");
        item.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                //recuperer la parcelle
                Parcelle p = (Parcelle) getInvoker();
                Modele.getInstance().planter(p.getIndiceX(),p.getIndiceY(),new Tomate());
            }
        });
        add(item);

        /* Menu Récolter */
        item = new javax.swing.JMenuItem("Récolter");
        item.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                System.out.println("Récolter");
            }
        });
        add(item);

        /* Menu Arracher */
        item = new javax.swing.JMenuItem("Arracher");
        item.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                Parcelle p = (Parcelle) getInvoker();
                Modele.getInstance().aracher(p.getIndiceX(),p.getIndiceY());
            }
        });
        add(item);
    }

    
}
