package VueController;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

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

        add(boutonPlanter);

        /* Menu Récolter */
        JMenuItem boutonRecolter = new JMenuItem("Récolter");
        boutonRecolter.addActionListener(evt -> {
            Parcelle p = (Parcelle) getInvoker();
            Modele.getInstance().recolter(p.getIndiceX(),p.getIndiceY());
        });
        add(boutonRecolter);

        /* Menu Arracher */
        JMenuItem boutonAracher = new JMenuItem("Arracher");
        boutonAracher.addActionListener(evt -> {
            Parcelle p = (Parcelle) getInvoker();
            Modele.getInstance().aracher(p.getIndiceX(),p.getIndiceY());
        });
        add(boutonAracher);

        /* Menu Labourer */
        JMenuItem boutonLabourer = new JMenuItem("Labourer");
        boutonLabourer.addActionListener(evt -> {
            Parcelle p = (Parcelle) getInvoker();
            Modele.getInstance().labourer(p.getIndiceX(),p.getIndiceY());
        });
        add(boutonLabourer);

        /* Menu Miner */
        JMenuItem boutonMiner = new JMenuItem("Miner");
        boutonMiner.addActionListener(evt -> {
            Parcelle p = (Parcelle) getInvoker();
            Modele.getInstance().miner(p.getIndiceX(),p.getIndiceY());
        });
        add(boutonMiner);

        addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                Parcelle p = (Parcelle) getInvoker();
                if(Modele.getInstance().getParcelle(p.getIndiceX(),p.getIndiceY()).aDeLHerbe()){
                    if(Modele.getInstance().getParcelle(p.getIndiceX(), p.getIndiceY()).aUnRocher()){
                        boutonLabourer.setEnabled(false);
                        boutonMiner.setEnabled(true);
                    }
                    else{
                        boutonLabourer.setEnabled(true);
                        boutonMiner.setEnabled(false);
                    }

                    boutonPlanter.setEnabled(false);
                    boutonAracher.setEnabled(false);
                    boutonRecolter.setEnabled(false);
                }
                else{
                    boutonLabourer.setEnabled(false);
                    boutonMiner.setEnabled(false);

                    if (Modele.getInstance().getLegume(p.getIndiceX(),p.getIndiceY()) != null){
                        boutonPlanter.setEnabled(false);
                        boutonAracher.setEnabled(true);
                        boutonRecolter.setEnabled(Modele.getInstance().getLegume(p.getIndiceX(), p.getIndiceY()).estRecoltable());
                    }
                    else{
                        boutonPlanter.setEnabled(true);
                        boutonAracher.setEnabled(false);
                        boutonRecolter.setEnabled(false);
                    }
                }
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
    }

    
}
