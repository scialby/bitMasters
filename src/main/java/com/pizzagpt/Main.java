package com.pizzagpt;

import com.pizzagpt.sortino.SortinoLoader;
import com.pizzagpt.Utils;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {




    // Variabili globali per stato e configurazione
    public static int playerScore = 0; // Punteggio del giocatore
    public static Label playerScoreLabel; // Etichetta per visualizzare il punteggio
    public static Stage stg; // Stage principale dell'applicazione
    public static Utils util; // Oggetto di utilità
    public static int windowWidth = 1000; // Larghezza finestra predefinita
    public static int windowHeight = 600; // Altezza finestra predefinita

    // da che view parte il programma
    private String firstViewPath = "/com.pizzagpt/scenes/userSession/MainView.fxml";

    /**
     * Metodo principale che avvia l'applicazione.
     *
     * @param primaryStage Stage principale passato da JavaFX.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        stg = primaryStage; // Imposta lo stage globale
        util.setScene(firstViewPath); // Carica la scena principale (Login)
    }



    /**
     * Metodo principale del programma. Lancia l'applicazione JavaFX.
     */
    public static void main(String[] args) {
        launch(); // Avvia l'applicazione JavaFX
    }
}
