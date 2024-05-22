public class Mena {
    private String zeme;
    private String mena;
    private int mnozstvi;
    private String kod;
    private double kurz;

    public Mena(String zeme, String mena, int mnozstvi, String kod, double kurz) {
        this.zeme = zeme;
        this.mena = mena;
        this.mnozstvi = mnozstvi;
        this.kod = kod;
        this.kurz = kurz;
    }

    public String getZeme() {
        return zeme;
    }

    public String getMena() {
        return mena;
    }

    public int getMnozstvi() {
        return mnozstvi;
    }

    public String getKod() {
        return kod;
    }

    public double getKurz() {
        return kurz;
    }

    public void setZeme(String zeme) {
        this.zeme = zeme;
    }

    public void setMena(String mena) {
        this.mena = mena;
    }

    public void setMnozstvi(int mnozstvi) {
        this.mnozstvi = mnozstvi;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public void setKurz(double kurz) {
        this.kurz = kurz;
    }

    @Override
    public String toString() {
        return mena + " (" + kod + ")";
    }
}
