public class MyColor {
    //Jednotlivé složky barvy a jméno
    private int r;
    private int g;
    private int b;
    private String jmeno;

    //Konstruktor pro inicializaci barvy
    public MyColor(int r, int g, int b, String jmeno) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.jmeno = jmeno;
    }
    //gettery pro získání barev a jména a hexa
    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getHex() {
        // Formátování hodnot R, G, B do hexadecimálního kódu
        return String.format("#%02x%02x%02x", r, g, b).toUpperCase();
    }

    //settery pro nastavení barev a jména
    public void setR(int r) {
        this.r = r;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }


}
