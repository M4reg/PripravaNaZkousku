import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI extends JFrame {
    private JTable tabulka;
    private JTextField vstupCervena;
    private JTextField vstupZelena;
    private JTextField vstupModra;
    private JTextField jmeno;
    private JPanel vyslednaBarva;
    private TabulkaBarev tabulkaBarva;

    //GUI Konstruktor
    /*
    -Nastavuje titulek, velikost a operaci uzavření okna.
    -Nastavuje layout BorderLayout pro celé okno.
    -Vytváří tabulku pro zobrazení barev a přidá ji do středu okna.
    -Vytváří panel pro vstupní pole (pro nastavení RGB hodnot a jména barvy) a přidá jej do spodní části okna.
    -Přidá tlačítka pro přidání barvy a export dat do CSV souboru.
    -Vytváří panel pro zobrazení aktuální vybrané barvy.
    */

    public GUI() {
        setTitle("Paleta na míchání barev");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Nastavení layoutu
        setLayout(new BorderLayout());

        // Vytvoření tabulky ve středu okna
        //instance třídy TabulkaBarev
        tabulkaBarva = new TabulkaBarev();

        //instanci komponenty JTable  bude zobrazovat data o barvách, která jsou uložena v objektu tabulkaBarva
        tabulka = new JTable(tabulkaBarva);
        JScrollPane tableScrollPane = new JScrollPane(tabulka);
        add(tableScrollPane, BorderLayout.CENTER);

        // Panel pro vstupní pole ve spodní části okna
        JPanel vstupPanel = new JPanel(new FlowLayout());

        // Vstupní pole
        vstupCervena = new JTextField(3);
        vstupZelena = new JTextField(3);
        vstupModra = new JTextField(3);
        jmeno = new JTextField(10);

        // Tlačítko pro přidání barvy
        JButton pridejTlacitko = new JButton("Přidej barvu");
        pridejTlacitko.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pridejBarvu();
            }
        });
        // Tlačítko pro export
        JButton exportTlacitko = new JButton("Export");
        exportTlacitko.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportDat.exportData(tabulka);
            }
        });
        // Tlačítko pro export
        JButton nactiTlacitko = new JButton("Načti");
        nactiTlacitko.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nactiData.nacti(tabulka);
            }
        });


        // Přidání vstupních komponent do panelu
        vstupPanel.add(new JLabel("R:"));
        vstupPanel.add(vstupCervena);
        vstupPanel.add(new JLabel("G:"));
        vstupPanel.add(vstupZelena);
        vstupPanel.add(new JLabel("B:"));
        vstupPanel.add(vstupModra);
        vstupPanel.add(new JLabel("Jméno:"));
        vstupPanel.add(jmeno);
        vstupPanel.add(pridejTlacitko);
        vstupPanel.add(exportTlacitko);
        vstupPanel.add(nactiTlacitko);

        // Panel pro zobrazení barvy
        vyslednaBarva = new JPanel();
        //Box 50x50 na zobrazení barvy
        vyslednaBarva.setPreferredSize(new Dimension(50, 50));
        vstupPanel.add(vyslednaBarva);

        // Přidání inputPanelu do spodní části okna
        add(vstupPanel, BorderLayout.SOUTH);

        //Při kliknutí na řádek tabulky nastaví do vstupních polí hodnoty z tabulky
        tabulka.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int vybranyRadek = tabulka.getSelectedRow();
                if (vybranyRadek != -1){
                    MyColor vybranaBarva = tabulkaBarva.ziskejBarvuNaRadku(vybranyRadek);
                    vstupCervena.setText(String.valueOf(vybranaBarva.getR()));
                    vstupZelena.setText(String.valueOf(vybranaBarva.getG()));
                    vstupModra.setText(String.valueOf(vybranaBarva.getB()));
                    jmeno.setText(vybranaBarva.getJmeno());
                    vyslednaBarva.setBackground(new Color(vybranaBarva.getR(), vybranaBarva.getG(), vybranaBarva.getB()));
                }
            }
        });
    }


    // Metoda pro přidání barvy do tabulky
    //Získá z vstupních polí pro RGB složky a jméno barvy
    private void pridejBarvu() {
        try {
            int r = Integer.parseInt(vstupCervena.getText());
            int g = Integer.parseInt(vstupZelena.getText());
            int b = Integer.parseInt(vstupModra.getText());
            String name = jmeno.getText();
            //overeni platnosti zadanych hodnot
            if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
                throw new NumberFormatException("RGB hodnoty musí být mezi 0 and 255. Jinak se jedná o neplatný údaj.");
            }
            //přidání do tabulky a zobrazení barvy v nahledu
            tabulkaBarva.pridejBarvu(new MyColor(r, g, b, name));
            vyslednaBarva.setBackground(new Color(r, g, b));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Prosím vložte platné hodnoty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}

