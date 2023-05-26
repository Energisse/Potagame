package VueController;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import Modele.Modele;
import Modele.Fabrique.FabriqueSalade;
import Modele.Fabrique.FabriqueTomate;

public class ContextMenu extends JPopupMenu{

    private static ContextMenu instance = null;

    public static ContextMenu getInstance(){
        if (instance == null){
            instance = new ContextMenu();
        }
        return instance ;
    }
    
    private ContextMenu(){
        super();

        /* Menu Planter */
        JMenu menu = new JMenu("Planter");
        //sub menu
        JMenuItem menuItem1 = new ContextMenuLegume("Tomate",new FabriqueTomate(), this);
        menu.add(menuItem1);
        JMenuItem menuItem2 = new ContextMenuLegume("Salade",new FabriqueSalade(), this);
        menu.add(menuItem2 );
        add(menu);

        /* Menu Récolter */
        JMenuItem item = new JMenuItem("Récolter");
        item.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                Parcelle p = (Parcelle) getInvoker();
                Modele.getInstance().recolter(p.getIndiceX(),p.getIndiceY());
            }
        });
        add(item);

        /* Menu Arracher */
        item = new JMenuItem("Arracher");
        item.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                Parcelle p = (Parcelle) getInvoker();
                Modele.getInstance().aracher(p.getIndiceX(),p.getIndiceY());
            }
        });
        add(item);
    }

    
}
