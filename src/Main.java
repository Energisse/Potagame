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

        try {
            Sauvegarde.charger();
        }
        catch (Exception ignored){}

        Modele m = Modele.getInstance();

        Vue v = Vue.getInstance();
        new Thread(Ordonnanceur.getInstance()).start();
        v.setVisible(true);
    }
}