import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //vytvoření a zobrazení hlavního okna aplikace
        SwingUtilities.invokeLater(new Runnable() {//
            @Override
            public void run() {
                //zobrazí hlavní okno aplikace
                new GUI().setVisible(true);
            }
        });
    }
}