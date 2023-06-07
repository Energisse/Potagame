package VueController;

import Modele.Fabrique.Fabrique;
import Modele.Fabrique.FabriqueLegume;
import Modele.Modele;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

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

        /* Menu Poser */
        JMenu boutonPoser = new JMenu("Poser");

        Fabrique.getInstances().forEach(fabrique -> {
            JMenuItem boutonFabrique = fabrique instanceof FabriqueLegume ? boutonPlanter : boutonPoser;

            boutonFabrique.add(new JMenuItem(){
                {
                    setText(fabrique.getNom() + " " + fabrique.getPrix() + "€");
                    addActionListener(evt -> {
                        Parcelle p = (Parcelle) getInvoker();
                        Modele.getInstance().poser(p.getIndiceX(),p.getIndiceY(),fabrique);
                    });
                }
                {
                    addPopupMenuListener(new PopupMenuListener() {
                        @Override
                        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                            setEnabled(Modele.getInstance().getArgent() >= fabrique.getPrix());
                        }
                        @Override
                        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
                        @Override
                        public void popupMenuCanceled(PopupMenuEvent e) {}
                    });
                }
            });
        });


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

        /* Menu enlever  */
        JMenuItem boutonEnleverObjet = new JMenuItem("Enlever");
        boutonEnleverObjet.addActionListener(evt -> {
            Parcelle p = (Parcelle) getInvoker();
            Modele.getInstance().enlever(p.getIndiceX(),p.getIndiceY());
        });

        addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                Parcelle p = (Parcelle) getInvoker();


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
                        if(Modele.getInstance().getLegume(p.getIndiceX(), p.getIndiceY()).estRecoltable()){
                            add(boutonRecolter);
                        }
                    }
                    else if(Modele.getInstance().getObjet(p.getIndiceX(),p.getIndiceY()) == null){
                        add(boutonPlanter);
                        add(boutonPoser);
                    }
                }
                if(!Modele.getInstance().getParcelle(p.getIndiceX(), p.getIndiceY()).aUnRocher()){
                    if(Modele.getInstance().getParcelle(p.getIndiceX(),p.getIndiceY()).getObjet() == null){
                        if( Modele.getInstance().getParcelle(p.getIndiceX(),p.getIndiceY()).getLegume() == null){
                            add(boutonPoser);
                        }
                    }
                    else{
                        add(boutonEnleverObjet);
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
