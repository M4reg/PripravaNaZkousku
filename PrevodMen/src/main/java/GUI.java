import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {
    private JComboBox<String> comboBoxMena;
    private JTextField textFieldCastka;
    private JButton buttonKonvertovat;
    private JLabel labelVysledek;

    private List<Mena> kurzy;

    // Konstruktor třídy GUI
    public GUI(List<Mena> kurzy) {
        this.kurzy = kurzy;

        setTitle("Převodník měn");
        setSize(600, 300);
        //ukončení procesu aby neběžel dál po uzavření
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Nastavení GridLayout pro umístění komponent
        setLayout(new GridLayout(4, 1));

        comboBoxMena = new JComboBox<>();
        comboBoxMena.setModel(new RozbalovaciSeznam(kurzy));
        add(comboBoxMena);

        textFieldCastka = new JTextField();
        add(textFieldCastka);

        labelVysledek = new JLabel();
        add(labelVysledek);

        buttonKonvertovat = new JButton("Konvertovat");
        buttonKonvertovat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                konvertovatMenu();
            }
        });
        add(buttonKonvertovat);

    }

    // Metoda pro provedení konverze
    private void konvertovatMenu() {
        // Získání vybrané položky z combo boxu jako řetězce
        String selectedItem = (String) comboBoxMena.getSelectedItem();

        // Extrahování kódu měny z vybrané položky (řetězec mezi závorkami)
        String kodMena = selectedItem.substring(selectedItem.lastIndexOf("(") + 1, selectedItem.lastIndexOf(")")).trim();
        Mena vybranaMena = null;
        // Procházení seznamu kurzů měn a nalezení měny s odpovídajícím kódem
        for (Mena mena : kurzy) {
            if (mena.getKod().equals(kodMena)) {
                vybranaMena = mena;
                break;
            }
        }
        if (vybranaMena == null) {
            labelVysledek.setText("Chyba: Měna nenalezena.");
            return;
        }

        try {
            // Převedení textu z textového pole na číslo
            double castka = Double.parseDouble(textFieldCastka.getText());
            // Získání kurzu vybrané měny
            double kurz = vybranaMena.getKurz();
            // Výpočet výsledku konverze
            double vysledek = castka * kurz;
            // Zobrazení výsledku konverze v labelu
            labelVysledek.setText("Výsledek: " + vysledek + " CZK");
        } catch (NumberFormatException e) {
            labelVysledek.setText("Chyba: Neplatná částka.");
        }
    }

    //nacitani z textoveho souboru
    public static List<Mena> nactiKurzy(String soubor) throws IOException {
        List<Mena> meny = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(soubor));
        String line;

        // Přeskočit první dva řádky (datum a hlavičku)
        reader.readLine(); // První řádek s datem
        reader.readLine(); // Druhý řádek s hlavičkou


        //čte řádek po řádku každý řádek je rozdělený pomocí Split kde | je oddělovač
        while ((line = reader.readLine()) != null) {
            //split dokaze pracovat jen \\| kdyby tam bylo pouze | bere to jako nebo
            String[] tokens = line.split("\\|");
            // Pokud pole tokens nemá dostatek prvků, přeskočte tento řádek
            if (tokens.length < 5) {
                System.err.println("Chybný formát řádku: " + line);
                continue;
            }
            String zeme = tokens[0].trim();
            String mena = tokens[1].trim();
            int mnozstvi;
            try {
                mnozstvi = Integer.parseInt(tokens[2].trim());
            } catch (NumberFormatException e) {
                System.err.println("Chybný formát množství: " + tokens[2].trim());
                continue;
            }
            String kod = tokens[3].trim();
            double kurz = Double.parseDouble(tokens[4].trim().replace(",", "."));
            Mena menaObj = new Mena(zeme, mena, mnozstvi, kod, kurz);
            meny.add(menaObj);
        }
        reader.close();
        return meny;
    }
    //nacitani z json souboru
    public static List<Mena> nactiKurzyJson(String soubor) throws IOException {
        //Deklarace seznamu pro uložení objektů typu Měna
        List<Mena> meny = new ArrayList<>();
        // Vytvoření instance Gson, což je knihovna pro práci s JSON
        Gson gson = new Gson();

        // Otevření BufferedReaderu pro čtení ze souboru specifikovaného argumentem "soubor"
        BufferedReader reader = new BufferedReader(new FileReader(soubor));

        // Specifikace typu, do kterého se bude JSON deserializovat
        java.lang.reflect.Type menaListType = new TypeToken<ArrayList<Mena>>() {}.getType();
        meny = gson.fromJson(reader, menaListType);

        reader.close();
        return meny;
    }
}
