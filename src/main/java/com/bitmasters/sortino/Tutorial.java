package com.bitmasters.sortino;

import com.bitmasters.Main;
import com.bitmasters.PATHS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.io.IOException;
import static com.bitmasters.sortino.Exercise.getExElement;
import static com.bitmasters.sortino.Navigation.navigate;

public class Tutorial {
    @FXML
    private Text textContent;

    @FXML
    private Button nextBtn;
    @FXML
    private Button mainMenuBtn;
    @FXML
    private Button previousBtn;

    @FXML
    public Label tutorialUserPoints;

    // Inizializza il tutorial, imposta il punteggio e i listener per i bottoni di navigazione
    @FXML
    public void initialize() {
        //imposto il punteggio nel label dei punti della schermata tutorial
        tutorialUserPoints.setText("Punteggio: " + Main.getCurrentUser().getProgress().getCurrentLevel());

        //creazione dei listener per i pulsanti di navigazione
        previousBtn.setOnAction(event -> navigate("previousBtn"));
        mainMenuBtn.setOnAction(event -> navigate("mainMenuBtn"));
        nextBtn.setOnAction(event -> navigate("nextBtn"));
    }

    // Cambia il testo mostrato nel componente textContent del tutorial
    public void changeText(String newText) {
        textContent.setText(newText);
    }

    // Imposta e mostra la scena del tutorial caricando il file FXML e aggiornando il contenuto del testo
    public static void setTutorial() throws IOException {
        SortinoMain.isTutorialActive = true;

        // Caricamento del file FXML
        FXMLLoader loader = new FXMLLoader(Tutorial.class.getResource(PATHS.SORTINO_TUTORIAL));
        Pane root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            // debug("[TUTORIAL] Errore durante il caricamento del file FXML: " + e.getMessage());
            throw new RuntimeException("[TUTORIAL] Impossibile caricare il file FXML.", e);
        }

        // Recupero del controller
        Tutorial controller = loader.getController();
        if (controller == null) {
            // debug("[TUTORIAL] Errore: controller non trovato.");
            throw new RuntimeException("[TUTORIAL] Controller non inizializzato.");
        }

        // Recupera e imposta il testo del tutorial in base all'ID dell'esercizio corrente
        String tutorialText;
        try {
            tutorialText = getExElement(SortinoMain.exId, "tutorialText");
        } catch (IOException e) {
            throw new RuntimeException("[TUTORIAL] Impossibile recuperare il testo del tutorial.", e);
        }

        controller.changeText(tutorialText);
        // debug("[TUTORIAL] Testo aggiornato con successo.");

        // Crea la scena e la imposta nello stage principale
        Scene scene = new Scene(root);
        Main.stg.setScene(scene);
        // Mostra la finestra con il tutorial
        Main.stg.show();
    }
}
