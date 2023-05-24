import Modele.Modele;
import Modele.Ordonnanceur;
import VueController.Vue;

public class Main {
    public static void main(String[] args) {
        Modele m = Modele.getInstance();
        Vue v = Vue.getInstance();
        new Thread(Ordonnanceur.getInstance()).start();
        m.addObserver(v);
        v.setVisible(true);
    }
}