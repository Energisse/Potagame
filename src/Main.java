public class Main {
    public static void main(String[] args) {
        Modele m = new Modele();
        Vue v = new Vue(m);
        new Thread(m).start();
        m.addObserver(v);
        v.setVisible(true);
    }
}