package com.bitmasters.sortino;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bitmasters.Main;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bitmasters.LoggerManager.debug;

public class ProgressManager {

    private static final String USERS_PROGRESS_FILE = "Users.json";
    private List<ProgressBar> progressBars;

    private static final ObjectMapper mapper = new ObjectMapper();

    public ProgressManager(HBox progressBar1, HBox progressBar2, HBox progressBar3) {
        progressBars = new ArrayList<>();

        progressBars.add(new ProgressBar(progressBar1));
        progressBars.add(new ProgressBar(progressBar2));
        progressBars.add(new ProgressBar(progressBar3));

        updateProgressBars();
    }

    public List<ProgressBar> getProgressBars() {
        return progressBars;
    }

    public void updateAllProgressBars(String exId) {
        for (ProgressBar pb : progressBars) {
            pb.updateProgressBar(Integer.parseInt(exId));
        }
    }

    public void updateProgressBars() {
        int level = Main.getCurrentUser().getProgress().getCurrentLevel();
        if (level > 9) {
            level = 9;
        }

        int fill1 = Math.min(3, level);
        int fill2 = Math.min(3, Math.max(level - 3, 0));
        int fill3 = Math.min(3, Math.max(level - 6, 0));

        progressBars.get(0).updateProgressBar(fill1);
        progressBars.get(1).updateProgressBar(fill2);
        progressBars.get(2).updateProgressBar(fill3);
    }

    public void saveProgress() {
        debug("Avvio del metodo saveProgress()");

        UserProgress progress = Main.getCurrentUser().getProgress();
        debug("Progress ottenuto: " + progress);

        String username = Main.getCurrentUser().getUsername();
        debug("Username ottenuto: " + username);

        if (progress == null || username == null || username.isEmpty()) {
            System.err.println("Progresso non valido, impossibile salvare.");
            debug("Progresso o username non valido. Uscita dal metodo saveProgress().");
            return;
        }

        // Carica i progressi esistenti
        debug("Caricamento dei progressi esistenti...");
        Map<String, UserProgress> progressMap = loadAllProgress();
        debug("Progressi esistenti caricati: " + progressMap);

        // Aggiorna o aggiungi il progresso per l'utente corrente
        progressMap.put(username, progress);
        debug("Progresso aggiornato per l'utente: " + username);

        // Salva il file in una directory esterna (ad esempio nella directory di lavoro dell'app)
        File externalFile = new File(System.getProperty("user.dir"), USERS_PROGRESS_FILE);
        debug("Percorso del file di salvataggio: " + externalFile.getAbsolutePath());

        try {
            debug("Inizio scrittura del file...");
            mapper.writerWithDefaultPrettyPrinter().writeValue(externalFile, progressMap);
            System.out.println("Progresso salvato per l'utente: " + username);
            debug("Progresso salvato correttamente per l'utente: " + username);
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio dei progressi: " + e.getMessage());
            debug("Errore nel salvataggio dei progressi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static UserProgress loadProgress(String username) {
        debug("Avvio del metodo loadProgress() con username: " + username);

        if (username == null || username.isEmpty()) {
            System.err.println("Username non valido per il caricamento dei progressi.");
            debug("Username non valido. Uscita dal metodo loadProgress().");
            return null;
        }

        Map<String, UserProgress> progressMap = loadAllProgress();
        debug("Progressi caricati: " + progressMap);

        if (progressMap.containsKey(username)) {
            System.out.println("Progresso caricato per l'utente: " + username);
            debug("Progresso trovato per l'utente: " + username);
            return progressMap.get(username);
        } else {
            System.out.println("Nessun progresso trovato per " + username + ". Viene creato un nuovo progresso.");
            debug("Nessun progresso trovato. Creazione di un nuovo progresso per l'utente: " + username);
            return new UserProgress(username);
        }
    }

    private static Map<String, UserProgress> loadAllProgress() {
        debug("Avvio del metodo loadAllProgress()");
        Map<String, UserProgress> progressMap = new HashMap<>();

        File externalFile = new File(System.getProperty("user.dir"), USERS_PROGRESS_FILE);
        try (InputStream is = externalFile.exists() ? externalFile.toURI().toURL().openStream() : null) {
            if (is != null) {
                debug("Risorsa " + USERS_PROGRESS_FILE + " trovata. Lettura del contenuto...");
                String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                debug("Contenuto del file letto: " + content);

                if (!content.trim().isEmpty()) {
                    progressMap = mapper.readValue(content, new TypeReference<Map<String, UserProgress>>() {});
                    System.out.println("Progressi caricati dalla risorsa: " + USERS_PROGRESS_FILE);
                    debug("Progressi caricati correttamente da " + USERS_PROGRESS_FILE);
                } else {
                    System.out.println("La risorsa " + USERS_PROGRESS_FILE + " è vuota, verrà usata una mappa vuota.");
                    debug("La risorsa " + USERS_PROGRESS_FILE + " è vuota.");
                }
            } else {
                System.out.println("La risorsa " + USERS_PROGRESS_FILE + " non esiste ancora. Verrà creata al salvataggio.");
                debug("La risorsa " + USERS_PROGRESS_FILE + " non trovata.");
            }
        } catch (IOException e) {
            System.err.println("Errore nel caricamento dei progressi: " + e.getMessage());
            debug("Errore nel caricamento dei progressi: " + e.getMessage());
            e.printStackTrace();
        }

        debug("Ritorno della mappa dei progressi: " + progressMap);
        return progressMap;
    }
}
