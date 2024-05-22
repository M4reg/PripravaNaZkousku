import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

class TabulkaBarev extends AbstractTableModel {
    //Seznam barev
    private final List<MyColor> barvy;
    //tabulka sloupce
    private final String[] nazvySloupcu = {"Jméno barvy", "R", "G", "B", "Hex"};

    public TabulkaBarev() {
        //Inicializace seznamu barev
        this.barvy = new ArrayList<>();
    }

    //Metoda pro přidání barvy do tabulky
    public void pridejBarvu(MyColor barva) {
        barvy.add(barva);
        fireTableRowsInserted(barvy.size() - 1, barvy.size() - 1);
    }

    // Metoda pro získání počtu řádků v tabulce
    @Override
    public int getRowCount() {
        return barvy.size();
    }

    // Metoda pro získání počtu sloupců v tabulce
    @Override
    public int getColumnCount() {
        return nazvySloupcu.length;
    }
    // Metoda pro získání názvu sloupce
    @Override
    public String getColumnName(int column) {
        return nazvySloupcu[column];
    }
    // Metoda pro získání hodnoty v buňce tabulky
    @Override
    public Object getValueAt(int indexRadku, int indeSloupce) {
        // Získání barvy na daném řádku
        //Vrací hodnotu buňky na deném řádku a sloupci tabulky
        MyColor color = barvy.get(indexRadku);
        switch (indeSloupce) {
            case 0:
                return color.getJmeno();
            case 1:
                return color.getR();
            case 2:
                return color.getG();
            case 3:
                return color.getB();
            case 4:
                return color.getHex();
            default:
                return null;
        }
    }
    // Metoda pro získání instance barvy na daném řádku
    public MyColor ziskejBarvuNaRadku(int indexRadku) {
        return barvy.get(indexRadku);
    }
}
