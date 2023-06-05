package Modele.Mouette;

import Config.Config;
import Modele.Modele;

import java.util.ArrayList;

public class GestionaireMouette implements Runnable {

    /**
     * Liste des mouettes
     */
    private final ArrayList<Mouette> listMouettes = new ArrayList<>();

    /**
     * Instance du gestionnaire de mouette
     */
    private static GestionaireMouette instance = null;

    /**
     * Nombre maximum de mouette
     */
    private static final int  MAX_MOUETTE = Config.getInstance().getConfigMouette().max();

    /**
     * Probabilité d'apparition d'une mouette
     */
    private static final float PROBABILITE_APPARITION = Config.getInstance().getConfigMouette().chanceApparition();

    /**
     * Retourne l'instance du gestionnaire de mouette
     * @return instance du gestionnaire de mouette
     */
    public static GestionaireMouette getInstance(){
        if (instance == null){
            instance = new GestionaireMouette();
        }
        return instance ;
    }

    @Override
    public void run(){
        //met à jour les mouettes
        for(Mouette mouette : listMouettes){
            mouette.deplacer();
        }

        //parcours listMouettes à l'envers pour pouvoir supprimer des éléments
        for(int iMouette = listMouettes.size() - 1; iMouette >= 0; iMouette--){
            if(listMouettes.get(iMouette).aFinit()){
                listMouettes.remove(iMouette);
            }
        }

        //ajoute une mouette
        if(listMouettes.size() < MAX_MOUETTE && Math.random() < PROBABILITE_APPARITION){
            float y = (float) (Math.random() * (float) Modele.getInstance().getHauteur());
            //apparait une demie case avant le bord a gauche
            listMouettes.add(new Mouette((float) -0.5,y));
        }
    }

    /**
     * Retourne la liste des mouettes
     * @return listMouettes
     */
    public ArrayList<Mouette> getMouettes() {
        return listMouettes;
    }
}
