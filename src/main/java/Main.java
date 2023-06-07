import Modele.Sauvegarde;

import Config.Config;
import Modele.Fabrique.FabriqueEpouvantail;
import Modele.Fabrique.FabriqueLac;
import Modele.Fabrique.FabriqueLegume;
import Modele.Ordonnanceur;
import Modele.Modele;
import VueController.Vue;

public class Main {
    public static void main(String[] args) {
        Config.load();
        FabriqueLegume.load(Config.getInstance().getFabriqueLegumes());
        FabriqueEpouvantail.load(Config.getInstance().getConfigEpouvantail());
        FabriqueLac.load(Config.getInstance().getConfigLac());

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