package com.pizzagpt;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    // Variabili globali per stato e configurazione
    public static int playerScore = 0; // Punteggio del giocatore
    public static Label playerScoreLabel; // Etichetta per visualizzare il punteggio
    public static Stage stg; // Stage principale dell'applicazione
    public static Utils util; // Oggetto di utilità
    public static int windowWidth = 1280; // Larghezza finestra predefinita
    public static int windowHeight = 720; // Altezza finestra predefinita
    public static String exId = "1_1"; // ID dell'esercizio corrente
    // da che view parte il programma
    private final String firstViewPath = PATHS.LOGIN_SCENE;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stg = primaryStage; // Imposta lo stage globale
        Utils.setScene(firstViewPath); // Carica la scena principale (Login)
    }

    /**
     * Metodo principale del programma. Lancia l'applicazione JavaFX.
     */
    public static void main(String[] args) {
        launch(); // Avvia l'applicazione JavaFX
    }
}
