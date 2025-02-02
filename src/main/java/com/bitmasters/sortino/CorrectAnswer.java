package com.bitmasters.sortino;

import com.bitmasters.PATHS;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

import static com.bitmasters.LoggerManager.debug;
import static com.bitmasters.Utils.setScene;
import static com.bitmasters.sortino.Navigation.handleNextExercise;

public class CorrectAnswer {

    @FXML
    private Button newExBtn;  // Pulsante per passare al prossimo esercizio
    @FXML
    private Button toMenuBtn; // Pulsante per tornare al menu principale

    /**
     * Gestisce il passaggio al prossimo esercizio.
     * Questo metodo viene invocato quando l'utente clicca sul pulsante "Nuovo Esercizio".
     */
    @FXML
    private void newEx() {
        try {
            handleNextExercise(); // Passa al prossimo esercizio
        } catch (IOException e) {
            // Logga l'errore e mostra un messaggio all'utente
            System.err.println("Errore durante il passaggio al prossimo esercizio: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gestisce il ritorno al menu principale.
     * Questo metodo viene invocato quando l'utente clicca sul pulsante "Menu".
     */
    @FXML
    private void toMenu() {
        try {
            debug("Tentativo di ritorno al menu principale...");
            setScene(PATHS.SORTINO_MAIN); // Torna al menu principale
        } catch (IOException e) {
            // Logga l'errore e mostra un messaggio all'utente
            System.err.println("Errore durante il ritorno al menu principale: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
