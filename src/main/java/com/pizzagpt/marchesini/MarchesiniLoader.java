package com.pizzagpt.marchesini;

import com.pizzagpt.Main;
import com.pizzagpt.Utils;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MarchesiniLoader implements Initializable {

    public static void loadExercise(int difficulty, int exercise) throws IOException, InterruptedException {
        Utils.setScene("/com.pizzagpt/scenes/marchesini/MarchesiniEx" + difficulty + "_" + exercise + ".fxml"); //Imposta la scena
        Main.stg.setTitle("Sezione " + difficulty + ", Esercizio " + exercise);
        Button btn;
        try {
            Scanner read = new Scanner(new File("src/main/java/com/pizzagpt/marchesini/es1.txt"));
            while(read.hasNextLine()) {
                String line = read.nextLine();
                String[] tokens = line.split(",");
                if(tokens[0].equals("MarchesiniEx" + difficulty + "_" + exercise)) {
                    btn = (Button)Main.stg.getScene().lookup("#"+tokens[1]);
                    btn.fire();
                    break;
                }
            }
        } catch(FileNotFoundException ex) {
            System.out.println("[Errore: " + ex + "] File non trovato, riprova.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
