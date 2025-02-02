package com.pizzagpt.sortino;

import com.pizzagpt.Main;
import com.pizzagpt.PATHS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.pizzagpt.LoggerManager.debug;
import static com.pizzagpt.Utils.setScene;
import static com.pizzagpt.sortino.Navigation.handleNextExercise;

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
