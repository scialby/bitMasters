package com.pizzagpt.marchesini;

import com.pizzagpt.Main;
import com.pizzagpt.Utils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MarchesiniUtils {

    private static String path = "src/main/resources/com.pizzagpt/scenes/marchesini/";

    //----------------------------------------------//
    // Carica la scena
    public static void loadScene(int difficulty, int exercise) throws IOException {
        //Carica l'esercizio e crea la schermata di caricamento, così che abbia il tempo di controllare se esiste già un salvataggio
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com.pizzagpt/scenes/marchesini/views/Es" + difficulty + "_" + exercise + ".fxml"));
        Pane loadedContent = fxmlLoader.load(); //Carica il contenuto
        StackPane root = new StackPane(); //Crea il parente per contenuto e caricamento
        root.getChildren().add(loadedContent); //Gli aggiunge il contenuto

        StackPane overlay = new StackPane(); //Crea caricamento
        overlay.getStyleClass().add("loading");
        ImageView imageview = new ImageView();
        imageview.setImage(new Image("file:" + path + "images/loading-gif.gif"));
        imageview.setFitWidth(70);
        imageview.setPreserveRatio(true);
        overlay.getChildren().add(imageview);
        root.getChildren().add(overlay); //Aggiunge il caricamento

        Scene scene = new Scene(root); //Crea la scena
        scene.getStylesheets().clear(); //Toglie eventuali altri file CSS
        scene.getStylesheets().add(MarchesiniUtils.class.getResource("/com.pizzagpt/scenes/marchesini/style.css").toExternalForm()); //Applica il CSS
        Main.stg.setResizable(false);
        Main.stg.setScene(scene);
        Main.stg.show();
        Main.stg.setTitle("Sezione " + difficulty + ", Esercizio " + exercise);

        //Controlla se c'è già un salvataggio
        try {
            Scanner read = new Scanner(new File(path + "saves/Es" + difficulty + ".txt"));
            while(read.hasNextLine()) {
                String line = read.nextLine();
                String[] tokens = line.split(",");
                if(exercise == Integer.parseInt(tokens[0])) {
                    Button btn = (Button)Main.stg.getScene().lookup("#"+tokens[1]);
                    btn.fire();
                    break;
                }
            }
        } catch(FileNotFoundException ex) {
            System.out.println("[Eccezione: " + ex + "] Salvataggio non trovato.");
        }

        //Controlla se c'è un precedente o un successivo
        root.getChildren().remove(overlay); //Rimuove la schermata di caricamento
    }
}
