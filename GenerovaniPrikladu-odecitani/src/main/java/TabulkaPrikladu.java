import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TabulkaPrikladu extends AbstractTableModel {
    private List<Priklady> priklady;
    private final String[] nazvySloupcu = {"Číslo A", "Operace", "Číslo B", "Výsledek"};

    public TabulkaPrikladu() {
        this.priklady = new ArrayList<>();
    }
    public List<Priklady> getPriklady() {
        return priklady;
    }

    public void setPriklady(List<Priklady> priklady) {
        this.priklady = priklady;
        fireTableDataChanged(); // Notify the table that the data has changed
    }

    @Override
    public int getRowCount() {
        return priklady.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Priklady priklad = priklady.get(rowIndex);
        switch (columnIndex) {
            case 0: return priklad.getA();
            case 1: return priklad.getOperace();
            case 2: return priklad.getB();
            case 3: return priklad.getVysledek();
            default: return null;
        }
    }
    @Override
    public String getColumnName(int column) {
        return nazvySloupcu[column];
    }
}
