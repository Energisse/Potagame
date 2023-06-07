package Modele;

public class Ordonnanceur extends Thread {
    private static Ordonnanceur ord;

    public void run(){

        while (true) {
            //temps d'exécution de la boucle
            Modele.getInstance().run();
            try {
                Thread.sleep(100 );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Ordonnanceur getInstance() {
        if (ord == null){
            ord = new Ordonnanceur();
        }
        return ord ;
    }
}
