package com.bitmasters.sortino;

import com.bitmasters.Main;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import static com.bitmasters.LoggerManager.debug;

/**
 * Classe responsabile della gestione dell'interfaccia utente degli esercizi.
 * Si occupa di:
 * - Configurazione dei componenti UI
 * - Gestione del layout
 * - Caricamento e visualizzazione immagini
 * - Styling dei componenti
 */
public class ExerciseUI {
    // Costanti di stile per la gestione della dimensione dei pulsanti
    private static final double BUTTON_TEXT_PADDING = 50;
    private static final double BUTTON_HEIGHT_OFFSET = 80;
    private static final String FALLBACK_IMAGE_PATH = "/com/bitmasters/images/sortinoEx1/Image-not-found.png";

    // Componenti UI
    private final ImageView exerciseImage;
    private final Label instructionsLabel;
    private final Button[] choiceButtons;
    private final HBox navigationBox;
    private final HBox choicesBox;

    /**
     * Costruttore che inizializza i componenti UI.
     */
    public ExerciseUI(ImageView exerciseImage, Label instructionsLabel,
                      Button[] choiceButtons, HBox navigationBox, HBox choicesBox) {
        this.exerciseImage = exerciseImage;
        this.instructionsLabel = instructionsLabel;
        this.choiceButtons = choiceButtons;
        this.navigationBox = navigationBox;
        this.choicesBox = choicesBox;
    }

    /**
     * Configura i pulsanti delle scelte con il testo fornito.
     */
    public void configureChoiceButtons(List<String> choices) {
        // Configura ciascun pulsante assegnando il testo corrispondente dal file json
        for (int i = 0; i < choices.size(); i++) {
            configureButton(choiceButtons[i], choices.get(i));
            addButtonSizeListener(choiceButtons[i]);
        }
    }

    /**
     * Configura un singolo pulsante con il testo dato.
     */
    private void configureButton(Button button, String text) {
        button.setText(text);
        button.setWrapText(true);

        // Calcola la larghezza del testo per determinare la larghezza minima del pulsante
        Text tempText = new Text(text);
        tempText.setBoundsType(TextBoundsType.VISUAL);

        double textWidth = tempText.getLayoutBounds().getWidth();
        button.setMinWidth(textWidth + BUTTON_TEXT_PADDING);
        button.setMinHeight(estimateButtonHeight(text, textWidth));
    }

    /**
     * Carica e visualizza l'immagine dell'esercizio.
     */
    public void loadImage(File imageFile) {
        try {
            Image image = new Image(imageFile.toURI().toString());
            double maxWidth = Main.stg.getWidth() - 40;

            exerciseImage.setImage(image);
            exerciseImage.setPreserveRatio(true);
            exerciseImage.setFitWidth(maxWidth);
            exerciseImage.setFitHeight(image.getHeight() * (maxWidth / image.getWidth()));
        } catch (Exception e) {
            handleImageError(e);  // Gestisce eventuali errori di caricamento dell'immagine
        }
    }

    /**
     * Gestisce gli errori di caricamento immagine, mostrando un'immagine di fallback.
     */
    private void handleImageError(Exception e) {
        debug("Errore di caricamento immagine: " + e.getMessage());
        try (InputStream fallbackStream = getClass().getResourceAsStream(FALLBACK_IMAGE_PATH)) {
            // Carica l'immagine di fallback in caso di errore
            Image fallback = new Image(fallbackStream);
            exerciseImage.setImage(fallback);
            exerciseImage.setFitWidth(600);
            exerciseImage.setFitHeight(400);
        } catch (Exception ex) {
            debug("Errore nel caricamento dell'immagine di fallback: " + ex.getMessage());
        }
    }

    /**
     * Imposta il testo delle istruzioni.
     */
    public void setInstructions(String instructions) {
        instructionsLabel.setText(instructions);
    }

    /**
     * Applica lo stile al pulsante selezionato (ad esempio, per risposte corrette/sbagliate).
     */
    public void setButtonFeedback(Button button, boolean isCorrect) {
        button.setStyle("-fx-background-color: #d3d3d3;");  // Applica uno stile base
    }

    /**
     * Stima l'altezza del pulsante in base al testo.
     * La larghezza del pulsante viene usata per calcolare l'altezza necessaria per il testo.
     */
    private double estimateButtonHeight(String text, double textWidth) {
        Text tempText = new Text(text);
        tempText.setWrappingWidth(textWidth);  // Imposta la larghezza massima del testo per il wrapping
        return tempText.getLayoutBounds().getHeight() + BUTTON_HEIGHT_OFFSET;
    }

    /**
     * Aggiunge un listener per l'aggiornamento dinamico del layout quando i pulsanti cambiano dimensione.
     */
    private void addButtonSizeListener(Button button) {
        button.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> updateUILayout());
    }

    /**
     * Aggiorna il layout dell'interfaccia, spostando la navigazione in basso se necessario.
     */
    private void updateUILayout() {
        double maxButtonHeight = calculateMaxButtonHeight();
        double newNavigationY = choicesBox.getLayoutY() + maxButtonHeight + 40;
        navigationBox.setLayoutY(newNavigationY);  // Aggiorna la posizione verticale della navigazione
    }

    /**
     * Calcola l'altezza massima tra tutti i pulsanti delle scelte.
     */
    private double calculateMaxButtonHeight() {
        double maxHeight = 0;
        for (Button button : choiceButtons) {
            maxHeight = Math.max(maxHeight, button.getHeight());
        }
        return maxHeight;
    }
}
