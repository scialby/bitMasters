package com.bitmasters.sortino;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bitmasters.Main;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bitmasters.LoggerManager.debug;


/**
 * Classe per la gestione del salvataggio e del caricamento dei progressi degli utenti.
 * I progressi di tutti gli utenti vengono salvati in un unico file JSON (Users.json).
 */
public class ProgressManager {

    // Percorso del file in cui vengono salvati i progressi di tutti gli utenti.
    private static final String USERS_PROGRESS_FILE = "src/main/java/com/pizzagpt/sortino/Users.json";
    private List<ProgressBar> progressBars;

    // Mapper per la conversione JSON
    private static final ObjectMapper mapper = new ObjectMapper();


    /**
     * Costruttore che accetta 3 HBox e crea una ProgressBar per ciascuno.
     * Aggiorna tutte le progress bar con il valore corrente di SortinoMain.exId.
     *
     */
    public ProgressManager(HBox progressBar1, HBox progressBar2, HBox progressBar3) {
        progressBars = new ArrayList<>();

        // Aggiunge una nuova ProgressBar per ciascun HBox
        progressBars.add(new ProgressBar(progressBar1));
        progressBars.add(new ProgressBar(progressBar2));
        progressBars.add(new ProgressBar(progressBar3));

        // Inizializza le progress bar in base al livello corrente dell'utente
        updateProgressBars();
    }

    /**
     * Restituisce la lista di tutte le ProgressBar gestite.
     *
     */
    public List<ProgressBar> getProgressBars() {
        return progressBars;
    }

    /**
     * Metodo per aggiornare tutte le progress bar con un nuovo esercizio.
     * La funzione aggiorna ciascuna barra di progresso chiamando il metodo updateProgressBar.
     *
     */
    public void updateAllProgressBars(String exId) {
        for (ProgressBar pb : progressBars) {
            pb.updateProgressBar(Integer.parseInt(exId)); // Aggiorna la barra di progresso con l'ID dell'esercizio
        }
    }

    /**
     * Aggiorna tutte le progress bar in base al livello corrente dell'utente.
     * logica:
     * - Il totale dei quadrati da riempire è dato dal currentLevel (massimo 9).
     * - La prima progress bar (barra1) riempie fino a 3 quadrati.
     * - La seconda progress bar riempie i quadrati da 4 a 6.
     * - La terza progress bar riempie i quadrati da 7 a 9.
     */
    public void updateProgressBars() {
        int level = Main.getCurrentUser().getProgress().getCurrentLevel();
        if (level > 9) {
            level = 9;  // Limitiamo il massimo a 9 quadrati
        }

        // Determina quanti quadrati riempire in ciascuna barra
        int fill1 = Math.min(3, level);
        int fill2 = Math.min(3, Math.max(level - 3, 0));
        int fill3 = Math.min(3, Math.max(level - 6, 0));

        // Aggiorna ciascuna progress bar
        progressBars.get(0).updateProgressBar(fill1);
        progressBars.get(1).updateProgressBar(fill2);
        progressBars.get(2).updateProgressBar(fill3);
    }

    /**
     * Salva il progresso di un utente aggiornando il file Users.json.
     * Verifica se il progresso è valido prima di tentare di salvarlo.
     */
    public void saveProgress() {
        UserProgress progress = Main.getCurrentUser().getProgress();
        if (progress == null || Main.getCurrentUser().getUsername() == null || Main.getCurrentUser().getUsername().isEmpty()) {
            System.err.println("Progresso non valido, impossibile salvare.");
            return;
        }

        // Carica l'attuale mappa dei progressi
        Map<String, UserProgress> progressMap = loadAllProgress();

        // Aggiorna (o aggiunge) il progresso per lo specifico utente
        progressMap.put(Main.getCurrentUser().getUsername(), progress);

        // Salva l'intera mappa nel file
        File file = new File(USERS_PROGRESS_FILE);
        try {
            // Scrive i progressi nel file JSON
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, progressMap);
            System.out.println("Progresso salvato per l'utente: " + Main.getCurrentUser().getUsername());
            System.out.println("Percorso file: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio dei progressi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carica il progresso di un utente specifico leggendo il file Users.json.
     * Se l'utente non ha un progresso salvato, ne crea uno nuovo.
     *
     */
    public static UserProgress loadProgress(String username) {
        if (username == null || username.isEmpty()) {
            System.err.println("Username non valido per il caricamento dei progressi.");
            return null;
        }

        // Carica l'intera mappa dei progressi
        Map<String, UserProgress> progressMap = loadAllProgress();

        if (progressMap.containsKey(username)) {
            // Se l'utente ha progressi salvati, restituisce il relativo progresso
            System.out.println("Progresso caricato per l'utente: " + username);
            return progressMap.get(username);
        } else {
            // Se l'utente non ha progressi salvati, ne crea uno nuovo
            System.out.println("Nessun progresso trovato per " + username + ". Viene creato un nuovo progresso.");
            return new UserProgress(username);
        }
    }

    /**
     * Metodo  per caricare l'intera mappa dei progressi da Users.json.
     * Restituisce una mappa con gli username come chiave e i progressi come valore.
     *
     * @return Una mappa con username come chiave e UserProgress come valore.
     */
    private static Map<String, UserProgress> loadAllProgress() {
        File file = new File(USERS_PROGRESS_FILE);
        Map<String, UserProgress> progressMap = new HashMap<>();

        // Se il file esiste, carica i progressi dal file JSON
        if (file.exists()) {
            try {
                progressMap = mapper.readValue(file, new TypeReference<Map<String, UserProgress>>() {});
                System.out.println("Progressi caricati da: " + file.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Errore nel caricamento dei progressi: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            // Se il file non esiste, lo segnala e lo crea al momento del salvataggio
            debug("Il file " + file.getAbsolutePath() + " non esiste ancora. Verrà creato al salvataggio.");
        }
        return progressMap;
    }
}
