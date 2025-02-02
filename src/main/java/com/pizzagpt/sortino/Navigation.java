package com.pizzagpt.sortino;

import com.pizzagpt.*;
import static com.pizzagpt.Utils.setScene;
import static com.pizzagpt.LoggerManager.debug;
import static com.pizzagpt.sortino.Exercise.MAX_EXERCISES_PER_LEVEL;

import java.io.IOException;

/**
 * Classe per la gestione della navigazione tra le scene.
 */
public class Navigation {

    /**
     * Naviga tra le scene in base al comando.
     * @param route Il comando di navigazione
     */
    public static void navigate(String route) {
        try {
            int currentLevel = Integer.parseInt(SortinoMain.exId.split("_")[1]); // Estrae il livello corrente da exId

            switch (route) {
                case "nextBtn":
                    // Gestisce il passaggio all'esercizio successivo
                    if(currentLevel == 3 && !SortinoMain.isTutorialActive) {
                        setScene(PATHS.SORTINO_MAIN);  // Torna alla schermata principale
                        return;
                    }
                    handleNextExercise();
                    break;
                case "previousBtn":
                    // Gestisce il ritorno all'esercizio precedente
                    if(currentLevel == 1 && SortinoMain.isTutorialActive) {
                        setScene(PATHS.SORTINO_MAIN);
                        return;
                    }
                    handlePreviousEx();
                    break;
                case "mainMenuBtn":
                    setScene(PATHS.SORTINO_MAIN);
                    break;
                default:
                    debug("Comando di navigazione non riconosciuto: " + route);
                    break;
            }
        } catch (IOException e) {
            debug("Errore navigazione: " + e.getMessage());
        }
    }

    /**
     * Gestisce il passaggio all'esercizio successivo.
     */
    static void handleNextExercise() throws IOException {
        debug("handleNextExercise: Inizio gestione prossimo esercizio");

        // Se il tutorial è attivo, lo disattiva e carica gli esercizi
        if (SortinoMain.isTutorialActive()) {
            debug("handleNextExercise: Disattivazione tutorial e inizio esercizi");
            SortinoMain.isTutorialActive = false;
            setScene(PATHS.SORTINO_EX);
            return;
        }
        String newId = calculateNextExerciseId(SortinoMain.exId);

        // Aggiorna l'ID dell'esercizio e carica la nuova scena
        SortinoMain.exId = newId;
        debug("handleNextExercise: Caricamento nuovo esercizio: " + newId);
        Tutorial.setTutorial();
    }

    /**
     * Calcola l'ID del prossimo esercizio in base all'ID corrente.
     * @return Il nuovo ID o null se non ci sono più esercizi
     */
    private static String calculateNextExerciseId(String currentId) {
        if (currentId == null) return "1_1";  // Se non c'è ID, torna al primo esercizio

        String[] parts = currentId.split("_");
        int level = Integer.parseInt(parts[0]);
        int exercise = Integer.parseInt(parts[1]);

        if (exercise < MAX_EXERCISES_PER_LEVEL) {
            // Passa al prossimo esercizio dello stesso livello
            return level + "_" + (exercise + 1);
        } else if (level < 3) {
            // Passa al primo esercizio del livello successivo
            return (level + 1) + "_1";
        }

        // Se non ci sono più esercizi disponibili, ritorna null
        return null;
    }

    /**
     * Gestisce l'esercizio da ripetere (resetta l'esercizio e carica di nuovo la scena).
     */
    public static void handleRepeatEx() throws IOException {
        debug("handleRepeatEx: Gestione esercizio ripetuto");

        // Se l'ID dell'esercizio è nullo, ritorna al menu principale
        if (SortinoMain.exId == null) {
            debug("handleRepeatEx: ID esercizio null, ritorno al menu principale");
            setScene(PATHS.SORTINO_MAIN);
            return;
        }

        // Ripristina il primo esercizio del livello attuale
        String[] parts = SortinoMain.exId.split("_");
        int currentLevel = Integer.parseInt(parts[0]);

        // Nuovo ID per l'esercizio da ripetere
        String newId = currentLevel + "_1";

        // Reset dei progressi
        debug("Nuovo ID esercizio: " + newId);
        Main.getCurrentUser().getProgress().resetCompletedExercises();
        SortinoMain.hasFailed = false;
        Exercise.wrongAnswersCount = 0;

        //cambio scena
        SortinoMain.exId = newId;
        setScene(PATHS.SORTINO_EX);
    }

    /**
     * Gestisce la navigazione all'esercizio precedente.
     * Se si è al primo esercizio di un livello, torna al livello precedente.
     * Se si è al primo esercizio del primo livello, torna al menu principale.
     */
    private static void handlePreviousEx() throws IOException {
        debug("handlePreviousEx: Gestione esercizio precedente");

        if (SortinoMain.exId == null) {
            debug("handlePreviousEx: ID esercizio null, ritorno al menu principale");
            setScene(PATHS.SORTINO_MAIN);
            return;
        }

        String[] parts = SortinoMain.exId.split("_");
        int currentLevel = Integer.parseInt(parts[0]);
        int currentExercise = Integer.parseInt(parts[1]);

        String newId;
        if (currentExercise > 1) {
            // Vai all'esercizio precedente nello stesso livello
            newId = currentLevel + "_" + (currentExercise - 1);
            debug("handlePreviousEx: Torno all'esercizio precedente: " + newId);
        }
        else if (currentLevel > 1) {
            // Vai all'ultimo esercizio del livello precedente
            newId = (currentLevel - 1) + "_" + MAX_EXERCISES_PER_LEVEL;
            debug("handlePreviousEx: Torno al livello precedente: " + newId);
        }
        else {
            // Siamo al primo esercizio del primo livello
            debug("handlePreviousEx: Primo esercizio del primo livello, ritorno al menu");
            setScene(PATHS.SORTINO_MAIN);
            return;
        }

        // Aggiorna l'ID dell'esercizio e carica la scena
        SortinoMain.exId = newId;
        debug("handlePreviousEx: Caricamento esercizio: " + newId);
        setScene(PATHS.SORTINO_EX);
    }
}
