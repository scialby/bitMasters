package com.bitmasters;

import com.bitmasters.sortino.SortinoMain;
import com.bitmasters.userSession.User;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

import static com.bitmasters.LoggerManager.debug;

public class Main extends Application {

    // Oggetti
    public static int playerScore = 0; // Punteggio del giocatore
    // Variabili globali per stato e configurazione
    public static Stage stg; // Stage principale dell'applicazione
    public static Utils util;
    public static int defaultWindowWidth = 1280;
    public static int defaultWindowHeight = 720;
    public static int windowWidth = defaultWindowWidth;
    public static int windowHeight = defaultWindowHeight;

    // View iniziale, all'avvio del programma
    private final String firstViewPath = PATHS.LOGIN_SCENE;

    // Utente corrente
    private static User currentUser;

    // Getters
    public static int getUserPoints() {
        return playerScore;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
    // Setters
    public static void setUserPoints(int userPoints) {
        Main.playerScore = userPoints;
        if (Main.playerScore < 0) Main.playerScore = 0;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void restoreWindowSizes() {
        windowWidth = defaultWindowWidth;
        windowHeight = defaultWindowHeight;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stg = primaryStage; // Imposta lo stage globale
        debug("Starting application..."); // Log di avvio
        debug("Avvio della scena principale: " + firstViewPath); // Log di avvio della scena principale

        Utils.setSceneCSS(firstViewPath); // Carica la scena principale (Login)

        // Aggiungi listener per la chiusura
        stg.setOnCloseRequest(event -> {
            debug("chiusura finestra, currentUser: " + currentUser);
            if (currentUser != null) {
                debug("Salvataggio progressi..."+ currentUser);
                if(SortinoMain.getProgressManager() != null) {
                    SortinoMain.getProgressManager().saveProgress();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(); // Avvia l'applicazione JavaFX
    }
}