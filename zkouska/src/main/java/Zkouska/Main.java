package Zkouska;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {//Runnable je rozhraní které má metodu run()
            public void run() { //metoda obsahuje kód který chceme spustit
                GUI.zobrazOkno(); //Zavolání metody z GUI
            }
        });
    }

}
