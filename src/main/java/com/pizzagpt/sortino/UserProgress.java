package com.pizzagpt.sortino;

import java.util.*;

import static com.pizzagpt.LoggerManager.debug;

/**
 * Classe per la gestione dei progressi dell'utente.
 * Tiene traccia degli esercizi completati, dei tentativi e del livello raggiunto.
 */
public class UserProgress {
    private int attempts = 0;      // Numero di tentativi
    private int currentLevel = 1;  // Livello  raggiunto dall'utente
    private Map<Integer, Set<String>> completedExercises; // Mappa degli esercizi completati per ogni livello

    // Imposta il livello iniziale a 1 e inizializza la mappa degli esercizi completati.
    public UserProgress() {
        this.completedExercises = new HashMap<>();
    }

    // crea un oggetto UserProgress associato a un utente.
    public UserProgress(String username) {
        this.completedExercises = new HashMap<>();
    }

    // Ritorna il numero di tentativi effettuati.
    public int getAttempts() {
        return attempts;
    }

    // Imposta il numero di tentativi effettuati.
    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    // Ritorna il livello corrente raggiunto dall'utente.
    public int getCurrentLevel() {
        return currentLevel;
    }

    // Imposta il livello corrente dell'utente.
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    // Aggiunge un tentativo e registra l'esercizio completato per il livello corrente.
    public void addAttempt(String exerciseId) {
        setAttempts(getAttempts() + 1);

        // Aggiungi l'esercizio come completato nel livello corrente
        int level = Integer.parseInt(exerciseId.split("_")[0]);
        completedExercises.computeIfAbsent(level, k -> new HashSet<>()).add(exerciseId);
    }

    // Ritorna la lista degli esercizi completati per un determinato livello.
    public List<String> getCompletedExercises(int level) {
        Set<String> completed = completedExercises.getOrDefault(level, new HashSet<>());
        debug("N. Esercizi completati del livello: " + completed.size());
        return new ArrayList<>(completed);  // Converte il Set in una List
    }

    // Resetta gli esercizi completati, svuotando la mappa.
    public void resetCompletedExercises() {
        completedExercises.clear();  // Svuota completamente la mappa, resettando tutti i livelli
        System.out.println("Tutti gli esercizi completati sono stati resettati.");
    }

    // Verifica se l'utente ha completato tutti gli esercizi di un determinato livello.
    public boolean hasCompletedLevel(int level) {
        Set<String> completed = completedExercises.getOrDefault(level, new HashSet<>());
        // Assumendo che il numero massimo di esercizi per livello sia 3
        return completed.size() == 3;
    }

    // Ritorna true se l'utente ha completato l'esercizio specificato.
    public boolean hasCompletedExercise(String exerciseId) {
        int level = Integer.parseInt(exerciseId.split("_")[0]);
        return completedExercises.getOrDefault(level, new HashSet<>()).contains(exerciseId);
    }
}
