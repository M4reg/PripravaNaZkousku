import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Nastavení názvu souboru, ze kterého se budou načítat kurzy měn
        String soubor = "denni_kurz2.json";
        try {
            // Načtení kurzů měn ze zadaného souboru
            List<Mena> kurzy = GUI.nactiKurzyJson(soubor);
            // Vytvoření instance třídy GUI pro konverzi měn s načtenými kurzy
            GUI converter = new GUI(kurzy);
            // Zobrazení uživatelského rozhraní
            converter.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}