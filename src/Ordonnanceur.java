import java.util.ArrayList;

public class Ordonnanceur extends Thread {
    private ArrayList<Runnable> tab ;//la météo devra tjrs être en premier dans le potager, elle sera maj avnt de faire un rafraîchissement de la vue
    static Ordonnanceur ord;
    static Ordonnanceur getOrdonnanceur(){
        if (ord == null){
            ord = new Ordonnanceur();
        }
        return ord ;
    }
    private void ordonnancement(){
        for (int i=0; i<=10;i++){
            System.out.println(("Processus"+i));
        }
    }
}
