import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Hlavní třída GUI, která rozšiřuje JFrame
public class GUI extends JFrame {

    //Vytvoření jednotlivých proměnných
    private JTable tabulka;
    private TabulkaPrikladu tabulkaPrikladu;
    private JTextField vstupPocetPrikladu;
    private JTextField vstupHorniInterval;
    private DefaultListModel polePrikladu;

    // Konstruktor třídy GUI
    public GUI(){

        //Nastavení vlastností hlavního okna
        setTitle("Generování příkladů k odečtení celých čísel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        //Vytváří novou instanci třídy TabulkaPrikladu, která je vlastní implementací AbstractTableModel. Tato třída slouží k definování modelu dat pro tabulku.
        // Tímto krokem se připravuje model, který bude použit pro zobrazení dat v tabulce
        tabulkaPrikladu = new TabulkaPrikladu();
        //Vytváří novou instanci třídy JTable, což je grafický komponent pro zobrazení tabulkových dat.
        tabulka = new JTable();

        // Přidání tabulky do scrollovacího panelu a jeho přidání do okna
        JScrollPane tableScrollPane = new JScrollPane(tabulka);
        add(tableScrollPane, BorderLayout.CENTER);

        // Vytvoření panelu pro vstupy
        JPanel vstupPanel = new JPanel(new FlowLayout());
        vstupPocetPrikladu = new JTextField(3);
        vstupHorniInterval = new JTextField(3);

        // Přidání popisků a textových polí do panelu
        vstupPanel.add(new JLabel("Počet příkladů vygenrovat:"));
        vstupPanel.add(vstupPocetPrikladu);
        vstupPanel.add(new JLabel("Horní interval generovaných čísel:"));
        vstupPanel.add(vstupHorniInterval);

        // Přidání panelu pro vstupy do spodní části okna
        add(vstupPanel, BorderLayout.SOUTH);

        // Vytvoření a přidání tlačítka "Vygeneruj" s přidáním ActionListeneru pro generování příkladů
        JButton vygenerujTlacitko = new JButton("Vygeneruj");
        vygenerujTlacitko.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vygenerujPriklady();
            }
        });

        // Vytvoření a přidání tlačítka "Exportuj" s přidáním ActionListeneru pro export příkladů
        JButton exportTlacitko = new JButton("Exportuj");
        exportTlacitko.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportPriklady();
            }
        });

        JButton nactiTlacitko = new JButton("Načti");

        //Lambda výrazy jsou způsob, jak zapsat krátkou funkci přímo v kódu, bez nutnosti psát celou třídu nebo metodu.
        //Proměnná může obsahovat funkci
        nactiTlacitko.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nactiPriklady();
            }
        });

        // Přidání tlačítek do panelu pro vstupy
        vstupPanel.add(vygenerujTlacitko);
        vstupPanel.add(exportTlacitko);
        vstupPanel.add(nactiTlacitko);


    }
    // Metoda pro generování příkladů
    private void  vygenerujPriklady(){
        try {
            // Získání vstupních hodnot z textových polí
            int pocet = Integer.parseInt(vstupPocetPrikladu.getText());
            int max = Integer.parseInt(vstupHorniInterval.getText());

            // Vytvoření seznamu příkladů
            List<Priklady> priklady = new ArrayList<>();
            for (int i = 0; i < pocet; i++) {

                //Generování náhodných čísel mezi 1 a max maximem floor zaokrouhluje dolu pro musime pridat 1 pokud chceme generovat cisla vcetne maxima
                int a = (int) Math.floor(Math.random() * max)+1;
                int b = (int) Math.floor(Math.random() * max)+1;

                // a je vetsi nez b
                if (a <= b) {
                    int c = a;
                    a = b;
                    b = c;
                }

                // Vysledek vetsi nez nula
                int vysledek = a - b;
                if (vysledek <= 0) {
                    a++;
                    vysledek = a - b;
                }
                // Přidání příkladu do seznamu
                priklady.add(new Priklady(a, "-", b, vysledek));
            }
            // Nastavení generovaných příkladů do modelu tabulky a aktualizace tabulky
            tabulkaPrikladu.setPriklady(priklady);
            tabulka.setModel(tabulkaPrikladu);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Prosím, zadejte platná čísla.", "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Metoda pro export příkladů do souboru JSON
    private void exportPriklady() {
        JFileChooser fileChooser = new JFileChooser();

        //Název okna dialogového okna
        fileChooser.setDialogTitle("Uložit jako");

        // Zobrazení dialogu pro uložení souboru
        //this - zavolej tuto metodu na objekt se kterým právě pracuji
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            // Zajištění, že soubor má příponu .json
            if (!filePath.toLowerCase().endsWith(".json")) {
                filePath += ".json";
                fileToSave = new File(filePath);
            }

            try {
                JsonExport.exportToJson(fileToSave.getAbsolutePath(), tabulkaPrikladu.getPriklady());
                JOptionPane.showMessageDialog(this, "Data byla úspěšně exportována do souboru " + fileToSave.getAbsolutePath(), "Export úspěšný", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Došlo k chybě při exportu dat: " + e.getMessage(), "Chyba", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void nactiPriklady() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Načíst příklady");

        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            try {
                // Načte data z JSON souboru pomocí metody loadFromJson() z třídy JsonExport
                List<Priklady> loadedPriklady = JsonExport.loadFromJson(fileToLoad.getAbsolutePath());
                // Nastaví načtená data do tabulky příkladů
                tabulkaPrikladu.setPriklady(loadedPriklady);
                // Aktualizuje model tabulky
                tabulka.setModel(tabulkaPrikladu);
                JOptionPane.showMessageDialog(this, "Data byla úspěšně načtena z souboru " + fileToLoad.getAbsolutePath(), "Načteno", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Došlo k chybě při načítání dat: " + ex.getMessage(), "Chyba", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
