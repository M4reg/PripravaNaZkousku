public class Priklady {

    private int a;
    private String operace;
    private int b;
    private int vysledek;

    public Priklady(int a, String operace, int b, int vysledek) {
        this.a = a;
        this.operace = operace;
        this.b = b;
        this.vysledek = vysledek;
    }

    public int getA() {
        return a;
    }

    public String getOperace() {
        return operace;
    }

    public int getB() {
        return b;
    }

    public int getVysledek() {
        return vysledek;
    }


    public void setA(int a) {
        this.a = a;
    }

    public void setOperace(String operace) {
        this.operace = operace;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setVysledek(int vysledek) {
        this.vysledek = vysledek;
    }
}
