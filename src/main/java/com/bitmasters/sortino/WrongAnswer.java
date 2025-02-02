package com.bitmasters.sortino;
import com.bitmasters.Main;
import com.bitmasters.PATHS;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import java.io.IOException;
import static com.bitmasters.LoggerManager.debug;
import static com.bitmasters.Utils.setScene;

public class WrongAnswer {
    @FXML
    private Button repeatExBtn;
    @FXML
    private Button toMenuBtn;
    @FXML
    private Text wrongLabel;

    /**
     * Inizializza il controller impostando il testo per il numero di tentativi rimanenti.
     */
    @FXML
    public void initialize() {
        debug("Inizializzazione controller WrongAnswer...");
        wrongLabel.setText(String.valueOf(3 - Exercise.wrongAnswersCount));
    }

    /**
     * Gestisce il ripetere l'esercizio in caso di risposta sbagliata.
     */
    @FXML
    private void repeatEx() {
        try {
            Navigation.handleRepeatEx(); // Passa all'esercizio da ripetere
        } catch (IOException e) {
            // Logga l'errore e mostra un messaggio all'utente
            System.err.println("Errore durante il passaggio al prossimo esercizio: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gestisce il ritorno al menu principale.
     */
    @FXML
    private void toMenu() {
        try {
            debug("Tentativo di ritorno al menu principale...");

            String[] parts = SortinoMain.exId.split("_");

            if(Exercise.correctAnswerCount == 2){
                int partValue = Integer.parseInt(parts[0]);
                Main.getCurrentUser().getProgress().setCurrentLevel(2 * partValue + (partValue - 1));
            }
            String currentExercise = SortinoMain.exId;
            // Aggiunge un attempt all'utente
            Main.getCurrentUser().getProgress().addAttempt(currentExercise);
            setScene(PATHS.SORTINO_MAIN);
        } catch (IOException e) {
            // Logga l'errore e mostra un messaggio all'utente
            System.err.println("Errore durante il ritorno al menu principale: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
