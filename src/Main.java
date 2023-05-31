import Modele.Modele;
import Modele.Ordonnanceur;
import Modele.Sauvegarde;
import VueController.Vue;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Modele m;
        if((m = Sauvegarde.charger()) == null){
            m = Modele.getInstance();
        }

        Vue v = Vue.getInstance();
        new Thread(Ordonnanceur.getInstance()).start();
        m.addObserver(v);
        v.setVisible(true);

        
    }
}