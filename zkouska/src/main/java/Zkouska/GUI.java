package Zkouska;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class GUI {
    private static JLabel stitek;
    private static JComboBox<String> vyber;
    private static DefaultListModel<Integer> seznam;
    private static JList<Integer> poleCisel;


    public static void zobrazOkno() {
        JFrame okno = new JFrame("Moje okno"); // Vytvoření hlavního okna (JFrame)
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Nastavení výchozích operací při zavření okna
        okno.setSize(500, 500); // Nastavení velikosti okna x na y pixelů
        okno.setLayout(new BorderLayout());// Použití BorderLayout pro lepší rozvržení komponent

        vytvorLabel();
        okno.add(stitek, BorderLayout.SOUTH);//pridani do okna
        vytvorComboBox();
        okno.add(vyber,BorderLayout.NORTH);
        JButton btcisla = new JButton("Vlož do seznamu vygenerovaná čísla");
        vytvorPoleCisel();

        JButton tlacitko = new JButton("Přidej číslo");
        tlacitko.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //po kliku na tlacitko vytvor dalsi nahodne cislo
                // a pridej ho do seznamu cisel
                Random nahodne = new Random();
                int nahodneCislo = nahodne.nextInt(1000);
                seznam.addElement(nahodneCislo);
            }
        });


        JPanel tlacitkoPanel = new JPanel();
        tlacitkoPanel.add(tlacitko); //Pridani tlacitka do panelu

        JPanel hlavniPanel = new JPanel(new BorderLayout());
        hlavniPanel.add(new JScrollPane(poleCisel),BorderLayout.WEST);//pridani pole cisel do hl panelu zarovnani na center
        hlavniPanel.add(tlacitkoPanel,BorderLayout.SOUTH);//pridani tlacitka do hl. panelu na jih
        okno.add(hlavniPanel);//Pridani hlavniho panelu do panelu



        JButton nactiTlacitko = new JButton("Načti z CSV");
        nactiTlacitko.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        tlacitkoPanel.add(nactiTlacitko);

        //casovac
        Timer casovac = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cas = new SimpleDateFormat("HH:mm:ss").format(new Date());//aktualni cas v danem formatu
                stitek.setText(cas); // Nastavení textu JLabel
            }
        });

        casovac.start(); //spusteni casovace
        okno.setVisible(true); // Zobrazení okna
    }
    private static void vytvorLabel(){
        stitek = new JLabel(); // Vytvoření JLabel pro zobrazení času
        stitek.setHorizontalAlignment(SwingConstants.RIGHT);
        stitek.setFont(stitek.getFont().deriveFont(20f));//nastaveni velikosti
    }

    private static void vytvorComboBox(){
        String[] zaznamy = { "Položka 1", "Položka 2", "Položka 3", "Položka 4" };
        vyber = new JComboBox<>(zaznamy);
        vyber.setSelectedIndex(0); // Nastavení výchozí vybrané položky
        vyber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Získání vybrané položky
                String vybranyZaznam = (String) vyber.getSelectedItem();
                System.out.println("Vybraná položka: " + vybranyZaznam);
            }
        });

    }
    private static void vytvorPoleCisel(){
        seznam = new DefaultListModel<>();// Vytvoření modelu seznamu
        Random nahodne = new Random();
        for (int i = 0; i < 10; i++) {
            int nahodneCislo = nahodne.nextInt(1000); //vygeneruje cislo od 0 do 999
            seznam.addElement(nahodneCislo);
        }
        poleCisel = new JList<>(seznam);
    }

}
