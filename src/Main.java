import Modele.Modele;
import Modele.Ordonnanceur;
import VueController.ArgentPanel;
import VueController.Vue;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Modele m = Modele.getInstance();
        Vue v = Vue.getInstance();
        new Thread(Ordonnanceur.getInstance()).start();
        m.addObserver(v);
        v.setVisible(true);
    }
}