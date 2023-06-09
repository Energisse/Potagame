package Modele.Mouette;

import Config.Config;
import java.io.Serializable;
import java.util.ArrayList;
import Modele.Modele;

public class GestionaireMouette implements Runnable, Serializable {

    /**
     * Liste des mouettes
     */
    private final ArrayList<Mouette> listMouettes = new ArrayList<>();

    /**
     * Nombre maximum de mouette
     */
    private static final int  MAX_MOUETTE = Config.getInstance().getConfigMouette().max();

    /**
     * Probabilité d'apparition d'une mouette
     */
    private static final float PROBABILITE_APPARITION = Config.getInstance().getConfigMouette().chanceApparition();


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
            //On evite d'apparaitre sur le bord du bas avec -0.5
            float y = (float) (Math.random() * (float) Modele.getInstance().getHauteur()-0.5);
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
