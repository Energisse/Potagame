import java.util.Observable;
import java.lang.Runnable;
import java.util.Random;

public class Modele extends Observable implements Runnable {

    public int size_x =10;
    public int size_y =10;
    private boolean [][] tab = new boolean[size_x][size_y];


    @Override
    public void run() {
        Random rdm = new Random();
        while (true) {
            for (int i = 0; i < size_x; i++){
                for (int j = 0; j < size_y; j++){
                    tab[i][j] = rdm.nextBoolean();
                }
            }
            setChanged();
            notifyObservers();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public boolean getCaseContent(int x, int y){
        return tab[x][y];
    }

    public void maj(int i, int j){
        tab[i][j] = !tab[i][j]; // =! est l'opérateur different alors que ! est la negation
    }













}




