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
import static com.pizzagpt.Utils.setScene;

public class ExerciseController {

    @FXML
    private Text content; // Collegato al FXML tramite fx:id

    public void changeTutorialText() throws IOException {
        // Carica il file FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PATHS.TUTORIAL));
        Pane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Ottieni il controller per accedere ai suoi metodi
        ExerciseController controller = loader.getController();


        // Modifica il contenuto del Text tramite il controller
        controller.changeText(ExerciseController.getTutorialText(Main.exId));

        // Imposta la scena
        Scene scene = new Scene(root);
        Main.stg.setScene(scene);
        Main.stg.show();
    }
    // Metodo per cambiare il testo
    public void changeText(String newText) {
        content.setText(newText);

    }
    public static String getTutorialText(String id) throws IOException {
        // lettura del file json
        byte[] jsonData = Files.readAllBytes(Paths.get("src/main/java/com/pizzagpt/sortino/Exercises.json"));

        //creazione dell'oggetto per manipolare il JSON
        ObjectMapper objectMapper = new ObjectMapper();

        //utilizza le chiavi del json per accedere al loro contenuto
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode idNode = rootNode.path(id).path("tutorialText");

        // Restituisce il contenuto della chiave
        return String.valueOf(idNode.asText());

    }
    public static String getExImage(String id) throws IOException {
        // lettura del file json
        byte[] jsonData = Files.readAllBytes(Paths.get("src/main/java/com/pizzagpt/sortino/Exercises.json"));

        //creazione dell'oggetto per manipolare il JSON
        ObjectMapper objectMapper = new ObjectMapper();

        //utilizza le chiavi del json per accedere al loro contenuto
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode idNode = rootNode.path(id).path("img");

        // Restituisce il contenuto della chiave
        return String.valueOf(idNode.asText());

    }
    // Metodi per la navigazione chiamati dai bottoni nella view
    public void setHomepageView(){
        System.out.println("Ritorno alla homepage");
        try {
            setScene(PATHS.SORTINO_MAIN);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void startExercise(){

        try {

            String exPath = PATHS.SORTINO_SCENES+"Ex"+Main.exId+".fxml";
            debug("Starting exercise: " + exPath);
            setScene(PATHS.SORTINO_SCENES+"Ex"+Main.exId+".fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
