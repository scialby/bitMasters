package com.pizzagpt;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;  // Importa il tipo Pane
import java.io.IOException;

public class Utils {

    public static void setScene(String path) throws IOException {
        //DIFFERENZA: carica il file con la dimensione default.
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));
        Pane loadedContent = fxmlLoader.load();
        Scene scene = new Scene(loadedContent);
        Main.stg.setScene(scene);
        Main.stg.show();
    }

    public static void setScene(String path, int windowWidth, int windowHeight) throws IOException {
        //DIFFERENZA: si può specificare la dimensione della finestra nei parametri della funzione. Ti ho lasciato il codice per evitare problemi

        // Carica il file FXML
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));
        Pane loadedContent = fxmlLoader.load(); // Usa Pane invece di VBox per evitare il cast errato

        // Crea la Label per il punteggio del giocatore
        Main.playerScoreLabel = new Label("Score: " + Main.playerScore);

        // Crea un VBox che contenga la Label e il contenuto caricato dal file FXML
        VBox root = new VBox();
        root.getChildren().addAll(/*Main.playerScoreLabel, */loadedContent);

        // Crea la scena con il VBox che contiene la Label e il contenuto FXML
        Scene scene = new Scene(root, windowWidth, windowHeight);

        // Imposta la scena sullo stage principale
        Main.stg.setScene(scene);
        Main.stg.show();
    }
}
