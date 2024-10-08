package view;

public class Singleton {        //Classe permettant de conserver des valeurs défini comme les dimmensions de l'écran ou le symbale euro
    private static Singleton instance;
    public int xm;
    public int ym;

    public int b1_x;
    public int b1_y;

    public String euroSymbol = "\u20AC";

    private Singleton() {

    }
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
    public void xm(int value) {
        this.xm = value;

    }
    public void ym(int value) {
        this.ym = value;
    }
}
