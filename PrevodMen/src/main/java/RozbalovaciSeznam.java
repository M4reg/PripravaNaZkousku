import javax.swing.*;
import java.util.List;

public class RozbalovaciSeznam extends AbstractListModel<String> implements ComboBoxModel<String>{
    private List<Mena> meny;
    private String selectedMena;

    public RozbalovaciSeznam(List<Mena> meny) {
        this.meny = meny;
    }

    @Override
    public int getSize() {
        return meny.size();
    }

    @Override
    public String getElementAt(int index) {
        return meny.get(index).getZeme() + " (" + meny.get(index).getKod() + ")";
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selectedMena = (String) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selectedMena;
    }
}
