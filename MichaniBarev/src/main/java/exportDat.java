import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class exportDat {

    //Metoda přijímá JTable jako vstupní parametr,
    //což je tabulka, ze které se budou exportovat data.
    public static void exportData(JTable table) {

        //dialog pro vyběr umístění a názvu souboru
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Vyberte umístění pro uložení souboru");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Soubory", "csv"));

        // Zobrazení dialogového okna pro výběr umístění a názvu souboru pro export
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {

            // Získání vybraného souboru a cesty k němu
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            // Přidání přípony ".csv", pokud není součástí názvu souboru
            if (!filePath.endsWith(".csv")) {
                fileToSave = new File(filePath + ".csv");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                // Získat index sloupce hex a jméno barvy
                int hexColumnIndex = table.getColumnModel().getColumnIndex("Hex");

                // Zkontrolujte, zda existují sloupce hex
                //metoda getColumnIndex vrací -1 když daná položka neexistuje
                
                if (hexColumnIndex == -1) {
                    JOptionPane.showMessageDialog(null, "Tabulka neobsahuje sloupce 'Hex'", "Chyba", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Záhlaví CSV
                writer.write("Hex,R,G,B\n");

                // Zapsat data do CSV
                for (int i = 0; i < table.getRowCount(); i++) {
                    String hex = (String) table.getValueAt(i, hexColumnIndex);
                    int red = (int) table.getValueAt(i, 1);
                    int green = (int) table.getValueAt(i, 2);
                    int blue = (int) table.getValueAt(i, 3);

                    //String, decimal, čárky změníme na jiný znak pro jinou formu csv
                    writer.write(String.format("%s,%d,%d,%d\n", hex, red, green, blue));
                }

                JOptionPane.showMessageDialog(null, "Data byla úspěšně exportována do souboru.", "Export úspěšný", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Chyba při exportu dat do souboru.", "Chyba", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}