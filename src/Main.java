import Config.Config;
import Modele.Fabrique.FabriqueEpouvantail;
import Modele.Fabrique.FabriqueLegume;
import Modele.Modele;
import Modele.Ordonnanceur;
import Modele.Sauvegarde;
import VueController.Vue;

public class Main {
    public static void main(String[] args) {
        Config.load();
        FabriqueLegume.load(Config.getInstance().getFabriqueLegumes());
        FabriqueEpouvantail.load(Config.getInstance().getConfigEpouvantail());

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