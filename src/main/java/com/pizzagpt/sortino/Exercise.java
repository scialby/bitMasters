package com.pizzagpt.sortino;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizzagpt.Main;
import com.pizzagpt.PATHS;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.pizzagpt.LoggerManager.debug;
import static com.pizzagpt.Utils.setScene;
import static com.pizzagpt.sortino.Navigation.handleNextExercise;
import static com.pizzagpt.sortino.SortinoMain.hasFailed;

public class Exercise {
    // Costanti per il numero massimo di esercizi per livello e il percorso del file JSON
    public static final int MAX_EXERCISES_PER_LEVEL = 3;
    private static final String JSON_PATH = "src/main/java/com/pizzagpt/sortino/Exercises.json";
    private static final double FEEDBACK_DELAY_SECONDS = 0.3;//tempo di animazione pulsante risposta

    // Dati degli esercizi caricati dal file JSON
    private static JsonNode exerciseData;

    // Contatore delle risposte
    public static int wrongAnswersCount = 0;
    public static int correctAnswerCount = 0;

    // Blocco statico per caricare il file JSON con gli esercizi all'avvio
    static {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(JSON_PATH));
            exerciseData = new ObjectMapper().readTree(jsonData);
        } catch (IOException e) {
            debug("Errore caricamento JSON: " + e.getMessage());
            exerciseData = new ObjectMapper().createObjectNode();
        }
    }

    // Variabili FXML per i componenti dell'interfaccia utente
    @FXML private HBox choicesBox, navigationBox;
    @FXML private ImageView exerciseImage;
    @FXML private Button nextBtn, mainMenuBtn, previousBtn;
    @FXML private Label instructionsLabel;
    @FXML private Button choice1, choice2, choice3, choice4;

    private Button selectedBtn;  // Bottone della scelta
    private ExerciseUI exerciseUI;  // Gestore dell'interfaccia dell'esercizio

    /**
     * Metodo di inizializzazione della schermata. Carica il contenuto dell'esercizio e configura i bottoni.
     */
    @FXML
    private void initialize() {
        exerciseUI = new ExerciseUI(exerciseImage, instructionsLabel,
                new Button[]{choice1, choice2, choice3, choice4},
                navigationBox, choicesBox);  // Inizializza l'interfaccia dell'esercizio
        loadExerciseContent(); // Carica il contenuto dell'esercizio
        setupNavigationHandlers(); // Configura i bottoni di navigazione
    }

    /**
     * Carica il contenuto dell'esercizio, inclusa l'immagine e le istruzioni.
     */
    private void loadExerciseContent() {
        try {
            File imageFile = new File(getExElement(SortinoMain.exId, "img"));
            exerciseUI.loadImage(imageFile);  // Carica l'immagine dell'esercizio
            exerciseUI.setInstructions(getExElement(SortinoMain.exId, "instructions")); // Imposta le istruzioni
            exerciseUI.configureChoiceButtons(getChoicesText(SortinoMain.exId)); // Configura i pulsanti di scelta
        } catch (IOException e) {
            debug("Errore nel caricamento del contenuto dell'esercizio: " + e.getMessage());
        }
    }

    /**
     * Gestisce la navigazione alla fine del livello
     */
    private void handleFinalLevel(boolean isPassed) {
        debug("Gestione fine livello");
        if (isPassed) {
            debug("Risposta corretta!");
            correctAnswerCount++;
        } else {
            debug("Risposta sbagliata!");
            hasFailed = true; // registra il livello come fallito
            wrongAnswersCount++;
        }
        Main.getCurrentUser().getProgress().addAttempt(SortinoMain.exId);//necessario per allExerciseCompleted()
        debug("Tutti gli esercizi completati? " + allExercisesCompleted());
        debug("Current User: " + Main.getCurrentUser());
        // Verifica se tutti gli esercizi sono stati completati
        if (allExercisesCompleted() && Main.getCurrentUser() != null) {
            debug("Livello completato!");
            try {
                if(hasFailed) {
                    debug("Passaggio alla schermata di errore...");
                    setScene(PATHS.WRONGVIEW); // Mostra la schermata di errore
                }
                else{
                    debug("Calcolo e impostazione dei punti...");
                    calculateAndSetPoints();
                    debug("Passaggio alla schermata di risposta corretta...");
                    setScene(PATHS.CORRECTVIEW);
                }
            } catch (IOException e) {
                debug("Errore nel passaggio alla schermata di errore: " + e.getMessage());
            }
        } else {
            // Passa al prossimo esercizio se non tutti sono stati completati
            try {
                debug("Passaggio al prossimo esercizio...");
                handleNextExercise();
            } catch (IOException e) {
                debug("Errore durante il passaggio al prossimo esercizio: " + e.getMessage());
            }
        }

    }
    /**
     * Gestisce la selezione di una risposta. Controlla se la risposta è corretta o sbagliata e avvia la navigazione.
     */
    @FXML
    private void handleChoice(javafx.scene.input.MouseEvent event) throws IOException {
        selectedBtn = (Button) event.getSource();
        debug("Pulsante selezionato: " + selectedBtn.getText());

        try {
            String correctAnswer = getExElement(SortinoMain.exId, "correct");
            boolean isCorrect = selectedBtn.getId().equals(correctAnswer);
            exerciseUI.setButtonFeedback(selectedBtn, isCorrect); // Mostra l'animazione sul bottone

            // avvia la navigazione dopo un breve ritardo
            scheduleNavigation(() -> {
                if (isCorrect) {
                    handleCorrectAnswer();
                } else {
                    handleWrongAnswer();
                }
            });
        } catch (IOException e) {
            debug("Errore risposta: " + e.getMessage());
        }
    }

    /**
     * Gestisce la risposta corretta, aggiorna il progresso dell'utente e calcola i punti se il livello è completato.
     */
    private void handleCorrectAnswer() {
           handleFinalLevel(true);
    }

    /**
     * Gestisce la risposta sbagliata
     */
    private void handleWrongAnswer() {

        handleFinalLevel(false);

    }

    /**
     * Calcola e imposta i punti in base al livello attuale dell'utente.
     */
    private void calculateAndSetPoints() {
        int currentLevel = Integer.parseInt(SortinoMain.exId.split("_")[0]);
        int newPoints = getPointsForLevel(currentLevel);
        Main.getCurrentUser().getProgress().setCurrentLevel(newPoints); // Imposta i punti per il livello
        debug("Calcolati punti per il livello " + currentLevel + ": " + newPoints + " punti.");
    }

    /**
     * Restituisce il numero di punti per un determinato livello.
     */
    private int getPointsForLevel(int level) {
        switch (level) {
            case 1: return 3;
            case 2: return 6;
            case 3: return 9;
            default: return 0;
        }
    }

    /**
     * Verifica se tutti gli esercizi del livello sono stati completati.
     */
    private boolean allExercisesCompleted() {
        int currentLevel = Integer.parseInt(SortinoMain.exId.split("_")[0]);//1_2 -> 1
        List<String> completedExercises = Main.getCurrentUser().getProgress().getCompletedExercises(currentLevel);

        for (int i = 1; i <= MAX_EXERCISES_PER_LEVEL; i++) {
            String exerciseId = currentLevel + "_" + i;
            if (!completedExercises.contains(exerciseId)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Pianifica la navigazione con un ritardo temporale.
     */
    private void scheduleNavigation(Runnable action) {
        new Timeline(new KeyFrame(
                Duration.seconds(FEEDBACK_DELAY_SECONDS),
                e -> action.run()
        )).play();
    }

    /**
     * Configura i gestori per la navigazione tra gli esercizi.
     */
    private void setupNavigationHandlers() {
        previousBtn.setOnAction(e -> Navigation.navigate("previousBtn"));
        mainMenuBtn.setOnAction(e -> Navigation.navigate("mainMenuBtn"));
        nextBtn.setOnAction(e -> Navigation.navigate("nextBtn"));
    }

    /**
     * Estrae il testo delle scelte per un esercizio.
     */
    public static List<String> getChoicesText(String id) throws IOException {
        JsonNode choicesNode = exerciseData.path(id).path("choices");
        List<String> choices = new ArrayList<>();
        choicesNode.forEach(node -> choices.add(node.asText()));
        return choices;
    }


    /**
     * Estrae un elemento dal file JSON in base all'id dell'esercizio e al tipo di elemento (es. "img", "instructions").
     */
    public static String getExElement(String id, String element) throws IOException {
        return exerciseData.path(id).path(element).asText();
    }
}