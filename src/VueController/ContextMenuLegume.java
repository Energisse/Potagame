package VueController;

import javax.swing.JMenuItem;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import Modele.Fabrique.FabriqueLegume;
import Modele.Modele;

public class ContextMenuLegume extends JMenuItem{

    public ContextMenuLegume(String nom, FabriqueLegume fabrique, ContextMenu contextMenu){
        super(nom + " " + fabrique.getPrix() + "€");
        addActionListener(evt -> {
            //recuperer la parcelle
            Parcelle p = (Parcelle) contextMenu.getInvoker();
            Modele.getInstance().poser(p.getIndiceX(),p.getIndiceY(),fabrique);
        });

        contextMenu.addPopupMenuListener(new PopupMenuListener() {
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
}
