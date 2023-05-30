package Modele.Mouette;

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
    private static final int  MAX_MOUETTE = 10;

    /**
     * Retourne l'instance du gestionnaire de mouette
     * @return
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

        //ajoute une mouette avec une probabilité de 1%
        if(listMouettes.size() < MAX_MOUETTE && Math.random() < 0.01){
            float y = (float) (Math.random() * (float) Modele.getInstance().getHauteur());
            listMouettes.add(new Mouette(0,y));
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
