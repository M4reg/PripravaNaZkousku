import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class nactiData {

    // Metoda přijímá JTable jako vstupní parametr,
    // což je tabulka, do které se budou načítat data.
    public static void nacti(JTable table) {
        // dialog pro výběr CSV souboru
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Vyberte soubor CSV pro načtení dat");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Soubory", "csv"));

        // Zobrazení dialogového okna pro výběr CSV souboru
        //null zobrazuje se okno ve stredu  kdyby bylo this zobrazuje se relativne k oknu
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Získání vybraného souboru
            File fileToLoad = fileChooser.getSelectedFile();

            try (BufferedReader reader = new BufferedReader(new FileReader(fileToLoad))) {
                // Čtení záhlaví CSV
                String headerLine = reader.readLine();
                if (headerLine == null || !headerLine.equals("Hex,R,G,B")) {
                    JOptionPane.showMessageDialog(null, "Neplatný formát souboru CSV", "Chyba", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Vytvoření modelu tabulky a nastavení záhlaví sloupců
                DefaultTableModel model = new DefaultTableModel(new String[]{"Hex", "R", "G", "B"}, 0);

                // Čtení datových řádků
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values.length != 4) {
                        JOptionPane.showMessageDialog(null, "Neplatný formát datového řádku v CSV souboru", "Chyba", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Přidání řádku do modelu tabulky
                    model.addRow(new Object[]{
                            values[0], // Hex
                            Integer.parseInt(values[1]), // R
                            Integer.parseInt(values[2]), // G
                            Integer.parseInt(values[3]) // B
                    });
                }

                // Nastavení modelu tabulky
                table.setModel(model);
                JOptionPane.showMessageDialog(null, "Data byla úspěšně načtena z CSV souboru.", "Načtení úspěšné", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Chyba při načítání dat ze souboru.", "Chyba", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Neplatný formát čísel v CSV souboru.", "Chyba", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}
