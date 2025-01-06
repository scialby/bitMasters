package com.pizzagpt.sortino;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizzagpt.Main;
import com.pizzagpt.PATHS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.pizzagpt.LoggerManager.debug;

public class TutorialController {

    @FXML
    private Text content; // Collegato al FXML tramite fx:id

    public void changeTutorialText(String id) throws IOException {
        // Carica il file FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PATHS.TUTORIAL));
        Pane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Ottieni il controller per accedere ai suoi metodi
        TutorialController controller = loader.getController();

        // Ottieni il contenuto del testo dal file json, basandoti sull'ID dell'esercizio

        // Modifica il contenuto del Text tramite il controller
        controller.changeText(TutorialController.getTutorialText(id));

        // Imposta la scena
        Scene scene = new Scene(root);
        Main.stg.setScene(scene);
        Main.stg.show();
    }
    // Metodo per cambiare il testo
    public void changeText(String newText) {
        System.out.println("Cambiato il testo in: " + newText);
        content.setText(newText);

    }
    public static String getTutorialText(String id) throws IOException {
        // lettura del file json
        byte[] jsonData = Files.readAllBytes(Paths.get("src/main/java/com/pizzagpt/sortino/TutorialText.json"));

        //creazione dell'oggetto per manipolare il JSON
        ObjectMapper objectMapper = new ObjectMapper();

        //utilizza le chiavi del json per accedere al loro contenuto
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode idNode = rootNode.path(id);

        // Restituisce il contenuto della chiave
        return String.valueOf(idNode.asText());

    }

}
