package VueController;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import Modele.Fabrique.FabriqueEpouvantail;
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
        JMenu boutonPlanter = new JMenu("Planter");

        boutonPlanter.add( new ContextMenuLegume("Tomate",new FabriqueTomate(), this));
        boutonPlanter.add(new ContextMenuLegume("Salade",new FabriqueSalade(), this));


        /* Menu Récolter */
        JMenuItem boutonRecolter = new JMenuItem("Récolter");
        boutonRecolter.addActionListener(evt -> {
            Parcelle p = (Parcelle) getInvoker();
            Modele.getInstance().recolter(p.getIndiceX(),p.getIndiceY());
        });

        /* Menu Arracher */
        JMenuItem boutonAracher = new JMenuItem("Arracher");
        boutonAracher.addActionListener(evt -> {
            Parcelle p = (Parcelle) getInvoker();
            Modele.getInstance().enlever(p.getIndiceX(),p.getIndiceY());
        });

        /* Menu Labourer */
        JMenuItem boutonLabourer = new JMenuItem("Labourer");
        boutonLabourer.addActionListener(evt -> {
            Parcelle p = (Parcelle) getInvoker();
            Modele.getInstance().labourer(p.getIndiceX(),p.getIndiceY());
        });

        /* Menu Miner */
        JMenuItem boutonMiner = new JMenuItem("Miner");
        boutonMiner.addActionListener(evt -> {
            Parcelle p = (Parcelle) getInvoker();
            Modele.getInstance().miner(p.getIndiceX(),p.getIndiceY());
        });

        /* Menu placer Epouvantail */
        JMenuItem boutonEpouvantail = new JMenuItem("Placer Epouvantail");
        boutonEpouvantail.addActionListener(evt -> {
            Parcelle p = (Parcelle) getInvoker();
            Modele.getInstance().poser(p.getIndiceX(),p.getIndiceY(),new FabriqueEpouvantail());
        });

        /* Menu enlever  */
        JMenuItem boutonEnleverObjet = new JMenuItem("Enlever");
        boutonEnleverObjet.addActionListener(evt -> {
            Parcelle p = (Parcelle) getInvoker();
            Modele.getInstance().enlever(p.getIndiceX(),p.getIndiceY());
        });

        addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                Parcelle p = (Parcelle) getInvoker();
                if(new FabriqueEpouvantail().peutEtrePose(Modele.getInstance().getParcelle(p.getIndiceX(),p.getIndiceY()))){
                    add(boutonEpouvantail);
                }
                else if(Modele.getInstance().getParcelle(p.getIndiceX(),p.getIndiceY()).getObjet() != null){
                    add(boutonEnleverObjet);
                }
                if(Modele.getInstance().getParcelle(p.getIndiceX(),p.getIndiceY()).aDeLHerbe()){
                    if(Modele.getInstance().getParcelle(p.getIndiceX(), p.getIndiceY()).aUnRocher()){
                        add(boutonMiner);
                    }
                    else{
                        add(boutonLabourer);
                    }
                }
                else{
                    if (Modele.getInstance().getLegume(p.getIndiceX(),p.getIndiceY()) != null){
                        add(boutonAracher);
                        boutonRecolter.setEnabled(Modele.getInstance().getLegume(p.getIndiceX(), p.getIndiceY()).estRecoltable());
                    }
                    else{
                        add(boutonPlanter);
                    }
                }
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                removeAll();
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
    }

    
}
