import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class JsonExport {

    //Vytvoření instance Gson pro serializaci objektů do JSON
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    //Metoda pro export seznamu objektů do souboru JSON
    public static void exportToJson(String filePath, List<Priklady> priklady) throws IOException {
        //Vytvoření instance FileWriter pro zápis do souboru

        try (FileWriter writer = new FileWriter(filePath)) {
            //Použití Gson k převedení seznamu objektů do JSON a zápis do souboru
            //tato metoda dokaze prevest pole bez for cyklu
            //Gson si sám poradí s tím, jak serializovat každý objekt v seznamu do formátu JSON a uložit je do daného
            gson.toJson(priklady, writer);
        }
    }
    public static List<Priklady> loadFromJson(String filePath) throws IOException {
        // Otevře soubor pro čtení pomocí BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Vytvoří Type objekt pro reprezentaci typu List<Priklady>
            //TypeToken je součást Gson knihovny
            //getType získává reálný typ objektu použit pro deserializaci
            //Program si vezme informaci že data se kterými bude pracovat je seznam typu přiklady
            Type listType = new TypeToken<List<Priklady>>(){}.getType();
            // Použije Gson k přečtení JSON dat z BufferedReader a jejich převedení na seznam typu List<Priklady>
            return gson.fromJson(reader, listType);
        }
    }


}