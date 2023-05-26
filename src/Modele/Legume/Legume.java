package Modele.Legume;
public abstract class Legume {
    /**
     * Croissance du légume
     */
    protected float croissance = 0;

    /**
     * Taux de croissance du légume
     * Si les conditions sont mauvaises, le taux de legume reduit jusqu'a 0
     * Si les conditions sont bonnes, le taux de legume augmente jusqu'a 2
     */
    protected float tauxCroissance = 1;

    /**
     * Vie du légume
     * @return
     */
    protected int vie = 100;

    /**
     * Retourne le prix du légume
     * @return int
     */
    public abstract int getPrixRevente();

    /**
     * Retourne l'image du légume
     * @return String
     */
    public abstract String getImage();

    /**
     * Retourne le nom du légume
     * @return String
     */
    public abstract String getNom();

    /**
     * Retourne la croissance du légume
     * @return pourcentage de croissance
     */
    public float getCroissance() {
        return croissance;
    }

    /**
     * Fait pousser le légume
     * @return void
     */
    public void pousser(){
        if(!aFinitCroissance()){
            croissance += 10;
        }
    }

    /**
     * Retourne si le légume a finit de pousser
     * @return boolean
     */
    private boolean aFinitCroissance(){
        return croissance == 100;
    }

    /**
     * Retourne si le légume est mort
     * @return boolean
     */
    public boolean estMort(){
        return croissance == 0;
    }

    /**
     * Retourne si le légume est récoltable
     * @return boolean
     */
    public boolean estRecoltable(){
        return aFinitCroissance() && vie > 0;
    }

}
