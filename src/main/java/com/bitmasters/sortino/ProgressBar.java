package com.bitmasters.sortino;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;


/**
 * Classe per gestire la barra di progresso.
 * Mostra i progressi dell'utente con dei quadrati colorati che si riempiono
 * man mano che l'utente avanza nei vari esercizi.
 */
public class ProgressBar {
    // Stili per i quadrati
    private static final String PROGRESS_COMPLETED = "-fx-fill: #27ae60; -fx-stroke: #1e8449; -fx-stroke-width: 2;";
    private static final String PROGRESS_INCOMPLETE = "-fx-fill: #ecf0f1; -fx-stroke: #bdc3c7; -fx-stroke-width: 2;";

    private final HBox progressBar;

    /**
     * Costruttore della barra di progresso.
     * Inizializza la barra di progresso, creandola con 3 quadrati.
     * @param progressBar Il contenitore HBox che rappresenta la barra di progresso.
     */
    public ProgressBar(HBox progressBar) {
        this.progressBar = progressBar;
        initializeProgressBar();
    }

    /**
     * Imposta la barra di progresso con 3 quadrati (rettangoli) come indicatori di progresso.
     * Ogni quadrato rappresenta una parte del progresso.
     */
    private void initializeProgressBar() {
        progressBar.getChildren().clear();
        int totalSquares = 3;  // Ogni barra ha 3 quadrati

        // Aggiunge 3 quadrati alla barra
        for (int i = 0; i < totalSquares; i++) {
            Rectangle rect = createProgressRectangle();
            progressBar.getChildren().add(rect);
        }
    }


    private Rectangle createProgressRectangle() {
        Rectangle rect = new Rectangle(30, 30, 20, 20);
        rect.setArcWidth(5);  // Angoli arrotondati
        rect.setArcHeight(5);
        rect.setStyle(PROGRESS_INCOMPLETE);  // Stato iniziale
        return rect;
    }


    public void updateProgressBar(int filledSquares) {
        Platform.runLater(() -> {
            for (int i = 0; i < progressBar.getChildren().size(); i++) {
                Node node = progressBar.getChildren().get(i);
                if (node instanceof Rectangle) {
                    // Cambia il colore dei quadrati in base al progresso
                    ((Rectangle) node).setStyle(i < filledSquares ? PROGRESS_COMPLETED : PROGRESS_INCOMPLETE);
                }
            }
        });
    }


}
