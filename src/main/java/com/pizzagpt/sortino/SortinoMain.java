package com.pizzagpt.sortino;

import com.pizzagpt.marchesini.controller.ExerciseController;
import com.pizzagpt.marchesini.loader.Loader;
import com.pizzagpt.Main;
import com.pizzagpt.PATHS;
import com.pizzagpt.Utils;
import com.pizzagpt.userSession.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.pizzagpt.LoggerManager.debug;
import static com.pizzagpt.Utils.setScene;
import static com.pizzagpt.sortino.Tutorial.setTutorial;

public class SortinoMain {
    private static final int INTERMEDIATE_THRESHOLD = 3;
    private static final int ADVANCED_THRESHOLD = 6;
    private static final String NO_ACCESS_STYLE = "-fx-background-color: red; -fx-text-fill: white;";
    private static final String ACCESS_STYLE = "-fx-background-color: white; -fx-text-fill: black;";

    @FXML private Button ex1_1, ex2_1, ex3_1;
    @FXML public Label playerScoreLabel;
    @FXML private HBox progressBar1;
    @FXML private HBox progressBar2;
    @FXML private HBox progressBar3;
    private static ProgressManager progressManager;
    private User loggedUser;

    public static String exId = "1_1";
    public static boolean isTutorialActive = false;
    public static boolean hasFailed = false;
    public static boolean shouldAwardPoints = true;

    public static ProgressManager getProgressManager() {
        return progressManager;
    }

    public static boolean isTutorialActive() {
        return isTutorialActive;
    }

    // Inizializza la scena e imposta tutto il necessario per il gioco
    public void initialize() {
        progressManager = new ProgressManager(progressBar1, progressBar2, progressBar3);
        setupButtonListeners();
        updateButtonAccessStyles();
        updateScoreDisplay();

        if (Main.getCurrentUser() != null) {
            int currentLevel = Main.getCurrentUser().getProgress().getCurrentLevel();
            exId = currentLevel + "_1";
            debug("Riprendo dal livello " + currentLevel + " con esercizio: " + exId);
        } else {
            exId = "1_1";
        }

        updateExerciseAccess();
    }

    // Imposta i listener per i bottoni degli esercizi
    private void setupButtonListeners() {
        ex1_1.setOnAction(event -> startExercise("1_1"));
        ex2_1.setOnAction(event -> startExercise("2_1"));
        ex3_1.setOnAction(event -> startExercise("3_1"));
    }

    // Aggiorna lo stile dei bottoni in base all'accesso agli esercizi
    private void updateButtonAccessStyles() {
        updateExerciseAccess();
    }

    // Restituisce il bottone corrispondente all'ID dell'esercizio
    private Button getExerciseButtonForId(String id) {
        switch (id) {
            case "1_1": return ex1_1;
            case "2_1": return ex2_1;
            case "3_1": return ex3_1;
            default: return null;
        }
    }

    // Aggiorna il punteggio visualizzato in base al livello corrente dell'utente
    private void updateScoreDisplay() {
        playerScoreLabel.setText("Livello: " + String.valueOf(Main.getCurrentUser().getProgress().getCurrentLevel()));
    }

    // Verifica se l'esercizio è già stato completato dall'utente
    private boolean isExerciseCompleted(String exerciseId) {
        int exerciseLevel = Character.getNumericValue(exerciseId.charAt(0));
        int userLevel = Main.getCurrentUser().getProgress().getCurrentLevel();
        return userLevel > exerciseLevel;
    }

    // Avvia l'esercizio selezionato
    @FXML
    private void startExercise(String id) {
        Main.getCurrentUser().getProgress().resetCompletedExercises();
        Exercise.correctAnswerCount=0;
        hasFailed = false;
        Exercise.wrongAnswersCount = 0;
        try {
            if (!hasExerciseAccess(id, getExerciseButtonForId(id))) {
                debug("Accesso negato all'esercizio " + id + ". Livello insufficiente.");
                return;
            }
            shouldAwardPoints = !isExerciseCompleted(id);
            if (!shouldAwardPoints) {
                debug("Esercizio " + id + " già completato. Non verranno assegnati punti.");
            }
            exId = id;
            progressManager.updateProgressBars();
            setTutorial();
        } catch (IOException e) {
            throw new RuntimeException("Errore nel caricamento dell'esercizio", e);
        }
    }

    // Aggiorna lo stile dei bottoni in base al livello dell'utente
    private void updateExerciseAccess() {
        int userLevel = Main.getCurrentUser().getProgress().getCurrentLevel();

        ex1_1.setStyle(ACCESS_STYLE);
        ex2_1.setStyle(userLevel < INTERMEDIATE_THRESHOLD ? NO_ACCESS_STYLE : ACCESS_STYLE);
        ex3_1.setStyle(userLevel < ADVANCED_THRESHOLD ? NO_ACCESS_STYLE : ACCESS_STYLE);

        if (ex1_1.getScene() != null) {
            ex1_1.getScene().getRoot().requestLayout();
            ex2_1.getScene().getRoot().requestLayout();
            ex3_1.getScene().getRoot().requestLayout();
        }
    }

    // Controlla se l'utente ha accesso all'esercizio richiesto in base al livello
    private boolean hasExerciseAccess(String exerciseId, Button exerciseButton) {
        int userLevel = Main.getCurrentUser().getProgress().getCurrentLevel();
        switch (exerciseId) {
            case "1_1":
                return true;
            case "2_1":
                return userLevel >= INTERMEDIATE_THRESHOLD;
            case "3_1":
                return userLevel >= ADVANCED_THRESHOLD;
            default:
                return false;
        }
    }

    // Gestisce il reset del punteggio dell'utente e aggiorna la scena
    @FXML
    public void handleResetPoints(MouseEvent event) {
        debug("Reset punteggio utente...");
        Main.getCurrentUser().getProgress().setCurrentLevel(1);
        Main.getCurrentUser().getProgress().resetCompletedExercises();
        updateButtonAccessStyles();
        updateScoreDisplay();

        exId = "1_1";
        progressManager.updateProgressBars();

        try {
            setScene(PATHS.SORTINO_MAIN);
        } catch (IOException e) {
            debug("Errore nel refresh della scena: " + e.getMessage());
        }
    }

    // Torna al menù principale caricando la scena corrispondente
    public void toMainMenu() {
        try {
            Loader loader = new Loader(loggedUser, PATHS.MAIN_MENU);
            loader.setTitle("Menù principale");
            loader.load();
            loader.show();
            getProgressManager().saveProgress();
            setScene(PATHS.LOGIN_SCENE);
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare il menu principale: " + e.getMessage());
        }
    }

    // Esegue il logout e torna alla scena di login
    public void logOut() {
        try {
            setScene(PATHS.LOGIN_SCENE);
            getProgressManager().saveProgress();

        } catch (IOException ex) {
            debug("Errore durante il logout: " + ex.getMessage());
        }
    }
}
