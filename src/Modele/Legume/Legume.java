package Modele.Legume;
public abstract class Legume {
    /**
     * Croissance du légume
     */
    private float croissance = 0;

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
}
