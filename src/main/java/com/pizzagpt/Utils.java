package com.pizzagpt;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;  // Importa il tipo Pane
import java.io.IOException;

public class Utils {

    public static void setScene(String path) throws IOException {

        // Carica il file FXML
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));
        Pane loadedContent = fxmlLoader.load();  // Usa Pane invece di VBox per evitare il cast errato

        // Crea la Label per il punteggio del giocatore
        Main.playerScoreLabel = new Label("Score: " + Main.playerScore);

        // Crea un VBox che contenga la Label e il contenuto caricato dal file FXML
        VBox root = new VBox();
        root.getChildren().addAll(Main.playerScoreLabel, loadedContent);

        // Crea la scena con il VBox che contiene la Label e il contenuto FXML
        Scene scene = new Scene(root, Main.windowWidth, Main.windowHeight);

        // Imposta la scena sullo stage principale
        Main.stg.setScene(scene);
        Main.stg.show();
    }
}
